package cn.ucai.fulisenter.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import cn.ucai.fulisenter.R;


public class MainActivity extends AppCompatActivity {
    int  index, currentIndex;
    RadioButton rbNewGoods,rbBoutique,rbCategory,rbCart, rbPersonl;
    RadioButton[] rbs = new RadioButton[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbNewGoods = (RadioButton) findViewById(R.id.layout_new_good);
        rbBoutique = (RadioButton) findViewById(R.id.layout_boutique);
        rbCategory = (RadioButton) findViewById(R.id.layout_category);
        rbCart = (RadioButton) findViewById(R.id.layout_cart);
        rbPersonl = (RadioButton) findViewById(R.id.layout_personal_center);


    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_good:
                break;
            case R.id.layout_boutique:
                break;
            case R.id.layout_category:
                break;
            case R.id.layout_cart:
                break;
            case R.id.layout_personal_center:
                break;

        }
        if (index != currentIndex) {
            setRadioStatus();
        }

    }

    private void setRadioStatus() {
        for (int i=0;i<rbs.length;i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex = index;

    }
}
