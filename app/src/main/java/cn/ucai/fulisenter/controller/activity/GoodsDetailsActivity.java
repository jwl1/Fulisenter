package cn.ucai.fulisenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.application.FuLiCenterApplication;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.AlbumsBean;
import cn.ucai.fulisenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulisenter.model.bean.MessageBean;
import cn.ucai.fulisenter.model.bean.User;
import cn.ucai.fulisenter.model.net.IModelGoodes;
import cn.ucai.fulisenter.model.net.IModelUser;
import cn.ucai.fulisenter.model.net.ModeUser;
import cn.ucai.fulisenter.model.net.ModelGoodes;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.utils.CommonUtils;
import cn.ucai.fulisenter.view.FlowIndicator;
import cn.ucai.fulisenter.view.MFGT;
import cn.ucai.fulisenter.view.SlideAutoLoopView;

public class GoodsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.iv_cart)
    ImageView ivCart;
    @BindView(R.id.tv_GoodsEnlishName)
    TextView tvGoodsEnlishName;
    @BindView(R.id.tv_GoodName)
    TextView tvGoodName;
    @BindView(R.id.tv_GoodsPich)
    TextView tvGoodsPich;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    FlowIndicator indicator;

    IModelGoodes mModle;
    IModelUser modelUser;
    int goodId;
    @BindView(R.id.wv)
    WebView wv;
    boolean isCollect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        goodId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, I.CAT_ID);
       // L.e("main","mgoodid"+goodId);
        if (goodId == I.CAT_ID) {
          //  L.e("main","000");
            MFGT.finish(this);
        } else {
            initData();
        }
    }

    private void initData() {
        mModle = new ModelGoodes();
        mModle.downloadList(this, goodId, new OnCompletionListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showGoodDetails(result);
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private void showGoodDetails(GoodsDetailsBean result) {
        tvGoodName.setText(result.getGoodsName());
        tvGoodsEnlishName.setText(result.getGoodsEnglishName());
        tvGoodsPich.setText(result.getCurrencyPrice());
        salv.startPlayLoop(indicator,getAlbumUrl(result),getAlbumCount(result));
        wv.loadDataWithBaseURL(null,result.getGoodsBrief(),"text/txt",I.UTF_8,null);
    }

    private int getAlbumCount(GoodsDetailsBean goods) {
        if (goods != null && goods.getProperties() != null && goods.getProperties().length > 0) {
            return goods.getProperties()[0].getAlbums().length;

        }
        return 0;
    }

    private String[] getAlbumUrl(GoodsDetailsBean goods) {
        if (goods != null && goods.getProperties() != null && goods.getProperties().length > 0) {
            AlbumsBean[] albums = goods.getProperties()[0].getAlbums();
            if (albums != null && albums.length > 0) {
                String[] urls = new String[albums.length];
                for(int i=0;i<albums.length;i++) {
                    urls[i] = albums[i].getImgUrl();
                }
                return urls;
            }
        }
        return new String[0];
    }

    @OnClick({R.id.iv_back, R.id.iv_share, R.id.iv_collect, R.id.iv_cart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                MFGT.finish(this);
                break;
            case R.id.iv_share:
                break;
            case R.id.iv_collect:
                setCollectListener();
                break;
            case R.id.iv_cart:
                setCart();
                break;
        }
    }

    private void setCart() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            modelUser = new ModeUser();
            modelUser.updateCart(this, I.ACTION_CART_ADD, user.getMuserName(), goodId, 1, 0,
                    new OnCompletionListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null) {
                                CommonUtils.showLongToast(R.string.add_goods_success);
                            }
                        }

                        @Override
                        public void onError(String error) {
                        CommonUtils.showLongToast("失败");
                        }
                    });
        }else {
            MFGT.gotoLogin(this);
        }

    }

    private void setCollectListener() {
        ivCollect.setEnabled(false);
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            setCollect(user);
        }else {
            MFGT.gotoLogin(this);
            ivCollect.setEnabled(true);
        }
    }

    private void setCollect(User user) {

        mModle.setCollect(this, goodId, user.getMuserName(),
                isCollect ? I.ACTION_DELETE_COLLECT : I.ACTION_ADD_COLLECT,
                new OnCompletionListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollect = !isCollect;
                            setCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                            sendBroadcast(new Intent(I.BROADCAST_UPDATA_COLLECT).putExtra(I.Collect.GOODS_ID,goodId));
                        }
                    }

                    @Override
                    public void onError(String error) {
                        ivCollect.setEnabled(true);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCollectStatus();
    }

    private void setCollectStatus() {
        if (isCollect) {
            ivCollect.setImageResource(R.mipmap.bg_collect_out);
        }else {
            ivCollect.setImageResource(R.mipmap.bg_collect_in);
        }
        ivCollect.setEnabled(true);
    }

    private void initCollectStatus() {
        ivCollect.setEnabled(false);
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            mModle.isCollect(this, goodId, user.getMuserName(), new OnCompletionListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollect = true;
                    }else {
                        isCollect=false;
                    }
                    setCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollect=false;
                    setCollectStatus();
                }
            });
        }
    }
}
