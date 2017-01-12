package cn.ucai.fulisenter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulisenter.controller.fragment.BoutiqueFragment;
import cn.ucai.fulisenter.controller.fragment.NewGoodsFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int index, currentIndex;
    RadioButton[] rbs = new RadioButton[5];
    FragmentTransaction ft ;

    @BindView(R.id.Lauout_newgoods)
    RadioButton LauoutNewgoods;
    @BindView(R.id.Layout_boutigue)
    RadioButton LayoutBoutigue;
    @BindView(R.id.Layout_category)
    RadioButton LayoutCategory;
    @BindView(R.id.Layout_cart)
    RadioButton LayoutCart;
    @BindView(R.id.Layout_personal)
    RadioButton LayoutPersonal;

   /* Fragment[] mFragments = new Fragment[5];
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListener();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new NewGoodsFragment()).commit();
        rbs[0] = LauoutNewgoods;
        rbs[1] = LayoutBoutigue;
        rbs[2] = LayoutCategory;
        rbs[3] = LayoutCart;
        rbs[4] = LayoutPersonal;


    }

    private void setListener() {
        LauoutNewgoods.setOnClickListener(this);
        LayoutBoutigue.setOnClickListener(this);
        LayoutCategory.setOnClickListener(this);
        LayoutCart.setOnClickListener(this);
        LayoutPersonal.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.Lauout_newgoods:
                index = 0;
                ft.replace(R.id.fragment_container, new NewGoodsFragment()).commit();
                break;
            case R.id.Layout_boutigue:
                index = 1;
                ft.replace(R.id.fragment_container, new BoutiqueFragment()).commit();
            case R.id.Layout_category:
                //index = 2;
                break;
            case R.id.Layout_cart:
                //index = 3;
                break;
            case R.id.Layout_personal:

                //index = 4;
                break;
        }
        if (index != currentIndex) {
            setRadioStatus();
        }

    }

    private void setRadioStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex = index;
    }

}


        /*rbs[0] = LauoutNewgoods;
        rbs[1] = LayoutBoutigue;
        rbs[2] = LayoutCategory;
        rbs[3] = LayoutCart;
        rbs[4] = LayoutPersonal;
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new NewGoodsFragment())
                .add(R.id.fragment_container,new BoutiqueFragment())
                .show(mNewGoodsFragment)
                .hide(mBoutiqueFragment)
                .commit();


    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.Lauout_newgoods:
                index = 0;
                break;
            case R.id.Layout_boutigue:
                index = 1;
                break;
            case R.id.Layout_category:
                index = 2;
                break;
            case R.id.Layout_cart:
                index = 3;
                break;
            case R.id.Layout_personal:
                index = 4;
                break;
        }
        if (index != currentIndex) {
            setRadioStatus();
        }
        setFragment();
        if (index!=currentIndex){
            setRadioStatus();
        }
    }
    private void setFragment(){
        getSupportFragmentManager().beginTransaction().show(mFragments[index])
                .hide(mFragments[currentIndex]).commit();
    }

    private void setRadioStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex = index;
    }

}*/
