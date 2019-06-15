package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.dialog.EditPurchaseAmountDialog;
import com.shuiwangzhijia.shuidian.utils.DensityUtils;
import com.shuiwangzhijia.shuidian.view.SlidingButtonView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车适配器
 * created by wangsuli on 2018/8/17.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> implements SlidingButtonView.IonSlidingButtonListener {
    private final Context mContext;

    private OnViewItemClickListener mOnViewItemClickListener;
    private SlidingButtonView mMenu;

    public List<GoodsBean> getData() {
        return data;
    }

    private List<GoodsBean> data;

    public ShoppingCartAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shopping_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.check.setTag(position);
        holder.cutBtn.setTag(position);
        holder.addBtn.setTag(position);
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                }
                mOnViewItemClickListener.onSelectClick((int) view.getTag());
            }
        });
        holder.cutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                }
                mOnViewItemClickListener.onCutClick((int) view.getTag());
            }
        });
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                }
                mOnViewItemClickListener.onAddClick((int) view.getTag());
            }
        });
        holder.num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                }
                EditPurchaseAmountDialog dialog = new EditPurchaseAmountDialog(mContext, data.get(position), new EditPurchaseAmountDialog.EditPurchaseAmountConfirmListener() {
                    @Override
                    public void onEditPurchaseAmountConfirm(int count) {
                        if (mOnViewItemClickListener != null) {
                            mOnViewItemClickListener.doEditCount(position, count);
                        }
                    }
                });
                dialog.show();
            }
        });
        GoodsBean bean = getItem(position);
        Glide.with(mContext).load(Constant.GOODS_IMAGE_URL + bean.getPicturl()).placeholder(R.drawable.wutupian).into(holder.image);
        holder.name.setText(bean.getGname());
        holder.price.setText("￥" + bean.getPrice());
        holder.num.setText("" + bean.getCount());
        holder.check.setChecked(bean.isCheck());
        holder.layoutContent.getLayoutParams().width = DensityUtils.getScreenWidth(mContext);
        holder.tvDelete.setTag(position);
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                }
                mOnViewItemClickListener.onDeleteClick((Integer) view.getTag());
            }
        });
    }

    public void setData(List<GoodsBean> result) {
        data = result;
        notifyDataSetChanged();
    }

    public GoodsBean getItem(int position) {
        return data.get(position);
    }

    public void deleteItem(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        mOnViewItemClickListener = listener;
    }

    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    @Override
    public void onDownOrMove(SlidingButtonView slidingDeleteView) {
        if (menuIsOpen()) {
            if (mMenu != slidingDeleteView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (mMenu != null) {
            mMenu.closeMenu();
            mMenu = null;
        }

    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }


    public interface OnViewItemClickListener {
        void onSelectClick(int position);

        void onCutClick(int position);

        void onAddClick(int position);

        void onDeleteClick(int position);

        void doEditCount(int position, int count);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.check)
        CheckBox check;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.cutBtn)
        TextView cutBtn;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.addBtn)
        TextView addBtn;
        @BindView(R.id.layout_content)
        LinearLayout layoutContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ((SlidingButtonView) itemView).setSlidingButtonListener(ShoppingCartAdapter.this);
        }
    }


}
