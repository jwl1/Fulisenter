package cn.ucai.fulisenter.model.utils;

import android.widget.Toast;

import cn.ucai.fulisenter.controller.application.FuLiSenterApplication;

public class CommonUtils {
    public static void showLongToast(String msg){
        Toast.makeText(FuLiSenterApplication.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(String msg){
        Toast.makeText(FuLiSenterApplication.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(int rId){
        showLongToast(FuLiSenterApplication.getInstance().getString(rId));
    }
    public static void showShortToast(int rId){
        showShortToast(FuLiSenterApplication.getInstance().getString(rId));
    }
}
