package cn.ucai.fulisenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.model.bean.CartBean;
import cn.ucai.fulisenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulisenter.model.utils.ImageLoader;
import cn.ucai.fulisenter.model.utils.L;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class CartAdapter extends RecyclerView.Adapter {
    private static final String TAG = CartAdapter.class.getSimpleName();
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context context, ArrayList<CartBean> mList) {
        mContext = context;
        this.mList = new ArrayList<>();
        this.mList.addAll(mList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = inflater.inflate(R.layout.item_cart, null);
        CartViewHolder vh = new CartViewHolder(layout);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder pholder, int position) {
        CartViewHolder holder =(CartViewHolder)pholder;
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @OnClick({ R.id.iv_cart_add, R.id.iv_cart_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cart_add:

                break;
            case R.id.iv_cart_del:
                break;
        }
    }
    @OnCheckedChanged(R.id.cbx_cart)
    public void onChecked(){

    }

    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        addData(list);
    }

    public void addData(ArrayList<CartBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_cart_avatar)
        ImageView ivCartAvatar;
        @BindView(R.id.tv_cart_title)
        TextView tvCartTitle;
        @BindView(R.id.tv_cart_number)
        TextView tvCartNumber;
        @BindView(R.id.tv_cart_price)
        TextView tvCartPrice;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
         public void bind(int position) {
             GoodsDetailsBean detailsBean = mList.get(position).getGoods();
             L.e(TAG,"detailsBean="+detailsBean);
             if (detailsBean != null) {
                 ImageLoader.downloadImg(mContext,ivCartAvatar,detailsBean.getGoodsThumb());
                 tvCartPrice.setText(detailsBean.getCurrencyPrice());
                 tvCartTitle.setText(detailsBean.getGoodsName());
             }
             tvCartNumber.setText("("+mList.get(position).getCount()+")");

         }
     }
}
