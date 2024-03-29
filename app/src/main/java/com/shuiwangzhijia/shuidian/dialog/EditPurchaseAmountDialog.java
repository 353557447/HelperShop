package com.shuiwangzhijia.shuidian.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.GoodsBean;


public class EditPurchaseAmountDialog extends Dialog implements View.OnClickListener {
    private final TextView cutBtn, addBtn;
    private final EditText editNum;
    private TextView cancelBtn, confirmBtn;
    private Context mContext;
    private EditPurchaseAmountConfirmListener listener;
    private int currentCount;
    private GoodsBean data;

    public EditPurchaseAmountDialog(Context context, GoodsBean data, final EditPurchaseAmountConfirmListener listener) {
        super(context, R.style.MyDialogStyle);
        mContext = context;
        this.data=data;
        this.currentCount = data.getCount();
        this.listener = listener;
        setContentView(R.layout.dialog_edit_purchase_amount);
        editNum = (EditText) findViewById(R.id.editNum);
        cutBtn = (TextView) findViewById(R.id.cutBtn);
        addBtn = (TextView) findViewById(R.id.addBtn);
        cancelBtn = (TextView) findViewById(R.id.cancelBtn);
        confirmBtn = (TextView) findViewById(R.id.confirmBtn);

        cutBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (!TextUtils.isEmpty(str)) {
                    int count = Integer.parseInt(str);
                    currentCount = count;
                }
            }
        };
        editNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        editNum.addTextChangedListener(watcher);
        setCount(currentCount + "");
    }

    /**
     * 设置数字
     *
     * @param count
     */
    private void setCount(String count) {
        editNum.setText(count);
        editNum.setSelection(count.length());
    }

    private void showHint(String hint) {
        Toast.makeText(mContext, hint, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cutBtn:
                if (currentCount > data.getLeast_p()) {
                    currentCount = currentCount - 1;
                    setCount(currentCount + "");
                } else {
                    showHint("购买数量不能小于"+data.getLeast_p());
                }
                break;
            case R.id.addBtn:
                currentCount = currentCount + 1;
                setCount(currentCount + "");
                break;
            case R.id.cancelBtn:
                dismiss();
                break;
            case R.id.confirmBtn:
                if (currentCount < data.getLeast_p()) {
                    showHint("购买数量不能小于"+data.getLeast_p());
                } else {
                    listener.onEditPurchaseAmountConfirm(currentCount);
                    dismiss();
                }
                break;
        }

    }

    public interface EditPurchaseAmountConfirmListener {
        void onEditPurchaseAmountConfirm(int count);
    }

    @Override
    public void show() {
        super.show();
        this.getWindow().setWindowAnimations(R.style.DialogOutAndInStyle);
    }
}
