package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.ShopBean;
import com.shuiwangzhijia.shuidian.bean.ShopInfoData;
import com.shuiwangzhijia.shuidian.dialog.DateDialog;
import com.shuiwangzhijia.shuidian.event.LoginOutEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopManageActivity extends BaseAct {
    @BindView(R.id.editStartTime)
    TextView editStartTime;
    @BindView(R.id.editEndTime)
    TextView editEndTime;
    @BindView(R.id.freeBtn)
    TextView freeBtn;
    @BindView(R.id.payBtn)
    TextView payBtn;
    @BindView(R.id.editPayMoney)
    EditText editPayMoney;
    @BindView(R.id.editFreeMoney)
    EditText editFreeMoney;
    @BindView(R.id.hint)
    TextView hint;
    @BindView(R.id.llPayInfo)
    LinearLayout llPayInfo;
    @BindView(R.id.editSpeed)
    EditText editSpeed;
    @BindView(R.id.editSpeedDistance)
    EditText editSpeedDistance;
    @BindView(R.id.saveBtn)
    TextView saveBtn;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.infoState)
    TextView infoState;
    @BindView(R.id.llInfo)
    LinearLayout llInfo;
    @BindView(R.id.editHint)
    EditText editHint;
    @BindView(R.id.count)
    TextView count;
    private boolean isFree;
    private ShopInfoData shopInfoData;
    private ShopBean shopBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_manage);
        ButterKnife.bind(this);
        setTitle("店铺设置");
        editFreeMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (s.length() > 0) {
                    setTextStyle(hint, s);
                }
            }
        });
        editHint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                count.setText(300 - text.length() + "");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getShopInfo();
    }

    private void getShopInfo() {
        showLoad();
        RetrofitUtils.getInstances().create().showShopInfo().enqueue(new Callback<EntityObject<ShopBean>>() {
            @Override
            public void onResponse(Call<EntityObject<ShopBean>> call, Response<EntityObject<ShopBean>> response) {
                hintLoad();
                EntityObject<ShopBean> body = response.body();
                if (body.getCode() == 200) {
                    shopBean = body.getResult();
                    initView();
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
                    if (shopInfoData.getIs_perfect() == 0) {
                        shopName.setText("未完善");
                        infoState.setText("去填写");
                    } else {
                        shopName.setText("已完善");
                        infoState.setText("去修改");
                    }
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ShopInfoData>> call, Throwable t) {

            }
        });
    }

    private void initView() {
        String busi_time = shopBean.getBusi_time();
        if (!TextUtils.isEmpty(busi_time)) {
            String[] busiTime = busi_time.split(" ");
            editStartTime.setText(busiTime[0]);
            editEndTime.setText(busiTime[1]);
        }
        int is_free = shopBean.getIs_free();
        //0 免运费 1 收费
        if (is_free == 0) {
            isFree = true;
        } else {
            isFree = false;
            editPayMoney.setText(isEmpty(shopBean.getAmount()));
            editFreeMoney.setText(isEmpty(shopBean.getFull_free()));
        }
        setTextStyle(hint, isEmpty(shopBean.getFull_free()));
        editSpeed.setText(isEmpty(shopBean.getEffic()));
        editHint.setText(shopBean.getAnnounce());
        editSpeedDistance.setText(isEmpty(shopBean.getDistance()));
        initState(isFree);
    }

    private String isEmpty(String text) {
        return TextUtils.isEmpty(text) ? "0" : text;
    }

    private void setTextStyle(TextView text, String content) {
        SpannableString spanString = new SpannableString("满" + content + "元免配送费");
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_30adfd)), 1, 1 + content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        text.setText(spanString);
    }

    @OnClick({R.id.editStartTime, R.id.editEndTime, R.id.freeBtn, R.id.payBtn, R.id.saveBtn, R.id.llInfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llInfo:
                ShopInfoActivity.startAtc(ShopManageActivity.this, shopInfoData);
                break;
            case R.id.editStartTime:
                DateDialog dateDialog = new DateDialog(ShopManageActivity.this, new DateDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(String time) {
                        editStartTime.setText(time);
                    }
                });
                dateDialog.show();
                break;
            case R.id.editEndTime:
                DateDialog dateDialogEnd = new DateDialog(ShopManageActivity.this, new DateDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(String time) {
                        editEndTime.setText(time);
                    }
                });
                dateDialogEnd.show();
                break;
            case R.id.freeBtn:
                initState(true);
                break;
            case R.id.payBtn:
                initState(false);
                break;
            case R.id.saveBtn:
                String start = editStartTime.getText().toString();
                String end = editEndTime.getText().toString();
                String speed = "", payMoney = "", freeMoney = "", distance = "";
                if (TextUtils.isEmpty(start)) {
                    hint("请选择营业开始时间");
                    return;
                }
                if (TextUtils.isEmpty(end)) {
                    hint("请选择营业结束时间");
                    return;
                }
                if (!isFree) {
                    payMoney = editPayMoney.getText().toString();
                    freeMoney = editFreeMoney.getText().toString();
                    if (TextUtils.isEmpty(payMoney)) {
                        hint("请输入配送费金额");
                        return;
                    }
                    if (Double.parseDouble(payMoney) > 1000) {
                        hint("配送费金额设置过大");
                        return;
                    }
                    if (TextUtils.isEmpty(freeMoney)) {
                        hint("请输入满免配送费金额");
                        return;
                    }
                    if (Double.parseDouble(freeMoney) > 1000) {
                        hint("免配送费金额设置过大");
                        return;
                    }

                }
                speed = editSpeed.getText().toString();
                if (TextUtils.isEmpty(speed)) {
                    hint("请输入配送效率");
                    return;
                }
                if (Double.parseDouble(speed) > 100) {
                    hint("配送效率设置过高");
                    return;
                }
                distance = editSpeedDistance.getText().toString();
                if (TextUtils.isEmpty(distance)) {
                    hint("请输入配送范围");
                    return;
                }
                if (Double.parseDouble(distance) > 100) {
                    hint("配送范围设置过大");
                    return;
                }
                String hint = editHint.getText().toString();
                showLoad();
                RetrofitUtils.getInstances().create().addShopInfo(start + " " + end
                        , isFree ? 0 : 1, speed, distance, payMoney, freeMoney, hint).enqueue(new Callback<EntityObject<Object>>() {
                    @Override
                    public void onResponse(Call<EntityObject<Object>> call, Response<EntityObject<Object>> response) {
                        hintLoad();
                        if (response.body().getCode() == 200) {
                            finish();
                        }
                        hint(response.body().getMsg());
                    }

                    @Override
                    public void onFailure(Call<EntityObject<Object>> call, Throwable t) {

                    }
                });
                break;
        }
    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }

    private void initState(boolean isFree) {
        this.isFree = isFree;
        freeBtn.setSelected(isFree ? true : false);
        payBtn.setSelected(!isFree ? true : false);
        llPayInfo.setVisibility(isFree ? View.GONE : View.VISIBLE);
    }

}
