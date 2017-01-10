package cn.ucai.fulisenter.application;

import android.app.Application;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class FuLiSenterApplication extends Application {
    private static FuLiSenterApplication instance;

    public FuLiSenterApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
