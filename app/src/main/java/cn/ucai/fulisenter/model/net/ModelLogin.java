package cn.ucai.fulisenter.model.net;

import android.content.Context;

import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.User;
import cn.ucai.fulisenter.model.utils.OkUtils;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class ModelLogin extends ModeBase implements IModelLogin {
    @Override
    public void login(Context context, String userName, String password, OkUtils.OnCompleteListener<User> listener) {
        OkUtils<User> utils = new OkUtils<>(context);
        utils.url(I.SERVER_ROOT+I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,userName)
                .addParam(I.User.PASSWORD,password)
                .targetClass(User.class)
                .execute(listener);
    }
}
