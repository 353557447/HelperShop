package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.AddressAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.AddressBean;
import com.shuiwangzhijia.shuidian.event.AddressEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddressManageActivity extends BaseAct implements AddressAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.addBtn)
    TextView addBtn;
    @BindView(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    private AddressAdapter mAddressAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage);
        ButterKnife.bind(this);
        setTitle("地址管理");
        initRecyclerView();
        initData();
    }

    private void initData() {
        showLoad();
        RetrofitUtils.getInstances().create().getAddressList().enqueue(new Callback<EntityObject<ArrayList<AddressBean>>>() {
            @Override
            public void onResponse(Call<EntityObject<ArrayList<AddressBean>>> call, Response<EntityObject<ArrayList<AddressBean>>> response) {
                hintLoad();
                EntityObject<ArrayList<AddressBean>> body = response.body();
                if (body.getCode() == 200) {
                    ArrayList<AddressBean> result = body.getResult();
                    if (result == null || result.size() == 0) {
                        rlEmpty.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                        return;
                    } else {
                        rlEmpty.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAddressAdapter.setData(result);
                    }
                }else{
                    rlEmpty.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ArrayList<AddressBean>>> call, Throwable t) {

            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
    /*    DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        mRecyclerView.addItemDecoration(divider);*/
        mAddressAdapter = new AddressAdapter(this);
        mAddressAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAddressAdapter);
    }

    @OnClick(R.id.addBtn)
    public void onViewClicked() {
        EditAddressActivity.startAct(this, EditAddressActivity.TYPE_ADD, null);
    }

    @Override
    public void onUpdateClick(int position) {
        EditAddressActivity.startAct(this, EditAddressActivity.TYPE_UPDATE, mAddressAdapter.getItem(position));
    }

    @Override
    public void onDefaultClick(final int position) {
        AddressBean item = mAddressAdapter.getItem(position);
        showLoad();
        RetrofitUtils.getInstances().create().setDefaultAddress(item.getId()).enqueue(new Callback<EntityObject<String>>() {
            @Override
            public void onResponse(Call<EntityObject<String>> call, Response<EntityObject<String>> response) {
                hintLoad();
                EntityObject<String> body = response.body();
                if (body.getCode() == 200) {
                    for (int i = 0; i < mAddressAdapter.getItemCount(); i++) {
                        mAddressAdapter.getItem(i).setDefaultInt(position == i ? 1 : 0);
                    }
                    mAddressAdapter.notifyDataSetChanged();
                } else {
                    hint(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<EntityObject<String>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDeleteClick(int position) {
        showLoad();
        final AddressBean item = mAddressAdapter.getItem(position);
        RetrofitUtils.getInstances().create().deleteAddress(item.getId()).enqueue(new Callback<EntityObject<String>>() {
            @Override
            public void onResponse(Call<EntityObject<String>> call, Response<EntityObject<String>> response) {
                hintLoad();
                EntityObject<String> body = response.body();
                if (body.getCode() == 200) {
                    hint("删除成功!");
                    initData();
                    EventBus.getDefault().post(new AddressEvent(item,true));
                } else {
                    hint("删除失败!");
                }
            }

            @Override
            public void onFailure(Call<EntityObject<String>> call, Throwable t) {

            }
        });
        mAddressAdapter.deleteItem(position);
    }

    @Override
    public void onItemClick(int position) {
        AddressBean item = mAddressAdapter.getItem(position);
        EventBus.getDefault().post(new AddressEvent(item,false));
        finish();

    }

    private void hint(String text) {
        ToastUitl.showToastCustom(text);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(AddressEvent event) {
        if (event.data == null) {
            initData();
        }
    }
}
