package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;

/**消息页面
 * created by wangsuli on 2018/8/17.
 */
public class MsgActivity extends BaseAct{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        setTitle("消息");
    }
}
