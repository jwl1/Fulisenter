package cn.ucai.fulisenter.model.net;

import android.content.Context;

import cn.ucai.fulisenter.application.I;
import cn.ucai.fulisenter.model.bean.BoutiqueBean;
import cn.ucai.fulisenter.model.ustils.OkHttpUtils;

/**
 * Created by MTJ on 2017/1/11.
 */

public class ModelBoutique implements IModelNewBoutique {
    @Override
    public void downData(Context context, OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);

    }
}
