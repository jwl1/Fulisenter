package cn.ucai.fulisenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.adapter.CategoryAdapter;
import cn.ucai.fulisenter.model.bean.CategoryChildBean;
import cn.ucai.fulisenter.model.bean.CategoryGroupBean;
import cn.ucai.fulisenter.model.net.IModelNewCategory;
import cn.ucai.fulisenter.model.net.ModelCategory;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.utils.ConvertUtils;
import cn.ucai.fulisenter.model.utils.L;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategroyFragment extends Fragment {

    private static final String TAG = CategroyFragment.class.getSimpleName();
    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;
    @BindView(R.id.tv_nomore)
    TextView tvNomore;
    IModelNewCategory model;
    CategoryAdapter mAdapter;
    ArrayList<CategoryGroupBean> mGroupBeen = new ArrayList<>();
    ArrayList<ArrayList<CategoryChildBean>> mChildBeen = new ArrayList<>();
    int groupCount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layout);
        mAdapter = new CategoryAdapter(getContext(), mGroupBeen, mChildBeen);
       elvCategory.setAdapter(mAdapter);

        initView(false);
        initData();
   return  layout;
    }

    private void initData() {
        model = new ModelCategory();
        model.downData(getContext(), new OnCompletionListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null) {

                    initView(true);
              ArrayList<CategoryGroupBean> list=ConvertUtils.array2List(result);
                    mGroupBeen.addAll(list);

                    for(int i=0;i<list.size();i++) {
                        downloadChildData(list.get(i).getId());
                    }
                } else {
                    initView(false);

                }
                }


            @Override
            public void onError(String error) {
                   initView(false);
                L.e(TAG,"error="+error);
            }
        });
    }

    private void downloadChildData(int id) {

        model.downData(getContext(), id, new OnCompletionListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
              groupCount++;

                if (result != null) {
            ArrayList<CategoryChildBean>list=ConvertUtils.array2List(result);


                 mChildBeen.add(list);
                }
                if (groupCount == mGroupBeen.size()) {
                    mAdapter.initData(mGroupBeen,mChildBeen);

                }
            }

            @Override
            public void onError(String error) {
                groupCount++;

            }
        });
    }

    private void initView(boolean hasData) {
        tvNomore.setVisibility(hasData?View.GONE:View.VISIBLE);
        elvCategory.setVisibility(hasData?View.VISIBLE:View.GONE);
    }

    @OnClick(R.id.tv_nomore)
    void onclick() {
        initData();
    }
}
