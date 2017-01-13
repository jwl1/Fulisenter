package cn.ucai.fulisenter.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.model.bean.CategoryChildBean;
import cn.ucai.fulisenter.model.bean.CategoryGroupBean;
import cn.ucai.fulisenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupBeen;
    ArrayList<ArrayList<CategoryGroupBean>> mChildBeen;

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> groupBeen, ArrayList<ArrayList<CategoryChildBean>> childBeen) {
        mContext = context;
        mGroupBeen = new ArrayList<>();
        mGroupBeen.addAll(mGroupBeen);
        mChildBeen = new ArrayList<>();

      mChildBeen.addAll(mChildBeen);
    }

    @Override
    public int getGroupCount() {
        return mGroupBeen != null ? mGroupBeen.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildBeen != null && mChildBeen.get(groupPosition) != null ?
               mChildBeen.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupBeen.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mChildBeen != null && mChildBeen.get(groupPosition) != null) {
            return mChildBeen.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpand, View view, ViewGroup viewGroup) {
       GroupViewHolder vh= null;
        if (view == null) {
            view = view.inflate(mContext, R.layout.item_category_group, null);
            vh = new GroupViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (GroupViewHolder) view.getTag();
        }
        ImageLoader.downloadImg(mContext,vh.ivGroupThumb,mGroupBeen.get(groupPosition).getImageUrl());

        vh.tvGroupName.setText(mGroupBeen.get(groupPosition).getName());
        vh.ivIndicater.setImageResource(isExpand?R.mipmap.expand_off:R.mipmap.expand_on);
        return view;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_category_child, null);


        return inflate;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    public  void initData(ArrayList<CategoryGroupBean> groupBeen,
                          ArrayList<ArrayList<CategoryChildBean>>childBeen){
        mGroupBeen.clear();
        mGroupBeen.addAll(groupBeen);
        mChildBeen.clear();
        mChildBeen.addAll(mChildBeen);
        notifyDataSetChanged();
    }

    static class GroupViewHolder {
        @BindView(R.id.iv_group_thumb)
        ImageView ivGroupThumb;
        @BindView(R.id.tv_group_name)
        TextView tvGroupName;
        @BindView(R.id.iv_indicater)
        ImageView ivIndicater;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    static class ChildViewHolder {
        @BindView(R.id.iv_category_child_thumb)
        ImageView ivCategoryChildThumb;
        @BindView(R.id.tv_category_child_name)
        TextView tvCategoryChildName;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
