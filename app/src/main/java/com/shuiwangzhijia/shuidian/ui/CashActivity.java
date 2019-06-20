package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.CashBean;
import com.shuiwangzhijia.shuidian.dialog.BankDialog;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CalculateUtils;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_cash, title = "提现")
public class CashActivity extends BaseActivity {
    @BindView(R.id.editMoney)
    EditText editMoney;
    @BindView(R.id.allBtn)
    TextView allBtn;
    @BindView(R.id.editBankName)
    EditText editBankName;
    @BindView(R.id.editBankNo)
    EditText editBankNo;
    @BindView(R.id.editBank)
    TextView editBank;
    @BindView(R.id.editHint)
    EditText editHint;
    @BindView(R.id.postBtn)
    TextView postBtn;
    @BindView(R.id.yuan)
    TextView yuan;
    @BindView(R.id.hintMoney)
    TextView hintMoney;
    @BindView(R.id.backHint)
    TextView backHint;
    @BindView(R.id.branchName)
    EditText mBranchName;
    private BankDialog bankDialog;
    private String id = "";
    private String mTotalMoney;
    private String[] mOrderSns;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        mTotalMoney = bundle.getString("money");
        mOrderSns = bundle.getStringArray("order_sn");
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        hintMoney.setText(mTotalMoney);
        editMoney.setText(mTotalMoney);
        editMoney.setEnabled(false);
    }

    @Override
    protected void initData() {
        getInfo();
    }

    @Override
    protected void initEvent() {

    }

    private void getInfo() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getCashInfo().enqueue(new Callback<EntityObject<CashBean>>() {
            @Override
            public void onResponse(Call<EntityObject<CashBean>> call, Response<EntityObject<CashBean>> response) {
                dismissLoadingDialog();
                EntityObject<CashBean> body = response.body();
                if (body.getCode() == 200) {
                    CashBean result = body.getResult();
                    int status = result.getStatus();
                    if (status == 1) {
                        editMoney.setText(result.getAmount());
                        hintMoney.setText(result.getBanlance());
                        editBankName.setText(result.getAccount());
                        editBankNo.setText(result.getCard_no());
                        editBank.setText(result.getBank());
                        editHint.setText(result.getRemark());
                        id = result.getId() + "";
                        postBtn.setText("审核中");
                        // postBtn.setBackgroundResource(R.drawable.gray_rectangle);
                        allBtn.setSelected(false);
                        allBtn.setClickable(false);
                        editBank.setClickable(false);
                    } else if (status == 3) {
                        editMoney.setText(result.getAmount());
                        hintMoney.setText(result.getBanlance());
                        editBankName.setText(result.getAccount());
                        editBankNo.setText(result.getCard_no());
                        editBank.setText(result.getBank());
                        editHint.setText(result.getRemark());
                        postBtn.setText("提交审核");
                        //  postBtn.setBackgroundResource(R.drawable.blue_rectangle);
                        postBtn.setSelected(true);
                        backHint.setText("驳回理由：" + result.getRefuse());
                        backHint.setVisibility(View.VISIBLE);
                    } else {
                        //  postBtn.setBackgroundResource(R.drawable.blue_rectangle);
                        postBtn.setSelected(true);
                        postBtn.setText("提交申请");
                    }
                } else {
                    showToast(body.getMsg());
                }

            }

            @Override
            public void onFailure(Call<EntityObject<CashBean>> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.allBtn, R.id.editBank, R.id.postBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.allBtn:
                String money = hintMoney.getText().toString();
                editMoney.setText(money);
                break;
            case R.id.editBank:
                bankDialog = new BankDialog(this, new BankDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirm(String back) {
                        editBank.setText(back);
                        bankDialog.dismiss();
                    }
                });
                if (!bankDialog.isShowing()) {
                    bankDialog.show();
                }
                break;
            case R.id.postBtn:
                postBtn.setClickable(false);
                postCash();
                break;
        }
    }

    private void postCash() {
        showLoadingDialog();
        if (!TextUtils.isEmpty(id)) {
            KLog.e("isEmpty");
            RetrofitUtils.getInstances().create().postCash("", "", "", "", "", id, mOrderSns,"").enqueue(new Callback<EntityObject<CashBean>>() {
                @Override
                public void onResponse(Call<EntityObject<CashBean>> call, Response<EntityObject<CashBean>> response) {
                    showLoadingDialog();
                    EntityObject<CashBean> body = response.body();
                    showToast(body.getMsg());
                }

                @Override
                public void onFailure(Call<EntityObject<CashBean>> call, Throwable t) {

                }
            });
        } else {
            KLog.e("notEmpty");
            String money = editMoney.getText().toString();
            String account = editBankName.getText().toString();
            String cardNo = editBankNo.getText().toString();
            String bank = editBank.getText().toString();
            String remark = editHint.getText().toString();
            String branchName = mBranchName.getText().toString();
            if (TextUtils.isEmpty(money)) {
                showToast("提现金额不能为空");
                return;
            }
            if (CalculateUtils.sub(Double.valueOf(mTotalMoney), Double.valueOf(money)) < 0) {
                showToast("提现金额不能超过余额");
                return;
            }
            if (TextUtils.isEmpty(account)) {
                showToast("请输入开户姓名");
                return;
            }
            if (TextUtils.isEmpty(cardNo)) {
                showToast("银行卡号不能为空");
                return;
            }
            if (TextUtils.isEmpty(bank)) {
                showToast("请选择银行");
                return;
            }
            if (isEmpty(branchName)) {
                showToast("请输入开户支行");
                return;
            }

            RetrofitUtils.getInstances().create().postCash(money, remark, account, bank, cardNo, id, mOrderSns,branchName).enqueue(new Callback<EntityObject<CashBean>>() {
                @Override
                public void onResponse(Call<EntityObject<CashBean>> call, Response<EntityObject<CashBean>> response) {
                    dismissLoadingDialog();
                    EntityObject<CashBean> body = response.body();
                    if (body.getCode() == 200) {
                        finish();
                    }
                    showToast(body.getMsg());

                }

                @Override
                public void onFailure(Call<EntityObject<CashBean>> call, Throwable t) {

                }
            });
        }
    }
}
