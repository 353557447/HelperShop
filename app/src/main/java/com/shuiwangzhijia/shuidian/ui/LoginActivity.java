package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.LoginBean;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.activity.HomePageActivity;
import com.shuiwangzhijia.shuidian.utils.AsteriskPasswordTransformationMethod;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends BaseAct {
    @BindView(R.id.editPhone)
    EditText editPhone;
    @BindView(R.id.editPsw)
    EditText editPsw;
    @BindView(R.id.loginBtn)
    TextView loginBtn;
    private String phone;
    private String psw;
    private boolean loginOut;

    public static void startActLoginOut(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("loginOut", true);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setNoTitleBar();
        editPsw.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        loginOut = getIntent().getBooleanExtra("loginOut", false);
        if (loginOut) {
            RetrofitUtils.getInstances().create().loginOut(CommonUtils.getMobile()).enqueue(new Callback<EntityObject<String>>() {
                @Override
                public void onResponse(Call<EntityObject<String>> call, Response<EntityObject<String>> response) {
                    ToastUitl.showToastCustom(response.body().getMsg());
                }

                @Override
                public void onFailure(Call<EntityObject<String>> call, Throwable t) {

                }
            });
        }
    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }

    @OnClick({R.id.loginBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                handleLogin();
                break;
        }
    }

    private void handleLogin() {
        phone = editPhone.getText().toString();
        psw = editPsw.getText().toString();
//        if (!CommonUtils.isMobileNO(phone)) {
//            hint("手机号码格式有误!");
//            return;
//        }
        if (phone.length() != 11) {
            hint("手机号码格式有误!");
            return;
        }
        if (psw.length() < 6 || psw.length() > 18) {
            hint("密码长度有误，应6-18位之间!");
            return;
        }
        showLoad();
        RetrofitUtils.getInstances().create().getLogin(phone, psw, Constant.WATER_FACTORY_ID).enqueue(new Callback<EntityObject<LoginBean>>() {
            @Override
            public void onResponse(Call<EntityObject<LoginBean>> call, Response<EntityObject<LoginBean>> response) {
                hintLoad();
                EntityObject<LoginBean> object = response.body();
                KLog.e(mGson.toJson(response.body()));
                if (object.getCode() == 200) {
                    LoginBean bean = object.getResult();
                    CommonUtils.putToken(bean.getToken());
                    CommonUtils.putUserId(bean.getToken());
                    CommonUtils.putMobile(phone);
                    CommonUtils.putDid(bean.getDid() + "");
                    CommonUtils.putHeaderpic(bean.getUser_info().getHeader_pic());
                    CommonUtils.putLogin(true);
                    CommonUtils.putWaterFactoryMobile(bean.getWater().getPhone());
                    CommonUtils.putWaterFactoryName(bean.getWater().getSname());
                    skipActivity(HomePageActivity.class);
                    finish();
                } else {
                    hint(object.getMsg());
                }

            }

            @Override
            public void onFailure(Call<EntityObject<LoginBean>> call, Throwable t) {

            }
        });
    }
}
