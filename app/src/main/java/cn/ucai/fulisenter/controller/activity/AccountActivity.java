package cn.ucai.fulisenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulisenter.R;

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.et_account_name)
    EditText etAccountName;
    @BindView(R.id.et_account_phone)
    EditText etAccountPhone;
    @BindView(R.id.sp_account)
    Spinner spAccount;
    @BindView(R.id.et_center)
    EditText etCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.btn_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_account:

                break;
        }
    }
}
