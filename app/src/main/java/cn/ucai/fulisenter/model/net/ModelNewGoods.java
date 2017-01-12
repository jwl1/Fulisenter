package cn.ucai.fulisenter.model.net;

import android.content.Context;

import cn.ucai.fulisenter.application.I;
import cn.ucai.fulisenter.model.bean.NewGoodsBean;
import cn.ucai.fulisenter.model.ustils.OkHttpUtils;

/**
 * Created by MTJ on 2017/1/11.
 */

public class ModelNewGoods implements IModelNewGoods {
    @Override
    public void downData(Context context, int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(catId))
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
}
