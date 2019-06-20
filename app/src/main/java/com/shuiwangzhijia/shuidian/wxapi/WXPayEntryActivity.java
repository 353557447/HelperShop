package com.shuiwangzhijia.shuidian.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import com.pingplusplus.ui.PaySuccessActivity;
import com.shuiwangzhijia.shuidian.base.App;
import com.shuiwangzhijia.shuidian.event.WechatPayFromWhichEvent;
import com.shuiwangzhijia.shuidian.event.WechatPayResultEvent;
import com.shuiwangzhijia.shuidian.newmodule.activity.MyWalletNewActivity;
import com.shuiwangzhijia.shuidian.ui.OrderPayActivity;
import com.shuiwangzhijia.shuidian.ui.PurchaseOrderActivity;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.socks.library.KLog;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private String fromWhich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = App.getIwxapi();
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);

    }


    @Override
    public void onReq(BaseReq baseReq) {
        KLog.e(baseReq.getType());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            KLog.e("baseResp.errCode:" + baseResp.errCode);
            if ("RechargeCouponPayActivity".equals(fromWhich)) {
                if (baseResp.errCode == 0) {
                    ToastUitl.showToastCustom("充值成功");
                    Intent intent = new Intent(this, MyWalletNewActivity.class);
                    startActivity(intent);
                } else if (baseResp.errCode == -1) {
                    ToastUitl.showToastCustom("充值失败");
                } else if (baseResp.errCode == -2) {
                    ToastUitl.showToastCustom("充值失败");
                }
            } else {
                if (baseResp.errCode == 0) {
                    //支付成功
                    EventBus.getDefault().post(new WechatPayResultEvent(1));
                    PurchaseOrderActivity.statAct(this, 2);
                } else if (baseResp.errCode == -1) {
                    //支付失败
                    EventBus.getDefault().post(new WechatPayResultEvent(0));
                    PurchaseOrderActivity.statAct(this, 0);
                } else if (baseResp.errCode == -2) {
                    //支付取消
                    EventBus.getDefault().post(new WechatPayResultEvent(2));
                    PurchaseOrderActivity.statAct(this, 0);
                }
            }
        }
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void fromWhich(WechatPayFromWhichEvent event) {
        fromWhich = event.getFromWhich();
    }
}
