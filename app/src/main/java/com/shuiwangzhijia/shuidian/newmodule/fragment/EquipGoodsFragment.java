package com.shuiwangzhijia.shuidian.newmodule.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.EquipGoodsBean;
import com.shuiwangzhijia.shuidian.event.CommonEvent;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.activity.AddEquipGoodsActivity;
import com.shuiwangzhijia.shuidian.newmodule.adapter.EquipGoodsDetailsAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.EquipGoodsTypeAdapter;
import com.shuiwangzhijia.shuidian.utils.EditInputFilter;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
@FndViewInject(contentViewId = R.layout.fragment_equip_goods)
public class EquipGoodsFragment extends BaseLazyFragment implements EquipGoodsTypeAdapter.OnTypeItemClickListener, EquipGoodsDetailsAdapter.OnDetailsItemOperationClickListener {
    private static final int REQUEST_DATA_CHANGED = 100;
    @BindView(R.id.add_immediately)
    Button mAddImmediately;
    @BindView(R.id.no_equip_goods_data)
    LinearLayout mNoEquipGoodsData;
    @BindView(R.id.type_rv)
    RecyclerView mTypeRv;
    @BindView(R.id.details_rv)
    RecyclerView mDetailsRv;
    @BindView(R.id.type_name)
    TextView mTypeName;
    @BindView(R.id.root_ll)
    LinearLayout mRootLl;
    Unbinder unbinder;
    private List<String> mTypelist;
    private List<EquipGoodsBean.DataBean.GoodsBean> mDetailslist;
    private EquipGoodsDetailsAdapter mEquipGoodsDetailsAdapter;
    private EquipGoodsTypeAdapter mEquipGoodsTypeAdapter;
    private List<EquipGoodsBean.DataBean> mData;

    public EquipGoodsFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView(View view) {
        initTypeRv();
        initDetailsRv();
    }


    private void initTypeRv() {
        mTypeRv.setLayoutManager(new LinearLayoutManager(mContext));
        mTypelist = new ArrayList<>();
        mEquipGoodsTypeAdapter = new EquipGoodsTypeAdapter(mContext);
        mEquipGoodsTypeAdapter.setOnTypeItemClickListener(this);
        mEquipGoodsTypeAdapter.setData(mTypelist);
        mTypeRv.setAdapter(mEquipGoodsTypeAdapter);
    }

    private void initDetailsRv() {
        mDetailsRv.setLayoutManager(new LinearLayoutManager(mContext));
        mDetailslist = new ArrayList<>();
        mEquipGoodsDetailsAdapter = new EquipGoodsDetailsAdapter(mContext);
        mEquipGoodsDetailsAdapter.setOnDetailsItemOperationClickListener(this);
        mEquipGoodsDetailsAdapter.setData(mDetailslist);
        mDetailsRv.setAdapter(mEquipGoodsDetailsAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        getData();
    }


    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getDeviceGoods(mToken).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        mNoEquipGoodsData.setVisibility(View.GONE);
                        mRootLl.setVisibility(View.VISIBLE);
                        EquipGoodsBean equipGoodsBean = mGson.fromJson(datas, EquipGoodsBean.class);
                        mData = equipGoodsBean.getData();
                        mTypelist.clear();
                        for (EquipGoodsBean.DataBean dataBean :
                                mData) {
                            mTypelist.add(dataBean.getType_name());
                        }
                        mEquipGoodsTypeAdapter.notifyDataSetChanged();
                        mEquipGoodsTypeAdapter.setSelectIndex(0);
                        handleDetails(0);
                    } else {
                        mNoEquipGoodsData.setVisibility(View.VISIBLE);
                        mRootLl.setVisibility(View.GONE);
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
        List<EquipGoodsBean.DataBean.GoodsBean> goods = mData.get(position).getGoods();
        mDetailslist.addAll(goods);
        mEquipGoodsDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.add_immediately})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_immediately:
                skipActivityForResult(AddEquipGoodsActivity.class, REQUEST_DATA_CHANGED);
                break;
        }
    }

    @Override
    public void onTypeItemClick(int position) {
        mEquipGoodsTypeAdapter.setSelectIndex(position);
        handleDetails(position);
    }


    @Override
    public void onDetailsItemDeleteClick(int position) {
        handleDelete(position);
    }

    private void handleDelete(final int position) {
        mRxDialogSureCancel.setContent("您确认要删除该商品么？");
        mRxDialogSureCancel.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(position);
            }
        });
        mRxDialogSureCancel.show();
    }

    private void delete(final int position) {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().upDownDeviceGoods(mDetailslist.get(position).getGid()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    mRxDialogSureCancel.dismiss();
                    if (code == 200) {
                        showToast("删除成功~");
                        mDetailslist.remove(position);
                        mEquipGoodsDetailsAdapter.notifyDataSetChanged();
                    } else {
                        showToast("删除失败~");
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
    public void onDetailsEditItemClick(final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.equip_goods_edit_dialog, null);
        ImageView goodsImg = view.findViewById(R.id.goods_img);
        TextView goodsName = view.findViewById(R.id.goods_name);
        TextView goodsPrice = view.findViewById(R.id.goods_price);
        ImageView close = view.findViewById(R.id.close);
        TextView sure = view.findViewById(R.id.sure);
        final EditText sellingPrice = view.findViewById(R.id.selling_price);
        InputFilter[] filters = {new EditInputFilter()};
        sellingPrice.setFilters(filters);
        final EquipGoodsBean.DataBean.GoodsBean goodsBean = mDetailslist.get(position);
        setImg(goodsImg, goodsBean.getPicturl(),"goods/");
        goodsName.setText(goodsBean.getGname());
        goodsPrice.setText("￥" + goodsBean.getPprice());
        sellingPrice.setText(goodsBean.getPrice());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissBottomDialog();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editPrice = sellingPrice.getText().toString().trim();
                handleEdit(position, goodsBean.getId() + "", editPrice);
            }
        });
        showBottomDialog(view);
    }

    private void handleEdit(final int position, String id, final String editPrice) {
        if (isEmpty(editPrice)) {
            showToast("售价不能为空~");
            return;
        }
        showLoadingDialog();
        RetrofitUtils.getInstances().create().editDeviceGoodsPrice(mToken, id, editPrice).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        dismissBottomDialog();
                        showToast("售价编辑成功~");
                        mDetailslist.get(position).setPrice(editPrice);
                        mEquipGoodsDetailsAdapter.notifyDataSetChanged();
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

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void equipGoodsAddGoods(CommonEvent commonEvent) {
        if ("equipGoodsAddGoods".equals(commonEvent.getMsg())) {
            skipActivityForResult(AddEquipGoodsActivity.class, REQUEST_DATA_CHANGED);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DATA_CHANGED && resultCode == RESULT_OK) {
            getData();
        }
    }
}
