package com.shuiwangzhijia.shuidian.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.ShareAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShareDialog extends BottomDialog {

    private final GridLayoutManager layoutManager;
    private final ShareAdapter shareAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private OnShareConfirmClickListener onShareConfirmClickListener;
    @BindView(R.id.cancelBtn)
    TextView cancelBtn;

    public ShareDialog(Context context, final OnShareConfirmClickListener listener) {
        super(context);
        setContentView(R.layout.dialog_share);
        onShareConfirmClickListener = listener;
        setCanceledOnTouchOutside(true);
        ButterKnife.bind(this);
        layoutManager = new GridLayoutManager(context,3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        shareAdapter = new ShareAdapter(context);
        mRecyclerView.setAdapter(shareAdapter);
        shareAdapter.setOnItemClickListener(new ShareAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onShareConfirmClickListener.onShare(ShareDialog.this, position);
            }
        });
    }

    @OnClick({R.id.cancelBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancelBtn:
                dismiss();
                break;
        }
    }

    public interface OnShareConfirmClickListener {
        void onShare(Dialog dialog, int type);
    }
}
