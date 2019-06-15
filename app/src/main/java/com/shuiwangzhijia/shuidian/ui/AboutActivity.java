package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;

/**关于我们
 * created by wangsuli on 2018/8/23.
 */
public class AboutActivity extends BaseAct {

    private TextView versionName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("关于我们");
        versionName =(TextView)findViewById(R.id.versionName);
        versionName.setText("V"+ CommonUtils.getAppVersionName(this));
    }
}
