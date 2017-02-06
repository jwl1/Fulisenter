package cn.ucai.fulisenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.adapter.CartAdapter;
import cn.ucai.fulisenter.controller.application.FuLiCenterApplication;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.CartBean;
import cn.ucai.fulisenter.model.bean.User;
import cn.ucai.fulisenter.model.net.IModelUser;
import cn.ucai.fulisenter.model.net.ModeUser;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.utils.ConvertUtils;
import cn.ucai.fulisenter.model.utils.L;
import cn.ucai.fulisenter.view.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private static final String TAG = CartFragment.class.getSimpleName();
    @BindView(R.id.srl)
    SwipeRefreshLayout msrl;
    @BindView(R.id.tv_nothing)
    TextView tvnothing;
    @BindView(R.id.tv_RefreshHint)
    TextView tvRefreshHint;
    @BindView(R.id.rv)
    RecyclerView mRv;

    Unbinder unbinder;

    IModelUser mModel;
    CartAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<CartBean> mArryList;
    User user ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, layout);
        initView(layout);
        user = FuLiCenterApplication.getUser();
        iniData(I.ACTION_DOWNLOAD);
        setListener();
        return layout;
    }

    private void setListener() {
        msrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                msrl.setRefreshing(true);
                tvRefreshHint.setVisibility(View.VISIBLE);
                iniData(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void iniData(final int action) {
        if (user == null) {
            MFGT.gotoLogin(getActivity());
        }else {
            mModel.getCart(getContext(), user.getMuserName(),
                    new OnCompletionListener<CartBean[]>() {
                        @Override
                        public void onSuccess(CartBean[] result) {
                            msrl.setRefreshing(false);
                            tvRefreshHint.setVisibility(View.GONE);
                            //  L.e("main", result.length + "");
                            if (result != null && result.length > 0) {
                                ArrayList<CartBean> list = ConvertUtils.array2List(result);
                                if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                                    mAdapter.initData(list);
                                } else {
                                    mAdapter.addData(list);
                                }
                            } else {
                                mRv.setVisibility(View.GONE);
                                tvnothing.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(String error) {
                            // Log.e("main", "gg");
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void initView(View layout) {
        mModel = new ModeUser();
        mArryList = new ArrayList<>();
        mAdapter = new CartAdapter(getContext(),mArryList);
        L.e(TAG,"mAdapter,mArrayList="+mArryList.toString());
        mLayoutManager = new LinearLayoutManager(getContext());
        mRv.setHasFixedSize(true);
        msrl.setVisibility(View.VISIBLE);
        tvnothing.setVisibility(View.GONE);
        mRv.setLayoutManager(mLayoutManager);
        mRv.setAdapter(mAdapter);
    }
    @OnClick(R.id.tv_nothing)
    public void onClick() {
        // L.e("main", "onclick");
        iniData(I.ACTION_DOWNLOAD);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
