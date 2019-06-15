package com.shuiwangzhijia.shuidian.newmodule.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.GMNewBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.GmGoodsDetailsAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.GoodsTypeAdapter;
import com.shuiwangzhijia.shuidian.utils.EditInputFilter;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@FndViewInject(contentViewId = R.layout.fragment_gm_in_the_sale)
public class GmInTheSaleFragment extends BaseLazyFragment implements GoodsTypeAdapter.OnTypeItemClickListener, GmGoodsDetailsAdapter.OnDetailsItemOperationClickListener {
    @BindView(R.id.type_rv)
    RecyclerView mTypeRv;
    @BindView(R.id.type_name)
    TextView mTypeName;
    @BindView(R.id.details_rv)
    RecyclerView mDetailsRv;
    @BindView(R.id.no_equip_goods_data)
    LinearLayout mNoEquipGoodsData;
    private List<String> mTypelist;
    private GoodsTypeAdapter mGoodsTypeAdapter;
    private List<GMNewBean.DataBean> mData;
    private List<GMNewBean.DataBean.ListBean> mDetailslist;
    private GmGoodsDetailsAdapter mGmGoodsDetailsAdapter;

    public GmInTheSaleFragment() {
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
        mGoodsTypeAdapter = new GoodsTypeAdapter(mContext);
        mGoodsTypeAdapter.setOnTypeItemClickListener(this);
        mGoodsTypeAdapter.setData(mTypelist);
        mTypeRv.setAdapter(mGoodsTypeAdapter);
    }

    private void initDetailsRv() {
        mDetailsRv.setLayoutManager(new LinearLayoutManager(mContext));
        mDetailslist = new ArrayList<>();
        mGmGoodsDetailsAdapter = new GmGoodsDetailsAdapter(mContext);
        mGmGoodsDetailsAdapter.setOnDetailsItemOperationClickListener(this);
        mGmGoodsDetailsAdapter.setData(mDetailslist);
        mDetailsRv.setAdapter(mGmGoodsDetailsAdapter);
    }


    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initData() {
        super.initData();
        getData();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getData();
        }
    }

    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getGoodsManageNewList(1).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
                        GMNewBean gmNewBean = mGson.fromJson(datas, GMNewBean.class);
                        mData = gmNewBean.getData();
                        mTypelist.clear();
                        for (GMNewBean.DataBean dataBean :
                                mData) {
                            mTypelist.add(dataBean.getGname());
                        }
                        mGoodsTypeAdapter.notifyDataSetChanged();
                        mGoodsTypeAdapter.setSelectIndex(0);
                        handleDetails(0);
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


    private void handleDetails(int position) {
        mDetailslist.clear();
        mTypeName.setText(mTypelist.get(position));
        List<GMNewBean.DataBean.ListBean> list = mData.get(position).getList();
        mDetailslist.addAll(list);
        mGmGoodsDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onTypeItemClick(int position) {
        mGoodsTypeAdapter.setSelectIndex(position);
        handleDetails(position);
    }

    @Override
    public void onDetailsItemDeleteClick(int position) {
        handleDelete(position);
    }

    private void handleDelete(final int position) {
        mRxDialogSureCancel.setContent("您确认要下架该商品么？");
        mRxDialogSureCancel.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(position);
            }
        });
        mRxDialogSureCancel.show();
    }


    private void delete(final int position) {
        GMNewBean.DataBean.ListBean listBean = mDetailslist.get(position);
        showLoadingDialog();
        RetrofitUtils.getInstances().create().goodsManageNewUpDownGoods(listBean.getSid(), listBean.getGid()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    mRxDialogSureCancel.dismiss();
                    if (code == 200) {
                        showToast("下架成功~");
                        mDetailslist.remove(position);
                        mGmGoodsDetailsAdapter.notifyDataSetChanged();
                    } else {
                        showToast("下架失败~");
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
        final GMNewBean.DataBean.ListBean goodsBean = mDetailslist.get(position);
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
        RetrofitUtils.getInstances().create().editGoodsManagementPrice(id, editPrice).enqueue(new Callback<Object>() {
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
                        mGmGoodsDetailsAdapter.notifyDataSetChanged();
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
}
