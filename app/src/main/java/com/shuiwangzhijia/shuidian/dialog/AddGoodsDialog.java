package com.shuiwangzhijia.shuidian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by xxc on 2019/3/26.
 */
public class AddGoodsDialog extends BottomDialog {

    private final Context mContext;
    @BindView(R.id.add)
    TextView mCommit;

    public AddGoodsDialog(Context context, final OnConfirmClickListener listener) {
        super(context);
        setContentView(R.layout.add_goods_dialog);
        this.mContext = context;
        setCanceledOnTouchOutside(true);
        ButterKnife.bind(this);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onConfirm("");
                }
            }
        });
    }

    public interface OnConfirmClickListener {
        void onConfirm(String bank);
    }
}
