package cn.ucai.fulisenter.model.net;

import android.content.Context;

import cn.ucai.fulisenter.model.bean.User;
import cn.ucai.fulisenter.model.utils.OkUtils;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public interface IModelLogin extends IModeBase {
    void login(Context context, String userName, String password, OkUtils.OnCompleteListener<User> listener);
}
