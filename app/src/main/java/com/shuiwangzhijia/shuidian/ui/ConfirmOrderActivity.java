package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.OrderItemAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.AddressBean;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.bean.OrderBean;
import com.shuiwangzhijia.shuidian.bean.TicketBean;
import com.shuiwangzhijia.shuidian.bean.UseTicketBean;
import com.shuiwangzhijia.shuidian.event.AddressEvent;
import com.shuiwangzhijia.shuidian.event.PayFinishEvent;
import com.shuiwangzhijia.shuidian.event.TicketEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CalculateUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 确认订单页面
 * created by wangsuli on 2018/8/22.
 */
public class ConfirmOrderActivity extends BaseAct {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.count)
    TextView count;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.payBtn)
    TextView payBtn;
    @BindView(R.id.llAddress)
    LinearLayout llAddress;
    @BindView(R.id.ticketDesc)
    TextView ticketDesc;
    @BindView(R.id.llTicket)
    LinearLayout llTicket;
    private OrderItemAdapter mOrderAdapter;
    private List<GoodsBean> buyData = new ArrayList<>();
    private String totalMoney;
    private AddressBean addressBean;
    private boolean isOrderManage;
    private String data;
    private UseTicketBean useTicketBean;
    private TicketEvent mTicketEvent;
    private double sub;

    public static void startAtc(Context context, List<GoodsBean> data, String total, boolean isOrderManage) {
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        intent.putExtra("data", (Serializable) data);
        intent.putExtra("money", total);
        intent.putExtra("isOrderManage", isOrderManage);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        setTitle("确认订单");
        buyData = (List<GoodsBean>) getIntent().getSerializableExtra("data");
        totalMoney = getIntent().getStringExtra("money");
        isOrderManage = getIntent().getBooleanExtra("isOrderManage", false);
        money.setText("￥" + totalMoney);
        shopName.setText(buyData.get(0).getSname());
        getDefaultAddress();
        initRecyclerView();
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                count.setText(text.length() + "/200");
            }
        });
        data = packageData();
        getTicketInfo();
    }

    private void getTicketInfo() {
        RetrofitUtils.getInstances().create().showTicketsUse(data).enqueue(new Callback<EntityObject<UseTicketBean>>() {
            @Override
            public void onResponse(Call<EntityObject<UseTicketBean>> call, Response<EntityObject<UseTicketBean>> response) {
                EntityObject<UseTicketBean> body = response.body();
                if (body.getCode() == 200) {
                    useTicketBean = body.getResult();
                    sub = CalculateUtils.sub(Double.valueOf(totalMoney), useTicketBean.getAmount());
                    money.setText("￥"+sub);
                    setNumStyle(ticketDesc, "你已选", "" + useTicketBean.getCount(), "张水票，可抵", "" + useTicketBean.getAmount(), "元");

                }
            }

            @Override
            public void onFailure(Call<EntityObject<UseTicketBean>> call, Throwable t) {

            }
        });
    }

    private void getDefaultAddress() {
        showLoad();
        RetrofitUtils.getInstances().create().getDefaultAddress().enqueue(new Callback<EntityObject<ArrayList<AddressBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<AddressBean>>> call, Response<EntityObject<ArrayList<AddressBean>>> response) {
                hintLoad();
                EntityObject<ArrayList<AddressBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<AddressBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        name.setText("请选择收货人地址信息");
                        phone.setText("");
                        address.setText("");
                    } else {
                        addressBean = result.get(0);
                        name.setText("联系人:" + addressBean.getAname());
                        phone.setText(addressBean.getSphone());
                        address.setText("收货地址:" + addressBean.getDaddress());
                    }

                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<AddressBean>>> call, Throwable t) {

            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(this.getResources().getDrawable(R.drawable.divider_bg_white));
        mRecyclerView.addItemDecoration(divider);
        mOrderAdapter = new OrderItemAdapter(this, isOrderManage);
        mOrderAdapter.setData(buyData);
        mRecyclerView.setAdapter(mOrderAdapter);
    }

    @OnClick({R.id.payBtn, R.id.llAddress, R.id.llTicket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llAddress:
                startActivity(new Intent(ConfirmOrderActivity.this, AddressManageActivity.class));
                break;
            case R.id.payBtn:
                postpay();
                break;
            case R.id.llTicket:
                if (useTicketBean == null) {
                    hint("没有优惠水票...");
                    return;
                }
                ArrayList<TicketBean> ticketBeanList = useTicketBean.getList();
                if (ticketBeanList == null || ticketBeanList.size() == 0) {
                    hint("没有优惠可选");
                    return;
                }
                //传商品 便于对比数据
                useTicketBean.setGoodsList(buyData);
                DiscountTicketActivity.startAtc(this, -2, useTicketBean);
                break;
        }
    }

    private void postpay() {
        if (addressBean == null) {
            hint("请选择收货人地址信息...");
            return;
        }

        String address = addressBean.getAname() + " " + addressBean.getSphone() + " " + addressBean.getDaddress();

        double ticketMoney = 0;
        String dataTicket = "";
        if (useTicketBean != null) {
            dataTicket = packageTicketData();
            ticketMoney = useTicketBean.getAmount();
        }
        if (mTicketEvent != null) {
            dataTicket = mTicketEvent.data;
            ticketMoney = mTicketEvent.amount;
        }
        sub = CalculateUtils.sub(Double.valueOf(totalMoney), ticketMoney);
        if (sub < 0) {
            sub = 0;
        }
        Log.i("createOrder", "ticketMoney=" + ticketMoney + "sprice=" + sub + "&addr=" + address + "&data=" + data + "&datas=" + dataTicket);
        showLoad();
        RetrofitUtils.getInstances().create().createOrder(CommonUtils.getToken(),sub + "", address, data, dataTicket, edit.getText().toString(),addressBean.getId()).enqueue(new Callback<EntityObject<OrderBean>>() {
            @Override
            public void onResponse(Call<EntityObject<OrderBean>> call, Response<EntityObject<OrderBean>> response) {
                hintLoad();
                EntityObject<OrderBean> body = response.body();
                //水票抵扣，不用进入订单支付页面
                if (body.getScode() == 700){
                    startActivity(new Intent(ConfirmOrderActivity.this, PurchaseOrderActivity.class));
                    finish();
                }
                if (body.getCode() == 200) {
                    updateShopCartInfo();
                    OrderBean result = body.getResult();
                    if (result.getIs_suc() == 1) {
                        hint("支付成功!");
                        EventBus.getDefault().post(new PayFinishEvent());
                        finish();
                    } else {
                        OrderPayActivity.startAtc(ConfirmOrderActivity.this, result,1);
                        finish();
                    }

                } else {
                    hint(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<EntityObject<OrderBean>> call, Throwable t) {

            }
        });
    }
    private void updateShopCartInfo() {
        List<GoodsBean> shopCart = CommonUtils.getShopCart();
        List<GoodsBean> newShopCart = new ArrayList<>();
        List<GoodsBean> buyGoodsList = CommonUtils.getBuyGoodsList();
        if (buyGoodsList != null) {
            GoodsBean buy, item = null;
            for (int j = 0; j < shopCart.size(); j++) {
                buy = shopCart.get(j);
                boolean remove = false;
                for (int i = 0; i < buyGoodsList.size(); i++) {
                    item = buyGoodsList.get(i);
                    if (buy.getGid().equals(item.getGid())) {
                        remove = true;
                    }
                }
                if (!remove) {
                    newShopCart.add(buy);
                }

            }
            CommonUtils.saveBuyGoodsList(null);
            CommonUtils.saveShopCart(newShopCart);
        }

    }

    /**
     * 组装数据
     *
     * @return
     */
    private String packageData() {
        JSONArray array = new JSONArray();
        JSONObject item = null;
        for (GoodsBean bean : buyData) {
            item = new JSONObject();
            try {
                item.put("did", bean.getDid());
                item.put("gid", bean.getGid());
                item.put("num", bean.getCount());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(item);
        }
        return array.toString();
    }

    private String packageTicketData() {
        JSONArray array = new JSONArray();
        JSONObject item = null;
        for (TicketBean bean : useTicketBean.getList()) {
            if (bean.getUse() == 1) {
                item = new JSONObject();
                try {
                    item.put("s_gid", bean.getS_gid());
                    item.put("num", bean.getTotal());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.put(item);
            }
        }
        return array.toString();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(TicketEvent event) {
        mTicketEvent = event;
        sub = CalculateUtils.sub(Double.valueOf(totalMoney), event.amount);
        money.setText("￥"+sub);
        setNumStyle(ticketDesc, "你已选", "" + event.count, "张水票，可抵", "" + event.amount, "元");

    }

    private void setNumStyle(TextView text, String first, String count, String second, String amount, String third) {
        SpannableString spanString = new SpannableString(first + count + second + amount + third);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_ff3939)), first.length(), (count + first).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_ff3939)), (count + first + second).length(), (count + first + second + amount).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        text.setText(spanString);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(PayFinishEvent event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(AddressEvent event) {
        if (event.isDelete) {
            AddressBean data = event.data;
            if (data.getId() == addressBean.getId()) {
                name.setText("请选择收货人地址信息");
                phone.setText("");
                address.setText("");
            }
            addressBean = null;
            if (data.getDefaultInt() != 0) {
                getDefaultAddress();
            } else {
                getListAddress();
            }

        } else {
            if (event.data != null) {
                addressBean = event.data;
                name.setText("联系人:" + addressBean.getAname());
                phone.setText(addressBean.getSphone());
                address.setText("收货地址:" + addressBean.getDaddress());
            }
        }
    }

    private void getListAddress() {
        RetrofitUtils.getInstances().create().getAddressList().enqueue(new Callback<EntityObject<ArrayList<AddressBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<AddressBean>>> call, Response<EntityObject<ArrayList<AddressBean>>> response) {
                hintLoad();
                EntityObject<ArrayList<AddressBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<AddressBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        name.setText("请选择收货人地址信息");
                        phone.setText("");
                        address.setText("");
                    } else {
                        addressBean = result.get(0);
                        name.setText("联系人:" + addressBean.getAname());
                        phone.setText(addressBean.getSphone());
                        address.setText("收货地址:" + addressBean.getDaddress());
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<AddressBean>>> call, Throwable t) {

            }
        });
    }


    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }

}
