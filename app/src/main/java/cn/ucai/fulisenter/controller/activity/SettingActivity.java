package cn.ucai.fulisenter.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.application.FuLiCenterApplication;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.Result;
import cn.ucai.fulisenter.model.bean.User;
import cn.ucai.fulisenter.model.net.IModelUser;
import cn.ucai.fulisenter.model.net.ModeUser;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.net.SharedPrefrenceUtils;
import cn.ucai.fulisenter.model.utils.CommonUtils;
import cn.ucai.fulisenter.model.utils.ImageLoader;
import cn.ucai.fulisenter.model.utils.L;
import cn.ucai.fulisenter.model.utils.OnSetAvatarListener;
import cn.ucai.fulisenter.model.utils.ResultUtils;
import cn.ucai.fulisenter.view.MFGT;

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = SettingActivity.class.getSimpleName();
    @BindView(R.id.iv_personinfo_avatar)
    ImageView mIvPersoninfoAvatar;
    @BindView(R.id.tv_personinfo_username)
    TextView mTvPersoninfoUsername;
    @BindView(R.id.tv_personinfo_nick)
    TextView mTvPersoninfoNick;

    OnSetAvatarListener mOnSetAvatarListener;
    IModelUser model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        inintData();
    }

    private void inintData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
        } else {
            MFGT.gotoLogin(this);
        }
    }

    private void loadUserInfo(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, mIvPersoninfoAvatar);
        mTvPersoninfoUsername.setText(user.getMuserName());
        mTvPersoninfoNick.setText(user.getMuserNick());
    }

    @OnClick(R.id.iv_personinfo_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.btn_personinfo_exit)
    public void onClick() {
        FuLiCenterApplication.setUser(null);
        SharedPrefrenceUtils.getInstance(this).removeUser();
        MFGT.gotoLogin(this);
        finish();
    }

    @OnClick(R.id.rl_nick)
    public void updateNick() {
        String nick = mTvPersoninfoNick.getText().toString().trim();
        //L.e(TAG,"nick="+nick);
        if (!TextUtils.isEmpty(nick)) {
            MFGT.gotoUpdata(this, nick);
            // L.e(TAG,"updateNick");
        }
        //L.e(TAG,"updateNick22");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e(TAG, "request=" + requestCode + ",resultCode=" + resultCode);
        if (resultCode != RESULT_OK ) {
            return;
        }
        if( requestCode == I.REQUEST_CODE_NICK){
            mTvPersoninfoNick.setText(FuLiCenterApplication.getUser().getMuserNick());
        } else if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            uploadAvatar();
        }
            mOnSetAvatarListener.setAvatar(requestCode,data,mIvPersoninfoAvatar);

    }

    private void uploadAvatar() {
        final User user = FuLiCenterApplication.getUser();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.update_user_avatar));
        dialog.show();
        model = new ModeUser();
        File file = null;
        file = new File(String.valueOf(OnSetAvatarListener.getAvatarFile(this,
               I.AVATAR_TYPE_USER_PATH+
                        "/"+user.getMuserName()+
                user.getMavatarSuffix())));
        L.e(TAG,"file="+file.getAbsolutePath());
        model.uploadAvatar(this,
                user.getMuserName(),
                file,
                new OnCompletionListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        L.e(TAG,"S="+s);
                        int msg = R.string.update_user_avatar_fail;
                        if (s != null) {
                            Result result = ResultUtils.getResultFromJson(s, User.class);
                            if (result != null) {
                                if (result.isRetMsg()) {
                                    msg = R.string.update_user_avatar_success;
                                }
                            }
                        }
                        CommonUtils.showLongToast(msg);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showLongToast(error);
                        dialog.dismiss();
                    }
                });
    }

    @OnClick(R.id.rl_name)
    public void onupdataname() {
        CommonUtils.showLongToast(R.string.username_connot_be_modify);
    }

    @OnClick(R.id.iv_personinfo_avatar)
    public void onClickavatar() {
        mOnSetAvatarListener = new OnSetAvatarListener(this,
                R.id.iv_personinfo_avatar,
                FuLiCenterApplication.getUser().getMuserName(),
                I.AVATAR_TYPE_USER_PATH);
    }
}
