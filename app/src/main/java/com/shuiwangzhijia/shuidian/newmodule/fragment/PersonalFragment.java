package com.shuiwangzhijia.shuidian.newmodule.fragment;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.MyFragmentInfoBean;
import com.shuiwangzhijia.shuidian.bean.ShopInfoData;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.activity.GoodsManageNewActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.MarketingToolsActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.MyReturnMoneyNewActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.MyWalletActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.MyWalletNewActivity;
import com.shuiwangzhijia.shuidian.newmodule.activity.ShopEarningsActivity;
import com.shuiwangzhijia.shuidian.newmodule.adapter.ServiceFunctionAdapter;
import com.shuiwangzhijia.shuidian.ui.BucketManageActivity;
import com.shuiwangzhijia.shuidian.ui.CustomerManageActivity;
import com.shuiwangzhijia.shuidian.ui.LoginActivity;
import com.shuiwangzhijia.shuidian.ui.MyCouActivity;
import com.shuiwangzhijia.shuidian.ui.OrderManageActivity;
import com.shuiwangzhijia.shuidian.ui.SetActivity;
import com.shuiwangzhijia.shuidian.ui.ShopCountActivity;
import com.shuiwangzhijia.shuidian.ui.ShopInfoActivity;
import com.shuiwangzhijia.shuidian.ui.ShopManageActivity;
import com.shuiwangzhijia.shuidian.ui.ShopSpreadActivity;
import com.shuiwangzhijia.shuidian.ui.ShopStudyActivity;
import com.shuiwangzhijia.shuidian.ui.TicketActivity;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.view.MyScrollView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
@FndViewInject(contentViewId = R.layout.fragment_personal)
public class PersonalFragment extends BaseLazyFragment implements ServiceFunctionAdapter.OnItemClickListener, MyScrollView.OnScrollListener {
    @BindView(R.id.shop_head)
    ImageView mShopHead;
    @BindView(R.id.shop_name)
    TextView mShopName;
    @BindView(R.id.shop_address)
    TextView mShopAddress;
    @BindView(R.id.shop_basic_info_rl)
    RelativeLayout mShopBasicInfoRl;
    @BindView(R.id.on_or_off)
    TextView mOnOrOff;
    @BindView(R.id.shop_status_switch)
    ImageView mShopStatusSwitch;
    @BindView(R.id.total_balance)
    TextView mTotalBalance;
    @BindView(R.id.total_balance_ll)
    LinearLayout mTotalBalanceLl;
    @BindView(R.id.total_return_money)
    TextView mTotalReturnMoney;
    @BindView(R.id.total_return_money_ll)
    LinearLayout mTotalReturnMoneyLl;
    @BindView(R.id.discount_coupon_counts)
    TextView mDiscountCouponCounts;
    @BindView(R.id.discount_coupon_counts_ll)
    LinearLayout mDiscountCouponCountsLl;
    @BindView(R.id.water_coupon_counts)
    TextView mWaterCouponCounts;
    @BindView(R.id.water_coupon_counts_ll)
    LinearLayout mWaterCouponCountsLl;
    @BindView(R.id.my_wallet)
    LinearLayout mMyWallet;
    @BindView(R.id.shop_earnings)
    LinearLayout mShopEarnings;
    @BindView(R.id.empty_bucket_manage)
    LinearLayout mEmptyBucketManage;
    @BindView(R.id.new_order)
    LinearLayout mNewOrder;
    @BindView(R.id.shipping)
    LinearLayout mShipping;
    @BindView(R.id.distribution)
    LinearLayout mDistribution;
    @BindView(R.id.completed)
    LinearLayout mCompleted;
    @BindView(R.id.function_rv)
    RecyclerView mFunctionRv;
    @BindView(R.id.nest_scroll_view)
    MyScrollView mNestScrollView;
    @BindView(R.id.setting)
    ImageView mSetting;
    @BindView(R.id.check_qrcode)
    ImageView mCheckQrcode;
    Unbinder unbinder;
    @BindView(R.id.title_bar_rl)
    RelativeLayout mTitleBarRl;
    private MyFragmentInfoBean.DataBean.ShopBean mShopData;
    private boolean mSwitchFlag;

    public PersonalFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(View view) {
        initRv();
    }

    private void initRv() {
        mFunctionRv.setLayoutManager(new GridLayoutManager(mContext, 2));
        List<String> serviceTitles = new ArrayList<>();
        serviceTitles.add("店铺设置");
        serviceTitles.add("商品管理");
        serviceTitles.add("店铺统计");
        serviceTitles.add("客户管理");
        serviceTitles.add("店铺学习");
        serviceTitles.add("店铺营销");
        serviceTitles.add("店铺推广");
        serviceTitles.add("生意宝典");
        ServiceFunctionAdapter serviceFunctionAdapter = new ServiceFunctionAdapter(mContext);
        serviceFunctionAdapter.setData(serviceTitles);
        serviceFunctionAdapter.setOnItemClickListener(this);
        mFunctionRv.setAdapter(serviceFunctionAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getShopInfoData();
    }

    @Override
    protected void lazyLoadData() {

    }

    private void getShopInfoData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getMyFragmentInfo(CommonUtils.getToken()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        MyFragmentInfoBean myFragmentInfoBean = mGson.fromJson(datas, MyFragmentInfoBean.class);
                        mShopData = myFragmentInfoBean.getData().getShop();
                        //初始化店铺营业和休息状态
                        int isBusiness = mShopData.getIs_business();
                        if (isBusiness == 1) {
                            mSwitchFlag = true;
                            mOnOrOff.setText("营业中");
                        } else {
                            mSwitchFlag = false;
                            mOnOrOff.setText("已打烊");
                        }

                        //设置水店信息
                        mShopName.setText(mShopData.getSname());
                        mShopAddress.setText(mShopData.getAddress());
                        // mShopAddress.setText(mShopData.getProvince() + mShopData.getCity() + mShopData.getDist() + mShopData.getAddress());
                        setImg(mShopHead, mShopData.getHeader_pic(), "wtshop/");
                        mTotalBalance.setText(mShopData.getTotal_balance());
                        mTotalReturnMoney.setText(mShopData.getTotal_rebate_amount());
                        mDiscountCouponCounts.setText(mShopData.getTotal_coupon() + "");
                        mWaterCouponCounts.setText(mShopData.getTotal_mypicket());
                    } else if (code == -200) {
                        LoginActivity.startActLoginOut(mContext);
                        getActivity().finish();
                    } else {

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }

    @Override
    protected void initEvent() {
        mNestScrollView.setOnScrollListener(this);
    }

    @OnClick({R.id.shop_basic_info_rl, R.id.shop_status_switch, R.id.total_balance_ll, R.id.total_return_money_ll,//
            R.id.discount_coupon_counts_ll, R.id.water_coupon_counts_ll, R.id.my_wallet, R.id.shop_earnings,//
            R.id.empty_bucket_manage, R.id.new_order, R.id.shipping, R.id.distribution, R.id.completed,//
            R.id.setting, R.id.check_qrcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shop_basic_info_rl:
                ShopInfoData shopInfoData = new ShopInfoData();
                shopInfoData.setHeader_pic(mShopData.getHeader_pic());
                shopInfoData.setSname(mShopData.getSname());
                shopInfoData.setProvice(mShopData.getProvince());
                shopInfoData.setCity(mShopData.getCity());
                shopInfoData.setDist(mShopData.getDist());
                shopInfoData.setAddress(mShopData.getAddress());
                shopInfoData.setLnglat(mShopData.getLnglat());
                shopInfoData.setIs_perfect(mShopData.getIs_perfect());
                shopInfoData.setAccount(mShopData.getAccount());
                ShopInfoActivity.startAtc(getActivity(), shopInfoData);
                break;
            case R.id.shop_status_switch:
                getShopState();
                break;
            case R.id.total_balance_ll:
                skipActivity(MyWalletNewActivity.class);
                break;
            case R.id.total_return_money_ll:
               skipActivity(MyReturnMoneyNewActivity.class);
                break;
            case R.id.discount_coupon_counts_ll:
                skipActivity(MyCouActivity.class);
                break;
            case R.id.water_coupon_counts_ll:
                skipActivity(TicketActivity.class);
                break;
            case R.id.my_wallet:
                skipActivity(MyWalletNewActivity.class);
                break;
            case R.id.shop_earnings:
                skipActivity(ShopEarningsActivity.class);
                break;
            case R.id.empty_bucket_manage:
                skipActivity(BucketManageActivity.class);
                break;
            case R.id.new_order:
                OrderManageActivity.startAtc(mContext, 0);
                break;
            case R.id.shipping:
                OrderManageActivity.startAtc(mContext, 1);
                break;
            case R.id.distribution:
                OrderManageActivity.startAtc(mContext, 2);
                break;
            case R.id.completed:
                OrderManageActivity.startAtc(mContext, 3);
                break;
            case R.id.setting:
                skipActivity(SetActivity.class);
                break;
            case R.id.check_qrcode:
                break;
        }
    }

    private void getShopState() {
        RetrofitUtils.getInstances().create().getShopState().enqueue(new Callback<EntityObject<Object>>() {
            @Override
            public void onResponse(Call<EntityObject<Object>> call, Response<EntityObject<Object>> response) {
                EntityObject<Object> body = response.body();
                if (body.getCode() == 200) {
                    mSwitchFlag = !mSwitchFlag;
                    if (mSwitchFlag) {
                        mOnOrOff.setText("营业中");
                    } else {
                        mOnOrOff.setText("已打烊");
                    }
                } else {
                    if (body.getScode() == -200) {
                        EventBus.getDefault().post(new LoginOutEvent());
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<Object>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                skipActivity(ShopManageActivity.class);
                break;
            case 1:
                skipActivity(GoodsManageNewActivity.class);
                break;
            case 2:
                skipActivity(ShopCountActivity.class);
                break;
            case 3:
                skipActivity(CustomerManageActivity.class);
                break;
            case 4:
                skipActivity(ShopStudyActivity.class);
                break;
            case 5:
                skipActivity(MarketingToolsActivity.class);
                break;
            case 6:
                skipActivity(ShopSpreadActivity.class);
                break;
            case 7:
                showToast("敬请期待");
                break;
        }
    }

    @Override
    public void onScroll(int scrollY) {
        if (scrollY < 500 && scrollY > 0) {
            int tras = scrollY / 5;
            if (tras > 10) {
                mTitleBarRl.setBackgroundColor(Color.parseColor("#" + tras + "FF014E"));
            }
        } else if (scrollY >= 500) {
            mTitleBarRl.setBackgroundColor(Color.parseColor("#FF014E"));
        } else {
            mTitleBarRl.setBackgroundColor(Color.parseColor("#00ffffff"));
        }
    }
}
