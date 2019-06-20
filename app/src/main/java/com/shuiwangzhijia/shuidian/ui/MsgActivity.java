package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;

public class MsgActivity extends BaseAct{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        setTitle("消息");
    }
}
