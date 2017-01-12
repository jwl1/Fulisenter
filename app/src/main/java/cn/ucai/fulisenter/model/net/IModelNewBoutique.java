package cn.ucai.fulisenter.model.net;

import android.content.Context;

import cn.ucai.fulisenter.model.bean.BoutiqueBean;

/**
 * Created by MTJ on 2017/1/11.
 */

public interface IModelNewBoutique {
    void downData(Context context, OnCompleteListener<BoutiqueBean[]> listener);
}
