package cn.ucai.fulisenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.activity.BoutiqueChildActivity;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.BoutiqueBean;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class MFGT {

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
}
