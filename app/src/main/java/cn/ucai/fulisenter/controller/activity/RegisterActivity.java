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
import cn.ucai.fulisenter.model.bean.Result;
import cn.ucai.fulisenter.model.net.IModelUser;
import cn.ucai.fulisenter.model.net.ModeUser;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.utils.CommonUtils;
import cn.ucai.fulisenter.model.utils.ResultUtils;
import cn.ucai.fulisenter.view.MFGT;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    @BindView(R.id.et_UserName)
    EditText mUsername;
    @BindView(R.id.et_Nick)
    EditText mNick;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.et_confirmpassword)
    EditText mCongirmPassword;
    IModelUser model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_register_back, R.id.iv_UserAvatar, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                MFGT.finish(this);
                break;
            case R.id.iv_UserAvatar:
                break;
            case R.id.btn_register:
                checkInput();
                break;
        }
    }

    private void checkInput() {
        String username = mUsername.getText().toString().trim();
        String usernick = mNick.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String confirm = mCongirmPassword.getText().toString().trim();
        if(TextUtils.isEmpty(username)) {
            mUsername.setError(getResources().getString(R.string.user_name_connot_be_empty));
            mUsername.requestFocus();
        }else if(!username.matches("[a-zA-Z]\\w{5,15}")){
            mUsername.setError(getResources().getString(R.string.illegal_user_name));
            mUsername.requestFocus();
        }
        else if(TextUtils.isEmpty(usernick)) {
            mNick.setError(getResources().getString(R.string.nick_name_connot_be_empty));
            mNick.requestFocus();
        }else if(TextUtils.isEmpty(password)) {
            mPassword.setError(getResources().getString(R.string.password_connot_be_empty));
            mPassword.requestFocus();
        }else if(TextUtils.isEmpty(confirm)) {
            mCongirmPassword.setError(getResources().getString(R.string.confirm_password_connot_be_empty));
            mCongirmPassword.requestFocus();
        } else if (!password.equals(confirm)) {
            mCongirmPassword.setError(getResources().getString(R.string.two_input_password));
            mCongirmPassword.requestFocus();
        }
        else {
            register(username,usernick,password);
        }
    }

    private void register(String username, String usernick, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.registering));
        model = new ModeUser();
        model.register(this, username, usernick, password, new OnCompletionListener<String>() {
            @Override
            public void onSuccess(String s) {

                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s,Result.class);
                    //L.e(TAG,"resutl="+result);
                    if (result.isRetMsg()) {
                        CommonUtils.showLongToast(R.string.register_success );
                        MFGT.finish(RegisterActivity.this);
                    }else {
                        CommonUtils.showLongToast(R.string.register_fail_exists );
                    }
                }else {
                    CommonUtils.showLongToast(R.string.register_fail );
                }
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                dialog.dismiss();
                CommonUtils.showLongToast(error);
            }
        });
    }


}
