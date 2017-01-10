package cn.ucai.fulisenter.view;

import android.app.Activity;

import android.content.Intent;

import cn.ucai.fulisenter.R;

public class MFGT  {
    public static void startActivity(Activity context ,Class<?>clz) {

        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        context.startActivity(new Intent(context,clz));
    }

}
