package cn.ucai.fulisenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.activity.GoodsDetailsActivity;
import cn.ucai.fulisenter.controller.application.FuLiCenterApplication;
import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.CollectBean;
import cn.ucai.fulisenter.model.bean.MessageBean;
import cn.ucai.fulisenter.model.bean.ModelNewGoods;
import cn.ucai.fulisenter.model.bean.User;
import cn.ucai.fulisenter.model.net.IModelGoodes;
import cn.ucai.fulisenter.model.net.IModelNewGoods;
import cn.ucai.fulisenter.model.net.ModelGoodes;
import cn.ucai.fulisenter.model.net.OnCompletionListener;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class CollectAdapter extends RecyclerView.Adapter {
    static final int TYPE_ITEM = 0;
    static final int TYPE_FOOTER = 1;
    ArrayList<CollectBean> arrayList;
    Context context;
    String footer;
    boolean isMore = true;
    boolean isDragging;
    IModelNewGoods model;
    IModelGoodes modelGoodes;
    User user;
    public CollectAdapter(ArrayList<CollectBean> arrayList, Context context) {
        this.arrayList = new ArrayList<>();
        arrayList.addAll(arrayList);
        this.context = context;
        modelGoodes = new ModelGoodes();
        user = FuLiCenterApplication.getUser();
    }

    public void initData(ArrayList<CollectBean> arrayList) {
        this.arrayList.clear();
        addData(arrayList);
    }

    public void addData(ArrayList<CollectBean> arrayList) {
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
                layout = layoutInflater.inflate(R.layout.item_collect, null);
                holder = new CollectViewHolder(layout);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder parentholder, final int position) {
        if (position == getItemCount() - 1) {
            FooterViewHolder holder = (FooterViewHolder) parentholder;
            holder.tvfooter.setText(isMore ? "上拉加载更多" : "没有更多数据");
            return;
        }
        CollectViewHolder holder = (CollectViewHolder) parentholder;
        final CollectBean contact = arrayList.get(position);
        holder.tvGoosName.setText(contact.getGoodsName());
        model = (IModelNewGoods) new ModelNewGoods();
        model.downloadnewgoodsImage(context, contact.getGoodsImg(), R.drawable.nopic, holder.ivGoodsThumb);
        //Log.e("main",)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailsActivity.class);
                intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, arrayList.get(position).getGoodsId());
                context.startActivity(intent);
            }
        });
        holder.ivCollectDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelGoodes.setCollect(context, arrayList.get(position).getGoodsId(),
                        user.getMuserName(), I.ACTION_DELETE_COLLECT,
                        new OnCompletionListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean result) {
                                if (result != null) {
                                    arrayList.remove(position);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
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


    static class CollectViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoosName)
        TextView tvGoosName;
        @BindView(R.id.iv_collect_del)
        ImageView ivCollectDel;
        @BindView(R.id.layout_goods)
        RelativeLayout layoutGoods;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public void removeItem(int goodsId){
        if (goodsId != 0) {
            arrayList.remove(new CollectBean(goodsId));
            notifyDataSetChanged();
        }
    }

}
