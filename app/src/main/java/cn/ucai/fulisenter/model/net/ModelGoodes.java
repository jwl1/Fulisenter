package cn.ucai.fulisenter.model.net;

import android.content.Context;

import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulisenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public class ModelGoodes implements IModelGoodes {
    @Override
    public void downloadList(Context context, int goodsId, OnCompletionListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,""+goodsId)
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }
}
