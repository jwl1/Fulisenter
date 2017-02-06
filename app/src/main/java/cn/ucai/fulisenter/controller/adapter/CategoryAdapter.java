package cn.ucai.fulisenter.controller.adapter;

import android.content.Context;
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
import cn.ucai.fulisenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<CategoryGroupBean> mGroupBean;
    ArrayList<ArrayList<CategoryChildBean>> mChlidBean;

    public CategoryAdapter(Context context, ArrayList<ArrayList<CategoryChildBean>> mChlidBean, ArrayList<CategoryGroupBean> mGroupBean) {
        this.context = context;
        this.mChlidBean = new ArrayList<>();
        mChlidBean.addAll(mChlidBean);
        this.mGroupBean = new ArrayList<>();
        mGroupBean.addAll(mGroupBean);
    }

    @Override
    public int getGroupCount() {
        return mGroupBean != null ? mGroupBean.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChlidBean != null && mChlidBean.get(groupPosition) != null ? mChlidBean.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return mGroupBean.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        if (mChlidBean != null && mChlidBean.get(groupPosition) != null) {
          //  L.e("main","childsize="+mChlidBean.size());
           // L.e("main","mChlidBean.get(groupPosition)="+mChlidBean.get(groupPosition).size());
            return mChlidBean.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_category_group, null);
            vh = new GroupViewHolder(convertView);
            convertView.setTag(vh);
        }
        vh = (GroupViewHolder) convertView.getTag();
        ImageLoader.downloadImg(context, vh.ivCategoryGroupAvater, mGroupBean.get(groupPosition).getImageUrl());
        vh.tvCategoryGroupTitle.setText(mGroupBean.get(groupPosition).getName());
        vh.ivCategoryGroupUp.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_category_child, null);
            vh = new ChildViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ChildViewHolder) convertView.getTag();
        }
        ImageLoader.downloadImg(context, vh.ivChildAvatar,getChild(groupPosition,childPosition).getImageUrl());
        vh.tvChildTitle.setText(getChild(groupPosition,childPosition).getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.gotoCategoryChild(context,mGroupBean.get(groupPosition).getName(),
                        mChlidBean.get(groupPosition).get(childPosition).getId(),
                        mChlidBean.get(groupPosition)
                );
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> groupArrayList,
                         ArrayList<ArrayList<CategoryChildBean>> childArryList) {
        mGroupBean.clear();
        mGroupBean.addAll(groupArrayList);
        mChlidBean.clear();
        mChlidBean.addAll(childArryList);
      //  L.e("Main",mChlidBean.size()+"chlid");
     //   L.e("Main",mGroupBean.size()+"group");
        notifyDataSetChanged();
    }

    static class GroupViewHolder {
        @BindView(R.id.iv_category_group_avater)
        ImageView ivCategoryGroupAvater;
        @BindView(R.id.tv_category_group_title)
        TextView tvCategoryGroupTitle;
        @BindView(R.id.iv_category_group_up)
        ImageView ivCategoryGroupUp;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    static class ChildViewHolder {
        @BindView(R.id.iv_child_avatar)
        ImageView ivChildAvatar;
        @BindView(R.id.tv_child_title)
        TextView tvChildTitle;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
