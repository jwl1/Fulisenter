package cn.ucai.fulisenter.controller.application;

import android.app.Application;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication intance;
    public static FuLiCenterApplication getInstance(){
        return intance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        intance = this;
    }
}
