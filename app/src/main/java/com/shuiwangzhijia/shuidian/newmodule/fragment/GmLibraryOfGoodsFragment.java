package com.shuiwangzhijia.shuidian.newmodule.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseLazyFragment;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.bean.GMNewBean;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.newmodule.adapter.GmAddGoodsDetailsAdapter;
import com.shuiwangzhijia.shuidian.newmodule.adapter.GoodsTypeAdapter;
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
@FndViewInject(contentViewId = R.layout.fragment_gm_library_of_goods)
public class GmLibraryOfGoodsFragment extends BaseLazyFragment implements GoodsTypeAdapter.OnTypeItemClickListener, GmAddGoodsDetailsAdapter.OnDetailsItemAddClickListener {
    @BindView(R.id.type_rv)
    RecyclerView mTypeRv;
    @BindView(R.id.type_name)
    TextView mTypeName;
    @BindView(R.id.details_rv)
    RecyclerView mDetailsRv;
    private ArrayList<String> mTypelist;
    private GoodsTypeAdapter mGoodsTypeAdapter;
    private List<GMNewBean.DataBean> mData;
    private GmAddGoodsDetailsAdapter mGmAddGoodsDetailsAdapter;
    private List<GMNewBean.DataBean.ListBean> mDetailslist;

    public GmLibraryOfGoodsFragment() {
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
        mGmAddGoodsDetailsAdapter = new GmAddGoodsDetailsAdapter(mContext);
        mGmAddGoodsDetailsAdapter.setOnDetailsItemClickListener(this);
        mGmAddGoodsDetailsAdapter.setData(mDetailslist);
        mDetailsRv.setAdapter(mGmAddGoodsDetailsAdapter);
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


    @Override
    protected void lazyLoadData() {

    }


    private void getData() {
        showLoadingDialog();
        RetrofitUtils.getInstances().create().getGoodsManageNewList(0).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object =new JSONObject(datas);
                    int code = object.getInt("code");
                    if(code==200){
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
                    }else{

                    }
                } catch (Exception e) {
                    KLog.e(e.getMessage());
                }finally {
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
        mGmAddGoodsDetailsAdapter.notifyDataSetChanged();
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
    public void onDetailsItemAddClick(int position) {
        handleAdd(position);
    }

    private void handleAdd(final int position) {
        final GMNewBean.DataBean.ListBean goodsBean = mDetailslist.get(position);
        showLoadingDialog();
        RetrofitUtils.getInstances().create().goodsManageNewUpDownGoods(goodsBean.getSid(),goodsBean.getGid()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String datas = mGson.toJson(response.body());
                    KLog.e(datas);
                    JSONObject object = new JSONObject(datas);
                    int code = object.getInt("code");
                    if (code == 200) {
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
                        mGmAddGoodsDetailsAdapter.notifyDataSetChanged();
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
