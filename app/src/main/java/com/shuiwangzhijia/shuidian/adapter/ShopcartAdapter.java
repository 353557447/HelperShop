package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.bean.GoodsBeanNew;
import com.shuiwangzhijia.shuidian.event.ShopCartEvent;
import com.shuiwangzhijia.shuidian.utils.PreferenceUtils;
import com.socks.library.KLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by xxc on 2019/4/2.
 */

public class ShopcartAdapter extends RecyclerView.Adapter<ShopcartAdapter.ViewHolder> {
    private final Context mContext;
    private List<GoodsBeanNew> mData;
    private boolean flag = true;

    private int selectIndex = -1;
    private boolean mIsDelete;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }

    public ShopcartAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shop_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GoodsBeanNew goodsBeanNew = mData.get(position);
        holder.mShopName.setText(goodsBeanNew.getWaterFactoryName());

        holder.mGoodsRv.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mGoodsRv.setHasFixedSize(true);
        final ShopcartItemAdapter adapter = new ShopcartItemAdapter(mContext);
        final List<GoodsBean> goodsList = goodsBeanNew.getGoodsList();
        adapter.setData(goodsList);
        holder.mGoodsRv.setAdapter(adapter);
        if (isAllChecked(goodsList)) {
            goodsBeanNew.setChecked(true);
        } else {
            goodsBeanNew.setChecked(false);
        }

        if (selectIndex == position) {
            if (goodsBeanNew.isChecked()) {
                holder.mShopCb.setChecked(true);
                setAllChecked(goodsList, true);
            } else {
                holder.mShopCb.setChecked(false);
                setAllChecked(goodsList, false);
            }
        } else if (selectIndex == -1) {
            holder.mShopCb.setChecked(false);
        } else {
            KLog.d(1111);
            holder.mShopCb.setChecked(false);
            setAllChecked(goodsList, false);
        }

        holder.mTopAllSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsBeanNew.setChecked(!goodsBeanNew.isChecked());
//                for (int i = 0; i < mData.size(); i++) {
//                    KLog.d(i+"   "+position);
//                    if (i == position){
//                        holder.mShopCb.setChecked(true);
//                        setAllChecked(goodsList, goodsBeanNew.isChecked());
//                    }else {
//                        holder.mShopCb.setChecked(false);
//                        setAllChecked(goodsList, !goodsBeanNew.isChecked());
//                    }
//                }
                boolean is_delete = PreferenceUtils.getBoolean("is_delete");
                if (!is_delete){
                    setSelectIndex(position);
                }
                holder.mShopCb.setChecked(goodsBeanNew.isChecked());
                if (goodsBeanNew.isChecked()) {
                    setAllChecked(goodsList, true);
                    // eventbus通知商品总价发生变化
                    EventBus.getDefault().post(new ShopCartEvent(position));
                    KLog.d("isChecked");
                } else {
                    setAllChecked(goodsList, false);
                    EventBus.getDefault().post(new ShopCartEvent(position));
                    KLog.d("notCheck");
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void setAllChecked(List<GoodsBean> goodsList, boolean checked) {
        for (GoodsBean bean : goodsList) {
            bean.setCheck(checked);
        }
    }

    public boolean isAllChecked(List<GoodsBean> goodsList) {
        boolean isAllChecked = true;
        for (GoodsBean bean : goodsList) {
            if (!bean.isCheck()) {
                isAllChecked = false;
            }
        }
        return isAllChecked;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<GoodsBeanNew> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public GoodsBeanNew getItem(int position) {
        return mData.get(position);
    }

    public void setIsDelete(boolean isDelete) {
        mIsDelete = isDelete;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_rv)
        RecyclerView mGoodsRv;
        @BindView(R.id.shop_cb)
        CheckBox mShopCb;
        @BindView(R.id.shop_name)
        TextView mShopName;
        @BindView(R.id.top_all_select)
        LinearLayout mTopAllSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
