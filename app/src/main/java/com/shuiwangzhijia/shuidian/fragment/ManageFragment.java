package com.shuiwangzhijia.shuidian.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.ManageAdapter;
import com.shuiwangzhijia.shuidian.base.BaseFragment;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.ShopBean;
import com.shuiwangzhijia.shuidian.bean.ShopInfoData;
import com.shuiwangzhijia.shuidian.bean.TicketBean;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.ui.CustomerManageActivity;
import com.shuiwangzhijia.shuidian.ui.DiscountTicketActivity;
import com.shuiwangzhijia.shuidian.ui.GoodsManageActivity;
import com.shuiwangzhijia.shuidian.ui.MsgActivity;
import com.shuiwangzhijia.shuidian.ui.OrderManageActivity;
import com.shuiwangzhijia.shuidian.ui.PurchaseOrderActivity;
import com.shuiwangzhijia.shuidian.ui.SetActivity;
import com.shuiwangzhijia.shuidian.ui.ShopCountActivity;
import com.shuiwangzhijia.shuidian.ui.ShopManageActivity;
import com.shuiwangzhijia.shuidian.ui.ShopMarketActivity;
import com.shuiwangzhijia.shuidian.ui.ShopSpreadActivity;
import com.shuiwangzhijia.shuidian.ui.ShopStudyActivity;
import com.shuiwangzhijia.shuidian.ui.BucketManageActivity;
import com.shuiwangzhijia.shuidian.ui.WalletActivity;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.GridDividerItemDecoration;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.shuiwangzhijia.shuidian.view.VerticalTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 管理页面
 * created by wangsuli on 2018/8/17.
 */
public class ManageFragment extends BaseFragment implements ManageAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.mSwitch)
    Switch mSwitch;
    @BindView(R.id.onOrOff)
    TextView onOrOff;
    @BindView(R.id.shopAddress)
    TextView shopAddress;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.msgBtn)
    ImageView msgBtn;
    @BindView(R.id.setBtn)
    ImageView setBtn;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.verticalTextView)
    VerticalTextView verticalTextView;
    @BindView(R.id.llTicket)
    LinearLayout llTicket;
    @BindView(R.id.buyBtn)
    RelativeLayout buyBtn;
    @BindView(R.id.sailBtn)
    RelativeLayout sailBtn;
    private ManageAdapter manageAdapter;
    private ShopBean shopBean;
    private boolean mSwitchFlag = false;
    private ArrayList<String> titleList = new ArrayList<>();
    private ShopInfoData shopInfoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_manage, container, false);
            unbinder = ButterKnife.bind(this, mRootView);
            initView();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            unbinder = ButterKnife.bind(this, mRootView);
        }
        getTicketInfo();
        return mRootView;
    }

    private void getTicketInfo() {
        RetrofitUtils.getInstances().create().getDiscountTicketList(0, 10).enqueue(new Callback<EntityObject<ArrayList<TicketBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<TicketBean>>> call, Response<EntityObject<ArrayList<TicketBean>>> response) {
                EntityObject<ArrayList<TicketBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<TicketBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        verticalTextView.setVisibility(View.GONE);
                        return;
                    } else {
                        for (TicketBean bean : result) {
                            titleList.add(bean.getSnum() + "张水票，仅售" + bean.getSprice() + "元！");
                        }
                        verticalTextView.setTextList(titleList);
                        verticalTextView.startAutoScroll();
                    }
                }

            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<TicketBean>>> call, Throwable t) {
                Log.i("json", t.getMessage());
            }
        });
    }

    private void initView() {
        initRecyclerView();
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getShopSate();
            }
        });
        verticalTextView.setText(13, 5, Color.WHITE);//设置属性
        verticalTextView.setTextStillTime(3000);//设置停留时长间隔
        verticalTextView.setAnimTime(100);//设置进入和退出的时间间隔
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoad();
        getShopInfo();
    }

    /**
     * 营业状态
     */
    private void getShopSate() {
        RetrofitUtils.getInstances().create().getShopState().enqueue(new Callback<EntityObject<Object>>() {
            @Override
            public void onResponse(Call<EntityObject<Object>> call, Response<EntityObject<Object>> response) {
                EntityObject<Object> body = response.body();
                if (body.getCode() == 200) {
                    mSwitchFlag = !mSwitchFlag;
                    if (mSwitchFlag) {
                        onOrOff.setText("营业中");
                    } else {
                        onOrOff.setText("休息中");
                    }
                    onOrOff.setSelected(mSwitchFlag);
                    mSwitch.setChecked(mSwitchFlag);
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

    private void getShopInfo() {
        RetrofitUtils.getInstances().create().getShopInfo().enqueue(new Callback<EntityObject<ShopBean>>() {
            @Override
            public void onResponse(Call<EntityObject<ShopBean>> call, Response<EntityObject<ShopBean>> response) {
                hintLoad();
                EntityObject<ShopBean> body = response.body();
                if (body.getCode() == 200) {
                    shopBean = body.getResult();
                    CommonUtils.saveShopInfo(shopBean);
                    shopName.setText(shopBean.getSname());
                    shopAddress.setText(shopBean.getAddress());
                    int isBusiness = shopBean.getIs_business();
                    if (isBusiness == 1) {
                        mSwitchFlag = true;
                        mSwitch.setChecked(true);
                        onOrOff.setText("营业中");
                        onOrOff.setSelected(true);
                    } else {
                        mSwitchFlag = false;
                        mSwitch.setChecked(false);
                        onOrOff.setText("休息中");
                        onOrOff.setSelected(false);
                    }
                    Glide.with(getActivity()).load(Constant.SHOP_IMAGE_URL + shopBean.getHeader_pic()).placeholder(R.drawable.morenshuidian).into(image);
                } else {
                    if (body.getScode() == -200) {
                        EventBus.getDefault().post(new LoginOutEvent());
                    }
                }

            }

            @Override
            public void onFailure(Call<EntityObject<ShopBean>> call, Throwable t) {

            }
        });

        RetrofitUtils.getInstances().create().getShopInfoData().enqueue(new Callback<EntityObject<ShopInfoData>>() {
            @Override
            public void onResponse(Call<EntityObject<ShopInfoData>> call, Response<EntityObject<ShopInfoData>> response) {
                EntityObject<ShopInfoData> body = response.body();
                if (body.getCode() == 200) {
                    shopInfoData = body.getResult();
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ShopInfoData>> call, Throwable t) {

            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(1, mContext.getResources().getColor(R.color.color_e5e5e5)));
        manageAdapter = new ManageAdapter(getActivity());
        manageAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(manageAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        verticalTextView.stopAutoScroll();
    }


    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), ShopManageActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), GoodsManageActivity.class));
                break;
            case 2:
                if (shopBean == null) {
                    ToastUitl.showToastCustom("正加载数据...");
                    return;
                }
                WalletActivity.startAtc(getActivity(), shopBean.getBanlance());
                break;
            case 3:
                startActivity(new Intent(getActivity(), CustomerManageActivity.class));
                break;
            case 4:
                startActivity(new Intent(getActivity(), ShopCountActivity.class));
                break;
            case 5:
                startActivity(new Intent(getActivity(), ShopStudyActivity.class));
                break;
            case 6:
                startActivity(new Intent(getActivity(), ShopSpreadActivity.class));
                break;
            case 7:
                startActivity(new Intent(getActivity(), ShopMarketActivity.class));
                break;
            case 8:
//                ToastUitl.showToastCustom("正在开发中，敬请期待...");
                startActivity(new Intent(getActivity(), BucketManageActivity.class));
                break;
        }

    }

    @OnClick({R.id.msgBtn, R.id.setBtn, R.id.buyBtn, R.id.sailBtn, R.id.llTicket,R.id.image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.msgBtn:
                startActivity(new Intent(getActivity(), MsgActivity.class));
                break;
            case R.id.setBtn:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
            case R.id.buyBtn:
                startActivity(new Intent(getActivity(), PurchaseOrderActivity.class));
                break;
            case R.id.sailBtn:
                OrderManageActivity.startAtc(getActivity(), 0);
                break;
            case R.id.llTicket:
                DiscountTicketActivity.startAtc(getActivity(), -1, null);
                break;
            case R.id.image:
               // ShopInfoActivity.startAtc(getActivity(), shopInfoData);
                break;
        }
    }


}
