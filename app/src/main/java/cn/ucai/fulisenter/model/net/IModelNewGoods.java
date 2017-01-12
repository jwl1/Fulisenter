package cn.ucai.fulisenter.model.net;

import android.content.Context;

import cn.ucai.fulisenter.model.bean.NewGoodsBean;
import cn.ucai.fulisenter.model.ustils.OkHttpUtils;

/**
 * Created by MTJ on 2017/1/11.
 */

public interface IModelNewGoods {
    void downData(Context context, int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener);
}
