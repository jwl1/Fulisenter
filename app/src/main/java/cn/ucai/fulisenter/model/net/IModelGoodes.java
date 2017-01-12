package cn.ucai.fulisenter.model.net;

import android.content.Context;

import cn.ucai.fulisenter.model.bean.GoodsDetailsBean;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public interface IModelGoodes {
    void downloadList(Context context, int goodsId, OnCompletionListener<GoodsDetailsBean> listener);
}
