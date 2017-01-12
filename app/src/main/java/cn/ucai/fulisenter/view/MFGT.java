package cn.ucai.fulisenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.application.I;
import cn.ucai.fulisenter.controller.activity.BoutiqueChildActivity;
import cn.ucai.fulisenter.model.bean.BoutiqueBean;

/**
 * Created by MTJ on 2017/1/10.
 */

public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void startActivity(Activity context, Class<?> clz) {
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        context.startActivity(new Intent(context, clz));

    }
    public static void startActivity(Activity context,Intent intent) {
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        context.startActivity(intent);
    }
    public static void gotoBoutiqueChild() {

    }

    public static void gotoBoutiqueChild(Context mcontext, BoutiqueBean boutiqueBean) {
        Intent intent = new Intent(mcontext, BoutiqueChildActivity.class);
        intent .putExtra(I.NewAndBoutiqueGoods.CAT_ID,boutiqueBean.getId());
        intent   .putExtra(I.Boutique.NAME,boutiqueBean.getTitle());
        startActivity((Activity)mcontext,intent);
    }

}