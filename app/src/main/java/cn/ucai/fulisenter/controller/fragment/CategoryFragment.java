package cn.ucai.fulisenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.adapter.CategoryAdapter;
import cn.ucai.fulisenter.model.bean.CategoryChildBean;
import cn.ucai.fulisenter.model.bean.CategoryGroupBean;
import cn.ucai.fulisenter.model.net.IModelCategory;
import cn.ucai.fulisenter.model.net.ModelCategory;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.utils.ConvertUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;
    Unbinder unbinder;

    CategoryAdapter mAdapter;
    IModelCategory mModel;
    ArrayList<CategoryGroupBean> GroupArrayList;
    ArrayList<ArrayList<CategoryChildBean>> childArryList;

    int groupCont;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      //  L.e("main","Category");
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, layout);
        GroupArrayList = new ArrayList<>();
        childArryList = new ArrayList<>();
        mAdapter = new CategoryAdapter(getContext(), childArryList, GroupArrayList );
       // L.e("main","mAdapterfinsh");
        initView(false);
        elvCategory.setGroupIndicator(null);
        initData();
       // L.e("main","setAdapter");
        elvCategory.setAdapter(mAdapter);
      //  L.e("main","setAdapterfinsh");
        return layout;
    }

    private void initData() {
        mModel = new ModelCategory();
        mModel.downData(getContext(), new OnCompletionListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null) {
                    initView(true);
                    ArrayList<CategoryGroupBean> list =  ConvertUtils.array2List(result);
                    GroupArrayList.addAll(list);
                   // L.e("main","GroupArrayList"+GroupArrayList.size());
                    for(int i=0;i<list.size();i++) {
                        childArryList.add(null);
                        downloadChildData(list.get(i).getId(),i);
                    }
                }else {
                    initView(false);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadChildData(int id,final int index) {
        mModel.downData(getContext(), id, new OnCompletionListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCont++;
                if (result != null) {
                    ArrayList<CategoryChildBean> list = ConvertUtils.array2List(result);
                    childArryList.set(index,list);

                }
                if (groupCont == GroupArrayList.size()) {
                   // L.e("main","groupcont ="+groupCont);
                    mAdapter.initData(GroupArrayList,childArryList);
                   // L.e("main","chilarrr"+childArryList.size());
                }
            }

            @Override
            public void onError(String error) {
                //groupCont++;
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(boolean hasData) {
      tvTitle.setVisibility(hasData?View.GONE:View.VISIBLE);
        elvCategory.setVisibility(hasData?View.VISIBLE:View.GONE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
