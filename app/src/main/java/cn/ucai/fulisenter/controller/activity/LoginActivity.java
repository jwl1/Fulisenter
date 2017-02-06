package cn.ucai.fulisenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.Result;
import cn.ucai.fulisenter.model.bean.User;
import cn.ucai.fulisenter.model.dao.UserDao;
import cn.ucai.fulisenter.model.net.IModelUser;
import cn.ucai.fulisenter.model.net.ModeUser;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.net.SharedPrefrenceUtils;
import cn.ucai.fulisenter.model.utils.CommonUtils;
import cn.ucai.fulisenter.model.utils.ResultUtils;
import cn.ucai.fulisenter.view.MFGT;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.etUserName)
    EditText mUserName;
    @BindView(R.id.etpassword)
    EditText mPassword;
    IModelUser model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_login_back, R.id.btnLogin, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                MFGT.finish(this);
                break;
            case R.id.btnLogin:
                checkInput();
                break;
            case R.id.btnRegister:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkInput() {
        String username = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            mUserName.setError(getString(R.string.user_name_connot_be_empty));
            mUserName.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.password_connot_be_empty));
            mPassword.requestFocus();
        }else {
            login(username,password);
        }
    }
    private void login(final String username, String password){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
        model = new ModeUser();
        model.login(this, username, password, new OnCompletionListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s,User.class);
                  //  L.e(TAG,"result="+result);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            User user = (User) result.getRetData();
                            boolean savaUser = UserDao.getInstance().savaUser(user);
                            if (savaUser) {
                                SharedPrefrenceUtils.getInstance(LoginActivity.this).saveUser(username);
                             //   L.e(TAG,"savaUser="+savaUser);
                            }
                           // L.e(TAG,"savaUser="+savaUser);
                           // SharedPrefrenceUtils.getInstance(LoginActivity.this).saveUser(user.getMuserName());
                            CommonUtils.showLongToast("Login");
                             MFGT.finish(LoginActivity.this);
                        }else {
                            if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_unknow_user));
                            }
                            if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_error_password));
                            }
                        }
                    }else {
                        CommonUtils.showLongToast(getString(R.string.login_fail));
                    }
                }else {
                    CommonUtils.showLongToast(getString(R.string.login_fail));
                }
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                dialog.dismiss();
              //  L.e(TAG,"error="+error);
                CommonUtils.showLongToast(error);
            }
        });
    }
}
