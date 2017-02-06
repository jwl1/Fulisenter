package cn.ucai.fulisenter.controller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.application.FuLiCenterApplication;
import cn.ucai.fulisenter.model.bean.User;
import cn.ucai.fulisenter.model.dao.UserDao;
import cn.ucai.fulisenter.model.net.SharedPrefrenceUtils;
import cn.ucai.fulisenter.view.MFGT;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new  Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String username = SharedPrefrenceUtils.getInstance(SplashActivity.this).getUser();
                if (username != null) {
                    User user = UserDao.getInstance().getUser(username);
                   // L.e(TAG,"user ="+user);
                    if(user!=null){
                        FuLiCenterApplication.setUser(user);
                    }
                }
                MFGT.startActivity(SplashActivity.this,MainActivity.class);
                MFGT.finish(SplashActivity.this);
            }
        },2000);
    }
}
