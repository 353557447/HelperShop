package com.shuiwangzhijia.shuidian.newmodule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.EquipModifySelGoodsBean;
import com.shuiwangzhijia.shuidian.utils.MyUtils;

import java.util.List;


public class EquipModifySelGoodsAdapter extends RecyclerView.Adapter<EquipModifySelGoodsAdapter.ViewHolder> {
    private Context mContext;
    private List<EquipModifySelGoodsBean.DataBean> data;

    public EquipModifySelGoodsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_equip_modify_sel_goods, parent, false);
        ImageView goodsIv = view.findViewById(R.id.goods_iv);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        goodsIv.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        EquipModifySelGoodsBean.DataBean dataBean = data.get(position);
        MyUtils.setImg(holder.mGoodsIv, dataBean.getPicturl(),"goods/");
        if(dataBean.isChecked()){
            holder.mGoodsIv.setBackgroundResource(R.drawable.equip_details_selected_goods_bg);
        }else{
            holder.mGoodsIv.setBackgroundColor(Color.parseColor("#ffffff"));
            //holder.mGoodsIv.setBackgroundResource(R.drawable.equip_details_unselected_goods_bg);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnGoodsSelectedListener != null)
                    mOnGoodsSelectedListener.onGoodsSelected(position);
            }
        });
    }


    public void setData(List<EquipModifySelGoodsBean.DataBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    private OnGoodsSelectedListener mOnGoodsSelectedListener;

    public interface OnGoodsSelectedListener {
        void onGoodsSelected(int position);
    }

    public void setOnGoodsSelectedListener(OnGoodsSelectedListener listener) {
        mOnGoodsSelectedListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mGoodsIv;

        public ViewHolder(View itemView) {
            super(itemView);
            mGoodsIv = itemView.findViewById(R.id.goods_iv);
        }
    }
}
