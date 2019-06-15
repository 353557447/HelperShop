package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruce.pickerview.LoopScrollListener;
import com.bruce.pickerview.LoopView;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.EquipModifySelGoodsBean;
import com.shuiwangzhijia.shuidian.bean.ModifyEquipGoodsReqBean;
import com.shuiwangzhijia.shuidian.bean.SmartEquipCountersBean;
import com.shuiwangzhijia.shuidian.bean.SmartEquipDataBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.EquipDetailsAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.EquipModifySelGoodsAdapter;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.shuiwangzhijia.shuidian.view.CentreCommonDialog;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@FndViewInject(contentViewId = R.layout.activity_equip_details, title = "设备详情")
public class EquipDetailsActivity extends BaseActivity implements EquipDetailsAdapter.OnSwitchClickListener, EquipDetailsAdapter.OnItemClickListener, EquipDetailsAdapter.OnModifyClickListener, EquipModifySelGoodsAdapter.OnGoodsSelectedListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.equip_name)
    TextView mEquipName;
    @BindView(R.id.one_key_open)
    TextView mOneKeyOpen;
    @BindView(R.id.equip_id)
    TextView mEquipId;
    @BindView(R.id.all_select)
    TextView mAllSelect;
    private boolean isAllSelected;
    private List<SmartEquipCountersBean.DataBean> mList;
    private List<SmartEquipDataBean.DataBean> mSmartEquipDataList;
    private EquipDetailsAdapter mEquipDetailsAdapter;
    private int mDeviceId;
    private String mDeviceName;
    private StringBuilder mStringBuilder;
    private int mLoopStayPosition;
    private List<EquipModifySelGoodsBean.DataBean> mDialogList;
    private EquipModifySelGoodsAdapter mEquipModifySelGoodsAdapter;
    private CentreCommonDialog mCentreCommonDialog;
    private String mGoodsId;
    private int equipSelectedId;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        mDeviceId = bundle.getInt("deviceId");
        mDeviceName = bundle.getString("deviceName");
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        mEquipName.setText("设备名称：" + mDeviceName);
        mEquipId.setText("设备ID：" + mDeviceId);

        initRv();
    }

    private void initRv() {
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 1 ? 2 : 1;
            }
        });
        mRv.setLayoutManager(manager);
        mList = new ArrayList<>();
        mEquipDetailsAdapter = new EquipDetailsAdapter(this);
        mEquipDetailsAdapter.setData(mList);
        mEquipDetailsAdapter.setOnSwitchClickListener(this);
        mEquipDetailsAdapter.setOnItemClickListener(this);
        mEquipDetailsAdapter.setOnModifyClickListener(this);
        mRv.setAdapter(mEquipDetailsAdapter);
    }

    @Override
    protected void initData() {
        mStringBuilder = new StringBuilder();
        getDeviceData();
    }

    private void getDeviceData() {
        RetrofitUtils.getInstances().create().getSmartEquipData(mToken, mDeviceId).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        SmartEquipDataBean smartEquipDataBean = mGson.fromJson(datas, SmartEquipDataBean.class);
                        mSmartEquipDataList = smartEquipDataBean.getData();
                        SmartEquipDataBean.DataBean dataBean = mSmartEquipDataList.get(0);
                        mEquipDetailsAdapter.setCounterCounts(mSmartEquipDataList.size());
                        mEquipDetailsAdapter.setCounterName(dataBean.getCabinet_number());
                        equipSelectedId = dataBean.getId();
                        getEquipCountersPositionData(equipSelectedId);
                    } else {

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

    private void getEquipCountersPositionData(int id) {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getSmartEquipCounters(mToken, id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        SmartEquipCountersBean smartEquipCountersBean = mGson.fromJson(datas, SmartEquipCountersBean.class);
                        List<SmartEquipCountersBean.DataBean> data = smartEquipCountersBean.getData();
                        if (data.size() >= 1)
                            data.add(1, null);
                        mList.clear();
                        mList.addAll(data);
                        mEquipDetailsAdapter.notifyDataSetChanged();
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

    @Override
    protected void initEvent() {

    }

    @Override
    public void OnSwitchClick() {
        View bottomDialog = LayoutInflater.from(this).inflate(R.layout.bottom_select_smart_equip, null);
        TextView sure = bottomDialog.findViewById(R.id.sure);
        TextView cancel = bottomDialog.findViewById(R.id.cancel);
        LoopView equipLoopView = bottomDialog.findViewById(R.id.equipLoopView);
        equipLoopView.setInitPosition(0);
        equipLoopView.setCanLoop(false);
        mLoopStayPosition=0;
        equipLoopView.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                mLoopStayPosition = item;
            }
        });
        List<String> list = new ArrayList<>();
        for (SmartEquipDataBean.DataBean dataBean :
                mSmartEquipDataList) {
            int cabinetNumber = dataBean.getCabinet_number();
            list.add(cabinetNumber + "号柜子");
        }
        equipLoopView.setDataList(list);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissBottomDialog();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.e("mLoopStayPosition:"+mLoopStayPosition);
                equipSelectedId = mSmartEquipDataList.get(mLoopStayPosition).getId();
                mEquipDetailsAdapter.setCounterName(mSmartEquipDataList.get(mLoopStayPosition).getCabinet_number());
                getEquipCountersPositionData(equipSelectedId);
                dismissBottomDialog();
            }
        });
        showBottomDialog(bottomDialog);
    }

    @Override
    public void onItemClick(int position) {
        SmartEquipCountersBean.DataBean dataBean = mList.get(position);
        if (dataBean != null)
            dataBean.setChecked(!dataBean.isChecked());
        mEquipDetailsAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.one_key_open, R.id.all_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.one_key_open:
                handleOpen();
                break;
            case R.id.all_select:
                isAllSelected = !isAllSelected;
                if(isAllSelected){
                    mAllSelect.setText("取消全选");
                }else{
                    mAllSelect.setText("全选");
                }
                for (SmartEquipCountersBean.DataBean dataBean :
                        mList) {
                    if (dataBean != null)
                        dataBean.setChecked(isAllSelected);
                }
                mEquipDetailsAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void handleOpen() {
        mStringBuilder.setLength(0);
        for (SmartEquipCountersBean.DataBean dataBean :
                mList) {
            if (dataBean != null) {
                if (dataBean.isChecked()) {
                    mStringBuilder.append(dataBean.getId() + ",");
                }
            }
        }
        String ids = mStringBuilder.toString();
        if (isEmpty(ids)) {
            showToast("请选择要打开的柜位~");
            return;
        }
        showLoadingDialog();
        RetrofitUtils.getInstances().create().openSmartEquipCounters(mToken, ids).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        showToast(object.getString("msg"));
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
    public void OnModifyClick(int position) {
        handleModify(position);
    }

    private void handleModify(final int position) {
        SmartEquipCountersBean.DataBean dataBean = mList.get(position);
        mCentreCommonDialog = new CentreCommonDialog(this, R.layout.dialog_center_equip_select_goods) {
            @Override
            protected void initViewAndEvent(View view) {
                ImageView centerDialogClose = view.findViewById(R.id.center_dialog_close);
                TextView confirmSelect = view.findViewById(R.id.confirm_select);
                TextView clearGrid = view.findViewById(R.id.clear_grid);
                RecyclerView dialogRv = view.findViewById(R.id.dialog_rv);

                dialogRv.setLayoutManager(new GridLayoutManager(EquipDetailsActivity.this, 3));
                mDialogList = new ArrayList<EquipModifySelGoodsBean.DataBean>();
                mEquipModifySelGoodsAdapter = new EquipModifySelGoodsAdapter(EquipDetailsActivity.this);
                mEquipModifySelGoodsAdapter.setData(mDialogList);
                mEquipModifySelGoodsAdapter.setOnGoodsSelectedListener(EquipDetailsActivity.this);
                dialogRv.setAdapter(mEquipModifySelGoodsAdapter);

                getSelectGoodsData();


                centerDialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
                confirmSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleConfirmSelect(position);
                    }
                });
                clearGrid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleClearGrid(position);
                    }
                });
            }
        };

        mCentreCommonDialog.show();
    }

    private void getSelectGoodsData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().selectCounterGoods(mToken).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        EquipModifySelGoodsBean equipModifySelGoodsBean = mGson.fromJson(datas, EquipModifySelGoodsBean.class);
                        List<EquipModifySelGoodsBean.DataBean> data = equipModifySelGoodsBean.getData();
                        mDialogList.addAll(data);
                        mEquipModifySelGoodsAdapter.notifyDataSetChanged();
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

    private void handleClearGrid(final int position) {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().removeCounterGoods(mToken, mList.get(position).getId() + "").enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        showToast("清空成功");
                        mCentreCommonDialog.dismiss();
                        getEquipCountersPositionData(equipSelectedId);
                    } else {
                        showToast("清空失败");
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

    private void handleConfirmSelect(int position) {
        //柜位id
        int id = mList.get(position).getId();
        if (mGoodsId == null) {
            showToast("请先选择放入柜位的商品~");
            return;
        }
        ModifyEquipGoodsReqBean modifyEquipGoodsReqBean = new ModifyEquipGoodsReqBean();
        modifyEquipGoodsReqBean.setId(id);
        modifyEquipGoodsReqBean.setGoods_id(Integer.parseInt(mGoodsId));
        List<ModifyEquipGoodsReqBean> params = new ArrayList<>();
        params.add(modifyEquipGoodsReqBean);
        RetrofitUtils.getInstances().create().modifyCounterGoods(mToken, mGson.toJson(params)).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        showToast("商品替换成功");
                        mCentreCommonDialog.dismiss();
                        getEquipCountersPositionData(equipSelectedId);
                    } else {

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
        mGoodsId = null;
    }

    @Override
    public void onGoodsSelected(int position) {
        for (int i = 0; i < mDialogList.size(); i++) {
            EquipModifySelGoodsBean.DataBean dataBean = mDialogList.get(i);
            if (i == position) {
                dataBean.setChecked(true);
            } else {
                dataBean.setChecked(false);
            }

        }
        mEquipModifySelGoodsAdapter.notifyDataSetChanged();
        EquipModifySelGoodsBean.DataBean dataBean = mDialogList.get(position);
        mGoodsId = dataBean.getGid();
    }
}
