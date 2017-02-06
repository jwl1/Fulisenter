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
import cn.ucai.fulisenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.BoutiqueBean;
import cn.ucai.fulisenter.model.net.IModelBoutique;
import cn.ucai.fulisenter.model.net.ModelBoutique;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.utils.ConvertUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {
    @BindView(R.id.srlBoutique)
    SwipeRefreshLayout srlBoutique;
    @BindView(R.id.tv_nomore)
    TextView tvNomore;
    @BindView(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @BindView(R.id.rvBoutique)
    RecyclerView rvBoutique;

    Unbinder unbinder;

    IModelBoutique mModel;
    BoutiqueAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<BoutiqueBean> mArryList;



    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_boutique, container, false);
        unbinder = ButterKnife.bind(this, layout);
       // Log.e("main", "lay");
        initView(layout);
       // L.e("main", "initView");
        iniData(I.ACTION_DOWNLOAD);
       // L.e("main", "inidatafinsh");
        setListener();
        return layout;
    }

    private void setListener() {
        srlBoutique.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlBoutique.setRefreshing(true);
                tvRefreshHint.setVisibility(View.VISIBLE);
                iniData(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void iniData(final int action) {
        mModel.downloadBoutiqueList(getContext(), new OnCompletionListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                srlBoutique.setRefreshing(false);
                tvRefreshHint.setVisibility(View.GONE);
              //  L.e("main", result.length + "");
                if (result != null && result.length > 0) {
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    } else {
                        mAdapter.addData(list);
                    }
                } else {
                    rvBoutique.setVisibility(View.GONE);
                    tvNomore.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
               // Log.e("main", "gg");
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(View layout) {
        mModel = new ModelBoutique();
        mArryList = new ArrayList<>();
        mAdapter = new BoutiqueAdapter(getContext(), mArryList, mModel);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvBoutique.setLayoutManager(mLayoutManager);
        rvBoutique.setAdapter(mAdapter);
    }
    @OnClick(R.id.tv_nomore)
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
