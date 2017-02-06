package cn.ucai.fulisenter.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.adapter.CollectAdapter;
import cn.ucai.fulisenter.controller.application.FuLiCenterApplication;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.CollectBean;
import cn.ucai.fulisenter.model.bean.User;
import cn.ucai.fulisenter.model.net.IModelUser;
import cn.ucai.fulisenter.model.net.ModeUser;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.utils.CommonUtils;
import cn.ucai.fulisenter.model.utils.ConvertUtils;
import cn.ucai.fulisenter.model.utils.L;
import cn.ucai.fulisenter.model.utils.SpaceItemDecoration;

public class ColletactActivity extends AppCompatActivity {
    private static final String TAG = ColletactActivity.class.getSimpleName();

    @BindView(R.id.rlv_collect)
    RecyclerView rlvCollect;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    IModelUser model;
    int pageId = 1;
    GridLayoutManager gm;
    CollectAdapter mAdapter;
    @BindView(R.id.tv_refreshing)
    TextView tvRefreshing;

    UpdateCollectReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colletact);
        ButterKnife.bind(this);
        User user = FuLiCenterApplication.getUser();
        mReceiver = new UpdateCollectReceiver();
        if (user == null) {
            finish();
        }else {
            initView();
            initData(I.ACTION_DOWNLOAD);
            setPullDownListener();
            setPullUpListener();
            setReceiverListener();
        }
    }

    private void setReceiverListener() {
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATA_COLLECT);
        registerReceiver(mReceiver,filter);

    }

    private void initData(final int action) {
        model = new ModeUser();
        model.getcollect(this, FuLiCenterApplication.getUser().getMuserName(), pageId, I.PAGE_SIZE_DEFAULT, new OnCompletionListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                srl.setRefreshing(false);
                tvRefreshing.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    }else {
                        mAdapter.addData(list);
                    }
                    if (list.size() < I.PAGE_ID_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                }
                else {
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                srl.setRefreshing(false);
                tvRefreshing.setVisibility(View.GONE);
                mAdapter.setMore(false);
                CommonUtils.showLongToast(error);
                L.e(TAG, "err0r=" + error);
            }
        });
    }

    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)

        );
        gm = new GridLayoutManager(this, I.COLUM_NUM);
        rlvCollect.addItemDecoration(new SpaceItemDecoration(12));
        rlvCollect.setLayoutManager(gm);
        rlvCollect.setHasFixedSize(true);
        mAdapter = new CollectAdapter(new ArrayList<CollectBean>(), this);
        rlvCollect.setAdapter(mAdapter);
    }

    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefreshing.setVisibility(View.VISIBLE);
                pageId = 1;
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }
    private void setPullUpListener(){
        rlvCollect.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPostion = gm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPostion == mAdapter.getItemCount() - 1
                        && mAdapter.isMore()) {
                    pageId++;
                    initData(I.ACTION_PULL_UP);
                }
            }
        });
    }

    class UpdateCollectReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int goodsId = intent.getIntExtra(I.Collect.GOODS_ID,0);
            L.e(TAG,"onReceive,goodIs="+goodsId);
            mAdapter.removeItem(goodsId);
        }
    }

    @OnClick(R.id.iv_collect_back)
    public void onClick() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }
}
