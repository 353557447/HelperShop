package com.shuiwangzhijia.shuidian.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.CouponBean;
import com.shuiwangzhijia.shuidian.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.shts.android.library.TriangleLabelView;

/**
 * created by wangsuli on 2018/10/8.
 */
public class CouponView extends LinearLayout {
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.labelView)
    TriangleLabelView labelView;
    @BindView(R.id.turnBtn)
    TextView turnBtn;
    @BindView(R.id.state)
    TextView stateTv;
    private Context context;
    private CouponBean data;
    private OnItemClickListener onItemClickListener;

    public CouponView(Context context) {
        super(context);
    }

    public CouponView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.coupon_view, this, true);
        ButterKnife.bind(this);
    }

    public void setData(CouponBean data) {
        this.data = data;
        setNumStyle(money, "￥", data.getPrice(), "\n满" + data.getFull() + "元可用");
        setOnDesClick(data.isShow());
        title.setText(data.getIs_brand() == 1 ? "仅限[" + data.getName() + "]品牌商品" : "不限商品");
        if (data.getIs_give() == 0) {
            info.setText("可赠送");
        } else {
            info.setText("详细信息");
        }
        boolean useFlag = true;
        int state = data.getState();
        switch (state) {
            case 1:
                //可使用
                useFlag = true;
                break;
            case 2:
                //已使用
                if (data.getStatus() == 0) {
                    //已使用
                    useFlag = false;
                    turnBtn.setVisibility(GONE);
                } else {
                    turnBtn.setVisibility(data.isShow() ? VISIBLE : GONE);
                    useFlag = true;
                    turnBtn.setText("撤销赠送");
                    info.setText("赠送中");
                }
                break;
            case 3:
                //已过期
                useFlag = false;
                break;
            case 4:
                //店铺优惠券
                useFlag = true;
                if (data.getIs_up() == 1) {
                    stateTv.setText("使用中");
                    stateTv.setSelected(true);
                } else {
                    stateTv.setText("暂停中");
                    stateTv.setSelected(false);
                }
                break;
        }
        money.setSelected(useFlag);
        if (useFlag) {
            labelView.setTriangleBackgroundColorResource(R.color.color_425274);
        } else {
            labelView.setTriangleBackgroundColorResource(R.color.color_bbbbbb);
        }
        labelView.setPrimaryText(data.getType() == 1 ? "商家券" : "平台券");
        if (data.getStart() > 0 && data.getEnd() > 0) {
            date.setText("有效期限:" + DateUtils.getYMDTime(data.getStart() ) + "~" + DateUtils.getYMDTime(data.getEnd()));
        } else {
            date.setText("有效期限:" + "永久有效");
        }
    }

    public void setOnDesClick(boolean click) {
        if (click) {
            info.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* CouponShareDialog shareDialog = new CouponShareDialog(context, data, new CouponShareDialog.OnShareConfirmClickListener() {
                        @Override
                        public void onShare(Dialog dialog, int type) {
                            dialog.dismiss();
                            Platform platform = ShareSDK.getPlatform(type==1?Wechat.NAME: WechatMoments.NAME);
                            Platform.ShareParams shareParams = new  Platform.ShareParams();
//                            shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
                            String des="[水的快递-"+labelView.getPrimaryText()+"] "+data.getPrice()+"元满"+data.getFull()+"元可用，"+title.getText().toString();
                            shareParams.setText(des);
                            shareParams.setTitle("送您一张优惠券");
                            shareParams.setUrl("http://bb.snss9.com/d5A5PFvgCk");
                            shareParams.setImageData(BitmapFactory.decodeResource(getResources(),R.drawable.share_icon));
                            shareParams.setShareType(Platform.SHARE_WEBPAGE);
                            platform.setPlatformActionListener(new PlatformActionListener() {
                                @Override
                                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                    RetrofitUtils.getInstances().create().markCoupon(data.getId()).enqueue(new Callback<EntityObject<Object>>() {
                                        @Override
                                        public void onResponse(Call<EntityObject<Object>> call, Response<EntityObject<Object>> response) {
                                            EntityObject<Object> body = response.body();
                                            if(body.getCode()==200){
                                                EventBus.getDefault().post(data);
                                            }else {
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<EntityObject<Object>> call, Throwable t) {

                                        }
                                    });
                                }

                                @Override
                                public void onError(Platform platform, int i, Throwable throwable) {

                                }

                                @Override
                                public void onCancel(Platform platform, int i) {

                                }
                            });
                            platform.share(shareParams);
                        }
                    });
                    if (!shareDialog.isShowing()) {
                        shareDialog.show();
                    }*/
                }
            });
        } else {
            info.setCompoundDrawables(null, null, null, null);
            info.setClickable(false);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void setNumStyle(TextView text, String first, String content, String second) {
        SpannableString spanString = new SpannableString(first + content + second);
        spanString.setSpan(new AbsoluteSizeSpan(28, true), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体大小
        spanString.setSpan(new StyleSpan(Typeface.BOLD), first.length(), (content + first).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//字体加粗
        text.setText(spanString);
    }


    @OnClick({R.id.state, R.id.turnBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.state:
                onItemClickListener.onOpenCloseClick(data.getPosition());
                break;
            case R.id.turnBtn:
                onItemClickListener.onTurnClick(data.getPosition());
                break;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }


    public interface OnItemClickListener {
        void onTurnClick(int position);

        void onOpenCloseClick(int position);
    }

}
