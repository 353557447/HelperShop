package com.shuiwangzhijia.shuidian.newmodule.fragment;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.event.GoodsEvent;
import com.shuiwangzhijia.shuidian.event.RedPointEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.ShoppingCartAdapter;
import com.shuiwangzhijia.shuidian.ui.CommitOrderActivity;
import com.shuiwangzhijia.shuidian.utils.CalculateUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.MyUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.shuiwangzhijia.shuidian.utils.WaterHelperUtils;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@FndViewInject(contentViewId = R.layout.my_fragment_shopping_cart)
public class MyShoppingCartFragment extends BaseLazyFragment implements ShoppingCartAdapter.OnItemClickListener, ShoppingCartAdapter.OnItemSubClickListener, ShoppingCartAdapter.OnItemAddClickListener {
    @BindView(R.id.title_right_tv)
    TextView mTitleRightTv;
    @BindView(R.id.title_bar_rl)
    RelativeLayout mTitleBarRl;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.all_selected)
    CheckBox mAllSelected;
    @BindView(R.id.payBtn)
    TextView mPayBtn;
    @BindView(R.id.total_price)
    TextView mTotalPrice;
    private List<GoodsBean> mSavedShopCartList;
    private ShoppingCartAdapter mShoppingCartAdapter;
    private List<GoodsBean> mShoppingCartList;
    private boolean isManage = true;
    private boolean isAllSelect = false;
    private List<GoodsBean> buyData = new ArrayList<>();
    private int mCode;

    public MyShoppingCartFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(View view) {
        initRv();
    }

    private void initRv() {
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mShoppingCartList = new ArrayList<>();
        mShoppingCartAdapter = new ShoppingCartAdapter(mContext);
        mShoppingCartAdapter.setData(mShoppingCartList);
        mShoppingCartAdapter.setOnItemClickListener(this);
        mShoppingCartAdapter.setOnItemSubClickListener(this);
        mShoppingCartAdapter.setOnItemAddClickListener(this);
        mRv.addItemDecoration(new SpacesItemDecoration(mContext, 30));
        mRv.setAdapter(mShoppingCartAdapter);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void onResume() {
        super.onResume();
        KLog.e("我执行了么");
        if (CommonUtils.isLogin()) {
            //所有的商品
            mSavedShopCartList = CommonUtils.getShopCart();
            KLog.e(mGson.toJson(mSavedShopCartList));
            if (mSavedShopCartList != null&&mSavedShopCartList.size()!=0) {
                setCentreViewDismiss();
                mShoppingCartList.clear();
                mShoppingCartList.addAll(mSavedShopCartList);
                mShoppingCartAdapter.notifyDataSetChanged();
                setTotalPrice();
            }else{
                if(mShoppingCartAdapter.getItemCount()==0)
                setCentreViewShow(R.drawable.konggouwuche,"购物车还是空的哦~");
            }
        } else {
            mShoppingCartList.clear();
            mShoppingCartAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onVisible() {
        super.onVisible();
        if (CommonUtils.isLogin()) {
            //所有的商品
            mSavedShopCartList = CommonUtils.getShopCart();
            KLog.e(mGson.toJson(mSavedShopCartList));
            if (mSavedShopCartList != null&&mSavedShopCartList.size()!=0) {
                setCentreViewDismiss();
                mShoppingCartList.clear();
                mShoppingCartList.addAll(mSavedShopCartList);
                mShoppingCartAdapter.notifyDataSetChanged();
                setTotalPrice();
            }else{
                setCentreViewShow(R.drawable.konggouwuche,"购物车还是空的哦~");
            }
        } else {
            mShoppingCartList.clear();
            mShoppingCartAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        //当切换到其他tab时保存到本地
        CommonUtils.saveShopCart(mShoppingCartList);
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.title_right_tv, R.id.payBtn, R.id.all_selected})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_right_tv:
                isManage = !isManage;
                changeOperationUi();
                break;
            case R.id.all_selected:
                isAllSelect = !isAllSelect;
                //设置全选
                for (GoodsBean bean :
                        mShoppingCartList) {
                    bean.setCheck(isAllSelect);
                }
                mShoppingCartAdapter.notifyDataSetChanged();
                setTotalPrice();
                break;
            case R.id.payBtn:
                handlePay();
                break;
        }
    }

    private void handlePay() {
        if (isManage) {
            //购买商品
            buyData.clear();
            for (int j = 0; j < mShoppingCartList.size(); j++) {
                GoodsBean bean = mShoppingCartList.get(j);
                if (bean.isCheck()) {
                    buyData.add(bean);
                }
            }
            if (buyData.size() == 0) {
                ToastUitl.showToastCustom("请选择购买的商品!");
                return;
            }
            CommonUtils.saveBuyGoodsList(buyData);
            CommitOrderActivity.startAtc(mContext, buyData);
        } else {
            //删除商品
            KLog.e("删除商品");
            for (int i = 0; i < mShoppingCartList.size(); i++) {
                GoodsBean bean = mShoppingCartList.get(i);
                if (bean.isCheck()) {
                    mShoppingCartList.remove(i);
                    i--;
                }
            }
            mShoppingCartAdapter.notifyDataSetChanged();
            CommonUtils.saveShopCart(mShoppingCartList);
            WaterHelperUtils.updateRedPointCounts();
        }
    }


    private void getShopCartList() {
        showLoadingDialog();
        KLog.d(CommonUtils.getToken() + "\n" + packageShopCartData(mShoppingCartList));
        RetrofitUtils.getInstances().create().getCheckCart(packageShopCartData(mShoppingCartList)).enqueue(new Callback<EntityObject<ArrayList<GoodsBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<GoodsBean>>> call, Response<EntityObject<ArrayList<GoodsBean>>> response) {
                dismissLoadingDialog();
                EntityObject<ArrayList<GoodsBean>> body = response.body();
                mCode = body.getCode();
                if (body.getCode() == 800) {
                    ToastUitl.showToastCustom(body.getMsg());
                    CommonUtils.saveShopCart(null);
                    mRv.setVisibility(View.GONE);
                    EventBus.getDefault().post(new GoodsEvent(0, null));
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<GoodsBean>>> call, Throwable t) {

            }
        });
    }


    private String packageShopCartData(List<GoodsBean> data) {
        if (data == null || data.size() == 0) {
            return "";
        }
        JSONArray array = new JSONArray();
        JSONObject item = null;
        for (GoodsBean bean : data) {
            item = new JSONObject();
            try {
                item.put("active", bean.getActive());
                item.put("activity_type", bean.getActivity_type());
                item.put("gid", bean.getGid());
                item.put("gname", bean.getGname());
                item.put("picturl", bean.getPicturl());
                item.put("price", bean.getPrice());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(item);
        }
        Log.i("retrofit", array.toString());
        return array.toString();
    }

    private void changeOperationUi() {
        if (isManage) {
            mTitleRightTv.setText("管理");
            mPayBtn.setText("结算");
            mPayBtn.setBackgroundColor(Color.parseColor("#FF4D14"));
            mTotalPrice.setVisibility(View.VISIBLE);
            setTotalPrice();
        } else {
            mTitleRightTv.setText("完成");
            mPayBtn.setText("删除");
            mPayBtn.setBackgroundColor(Color.parseColor("#FE0233"));
            mTotalPrice.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemClick(int position) {
        GoodsBean goodsBean = mShoppingCartList.get(position);
        goodsBean.setCheck(!goodsBean.isCheck());
        mShoppingCartAdapter.notifyDataSetChanged();
        setTotalPrice();
    }

    @Override
    public void onItemSubClick(int position) {
        GoodsBean bean = mShoppingCartList.get(position);
        int count = bean.getCount();
        count--;
        bean.setCount(count);
        mShoppingCartAdapter.notifyDataSetChanged();
        CommonUtils.saveShopCart(mShoppingCartList);
        setTotalPrice();
    }

    @Override
    public void onItemAddClick(int position) {
        GoodsBean bean = mShoppingCartList.get(position);
        int count = bean.getCount();
        count++;
        bean.setCount(count);
        mShoppingCartAdapter.notifyDataSetChanged();
        CommonUtils.saveShopCart(mShoppingCartList);
        setTotalPrice();
    }

    private void setTotalPrice() {
        double totalPrice = 0.00;
        for (int i = 0; i < mShoppingCartList.size(); i++) {
            GoodsBean bean = mShoppingCartList.get(i);
            if (bean.isCheck()) {
                int count = bean.getCount();
                String price = bean.getPrice();
                double mul = CalculateUtils.mul(Double.parseDouble(price), count);
                totalPrice += mul;
            }
        }
        mTotalPrice.setText("合计：" + MyUtils.formatPrice(totalPrice));
    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void redPointCountsChanged(RedPointEvent event) {
        int redPointCounts = event.getRedPointCounts();
        if (redPointCounts == 0) {
            setCentreViewShow(R.drawable.konggouwuche,"购物车还是空的哦~");
        } else {
            setCentreViewDismiss();
        }
    }


}
