package cn.ucai.fulisenter.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.application.FuLiCenterApplication;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.Result;
import cn.ucai.fulisenter.model.bean.User;
import cn.ucai.fulisenter.model.dao.UserDao;
import cn.ucai.fulisenter.model.net.IModelUser;
import cn.ucai.fulisenter.model.net.ModeUser;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.utils.CommonUtils;
import cn.ucai.fulisenter.model.utils.L;
import cn.ucai.fulisenter.model.utils.ResultUtils;

public class UpdataNickActivity extends AppCompatActivity {
    private static final String TAG = UpdataNickActivity.class.getSimpleName();
    @BindView(R.id.tv_updatanick)
    EditText mEdUpdatanick;
    IModelUser model;
    String nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_nick);
        ButterKnife.bind(this);
        iniData();
    }

    private void iniData() {
        Intent intent=getIntent();
        nick = intent.getStringExtra("nick");
        L.e(TAG,"nick="+nick);
        mEdUpdatanick.setText(nick);
    }

    @OnClick({R.id.iv_back, R.id.btn_nick})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_nick:
                //确认更改
                String newnick = mEdUpdatanick.getText().toString().trim();
                L.e(TAG,"newnick="+newnick);
                updatanick(newnick);
                break;
        }
    }

    private void updatanick(String newnick) {

        if (!TextUtils.isEmpty(newnick)) {
            L.e(TAG,"nick="+nick+",newnick="+newnick);
            if (!newnick.equals(nick)) {
                updateNick(newnick);
            }else {
                CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
            }
        }else {
            CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
        }


    }

    private void updateNick(String newnick) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("昵称修改中。。。");
        dialog.show();
        model = new ModeUser();
        model.updatNick(this, FuLiCenterApplication.getUser().getMuserName(), newnick, new OnCompletionListener<String>() {
            @Override
            public void onSuccess(String s) {
                int msg = R.string.update_fail;
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result != null) {
                        if(result.isRetMsg()){
                            msg = R.string.update_user_nick_success;
                            User user = (User) result.getRetData();
                            L.e(TAG,"updata,user="+user);
                            saveNewUser(user);
                            setResult(RESULT_OK);
                            finish();
                        }else {
                            if (result.getRetCode() == I.MSG_USER_SAME_NICK ||
                                    result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                                msg = R.string.update_nick_fail_unmodify;
                            }
                        }
                    }else {
                        msg = R.string.update_fail;
                    }
                }else {
                    msg = R.string.update_fail;
                }
                CommonUtils.showLongToast(msg);
                dialog.dismiss();
                finish();
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(R.string.update_fail);
                dialog.dismiss();
                L.e(TAG,"error="+error);
            }
        });

    }

    private void saveNewUser(User user) {
        FuLiCenterApplication.setUser(user);
        UserDao.getInstance().savaUser(user);
    }
}
