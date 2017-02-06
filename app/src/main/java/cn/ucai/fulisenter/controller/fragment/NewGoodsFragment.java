package cn.ucai.fulisenter.controller.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.adapter.NewGoodsAdapter;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.ModelNewGoods;
import cn.ucai.fulisenter.model.bean.NewGoodsBean;
import cn.ucai.fulisenter.model.net.IModelNewGoods;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.utils.ConvertUtils;

import static cn.ucai.fulisenter.controller.application.I.ACTION_DOWNLOAD;
import static cn.ucai.fulisenter.controller.application.I.ACTION_PULL_DOWN;
import static cn.ucai.fulisenter.controller.application.I.ACTION_PULL_UP;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    @BindView(R.id.srlNewGoods)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tvRefreshHint)
    TextView mtvRefreshHint;
    @BindView(R.id.rvNewGoods)
    RecyclerView rvNewGoods;

    ArrayList<NewGoodsBean> List;
    Unbinder unbinder;
    NewGoodsAdapter mAdapter;
    GridLayoutManager mLayoutManager;
    IModelNewGoods mModel;
    int mPageId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        mModel =  new ModelNewGoods();
        ButterKnife.bind(this,layout);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        setPullUpListener();
        setPullDownListener();
    }
    private void setPullUpListener() {
        rvNewGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mAdapter.setDragging(newState == RecyclerView.SCROLL_STATE_DRAGGING);
                int lastPosition = mLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == mAdapter.getItemCount() - 1 && mAdapter.isMore()) {
                    mPageId++;
                  //  L.e("main","downloadNewgoodsListstart");
                    downloadNewgoodsList(I.ACTION_PULL_UP,mPageId);
                 //   L.e("main","downloadNewgoodsListend");
                }
            }
        });
    }

    private void setPullDownListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mtvRefreshHint.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setRefreshing(true);
                mPageId=1;
                downloadNewgoodsList(I.ACTION_PULL_DOWN,mPageId);
            }
        });
    }
    private void initData() {
        mPageId = 1;
        downloadNewgoodsList(ACTION_DOWNLOAD,mPageId);
    }

    private void downloadNewgoodsList(final int action, int pageId) {
        int catId = getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID,I.CAT_ID);
        mModel.downloadContactList(getContext(), catId, pageId, new OnCompletionListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
              //  L.e("main","result.length="+result.length);
                if (result != null && result.length > 0) {
                  //  Log.e("main","111");
                        ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                        switch (action) {
                            case ACTION_DOWNLOAD:
                                mAdapter.initData(list);
                                break;
                            case ACTION_PULL_DOWN:
                                mSwipeRefreshLayout.setRefreshing(false);
                                mtvRefreshHint.setVisibility(View.GONE);
                                mAdapter.initData(list);
                                break;
                            case ACTION_PULL_UP:
                                mAdapter.addData(list);
                                break;

                        }
                    if (result.length<10){
                        mAdapter.setMore(false);
                    }else{
                        mAdapter.setMore(true);
                    }
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initView() {
        List  = new ArrayList<>();
        mAdapter = new NewGoodsAdapter(List,getContext(),mModel);
        mLayoutManager = new GridLayoutManager(getContext(),I.COLUM_NUM);
        rvNewGoods.setLayoutManager(mLayoutManager);
        rvNewGoods.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // unbinder.unbind();
    }

    public void sortGoods(int sortBy) {
        mAdapter.sortGoods(sortBy);
    }
}
