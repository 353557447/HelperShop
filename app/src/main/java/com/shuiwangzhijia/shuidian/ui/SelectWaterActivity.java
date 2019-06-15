package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.SelectWaterAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.bean.ShowPlantsBean;
import com.shuiwangzhijia.shuidian.bean.TicketBean;
import com.shuiwangzhijia.shuidian.bean.UseTicketBean;
import com.shuiwangzhijia.shuidian.event.TicketEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CalculateUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectWaterActivity extends BaseAct implements SwipeRefreshLayout.OnRefreshListener, SelectWaterAdapter.OnViewItemClickListener {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRv;
    @BindView(R.id.hintBtn)
    TextView mHintBtn;
    @BindView(R.id.payBtn)
    TextView mPayBtn;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    private int type;
    private UseTicketBean data;
    private Map<String, Integer> ticketData = new HashMap<>();
    private Map<String,Integer> originTicketData=new HashMap<>();
    private List<ShowPlantsBean.HarvestInfoBean.OrderGoodsBean> mGoodsListNew;
    private List<GoodsBean> mGoodsList;
    private ArrayList<TicketBean> dataList;
    private SelectWaterAdapter mSelectWaterAdapter;
    private LinearLayoutManager layoutManager;
    private String gid;
    private int count;
    private double amount;
    private int total;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x101:
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        completeSwipeRefresh();
                    }

                    break;
            }
        }
    };

    public static void startAtc(Context context, int type, UseTicketBean data) {
        Intent intent = new Intent(context, SelectWaterActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_water);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", -1);
        initRecycleView();
        if (type == -1){
            setTitle("优惠水票");
            onRefresh();
        }else if (type == -3){
            setTitle("选择水票");
            data = (UseTicketBean) getIntent().getSerializableExtra("data");
            mGoodsList = data.getGoodsList();
            for (GoodsBean goodsBean : mGoodsList){
                KLog.d(goodsBean.getCount()+"   "+goodsBean.getGid());
                ticketData.put(goodsBean.getGid(), goodsBean.getCount());
                originTicketData.put(goodsBean.getGid(),goodsBean.getCount());
            }
            dataList = data.getList();
            mSelectWaterAdapter.setData(dataList);
        }else {
            setTitle("选择水票");
            data = (UseTicketBean) getIntent().getSerializableExtra("data");
            mGoodsListNew = data.getGoodsListNew();
            if (mGoodsListNew != null){
                for (ShowPlantsBean.HarvestInfoBean.OrderGoodsBean goodsBean : mGoodsListNew){
                    ticketData.put(goodsBean.getGid(), goodsBean.getCount());
                    originTicketData.put(goodsBean.getGid(),goodsBean.getCount());
                }
            }
            dataList = data.getList();
            mSelectWaterAdapter.setData(dataList);
        }
    }

    private void initRecycleView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        mRv.setLayoutManager(layoutManager);
        mRv.setHasFixedSize(true);
//        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg_tran));
//        mRv.addItemDecoration(divider);
        mSelectWaterAdapter = new SelectWaterAdapter(this);
        mSelectWaterAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mSelectWaterAdapter);
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (type == -1 && dy > 0 && lastVisibleItemPosition + 1 == layoutManager.getItemCount()) {
                    if (loading) {
                        loading = false;
                        new LoadDataThread().start();
                    }
                }
            }
        });
    }

    @OnClick(R.id.payBtn)
    public void onViewClicked() {
        List<TicketBean> selectData = new ArrayList<>();
        for (int i = 0; i < mSelectWaterAdapter.getItemCount(); i++) {
            TicketBean item = mSelectWaterAdapter.getItem(i);
            if (item.getCount() > 0) {
                gid = item.getGid();
                //与商品的gid 一致后 看选择的商品数量和当前水票的可使用张数作对比
                if (ticketData.containsKey(gid)) {
                   // int count = ticketData.get(gid);
                    count=  item.getCount();
                    KLog.d(count);
                    if (count > 0) {
                        // 当水票的张数高于商品的数量 直接使用当前的水票
                        // 否则 不够的水票数量 用下一个水票数量
                     /*   if (CalculateUtils.sub(item.getSum(), count) > 0) {
                            item.setTotal(item.getCount() + "");
                            ticketData.put(gid, 0);
                        } else {
                            item.setTotal(item.getSum() + "");
                            ticketData.put(gid, (int) CalculateUtils.sub(count, item.getSum()));
                        }*/
                        selectData.add(item);
                    }

                }

            }
        }
        String dataInfo = packageTicketData(selectData);
        KLog.d(dataInfo);
        Log.i("tag", "水票数据:" + dataInfo);
        EventBus.getDefault().post(new TicketEvent(count, amount, dataInfo));
        finish();
    }

    private String packageTicketData(List<TicketBean> dataList) {
        count = 0;
        amount = 0;
        JSONArray array = new JSONArray();
        JSONObject item = null;
        for (TicketBean bean : dataList) {
            int total = Integer.valueOf(bean.getCount());
            count = count + total;
            amount = CalculateUtils.add(amount, CalculateUtils.mul(total, bean.getPprice()));
            item = new JSONObject();
            try {
                item.put("s_gid", bean.getS_gid());
                item.put("gid", bean.getGid());
                item.put("num", bean.getCount());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(item);
        }
        return array.toString();
    }

    @Override
    public void onRefresh() {
        if (type == -1) {
            page = -1;
            showLoad();
            new LoadDataThread().start();
        } else {
            completeSwipeRefresh();
        }
    }

    @Override
    public void onAddGoodsClick(int position) {
        TicketBean item = mSelectWaterAdapter.getItem(position);
        int value = 0;
        if (ticketData.containsKey(item.getGid())){
            Integer integer = ticketData.get(item.getGid());
            value = integer.intValue();
            KLog.d(value);
        }
        int count = item.getCount();
        KLog.e("count:"+count+"   "+"value: "+value);
        count++;
        if (count>item.getSum() || value==0){
            if (count>item.getSum()){
                ToastUitl.showToastCustom("最多选择"+item.getSum()+"张");
                item.setCount(--count);
                return;
            }
            if (count>value){
                ToastUitl.showToastCustom("最多选择"+originTicketData.get(item.getGid())+"张");
                item.setCount(--count);
                return;
            }
        }
        item.setCount(count);

        if (ticketData.containsKey(item.getGid())){
            Integer integer = ticketData.get(item.getGid());
            KLog.d(integer-1);
            ticketData.put(item.getGid(),integer-1);
        }

        mSelectWaterAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void onSubtractClick(int position) {
        TicketBean item = mSelectWaterAdapter.getItem(position);
        int count = item.getCount();
        count--;
        if (count<0){
            item.setCount(++count);
            return;
        }
        item.setCount(count);

        if (ticketData.containsKey(item.getGid())){
            Integer integer = ticketData.get(item.getGid());
            ticketData.put(item.getGid(),integer+1);
        }

        mSelectWaterAdapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends Thread {
        @Override
        public void run() {
            page++;
            getList();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }
    }

    private void getList() {
        RetrofitUtils.getInstances().create().getDiscountTicketList(page, PageSize).enqueue(new Callback<EntityObject<ArrayList<TicketBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<TicketBean>>> call, Response<EntityObject<ArrayList<TicketBean>>> response) {
                completeSwipeRefresh();
                hintLoad();
                EntityObject<ArrayList<TicketBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<TicketBean> result = body.getResult();
                    if (result == null) {
                        loading = false;
                        rlEmpty.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                        return;
                    } else {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        rlEmpty.setVisibility(View.GONE);
                        if (result.size() < PageSize) {
                            loading = false;
                        } else {
                            loading = true;
                        }
                        if (page == 0) {
                            mSelectWaterAdapter.setData(result);
                        } else {
                            mSelectWaterAdapter.addData(result);
                        }

                    }

                } else {
                    if (page == 0) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                    }
                    loading = false;
                }

            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<TicketBean>>> call, Throwable t) {
                rlEmpty.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
                Log.i("json", t.getMessage());
            }
        });
    }

    private void calculate() {
        total = 0;
        ArrayList<TicketBean> data = mSelectWaterAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getCount() > 0){
                total += data.get(i).getCount();
            }
        }
        mHintBtn.setText("已选"+total+"张水票");
    }

    private void completeSwipeRefresh() {
        if (swipeRefreshLayout == null)
            return;
        swipeRefreshLayout.setRefreshing(false);
    }
}
