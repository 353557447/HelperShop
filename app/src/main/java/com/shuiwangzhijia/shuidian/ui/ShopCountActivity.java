package com.shuiwangzhijia.shuidian.ui;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.StatisticsBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_shop_count)
public class ShopCountActivity extends BaseActivity {
    @BindView(R.id.buyCount)
    TextView buyCount;
    @BindView(R.id.buyMoney)
    TextView buyMoney;
    @BindView(R.id.orderCount)
    TextView orderCount;
    @BindView(R.id.orderMoney)
    TextView orderMoney;
    @BindView(R.id.rlBuy)
    LinearLayout rlBuy;
    @BindView(R.id.rlOrder)
    LinearLayout rlOrder;
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        setBaseBarHide();
       // setTextStyle(buyCount, "0" + "\n", "采购量");
        buyCount.setText("0");
        buyMoney.setText("0");
        orderCount.setText("0");
        orderMoney.setText("0");
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void initEvent() {

    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getShopStatistics().enqueue(new Callback<EntityObject<StatisticsBean>>() {
            @Override
            public void onResponse(Call<EntityObject<StatisticsBean>> call, Response<EntityObject<StatisticsBean>> response) {
                dismissLoadingDialog();
                EntityObject<StatisticsBean> body = response.body();
                if (body.getCode() == 200) {
                    StatisticsBean result = body.getResult();
                    buyCount.setText(result.getPurchasing_num()+"");
                    buyMoney.setText(result.getTotal_price()+"");
                    orderCount.setText(result.getOrder_num()+"");
                    orderMoney.setText(result.getT_total()+"");
                }
            }

            @Override
            public void onFailure(Call<EntityObject<StatisticsBean>> call, Throwable t) {

            }
        });
    }

    private void setTextStyle(TextView text, String content, String title) {
        SpannableString spanString = new SpannableString(content + title);
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new AbsoluteSizeSpan(20, true), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体大小
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), content.length(), (content + title).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        spanString.setSpan(new AbsoluteSizeSpan(14, true), content.length(), (content + title).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体大小
        text.setText(spanString);
    }

    @OnClick({R.id.rlBuy, R.id.rlOrder, R.id.back_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlBuy:
                CountActivity.startAct(ShopCountActivity.this, CountActivity.TYPE_BUY);
                break;
            case R.id.rlOrder:
                CountActivity.startAct(ShopCountActivity.this, CountActivity.TYPE_ORDER);
                break;
            case R.id.back_return:
                closeActivity();
                break;
        }
    }
}
