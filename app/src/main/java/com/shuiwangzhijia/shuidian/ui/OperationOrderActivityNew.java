package com.shuiwangzhijia.shuidian.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.DetermineDeliveryAdapter;
import com.shuiwangzhijia.shuidian.adapter.OperationHuiShouAdapter;
import com.shuiwangzhijia.shuidian.adapter.OperationZiyingAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.BuyOrderListBean;
import com.shuiwangzhijia.shuidian.bean.DefaultWareHouseBean;
import com.shuiwangzhijia.shuidian.bean.OperationSureBean;
import com.shuiwangzhijia.shuidian.bean.OrderBean;
import com.shuiwangzhijia.shuidian.bean.OrderShowBean;
import com.shuiwangzhijia.shuidian.bean.StoreListBean;
import com.shuiwangzhijia.shuidian.dialog.BackBarrelDialog;
import com.shuiwangzhijia.shuidian.dialog.CreateOrder;
import com.shuiwangzhijia.shuidian.dialog.EditAmountDialog;
import com.shuiwangzhijia.shuidian.event.OrderListFlashEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.shuiwangzhijia.shuidian.view.MaxRecycleview;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationOrderActivityNew extends BaseAct {

    @BindView(R.id.shopName)
    TextView mShopName;
    @BindView(R.id.orderId)
    TextView mOrderId;
    @BindView(R.id.detailBtn)
    TextView mDetailBtn;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.number)
    TextView mNumber;
    @BindView(R.id.numRecyclerView)
    RecyclerView mNumRecyclerView;
    @BindView(R.id.huiShouRecyclerView)
    MaxRecycleview mHuiShouRecyclerView;
    @BindView(R.id.huiShouBucketBtn)
    TextView mHuiShouBucketBtn;
    @BindView(R.id.huitong_linear)
    LinearLayout mHuitongLinear;
    @BindView(R.id.tuiShuiRecyclerView)
    RecyclerView mTuiShuiRecyclerView;
    @BindView(R.id.tuiShuiBucketBtn)
    TextView mTuiShuiBucketBtn;
    @BindView(R.id.mSwitch)
    Switch mMSwitch;
    @BindView(R.id.zatong_bg)
    LinearLayout mZatongBg;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.remove)
    ImageView mRemove;
    @BindView(R.id.account)
    TextView mAccount;
    @BindView(R.id.add)
    ImageView mAdd;
    @BindView(R.id.zatong_lin)
    LinearLayout mZatongLin;
    @BindView(R.id.zatong_number)
    TextView mZatongNumber;
    @BindView(R.id.po_remove)
    ImageView mPoRemove;
    @BindView(R.id.po_account)
    TextView mPoAccount;
    @BindView(R.id.po_add)
    ImageView mPoAdd;
    @BindView(R.id.potong_lin)
    LinearLayout mPotongLin;
    @BindView(R.id.potong_number)
    TextView mPotongNumber;
    @BindView(R.id.ziYingRecyclerView)
    RecyclerView mZiYingRecyclerView;
    @BindView(R.id.addBucketBtn)
    TextView mAddBucketBtn;
    @BindView(R.id.butie_money)
    TextView mButieMoney;
    @BindView(R.id.numTv)
    EditText mNumTv;
    @BindView(R.id.select_zhifu)
    LinearLayout mSelectZhifu;
    @BindView(R.id.payStatus)
    TextView mPayStatus;
    @BindView(R.id.gotopay)
    TextView mGotopay;
    @BindView(R.id.delete_linear)
    LinearLayout mDeleteLinear;
    @BindView(R.id.zatong_ll)
    LinearLayout mZatongLl;
    @BindView(R.id.unPayBtn)
    TextView mUnPayBtn;
    @BindView(R.id.sureBtn)
    TextView mSureBtn;
    private int mCount = 1;
    private int poCount = 0;
    private OrderShowBean mResult;
    private int mPay_style;
    private int mPay_status;
    private int type;//已支付  未支付
    private List<OrderShowBean.RecyclerBean> mGoods1;
    private OperationZiyingAdapter mZiyingAdapter;
    private List<OrderShowBean.GoodsBean> mGoods;
    private List<OrderShowBean.RecyclerBean> mRecycler;
    private List<OrderShowBean.GoodsBean> mRefund_water;
    private Double price;
    private double mSub;
    private int mTag;
    private int mType;
    private OperationHuiShouAdapter mHuitongAdapter;
    private ArrayList<OrderShowBean.RecyclerBean> recyclerTongDialogData;
    private BuyOrderListBean data;
    private DetermineDeliveryAdapter mDdAdapter;
    private int mId;
    private ArrayList<StoreListBean> recycler;
    private String mStoreId;
    private String mOrdersn;
    private String order_sn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_order_new);
        ButterKnife.bind(this);
        setTitle("提货操作");
        data = (BuyOrderListBean) getIntent().getSerializableExtra("orderData");
        mOrdersn = getIntent().getStringExtra("ordersn");
        mTag = getIntent().getIntExtra("type", 0);
        recycler = new ArrayList<>();
        initListener();
        showLoad();
        getList();
    }

    private void initListener() {
        mNumTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //正则，用来判断是否输入了小数点
                String regex = "^\\d+.$";
                Pattern r = Pattern.compile(regex);
                Matcher matcher = r.matcher(charSequence);
                if (matcher.matches()) {
                    mNumTv.setFilters(new InputFilter[]{
                            new InputFilter.LengthFilter(charSequence.length() + 2)
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (!text.equals("")) {
                    if (text.equals("0")){
                        mGotopay.setText("确认");
                        mGotopay.setBackgroundResource(R.drawable.blue_rectangle);
                    }else {
                        mGotopay.setText("去支付");
                        mGotopay.setBackgroundResource(R.drawable.blue_rectangle);
                    }
                } else {
                    mGotopay.setText("确认");
                }
            }
        });
        mMSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    KLog.d("mMSwitch.isChecked");
                    type = 1;
                    mDeleteLinear.setVisibility(View.VISIBLE);
                    mZatongBg.setBackgroundResource(R.drawable.operation_bg_songda);
                } else {
                    KLog.d("mMSwitch.notChecked");
                    type = 0;
                    mDeleteLinear.setVisibility(View.GONE);
                    mZatongBg.setBackgroundResource(R.drawable.operation_bg_songda_new);
                    deleteZatong();
                }
            }
        });
        mSureBtn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (type == 1) {
                    hint("请先支付杂桶");
                    return;
                }
                //没有默认的仓库
                if (mId == 0){
                    CreateOrder dialog = new CreateOrder(OperationOrderActivityNew.this, recycler, new CreateOrder.OnConfirmClickListener() {
                        @Override
                        public void onConfirm(Dialog dialog, int cid) {
                            KLog.d(CommonUtils.getToken()+"\n"+order_sn+"\n"+packageGoodsData(mDdAdapter.getData())+"\n"+mId+"\n"+packageData(mHuitongAdapter.getData()));
                            dialog.dismiss();
                            RetrofitUtils.getInstances().create().sureOrder(CommonUtils.getToken(), order_sn,
                                    packageGoodsData(mDdAdapter.getData()), cid+"", packageData(mHuitongAdapter.getData())).enqueue(new Callback<EntityObject<OperationSureBean>>() {
                                @Override
                                public void onResponse(Call<EntityObject<OperationSureBean>> call, Response<EntityObject<OperationSureBean>> response) {
                                    EntityObject<OperationSureBean> body = response.body();
                                    if (body.getCode() == 200) {
                                        hint(body.getMsg());
                                        finish();
                                        EventBus.getDefault().post(new OrderListFlashEvent(true,0));
                                    } else {
                                        ToastUitl.showToastCustom(body.getMsg());
                                    }
                                }

                                @Override
                                public void onFailure(Call<EntityObject<OperationSureBean>> call, Throwable t) {

                                }
                            });
                        }
                    });
                    dialog.show();
                }else {
                    KLog.d(CommonUtils.getToken()+"\n"+order_sn+"\n"+packageGoodsData(mDdAdapter.getData())+"\n"+mId+"\n"+packageData(mHuitongAdapter.getData()));
                    RetrofitUtils.getInstances().create().sureOrder(CommonUtils.getToken(), order_sn,
                            packageGoodsData(mDdAdapter.getData()), mId+"", packageData(mHuitongAdapter.getData())).enqueue(new Callback<EntityObject<OperationSureBean>>() {
                        @Override
                        public void onResponse(Call<EntityObject<OperationSureBean>> call, Response<EntityObject<OperationSureBean>> response) {
                            EntityObject<OperationSureBean> body = response.body();
                            if (body.getCode() == 200) {
                                hint(body.getMsg());
                                finish();
                                EventBus.getDefault().post(new OrderListFlashEvent(true,0));
                            } else {
                                ToastUitl.showToastCustom(body.getMsg());
                            }
                        }

                        @Override
                        public void onFailure(Call<EntityObject<OperationSureBean>> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void deleteZatong() {
        RetrofitUtils.getInstances().create().clearChangeBucket(order_sn).enqueue(new Callback<EntityObject<String>>() {
            @Override
            public void onResponse(Call<EntityObject<String>> call, Response<EntityObject<String>> response) {
                EntityObject<String> body = response.body();
                if (body.getCode() == 200) {
                    hint("删除成功");
                } else {
                    hint(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<EntityObject<String>> call, Throwable t) {
            }
        });
    }

    private void getList() {
        if (mTag == 1){
            order_sn = mOrdersn;
        }else {
            order_sn = data.getOrder_sn();
        }

        if (order_sn == null || order_sn.equals("")){
            order_sn = data.getOrder_sn();
        }

        KLog.d(order_sn);
        //操作订单显示接口
        RetrofitUtils.getInstances().create().orderShowNew(order_sn).enqueue(new Callback<EntityObject<OrderShowBean>>() {
            @Override
            public void onResponse(Call<EntityObject<OrderShowBean>> call, Response<EntityObject<OrderShowBean>> response) {
                hintLoad();
                EntityObject<OrderShowBean> body = response.body();
                if (body != null) {
                    if (body.getCode() == 200) {
                        KLog.d(5555);
                        mResult = body.getResult();
                        mShopName.setText(mResult.getSname());
                        mOrderId.setText("订单号：" + mResult.getOrder_sn());
                        mMoney.setText("￥" + mResult.getTprice());
                        mNumber.setText(mResult.getSnum() + "");
                        //是否有换桶数据
                        OrderShowBean.ChangeBucketBean change_bucket = mResult.getChange_bucket();
                        if (change_bucket != null) {
                            mGotopay.setBackgroundResource(R.drawable.blue_rectangle);
                            mZatongBg.setBackgroundResource(R.drawable.operation_bg_songda);
                            mMSwitch.setChecked(true);
                            mPay_status = change_bucket.getPay_status();
                            if (mPay_status == 0) {
                                mPayStatus.setText("支付状态：未支付");
                                mMSwitch.setVisibility(View.VISIBLE);
                                mGotopay.setVisibility(View.VISIBLE);
                                mZatongLin.setVisibility(View.VISIBLE);
                                mPotongLin.setVisibility(View.VISIBLE);
                                mZatongNumber.setVisibility(View.GONE);
                                mPotongNumber.setVisibility(View.GONE);
                                mNumTv.setVisibility(View.VISIBLE);
                                mAddBucketBtn.setVisibility(View.VISIBLE);
                                type = 1;
                            } else {
                                if (mTag == 1){
                                    mPayStatus.setText("支付状态：支付完成");
                                }else {
                                    mPayStatus.setText("支付状态：已支付");
                                }
                                mMSwitch.setVisibility(View.GONE);
                                mGotopay.setVisibility(View.GONE);
                                mZatongLin.setVisibility(View.GONE);
                                mPotongLin.setVisibility(View.GONE);
                                mZatongNumber.setVisibility(View.VISIBLE);
                                mPotongNumber.setVisibility(View.VISIBLE);
                                mNumTv.setVisibility(View.GONE);
                                mAddBucketBtn.setVisibility(View.GONE);
                                mButieMoney.setText("￥" + change_bucket.getTotal_price());
                                mZatongNumber.setText("x" + change_bucket.getMix_num() + "");
                                mPotongNumber.setText("x" + change_bucket.getBrokenBucketNum());
                                type = 0;
                            }
                            mGoods1 = mResult.getChange_bucket().getGoods();
                        } else {
                            mDeleteLinear.setVisibility(View.GONE);
                            mZatongBg.setBackgroundResource(R.drawable.operation_bg_songda_new);
                            mMSwitch.setChecked(false);
                            mGotopay.setBackgroundResource(R.drawable.gray_rectangle);
                        }
                        if (mPay_status == 0) {
                            initZiyingRecycle(mGoods1, 1);
                        } else {
                            initZiyingRecycle(mGoods1, 2);
                        }
                        mGoods = mResult.getGoods();
                        mRecycler = mResult.getRecycler();
                        mRefund_water = mResult.getRefund_water();
                        if (mTag != 1 && mType != 1) {
                            initGoodsRecycle(mGoods);//已配送
                            initRecycler(mRecycler);//回桶自营桶
                        } else {
                            List<OrderShowBean.GoodsBean> peiSong = CommonUtils.getPeiSong();
                            List<OrderShowBean.RecyclerBean> huiTong = CommonUtils.getHuiTong();
                            initGoodsRecycle(peiSong);//已配送
                            initRecycler(huiTong);//回桶自营桶
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<OrderShowBean>> call, Throwable t) {

            }
        });

        RetrofitUtils.getInstances().create().canHuiTonglist(order_sn).enqueue(new Callback<EntityObject<ArrayList<OrderShowBean.RecyclerBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<OrderShowBean.RecyclerBean>>> call, Response<EntityObject<ArrayList<OrderShowBean.RecyclerBean>>> response) {
                EntityObject<ArrayList<OrderShowBean.RecyclerBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<OrderShowBean.RecyclerBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        return;
                    }
                    recyclerTongDialogData = result;
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<OrderShowBean.RecyclerBean>>> call, Throwable t) {

            }
        });

        RetrofitUtils.getInstances().create().defaultWareHouse(order_sn).enqueue(new Callback<EntityObject<DefaultWareHouseBean>>() {
            @Override
            public void onResponse(Call<EntityObject<DefaultWareHouseBean>> call, Response<EntityObject<DefaultWareHouseBean>> response) {
                EntityObject<DefaultWareHouseBean> body = response.body();
                if (body.getCode() == 200){
                    DefaultWareHouseBean result = body.getResult();
                    mId = result.getId();
                }
            }

            @Override
            public void onFailure(Call<EntityObject<DefaultWareHouseBean>> call, Throwable t) {

            }
        });

        RetrofitUtils.getInstances().create().getWareHouseList(order_sn).enqueue(new Callback<EntityObject<ArrayList<StoreListBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<StoreListBean>>> call, Response<EntityObject<ArrayList<StoreListBean>>> response) {
                EntityObject<ArrayList<StoreListBean>> body = response.body();
                    if (body.getCode() == 200) {
                        ArrayList<StoreListBean> result = body.getResult();
                        recycler.clear();
                        recycler.addAll(result);
                    }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<StoreListBean>>> call, Throwable t) {

            }
        });
    }

    //选择仓库dialog传回来的
//    @Subscribe(threadMode = ThreadMode.MainThread)
//    public void onMessageEventMainThread(ChuHuoEvent event) {
//        mStoreId = PreferenceUtils.getString("storeId");
//        KLog.d("\n"+CommonUtils.getToken()+"\n"+data.getOrder_sn()+"\n"+packageGoodsData(mDdAdapter.getData())+"\n"+mStoreId+"\n"+packageData(mHuitongAdapter.getData()));
//        RetrofitUtils.getInstances().create().sureOrder(CommonUtils.getToken(), data.getOrder_sn(),
//                packageGoodsData(mDdAdapter.getData()), mStoreId, packageData(mHuitongAdapter.getData())).enqueue(new Callback<EntityObject<OperationSureBean>>() {
//            @Override
//            public void onResponse(Call<EntityObject<OperationSureBean>> call, Response<EntityObject<OperationSureBean>> response) {
//                EntityObject<OperationSureBean> body = response.body();
//                if (body.getCode() == 200) {
//                    hint(body.getMsg());
//                    finish();
//                    EventBus.getDefault().post(new OrderListFlashEvent(true,0));
//                } else {
//                    ToastUitl.showToastCustom(body.getMsg());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<EntityObject<OperationSureBean>> call, Throwable t) {
//
//            }
//        });
//    }

    private void initGoodsRecycle(List<OrderShowBean.GoodsBean> goods) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mNumRecyclerView.setLayoutManager(manager);
        mDdAdapter = new DetermineDeliveryAdapter(this, goods);
        mNumRecyclerView.setAdapter(mDdAdapter);
    }

    //回桶自营桶
    private void initRecycler(List<OrderShowBean.RecyclerBean> recycler) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mHuiShouRecyclerView.setLayoutManager(manager);
        mHuitongAdapter = new OperationHuiShouAdapter(this, recycler);
        mHuitongAdapter.setOnViewItemClickListener(new OperationHuiShouAdapter.OnViewItemClickListener() {
            @Override
            public void doEditClick(int position, int count) {
                OrderShowBean.RecyclerBean item = mHuitongAdapter.getItem(position);
//                if (count < 1) {
//                    hint("购买数量不能低于最低购买量!");
//                    return;
//                }
                item.setSnum(count);
                mHuitongAdapter.notifyDataSetChanged();
            }
        });
        mHuiShouRecyclerView.setAdapter(mHuitongAdapter);
    }

    //换桶列表
    private void initZiyingRecycle(List<OrderShowBean.RecyclerBean> goods, int type) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mZiYingRecyclerView.setLayoutManager(manager);
        mZiyingAdapter = new OperationZiyingAdapter(this, goods, type);
        mZiyingAdapter.setOnItemClickListener(new OperationZiyingAdapter.OnViewItemClickListener() {
            @Override
            public void doEditCount(int position, int count) {
                OrderShowBean.RecyclerBean item = mZiyingAdapter.getItem(position);
                if (count < 1) {
                    hint("购买数量不能低于最低购买量!");
                    return;
                }
                item.setNum(count);
                mZiyingAdapter.notifyDataSetChanged();
            }
        });
        mZiYingRecyclerView.setAdapter(mZiyingAdapter);
    }

    @OnClick({R.id.detailBtn, R.id.huiShouBucketBtn, R.id.tuiShuiBucketBtn, R.id.remove, R.id.add, R.id.account, R.id.addBucketBtn, R.id.gotopay, R.id.po_add, R.id.po_account, R.id.po_remove})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gotopay:
                postGoPay();
                break;
            case R.id.addBucketBtn:
                BackBarrelDialog ziYingDialog = new BackBarrelDialog(this, recyclerTongDialogData, new BackBarrelDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog, List<OrderShowBean.RecyclerBean> data) {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setNum(1);
                        }
                        mZiyingAdapter.setData(data);
                    }
                });
                ziYingDialog.show();
                break;
            case R.id.remove:
                if (mCount <= 0) {
                    return;
                }
                mCount--;
                mAccount.setText(mCount + "");
                break;
            case R.id.add:
                mCount++;
                mAccount.setText(mCount + "");
                break;
            case R.id.account:
                EditAmountDialog mEditPurchaseAmountDialog = new EditAmountDialog(OperationOrderActivityNew.this, mCount, new EditAmountDialog.EditPurchaseAmountConfirmListener() {
                    @Override
                    public void onEditPurchaseAmountConfirm(int count) {
                        mCount = count;
                        mAccount.setText(count + "");
                    }
                });
                mEditPurchaseAmountDialog.show();
                break;
            case R.id.po_remove:
                if (poCount <= 0) {
                    return;
                }
                poCount--;
                mPoAccount.setText(poCount + "");
                break;
            case R.id.po_add:
                poCount++;
                mPoAccount.setText(poCount + "");
                break;
            case R.id.po_account:
                EditAmountDialog mPurchaseAmountDialog = new EditAmountDialog(OperationOrderActivityNew.this, poCount, new EditAmountDialog.EditPurchaseAmountConfirmListener() {
                    @Override
                    public void onEditPurchaseAmountConfirm(int count) {
                        poCount = count;
                        mPoAccount.setText(count + "");
                    }
                });
                mPurchaseAmountDialog.show();
                break;
            case R.id.detailBtn:
                PurchaseDetailsActivity.startAtc(this, data);
                break;
            case R.id.huiShouBucketBtn:
                if (mHuitongAdapter.getData() != null && recyclerTongDialogData != null) {
                    for (int i = 0; i < mHuitongAdapter.getData().size(); i++) {
                        for (int j = 0; j < recyclerTongDialogData.size(); j++) {
                            if (mHuitongAdapter.getData().get(i).getGid() == recyclerTongDialogData.get(j).getGid()) {
                                recyclerTongDialogData.get(j).setCheck(true);
                            }
                        }
                    }
                }
                BackBarrelDialog dialog = new BackBarrelDialog(this, recyclerTongDialogData, new BackBarrelDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog, List<OrderShowBean.RecyclerBean> data) {
                        mHuitongAdapter.setData(data);
                    }
                });
                dialog.show();
                break;
        }
    }

    private void postGoPay() {
        //将设置的商品保存在本地
        CommonUtils.savePeisong(mDdAdapter.getData());
        CommonUtils.saveHuiTong(mHuitongAdapter.getData());
        String count = mAccount.getText().toString().trim();
        String poNum = mPoAccount.getText().toString().trim();
        String butiePrice = mNumTv.getText().toString().trim();
        if (butiePrice.equals("")) {
            ToastUitl.showToastCustom("价格不能为空");
            return;
        }
        KLog.d("\n" + CommonUtils.getToken() + "\n" + mResult.getOrder_sn() + "\n" + count + "\n" + packageZYData(mZiyingAdapter.getData()) + "\n" + butiePrice);
        RetrofitUtils.getInstances().create().goPay(CommonUtils.getToken(), mResult.getOrder_sn(), count, poNum, packageZYData(mZiyingAdapter.getData()), butiePrice).enqueue(new Callback<EntityObject<OrderBean>>() {
            @Override
            public void onResponse(Call<EntityObject<OrderBean>> call, Response<EntityObject<OrderBean>> response) {
                EntityObject<OrderBean> body = response.body();
                if (body.getCode() == 200) {
                    OrderBean result = body.getResult();
                    OrderPayActivity.startAtc(OperationOrderActivityNew.this, result, 2);
                    finish();
                } else if (body.getCode() == 800) {
                    mType = 1;
                    KLog.d(mTag);
                    getList();
                } else {
                    hint(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<EntityObject<OrderBean>> call, Throwable t) {

            }
        });
    }

    private String packageData(List<OrderShowBean.RecyclerBean> data) {
        if (data == null || data.size() == 0) {
            return "";
        }
        JSONArray array = new JSONArray();
        JSONObject item = null;
        for (OrderShowBean.RecyclerBean bean : data) {
            item = new JSONObject();
            try {
                item.put("gid", bean.getGid());
                item.put("num", bean.getSnum());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(item);
        }
        Log.i("retrofit", array.toString());
        return array.toString();
    }

    private String packageZYData(List<OrderShowBean.RecyclerBean> data) {
        if (data == null || data.size() == 0) {
            return "";
        }
        JSONArray array = new JSONArray();
        JSONObject item = null;
        for (OrderShowBean.RecyclerBean bean : data) {
            item = new JSONObject();
            try {
                item.put("gid", bean.getGid());
                item.put("num", bean.getNum());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(item);
        }
        Log.i("retrofit", array.toString());
        return array.toString();
    }

    private String packageGoodsData(List<OrderShowBean.GoodsBean> data) {
        if (data == null || data.size() == 0) {
            return "";
        }
        JSONArray array = new JSONArray();
        JSONObject item = null;
        for (OrderShowBean.GoodsBean bean : data) {
            item = new JSONObject();
            try {
                item.put("gid", bean.getGid());
                item.put("num", bean.getSnum());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(item);
        }
        Log.i("retrofit", array.toString());
        return array.toString();
    }

    public abstract class NoDoubleClickListener implements View.OnClickListener {
        public static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime = 0;

        @Override
        public void onClick(View view) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                onNoDoubleClick(view);
            }
        }

        protected void onNoDoubleClick(View v) {

        }
    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }
}
