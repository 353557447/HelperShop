package com.shuiwangzhijia.shuidian.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCouponActivity extends BaseAct {
    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.limit)
    EditText limit;
    @BindView(R.id.editStartTime)
    TextView editStartTime;
    @BindView(R.id.editEndTime)
    TextView editEndTime;
    @BindView(R.id.giveBtn)
    TextView giveBtn;
    @BindView(R.id.noGiveBtn)
    TextView noGiveBtn;
    @BindView(R.id.sureBtn)
    TextView sureBtn;
    private boolean giveFlag = true;
    private Long startTimeStr, endTimeStr;
    private int year, month, day;
    private String price;
    private String full;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_coupon);
        ButterKnife.bind(this);
        setTitle("创建优惠券");
        setGiveState(true);
        date();
    }

    //获取当前系统时间
    private void date() {
        Calendar c = Calendar.getInstance();
        //年
        year = c.get(Calendar.YEAR);
        //月
        month = c.get(Calendar.MONTH) + 1;
        //日
        day = c.get(Calendar.DAY_OF_MONTH);
        String date = String.format("%d-%d-%d", year, month, day);
        editStartTime.setText(date);
        String dateEnd = DateUtils.addDaysDateTime(new Date(), 1);
        editEndTime.setText(dateEnd);
        startTimeStr = DateUtils.getTime(date);
        endTimeStr = DateUtils.getTime(dateEnd);
    }

    @OnClick({ R.id.editStartTime, R.id.editEndTime, R.id.giveBtn, R.id.noGiveBtn, R.id.sureBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editStartTime:
                datePickerDialog(true);
                break;
            case R.id.editEndTime:
                datePickerDialog(false);
                break;
            case R.id.giveBtn:
                setGiveState(true);
                break;
            case R.id.noGiveBtn:
                setGiveState(false);
                break;
            case R.id.sureBtn:
                sureBtn.setClickable(false);
                price = money.getText().toString();
                if (TextUtils.isEmpty(price)) {
                    ToastUitl.showToastCustom("请输入面额");
                    return;
                }
                full = limit.getText().toString();
                if (TextUtils.isEmpty(full)) {
                    ToastUitl.showToastCustom("请输入满减条件");
                    return;
                }

                RetrofitUtils.getInstances().create().createCoupon(price, full, startTimeStr / 1000 + "", endTimeStr / 1000 + "", giveFlag ? 0 : 1).enqueue(new Callback<EntityObject<Object>>() {
                    @Override
                    public void onResponse(Call<EntityObject<Object>> call, Response<EntityObject<Object>> response) {
                        EntityObject<Object> body = response.body();
                        sureBtn.setClickable(true);
                        if (body.getCode() == 200) {
                            ToastUitl.showToastCustom("创建成功!");
                            finish();
                        } else {
                            ToastUitl.showToastCustom(body.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<EntityObject<Object>> call, Throwable t) {

                    }
                });
                break;
        }
    }

    //起始时间的选取
    private void datePickerDialog(final boolean isStart) {
        new DatePickerDialog(CreateCouponActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String date = String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth);
                if (isStart) {
                    editStartTime.setText(date);
                    startTimeStr = DateUtils.getTime(date);
                } else {
                    editEndTime.setText(date);
                    endTimeStr = DateUtils.getTime(date);
                }

            }
        }, year, month - 1, day).show();
    }

    private void setGiveState(boolean flag) {
        giveFlag = flag;
        giveBtn.setSelected(giveFlag ? true : false);
        noGiveBtn.setSelected(giveFlag ? false : true);
    }

}
