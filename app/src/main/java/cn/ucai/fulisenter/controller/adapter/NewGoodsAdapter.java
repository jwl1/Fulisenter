package cn.ucai.fulisenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.activity.GoodsDetailsActivity;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.NewGoodsBean;
import cn.ucai.fulisenter.model.net.IModelNewGoods;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter {
    static final int TYPE_ITEM = 0;
    static final int TYPE_FOOTER = 1;
    ArrayList<NewGoodsBean> arrayList;
    Context context;
    String footer;
    boolean isMore=true;
    boolean isDragging;
    IModelNewGoods model;

    public NewGoodsAdapter(ArrayList<NewGoodsBean> arrayList, Context context,IModelNewGoods model) {
        this.arrayList = arrayList;
        this.context = context;
        this.model=model;
    }

    public void initData(ArrayList<NewGoodsBean> arrayList) {
        this.arrayList.clear();
        addData(arrayList);
    }

    public void addData(ArrayList<NewGoodsBean> arrayList) {
        this.arrayList.addAll(arrayList);
        notifyDataSetChanged();
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void sortGoods(final int sortBy) {
        Collections.sort(arrayList, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean leftBean, NewGoodsBean rightBean) {
                int result = 0;
                switch (sortBy) {
                    case I.SORT_BY_ADDTIME_ASC:
                        result = (int) (leftBean.getAddTime() - rightBean.getAddTime());
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result = (int) (rightBean.getAddTime() - leftBean.getAddTime());
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result=getPrice(leftBean.getCurrencyPrice())-getPrice(rightBean.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result=getPrice(rightBean.getCurrencyPrice())-getPrice(leftBean.getCurrencyPrice());
                        break;
                }
                return result;
            }
        });
        notifyDataSetChanged();
    }

    int getPrice(String price) {
        int p = 0;
        p = Integer.valueOf(price.substring(price.indexOf("￥")+1)) ;
     //   L.e("main","p="+p);
        return p;
    }
    public void setDragging(boolean dragging) {
        isDragging = dragging;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View layout;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_FOOTER:
                layout = layoutInflater.inflate(R.layout.item_newgoods_footer, null);
                holder = new FooterViewHolder(layout);
                break;
            case TYPE_ITEM:
                layout = layoutInflater.inflate(R.layout.item_newgoods_contact, null);
                holder = new ContactViewHolder(layout);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentholder, final int position) {
        if (position == getItemCount() - 1) {
            FooterViewHolder holder = (FooterViewHolder) parentholder;
            holder.tvfooter.setText(isMore?"上拉加载更多":"没有更多数据");
            return;
        }
        ContactViewHolder holder = (ContactViewHolder) parentholder;
        final NewGoodsBean contact = arrayList.get(position);
        holder.tvName.setText(contact.getGoodsName());
        holder.tvMuch.setText(contact.getCurrencyPrice());
        model.downloadnewgoodsImage(context,contact.getGoodsImg(),R.drawable.nopic,holder.ivAvatar);
        //Log.e("main",)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context, GoodsDetailsActivity.class);
                intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,arrayList.get(position).getGoodsId());
             //   L.e("main","goodId"+arrayList.get(position).getGoodsId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView tvfooter;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tvfooter = (TextView) itemView.findViewById(R.id.tv_newgoods_footer);
        }
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvMuch;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_Newgood_Avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_Newgood_Name);
            tvMuch = (TextView) itemView.findViewById(R.id.tv_Newgood_Price);
        }
    }
}
