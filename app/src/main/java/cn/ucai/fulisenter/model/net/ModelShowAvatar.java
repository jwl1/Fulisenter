package cn.ucai.fulisenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.BoutiqueBean;
import cn.ucai.fulisenter.model.bean.MessageBean;
import cn.ucai.fulisenter.model.bean.NewGoodsBean;
import cn.ucai.fulisenter.model.utils.OkUtils;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class ModelShowAvatar extends ModeBase implements IModelShowAvatar {
    @Override
    public void showAvatar(Context context, String userName, File file, OkUtils.OnCompleteListener<MessageBean> listener) {
        OkUtils<MessageBean> utils = new OkUtils<>(context);
        utils.url(I.DOWNLOAD_AVATAR_URL+userName)
                .targetClass(MessageBean.class)
                .downloadFile(file)
                .execute(listener);
    }

    @Override
    public void showNewGoodsAvatar(Context context, String NewGoodsName, File file, OkUtils.OnCompleteListener<NewGoodsBean> listener) {
        OkUtils<NewGoodsBean> utils = new OkUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .targetClass(NewGoodsBean.class)
                .downloadFile(file)
                .execute(listener);
    }

    @Override
    public void showBoutiqueAvatar(Context context, String BoutiqueName, File file, OkUtils.OnCompleteListener<BoutiqueBean> listener) {

    }
}
