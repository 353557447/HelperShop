package com.shuiwangzhijia.shuidian.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.DateUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 创建水票
 * created by wangsuli on 2018/10/11.
 */
public class CreateTicketActivity extends BaseAct {

    @BindView(R.id.ticketName)
    TextView ticketName;
    @BindView(R.id.cutBtn)
    TextView cutBtn;
    @BindView(R.id.num)
    EditText numEdit;
    @BindView(R.id.addBtn)
    TextView addBtn;
    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.salePrice)
    EditText salePrice;
    @BindView(R.id.limitDate)
    TextView limitDate;
    @BindView(R.id.deadline)
    TextView deadline;
    @BindView(R.id.editStartTime)
    TextView editStartTime;
    @BindView(R.id.editEndTime)
    TextView editEndTime;
    @BindView(R.id.llDateInfo)
    LinearLayout llDateInfo;
    @BindView(R.id.sureBtn)
    TextView sureBtn;
    private boolean dateFlag = false, giveFlag = true;
    private Long startTimeStr, endTimeStr;
    private int year, month, day;
    private String price, sprice;
    private String num;
    private String gid;
    private String name;
    private int count = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);
        ButterKnife.bind(this);
        setTitle("创建水票");
        setDateState(false);
        date();
        setCount(count + "");
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

    private void setCount(String count) {
        numEdit.setText(count);
        numEdit.setSelection(count.length());
    }

    @OnClick({R.id.ticketName, R.id.cutBtn, R.id.addBtn, R.id.limitDate, R.id.deadline, R.id.editStartTime, R.id.editEndTime, R.id.sureBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ticketName:
                startActivity(new Intent(CreateTicketActivity.this, SelectGoodsActivity.class));
                break;
            case R.id.cutBtn:
                num = numEdit.getText().toString();
                count = Integer.valueOf(num);
                if (count < 1) {
                    ToastUitl.showToastCustom("不能再减了...");
                    return;
                }
                count--;
                setCount(count + "");
                break;
            case R.id.addBtn:
                num = numEdit.getText().toString();
                count = Integer.valueOf(num);
                count++;
                setCount(count + "");
                break;
            case R.id.limitDate:
                setDateState(true);
                break;
            case R.id.deadline:
                setDateState(false);
                break;
            case R.id.editStartTime:
                datePickerDialog(true);
                break;
            case R.id.editEndTime:
                datePickerDialog(false);
                break;
            case R.id.sureBtn:
                sureBtn.setClickable(false);
//                num = numEdit.getText().toString();
                if (TextUtils.isEmpty(gid)) {
                    ToastUitl.showToastCustom("请选择商品");
                    return;
                }
                if (count < 1) {
                    ToastUitl.showToastCustom("请输入数量");
                    return;
                }
                price = money.getText().toString();
                if (TextUtils.isEmpty(price)) {
                    ToastUitl.showToastCustom("请输入面额");
                    return;
                }
                sprice = salePrice.getText().toString();
                if (TextUtils.isEmpty(sprice)) {
                    ToastUitl.showToastCustom("请输入售卖价格");
                    return;
                }
                String start = "";
                String end = "";
                if (dateFlag) {
                    start = startTimeStr / 1000 + "";
                    end = endTimeStr / 1000 + "";
                }
                RetrofitUtils.getInstances().create().createTicket(gid, name, count + "", price, sprice, start, end).enqueue(new Callback<EntityObject<Object>>() {
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
        new DatePickerDialog(CreateTicketActivity.this, new DatePickerDialog.OnDateSetListener() {
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


    private void setDateState(boolean flag) {
        dateFlag = flag;
        limitDate.setSelected(flag ? true : false);
        deadline.setSelected(flag ? false : true);
        llDateInfo.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(GoodsBean goodsBean) {
        gid = goodsBean.getGid();
        name = goodsBean.getGname();
        ticketName.setText(name);
    }
}
