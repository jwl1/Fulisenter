package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulisenter.model.bean.BoutiqueBean;

import cn.ucai.fulisenter.model.net.IModelNewBoutique;

import cn.ucai.fulisenter.model.net.ModelBoutique;

import cn.ucai.fulisenter.model.net.OnCompleteListener;

import cn.ucai.fulisenter.model.ustils.ConvertUtils;
import cn.ucai.fulisenter.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {
    static final int ACTION_DOWNLOAD = 0;//下载首页
    static final int ACTION_PULL_DOWN = 1;//下拉刷新

    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.mRw)
    RecyclerView mRw;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    LinearLayoutManager gm;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList = new ArrayList<>();
    IModelNewBoutique mModel;
    int pageId = 1;

    public BoutiqueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, layout);
        initView();
        mModel = new ModelBoutique();
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        setPullDownListener();
    }


    //下拉
    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                downloadContactLiset(ACTION_PULL_DOWN, pageId);
            }
        });
    }

    private void initData() {
        pageId = 1;
        downloadContactLiset(ACTION_DOWNLOAD, pageId);
    }

    private void downloadContactLiset(final int action, int PageId) {
        mModel.downData(getContext(), new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                switch (action) {
                    case ACTION_DOWNLOAD:
                        mAdapter.initData(list);
                        break;
                    case ACTION_PULL_DOWN:
                        srl.setRefreshing(false);
                        tvRefresh.setVisibility(View.GONE);
                        mAdapter.initData(list);
                        break;
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

            }

        });

    }


    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_red));
        gm = new LinearLayoutManager(getContext());
        mRw.setLayoutManager(gm);
        mRw.setHasFixedSize(true);
        mAdapter = new BoutiqueAdapter(getContext(), mList);
        mRw.setAdapter(mAdapter);
        mRw.addItemDecoration(new SpaceItemDecoration(20));
    }

}
