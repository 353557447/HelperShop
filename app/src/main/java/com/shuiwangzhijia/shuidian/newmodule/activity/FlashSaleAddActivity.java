package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.FlashSaleSessionBean;
import com.shuiwangzhijia.shuidian.bean.SessionGoodsBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.FlashSaleAddAdapter;
import com.shuiwangzhijia.shuidian.utils.MeasureUtil;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.shuiwangzhijia.shuidian.view.SelectTimeDialog;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_flash_sale_add, title = "限时抢购")
public class FlashSaleAddActivity extends BaseActivity implements FlashSaleAddAdapter.OnItemDeleteEditListener {
    @BindView(R.id.session_rl)
    RelativeLayout mSessionRl;
    @BindView(R.id.add_goods)
    TextView mAddGoods;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.sure)
    Button mSure;
    @BindView(R.id.session_details)
    TextView mSessionDetails;
    private int selectedSeckillId;
    private List<SessionGoodsBean.DataBean> mList;
    private FlashSaleAddAdapter mFlashSaleAddAdapter;
    private long selectDateTimestamp;
    private int selectTimePosition;
    private SimpleDateFormat mFormat;
    private Bundle mBundle;
    private SimpleDateFormat mSimpleDateFormat;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        mBundle = getIntent().getExtras();
        mFormat = new SimpleDateFormat("yyyy-MM-dd");
        mSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        if (mBundle != null) {
            /*bundle.putInt("seckill_id",dataBean.getSeckill_id());
            bundle.putLong("date_time",dataBean.getDate_time());
            bundle.putString("start_time",dataBean.getStart_time());
            bundle.putString("end_time",dataBean.getEnd_time());
             bundle.putBoolean("isEditable",false);
            */
            //场次改不了
            selectedSeckillId = mBundle.getInt("seckill_id");
            boolean isEditable = mBundle.getBoolean("isEditable", false);
            selectDateTimestamp = mBundle.getLong("date_time");
            String startTime = mBundle.getString("start_time");
            String endTime = mBundle.getString("end_time");
            if (!isEditable) {
                mSessionRl.setEnabled(false);
                mAddGoods.setEnabled(false);
                mAddGoods.setVisibility(View.GONE);
            }
            Date date = new Date(selectDateTimestamp * 1000);
            mSessionDetails.setText(mFormat.format(date) + "  " + startTime + "~" + endTime);
        }
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        initRv();
    }

    private void initRv() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mFlashSaleAddAdapter = new FlashSaleAddAdapter(this);
        mRv.addItemDecoration(new SpacesItemDecoration(this, MeasureUtil.dip2px(this,12)));
        mList = new ArrayList<>();
        mFlashSaleAddAdapter.setData(mList);
        mFlashSaleAddAdapter.setBuddle(mBundle);
        mFlashSaleAddAdapter.setOnItemDeleteEditListener(this);
        mRv.setAdapter(mFlashSaleAddAdapter);
    }

    @Override
    protected void initData() {
        if (mBundle == null)
            getDefaultSession();
        else
            getSessionGoodsList();
    }

    private void getDefaultSession() {
        RetrofitUtils.getInstances().create().getFlashSaleDefualtSession(mToken).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        JSONObject data = object.getJSONObject("data");
                        selectedSeckillId = data.getInt("seckill_id");
                        String startTime = data.getString("start_time");
                        String endTime = data.getString("end_time");
                        Date date = new Date();
                        selectDateTimestamp = (mFormat.parse(mFormat.format(date)).getTime()) / 1000;
                        mSessionDetails.setText(mFormat.format(date) + "  " + startTime + "~" + endTime);
                        getSessionGoodsList();
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    private void getSessionGoodsList() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getFlashSaleSessionGoodsList(mToken, selectedSeckillId, selectDateTimestamp + "").enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        setCentreViewDismiss();
                        mRv.setVisibility(View.VISIBLE);
                        SessionGoodsBean sessionGoodsBean = mGson.fromJson(datas, SessionGoodsBean.class);
                        List<SessionGoodsBean.DataBean> data = sessionGoodsBean.getData();
                        mList.clear();
                        mList.addAll(data);
                        mFlashSaleAddAdapter.notifyDataSetChanged();
                    } else {
                        mRv.setVisibility(View.GONE);
                        setCentreViewShow(R.drawable.no_equip_goods_data, "该场次暂无商品~");
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }


    private void getSessionData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getFlashSaleSession(mToken).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        FlashSaleSessionBean flashSaleSessionBean = mGson.fromJson(datas, FlashSaleSessionBean.class);
                        List<FlashSaleSessionBean.DataBean> data = flashSaleSessionBean.getData();
                        initTimePickView(data);
                    } else {

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }


    private void initTimePickView(final List<FlashSaleSessionBean.DataBean> data) {
        SelectTimeDialog dialog = new SelectTimeDialog(this, data, new SelectTimeDialog.OnItemClickListener() {
            @Override
            public void onSureClick(SelectTimeDialog dialog, long dateTimestamp, int selectTimePosition) {
                FlashSaleSessionBean.DataBean dataBean = data.get(selectTimePosition);
                String yyyyMMdd = mFormat.format(new Date(dateTimestamp * 1000));
                String currentyyyyMMdd = mFormat.format(new Date());
                if (yyyyMMdd.equals(currentyyyyMMdd)) {
                    String endTime = dataBean.getEnd_time();
                    try {
                        long time = mSimpleDateFormat.parse(endTime).getTime();
                        long currentTime= mSimpleDateFormat.parse(mSimpleDateFormat.format(new Date())).getTime();
                        if(currentTime-time>0){
                            showToast("您好，该场次已过，禁止添加~");
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                selectDateTimestamp = dateTimestamp;
                selectedSeckillId = dataBean.getSeckill_id();
                String str = yyyyMMdd + "  " + dataBean.getStart_time() + "~" + dataBean.getEnd_time();
                mSessionDetails.setText(str);
                getSessionGoodsList();
                dialog.dismiss();

            }
        });
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @OnClick({R.id.session_rl, R.id.add_goods, R.id.sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.session_rl:
                getSessionData();
                break;
            case R.id.add_goods:
                Bundle bundle = new Bundle();
                bundle.putInt("selectedSeckillId", selectedSeckillId);
                bundle.putLong("dateTime", selectDateTimestamp);
                skipActivity(FlashSaleAddGoodsActivity.class, bundle);
                break;
            case R.id.sure:
                break;
        }
    }

    @Override
    public void onItemDelete(final int position) {
        mRxDialogSureCancel.setContent("您确认要删除该商品么？");
        mRxDialogSureCancel.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDelete(position);
            }
        });
        mRxDialogSureCancel.show();
    }

    private void handleDelete(final int position) {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().deleteFlashSaleSessionGoods(mToken, mList.get(position).getId()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        showToast("删除成功");
                        mList.remove(position);
                        mFlashSaleAddAdapter.notifyDataSetChanged();
                        mRxDialogSureCancel.dismiss();
                    } else {
                        showToast("删除失败");
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }

    @Override
    public void onItemEdit(int position) {
        handleEdit(position);
    }


    private void handleEdit(final int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.add_goods_dialog, null);
        TextView close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissBottomDialog();
            }
        });
        ImageView goodsImg = view.findViewById(R.id.goods_img);
        TextView goodsName = view.findViewById(R.id.goods_name);
        TextView goodsPrice = view.findViewById(R.id.goods_price);
        final EditText panicBuyingPrice = view.findViewById(R.id.panic_buying_price);
        final EditText repertoryCounts = view.findViewById(R.id.repertory_counts);
        final EditText purchaseLimitCounts = view.findViewById(R.id.purchase_limit_counts);
        TextView sure = view.findViewById(R.id.sure);

        SessionGoodsBean.DataBean dataBean = mList.get(position);
        setImg(goodsImg, dataBean.getPicturl(), "goods/");
        goodsName.setText(dataBean.getGname());
        goodsPrice.setText("￥" + dataBean.getPprice());
        panicBuyingPrice.setText(dataBean.getSeckill_price() + "");
        purchaseLimitCounts.setText(dataBean.getSeckill_restrictions() + "");
        repertoryCounts.setText(dataBean.getSeckill_stock() + "");

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String panicBuyingPriceStr = panicBuyingPrice.getText().toString().trim();
                String repertoryCountsStr = repertoryCounts.getText().toString().trim();
                String purchaseLimitCountsStr = purchaseLimitCounts.getText().toString().trim();
                if (isEmpty(panicBuyingPriceStr)) {
                    showToast("抢购价不能为空");
                    return;
                }
                if (isEmpty(repertoryCountsStr)) {
                    showToast("库存不能为空");
                    return;
                }
                if (isEmpty(purchaseLimitCountsStr)) {
                    showToast("每人限购不能为空");
                    return;
                }

                if ("0".equals(repertoryCountsStr)) {
                    showToast("库存不能为0");
                    return;
                }

                if ("0".equals(purchaseLimitCountsStr)) {
                    showToast("限购不能为0");
                    return;
                }

                editGoods(position, panicBuyingPriceStr, repertoryCountsStr, purchaseLimitCountsStr);

            }
        });
        showBottomDialog(view);
    }

    private void editGoods(int position, String panicBuyingPriceStr, String repertoryCountsStr, String purchaseLimitCountsStr) {
       /* token	是	string	token
        seckill_id	是	int	秒杀时段ID
        gid	是	int	商品ID
        seckill_price	是	string	秒杀价
        seckill_restrictions	是	int	秒杀限购
        seckill_stock	是	int	秒杀库存
        date_time	是	string	日期
        id	否	int	商品秒杀id*/
        SessionGoodsBean.DataBean dataBean = mList.get(position);
        showLoadingDialog();
        RetrofitUtils.getInstances().create().editFlashSaleGoods(mToken, selectedSeckillId, dataBean.getGid(),//
                panicBuyingPriceStr, Integer.parseInt(purchaseLimitCountsStr),//
                Integer.parseInt(repertoryCountsStr), selectDateTimestamp + "", dataBean.getId()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        showToast("编辑成功");
                        dismissBottomDialog();
                        getSessionGoodsList();
                    } else {
                        showToast("编辑失败");
                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                } finally {
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                showErrorToast();
                KLog.e(t.getMessage());
                dismissLoadingDialog();
            }
        });
    }
}

