package cn.ucai.fulisenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulisenter.model.bean.CategoryChildBean;
import cn.ucai.fulisenter.view.CatFileterButton;

public class CategoryChildActivity extends AppCompatActivity {

    @BindView(R.id.tv_category_child_pice)
    TextView tvCategoryChildPice;
    @BindView(R.id.tv_category_child_time)
    TextView tvCategoryChildTime;
    @BindView(R.id.fl_category_child)
    FrameLayout flCategoryChild;
    NewGoodsFragment mNewGoodsFragment;
    @BindView(R.id.iv_back_name)
    ImageView ivBackName;

    boolean priceAsc = false;
    boolean addTiemAsc = false;
    @BindView(R.id.iv_pirc)
    ImageView ivPirc;
    @BindView(R.id.iv_addTime)
    ImageView ivAddTime;
    @BindView(R.id.cfb)
    CatFileterButton cfb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mNewGoodsFragment = new NewGoodsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_category_child, mNewGoodsFragment)
                .commit();
        ArrayList<CategoryChildBean> list = (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra(I.CategoryChild.DATA);
        cfb.initCatFileterButton(getIntent().getStringExtra(I.CategoryChild.NAME),list);
    }

    @OnClick({R.id.tv_category_child_pice, R.id.tv_category_child_time, R.id.iv_back_name})
    public void onClick(View view) {
        int sortBy = I.SORT_BY_ADDTIME_ASC;
        switch (view.getId()) {
            case R.id.tv_category_child_pice:
                if (priceAsc) {
                    sortBy = I.SORT_BY_PRICE_DESC;
                    ivPirc.setRotation(0);
                } else {
                    sortBy = I.SORT_BY_PRICE_ASC;
                    ivPirc.setRotation(180);
                }
                priceAsc = !priceAsc;
                break;
            case R.id.tv_category_child_time:
                if (addTiemAsc) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                    ivAddTime.setRotation(0);
                } else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                    ivAddTime.setRotation(180);
                }
                addTiemAsc = !addTiemAsc;
                break;
            case R.id.iv_back_name:
                this.finish();
        }
        mNewGoodsFragment.sortGoods(sortBy);
    }
}
