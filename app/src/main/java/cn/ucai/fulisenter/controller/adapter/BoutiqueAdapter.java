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
import cn.ucai.fulisenter.model.bean.BoutiqueBean;
import cn.ucai.fulisenter.model.ustils.ImageLoader;

/**
 * Created by MTJ on 2017/1/11.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    private static final int TYPE_BOUTIQUE = 0;
    private static final int TYPE_FOOTER = 1;
    Context mContext;
    ArrayList<BoutiqueBean> mList;
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


    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_BOUTIQUE) {
            holder = new BoutiqueViewHolder(View.inflate(mContext, R.layout.item_boutique, null));
            return holder;
        } else {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
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

        BoutiqueViewHolder holder1 = (BoutiqueViewHolder) holder;
        ImageLoader.downloadImg(mContext, holder1.ivBoutique, mList.get(position).getImageurl());
        holder1.tvBoutique1.setText(mList.get(position).getName());
        holder1.tvBoutique2.setText(mList.get(position).getTitle());
        holder1.tvBoutique3.setText(mList.get(position).getDescription());
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
        return TYPE_BOUTIQUE;
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<BoutiqueBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }



    static class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBoutique)
        ImageView ivBoutique;
        @BindView(R.id.tvBoutique1)
        TextView tvBoutique1;
        @BindView(R.id.tvBoutique2)
        TextView tvBoutique2;
        @BindView(R.id.tvBoutique3)
        TextView tvBoutique3;

        BoutiqueViewHolder(View view) {
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
