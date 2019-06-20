package com.shuiwangzhijia.shuidian.dialog;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.OftenBuyAdapter;
import com.shuiwangzhijia.shuidian.bean.ShowPlantsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderlDialog extends BottomDialog implements OftenBuyAdapter.OnViewItemClickListener {

    private final Context mContext;
    @BindView(R.id.name)
    TextView mName;
    private ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> data;
    private String mSname;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.commit)
    TextView mCommit;
    private OftenBuyAdapter mAdapter;
    private List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> newData = new ArrayList<>();

    public OrderlDialog(Context context, ArrayList<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> mData, String name, final OnConfirmClickListener listener) {
        super(context);
        setContentView(R.layout.choice_order_dialog);
        this.mContext = context;
        this.data = mData;
        this.mSname = name;
        setCanceledOnTouchOutside(true);
        ButterKnife.bind(this);
        initRecyleView();
        newData.clear();
        mName.setText(mSname);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ShowPlantsBean.HarvestInfoBean.OrderGoodsBean bean : mAdapter.getData()){
                    if (bean.getCount()>0){
                        newData.add(bean);
                    }
                }
                if (listener != null) {
                    listener.onConfirm(newData);
                }
            }
        });
    }

    private void initRecyleView() {
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(mContext.getResources().getDrawable(R.drawable.divider_bg_tran));
        mRv.addItemDecoration(divider);
        mAdapter = new OftenBuyAdapter(mContext);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setData(data);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void onCutClick(int position) {
        ShowPlantsBean.HarvestInfoBean.OrderGoodsBean item = mAdapter.getItem(position);
        int count = item.getCount();
        if (count <= 0){
            return;
        }
        count--;
        item.setCount(count);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddClick(int position) {
        ShowPlantsBean.HarvestInfoBean.OrderGoodsBean item = mAdapter.getItem(position);
        item.setCount(item.getCount() + 1);
        mAdapter.notifyDataSetChanged();
    }

    public interface OnConfirmClickListener {
        void onConfirm(List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> data);
    }
}
