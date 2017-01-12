package cn.ucai.fulisenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.model.bean.NewGoodsBean;
import cn.ucai.fulisenter.model.ustils.ImageLoader;

/**
 * Created by MTJ on 2017/1/11.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    private static final int TYPE_GOODS = 0;
    private static final int TYPE_FOOTER = 1;
    Context mContext;
    ArrayList<NewGoodsBean> mList;
    boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    String footer;
    boolean isDragging;


    public boolean isDragging() {
        return isDragging;
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
        notifyDataSetChanged();
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
        notifyDataSetChanged();
    }



    public GoodsAdapter(Context context, ArrayList<NewGoodsBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_GOODS) {
            RecyclerView.ViewHolder holder =
                    new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
            return holder;
        } else {
            RecyclerView.ViewHolder holder =
                    new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
            return holder;
        }
    }
    //RecyclerView.ViewHolder holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterViewHolder vh = (FooterViewHolder) holder;
            vh.tvFooter.setText(getFooter());
            return;
        }

        GoodsViewHolder holder1 = (GoodsViewHolder) holder;
        ImageLoader.downloadImg(mContext, holder1.ivGoodsThumb, mList.get(position).getGoodsThumb());
        holder1.tvGoodsName.setText(mList.get(position).getGoodsName());
        holder1.tvGoodsPrice.setText(mList.get(position).getCurrencyPrice());
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_GOODS;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
