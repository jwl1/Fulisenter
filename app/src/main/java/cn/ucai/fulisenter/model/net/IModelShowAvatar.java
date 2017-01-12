package cn.ucai.fulisenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulisenter.model.bean.BoutiqueBean;
import cn.ucai.fulisenter.model.bean.MessageBean;
import cn.ucai.fulisenter.model.bean.NewGoodsBean;
import cn.ucai.fulisenter.model.utils.OkUtils;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public interface IModelShowAvatar extends IModeBase {
    void showAvatar(Context context, String userName, File file, OkUtils.OnCompleteListener<MessageBean> listener);
    void showNewGoodsAvatar(Context context, String NewGoodsName, File file, OkUtils.OnCompleteListener<NewGoodsBean> listener);
    void showBoutiqueAvatar(Context context, String BoutiqueName, File file, OkUtils.OnCompleteListener<BoutiqueBean> listener);
}
