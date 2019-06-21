package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.RMCheckOutOrderListBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.view.MyScrollView;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_settle_accounts)
public class SettleAccountsActivity extends BaseActivity {
    @BindView(R.id.r_type)
    TextView mRType;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.rule_details)
    TextView mRuleDetails;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.nest_scroll_view)
    MyScrollView mNestScrollView;
    @BindView(R.id.hintBtn)
    TextView mHintBtn;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.payBtn)
    TextView mPayBtn;
    @BindView(R.id.back_return)
    RelativeLayout mBackReturn;
    @BindView(R.id.title_bar_rl)
    RelativeLayout mTitleBarRl;
    private int mRId;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        mRId = getIntent().getExtras().getInt("rId");
        setBaseBarHide();
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
      /*  did	是	int	水厂id
        r_id	是	int	水厂id
        start_time	是	int	周期开始时间
        end_time	是	int	周期结束时间*/
      /*  showLoadingDialog();
        RetrofitUtils.getInstances().create().getMyReturnMoneyCheckOutList(CommonUtils.getDid(),mRId).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    if(code==200){

                    }else{

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                }finally {
                    dismissLoadingDialog();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });*/
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.time, R.id.payBtn, R.id.back_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.time:
              //  RMCheckOutOrderListBean
                break;
            case R.id.payBtn:
                break;
            case R.id.back_return:
                closeActivity();
                break;
        }
    }
}
