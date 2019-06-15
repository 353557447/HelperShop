package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.FlashSaleAddGoodsBean;
import com.shuiwangzhijia.shuidian.event.RefreshDataEvent;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.FlashSaleAddGoodsAdapter;
import com.shuiwangzhijia.shuidian.utils.MeasureUtil;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.socks.library.KLog;
import com.shuiwangzhijia.shuidian.view.SpacesItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_flash_sale_add_goods, title = "添加商品")
public class FlashSaleAddGoodsActivity extends BaseActivity implements FlashSaleAddGoodsAdapter.OnItemClickListener {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.add)
    TextView mAdd;
    private List<FlashSaleAddGoodsBean.DataBean> mList;
    private FlashSaleAddGoodsAdapter mFlashSaleAddGoodsAdapter;
    private int selectedPosition;
    private int mSelectedSeckillId;
    private long mDateTime;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        mSelectedSeckillId = bundle.getInt("selectedSeckillId");
        mDateTime = bundle.getLong("dateTime");
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
        mRv.setHasFixedSize(true);
      /*  DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg_tran));*/
        mRv.addItemDecoration(new SpacesItemDecoration(this, MeasureUtil.dip2px(this,12)));
        mFlashSaleAddGoodsAdapter = new FlashSaleAddGoodsAdapter(this);
        mList = new ArrayList<FlashSaleAddGoodsBean.DataBean>();
        mFlashSaleAddGoodsAdapter.setData(mList);
        mFlashSaleAddGoodsAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mFlashSaleAddGoodsAdapter);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        showLoadingDialog();
        KLog.e(mToken);
        RetrofitUtils.getInstances().create().getFlashSaleGoodsAddList(mToken).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        setCentreViewDismiss();
                        FlashSaleAddGoodsBean flashSaleAddGoodsBean = mGson.fromJson(datas, FlashSaleAddGoodsBean.class);
                        List<FlashSaleAddGoodsBean.DataBean> data = flashSaleAddGoodsBean.getData();
                        mList.addAll(data);
                        mFlashSaleAddGoodsAdapter.notifyDataSetChanged();
                    } else {
                        setCentreViewShow(R.drawable.no_add_goods, "暂无可添加商品");
                        mAdd.setBackgroundResource(R.drawable.gray_solid_big_radius_bg);
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
    protected void initEvent() {

    }

    @OnClick(R.id.add)
    public void onViewClicked() {
        if (mList.size() == 0)
            showToast("暂无商品可供选择~");
        else
            handleAdd();
    }

    private void handleAdd() {
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

        FlashSaleAddGoodsBean.DataBean dataBean = mList.get(selectedPosition);
        setImg(goodsImg, dataBean.getPicturl(), "goods/");
        goodsName.setText(dataBean.getGname());
        goodsPrice.setText("￥" + dataBean.getPprice());
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

                if("0".equals(repertoryCountsStr)){
                    showToast("库存不能为0");
                    return;
                }

                if("0".equals(purchaseLimitCountsStr)){
                    showToast("限购不能为0");
                    return;
                }

                addGoods(panicBuyingPriceStr, repertoryCountsStr, purchaseLimitCountsStr);
            }
        });
        showBottomDialog(view);
    }

    private void addGoods(String panicBuyingPriceStr, String repertoryCountsStr, String purchaseLimitCountsStr) {

     /* token	是	string	token
        seckill_id	是	int	秒杀时段ID
        gid	是	int	商品ID
        seckill_price	是	string	秒杀价
        seckill_restrictions	是	int	秒杀限购
        seckill_stock	是	int	秒杀库存
        date_time	是	string	日期*/
        mList.get(selectedPosition);
        String gid = mList.get(selectedPosition).getGid();
        showLoadingDialog();
        KLog.e("mDateTime:"+mDateTime);
        RetrofitUtils.getInstances().create().addFlashSaleGoods(mSelectedSeckillId, gid, panicBuyingPriceStr, Integer.parseInt(purchaseLimitCountsStr), Integer.parseInt(repertoryCountsStr), mDateTime + "").enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        showToast("添加活动商品成功");
                        EventBus.getDefault().post(new RefreshDataEvent("FlashSaleAddActivity", "getSessionGoodsList"));
                        dismissBottomDialog();
                        closeActivity();
                    } else {
                        showToast(object.getString("msg"));
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
    public void onItemClick(int position) {
        for (int i = 0; i < mList.size(); i++) {
            if (i == position) {
                mList.get(i).setCheck(true);
                selectedPosition = i;
            } else
                mList.get(i).setCheck(false);
        }
        mFlashSaleAddGoodsAdapter.notifyDataSetChanged();
    }


}
