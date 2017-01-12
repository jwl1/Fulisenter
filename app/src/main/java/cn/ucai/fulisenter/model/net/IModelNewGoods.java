package cn.ucai.fulisenter.model.net;

import android.content.Context;
import android.widget.ImageView;

import cn.ucai.fulisenter.model.bean.NewGoodsBean;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public interface IModelNewGoods {
    void downloadContactList(Context context, int cartIn, int pageId, OnCompletionListener<NewGoodsBean[]> listener);
    void downloadnewgoodsImage(Context context, String imageurl, int defaultPictureId, ImageView imageView);
}
