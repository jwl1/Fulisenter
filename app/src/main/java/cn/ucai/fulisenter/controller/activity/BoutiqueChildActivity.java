package cn.ucai.fulisenter.controller.activity;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.application.I;
import cn.ucai.fulisenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulisenter.model.bean.NewGoodsBean;
import cn.ucai.fulisenter.model.net.IModelNewGoods;
import cn.ucai.fulisenter.model.net.ModelNewGoods;
import cn.ucai.fulisenter.model.net.OnCompleteListener;
import cn.ucai.fulisenter.model.ustils.ConvertUtils;
import cn.ucai.fulisenter.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueChildActivity extends AppCompatActivity{
    static final int ACTION_DOWNLOAD = 0;//下载首页
    static final int ACTION_PULL_DOWN = 1;//下拉刷新
    static final int ACTION_PULL_UP = 2;//上拉加载

    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.mRw)
    RecyclerView mRw;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    GridLayoutManager gm;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList = new ArrayList<>();
    IModelNewGoods mModel;
    int pageId = 1;

    public BoutiqueChildActivity() {
        // Required empty public constructor
    }

   @Override
   public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
       super.onCreate(savedInstanceState, persistentState);
       setContentView(R.layout.activity_boutique_child);
       ButterKnife.bind(this);

       initView();
       mModel = new ModelNewGoods();
       initData(I.ACTION_DOWNLOAD);
       setPullDownListener();
       setPullUpListener();
   }



    //上拉
    private void setPullUpListener() {
        mRw.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mAdapter.setDragging(newState == RecyclerView.SCROLL_STATE_DRAGGING);
                int lastPosition = gm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mAdapter.isMore() && lastPosition == mAdapter.getItemCount() - 1) ;
                pageId++;
                downloadContactLiset(ACTION_PULL_UP, pageId);
            }
        });

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

    private void initData(int actionDownload) {
        pageId = 1;
        downloadContactLiset(ACTION_DOWNLOAD, pageId);
    }

    private void downloadContactLiset(final int action, int PageId) {
     int caId=  getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID,I.CAT_ID);

        mModel.downData(this, caId, pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mAdapter.setMore(result != null && result.length > 0);
                if (!mAdapter.isMore()) {
                    if (action == ACTION_PULL_UP) {
                        mAdapter.setFooter("没有更多数据了/(ㄒoㄒ)");
                    }
                    return;
                }
                mAdapter.setFooter("加载更多数据O(∩_∩)");
                ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                switch (action) {
                    case ACTION_DOWNLOAD:
                        mAdapter.initData(list);
                        break;
                    case ACTION_PULL_DOWN:
                        srl.setRefreshing(false);
                        tvRefresh.setVisibility(View.GONE);
                        mAdapter.initData(list);
                        break;
                    case ACTION_PULL_UP:
                        mAdapter.addData(list);
                        break;
                }
            }

            @Override
            public void onError(String error) {


            }
        });
    }


    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_red));
        gm = new GridLayoutManager(this, I.COLUM_NUM);
        mRw.setLayoutManager(gm);
        mRw.setHasFixedSize(true);
        mAdapter = new GoodsAdapter(this, mList);
        mRw.setAdapter(mAdapter);
        mRw.addItemDecoration(new SpaceItemDecoration(20));

    }


}
