package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.AddEquipGoodsBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.AddEquipGoodsDetailsAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.AddEquipGoodsTypeAdapter;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_add_equip_goods, title = "添加商品")
public class AddEquipGoodsActivity extends BaseActivity implements AddEquipGoodsTypeAdapter.OnTypeItemClickListener, AddEquipGoodsDetailsAdapter.OnDetailsItemAddClickListener {
    @BindView(R.id.type_rv)
    RecyclerView mTypeRv;
    @BindView(R.id.details_rv)
    RecyclerView mDetailsRv;
    @BindView(R.id.type_name)
    TextView mTypeName;
    @BindView(R.id.root_ll)
    LinearLayout mRootLl;
    private AddEquipGoodsTypeAdapter mAddEquipGoodsTypeAdapter;
    private List<String> mTypelist;
    private AddEquipGoodsDetailsAdapter mAddEquipGoodsDetailsAdapter;
    private List<AddEquipGoodsBean.DataBean.GoodsBean> mDetailslist;
    private List<AddEquipGoodsBean.DataBean> mData;
    private boolean equipGoodsDataHasChanged;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                if (equipGoodsDataHasChanged)
                    setResult(RESULT_OK);
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        initTypeRv();
        initDetailsRv();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (equipGoodsDataHasChanged)
            setResult(RESULT_OK);
        closeActivity();
    }

    private void initTypeRv() {
        mTypeRv.setLayoutManager(new LinearLayoutManager(this));
        mTypelist = new ArrayList<>();
        mAddEquipGoodsTypeAdapter = new AddEquipGoodsTypeAdapter(this);
        mAddEquipGoodsTypeAdapter.setOnTypeItemClickListener(this);
        mAddEquipGoodsTypeAdapter.setData(mTypelist);
        mTypeRv.setAdapter(mAddEquipGoodsTypeAdapter);
    }

    private void initDetailsRv() {
        mDetailsRv.setLayoutManager(new LinearLayoutManager(this));
        mDetailslist = new ArrayList<>();
        mAddEquipGoodsDetailsAdapter = new AddEquipGoodsDetailsAdapter(this);
        mAddEquipGoodsDetailsAdapter.setOnDetailsItemClickListener(this);
        mAddEquipGoodsDetailsAdapter.setData(mDetailslist);
        mDetailsRv.setAdapter(mAddEquipGoodsDetailsAdapter);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getAddableDeviceGoods().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        mRootLl.setVisibility(View.VISIBLE);
                        AddEquipGoodsBean addEquipGoodsBean = mGson.fromJson(datas, AddEquipGoodsBean.class);
                        mData = addEquipGoodsBean.getData();
                        mTypelist.clear();
                        for (AddEquipGoodsBean.DataBean dataBean :
                                mData) {
                            mTypelist.add(dataBean.getType_name());
                            KLog.e(dataBean.getType_name());
                        }
                        mAddEquipGoodsTypeAdapter.notifyDataSetChanged();
                        handleDetails(0);
                    } else {
                        mRootLl.setVisibility(View.GONE);
                        setCentreViewShow(R.drawable.no_equip_goods_data, "暂无可添加的商品~");
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

    private void handleDetails(int position) {
        mDetailslist.clear();
        mTypeName.setText(mTypelist.get(position));
        List<AddEquipGoodsBean.DataBean.GoodsBean> goods = mData.get(position).getGoods();
        mDetailslist.addAll(goods);
        mAddEquipGoodsDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onTypeItemClick(int position) {
        mAddEquipGoodsTypeAdapter.setSelectIndex(position);
        handleDetails(position);
    }

    @Override
    public void onDetailsItemAddClick(int position) {
        handleAdd(position);
    }

    private void handleAdd(final int position) {
        final AddEquipGoodsBean.DataBean.GoodsBean goodsBean = mDetailslist.get(position);
        showLoadingDialog();
        RetrofitUtils.getInstances().create().upDownDeviceGoods(goodsBean.getGid()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        equipGoodsDataHasChanged = true;
                        //状态改变成功
                        // 0下架，1上架
                        int isUp = goodsBean.getIs_up();
                        if (isUp == 0) {
                            goodsBean.setIs_up(1);
                            showToast("上架成功~");
                        } else {
                            goodsBean.setIs_up(0);
                            showToast("下架成功~");
                        }
                        mAddEquipGoodsDetailsAdapter.notifyDataSetChanged();
                    } else {
                        showToast("操作失败~");
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
