package cn.ucai.fulisenter.model.net;

import android.content.Context;
import android.widget.ImageView;

import cn.ucai.fulisenter.model.bean.NewGoodsBean;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public interface IModelContact extends IModeBase{
    void downloadNewgoodsList(Context context, String goodsImg, int defaultPicture, ImageView imageView, OnCompletionListener<NewGoodsBean[]> listener);
}
