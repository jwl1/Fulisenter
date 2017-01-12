package cn.ucai.fulisenter.model.net;

import android.content.Context;
import android.widget.ImageView;

import cn.ucai.fulisenter.model.bean.NewGoodsBean;
import cn.ucai.fulisenter.model.utils.OkUtils;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class ModelContact extends ModelBaseImage implements IModelContact {
    @Override
    public void downloadNewgoodsList(Context context, String goodsImg, int defaultPicture, ImageView imageView, OnCompletionListener<NewGoodsBean[]> listener) {
        OkUtils<NewGoodsBean[]> utils = new OkUtils<>(context);
        /*utils.url(I.SERVER_ROOT+I.REQUEST_DOWNLOAD_IMAGE)
                .addParam(I.IMAGE_URL,goodsImg)
                .*/
    }
}
