package cn.ucai.fulisenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.activity.BoutiqueChildActivity;
import cn.ucai.fulisenter.controller.activity.CategoryChildActivity;
import cn.ucai.fulisenter.controller.activity.ColletactActivity;
import cn.ucai.fulisenter.controller.activity.GoodsDetailsActivity;
import cn.ucai.fulisenter.controller.activity.LoginActivity;
import cn.ucai.fulisenter.controller.activity.RegisterActivity;
import cn.ucai.fulisenter.controller.activity.SettingActivity;
import cn.ucai.fulisenter.controller.activity.UpdataNickActivity;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.BoutiqueBean;
import cn.ucai.fulisenter.model.bean.CategoryChildBean;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class MFGT {
    private static final String TAG = MFGT.class.getSimpleName();
    public static void finish(Activity context){
        context.finish();
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void startActivity(Activity context,Class<?> clz){
        context.startActivity(new Intent(context,clz));
        context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    public static void startActivity(Activity context,Intent intent){
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoBoutiqueChildActivity(Context mContext, BoutiqueBean bean) {
        Intent intent = new Intent(mContext, BoutiqueChildActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID,bean.getId())
                .putExtra(I.Boutique.TITLE,bean.getTitle());
        startActivity((Activity) mContext,intent);
    }
    public static void gotoGoodsDetail(Context context,int goodId){
        Intent intent = new Intent(context, GoodsDetailsActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodId);
        startActivity((Activity)context,intent);
    }
    public static void gotoCategoryChild(Context context, String title, int catId, ArrayList<CategoryChildBean> list){
        Intent intent = new Intent(context, CategoryChildActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID,catId);
        intent.putExtra(I.CategoryChild.NAME,title);
        intent.putExtra(I.CategoryChild.DATA,list);
        startActivity((Activity)context,intent);
    }

    public static void gotoLogin(Context context) {

        startActivity((Activity)context,LoginActivity.class);
    }

    public static void gotoRegister(LoginActivity loginActivity) {
        startActivity(loginActivity, RegisterActivity.class);
    }

    public static void gotoSettings(Activity activity) {
        startActivity(activity, SettingActivity.class);
    }

    public static void gotoUpdata(Activity activity,String nick) {
        Intent intent = new Intent();
        intent.setClass(activity, UpdataNickActivity.class);
      //  L.e(TAG,"mfgt,nick"+nick);
        intent.putExtra("nick",nick);
        activity.startActivityForResult(intent,I.REQUEST_CODE_NICK);
       // startActivity(activity, UpdataNickActivity.class);
    }

    public static void gotoColletace(FragmentActivity activity) {
        startActivity(activity, ColletactActivity.class);
    }
    public static void gotoOrder(Activity activity) {
        startActivity(activity, ColletactActivity.class);
    }
}

