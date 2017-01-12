package cn.ucai.fulisenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulisenter.view.MFGT;

public class BoutiqueChildActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_child);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().add(R.id.fl_Boutique_Child, new NewGoodsFragment()).commit();
        getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID, I.CAT_ID);
        String stl = getIntent().getStringExtra(I.Boutique.TITLE);
        title.setText(stl);
    }

    @OnClick(R.id.back)
    public void onClick() {
        MFGT.finish(this);
    }
}
