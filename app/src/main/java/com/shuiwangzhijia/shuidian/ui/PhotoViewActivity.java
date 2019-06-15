package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.PhotoViewAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.AlbumItem;
import com.shuiwangzhijia.shuidian.event.PictureEvent;
import com.shuiwangzhijia.shuidian.view.PhotoViewPager;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 图片展示
 * Created by wangsuli on 2018/9/3.
 */

public class PhotoViewActivity extends BaseAct {

    private PhotoViewPager mViewPager;
    private int currentPosition;
    private PhotoViewAdapter adapter;
    private List<AlbumItem> data;
    private ArrayList<String> commentData;
    private boolean isComment;

    public static void statAct(Context context, int position, ArrayList<AlbumItem> data, List<String> commentData) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        bundle.putStringArrayList("commentData", (ArrayList<String>) commentData);
        bundle.putInt("currentPosition", position);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        initView();
        initData();
    }

    private void initView() {
        setTitleBarBgColor(R.color.color_black);
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager_photo);
    }

    private void initData() {
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("currentPosition", 0);
        data = (List<AlbumItem>) intent.getSerializableExtra("data");
        if (data == null || data.size() == 0) {
            isComment = true;
            commentData = intent.getStringArrayListExtra("commentData");
            setTitle("图片(" +  (currentPosition + 1) + "/" + commentData.size() + ")");
            adapter = new PhotoViewAdapter(true, this);
            adapter.setCommentData(commentData);
        } else {
            isComment = false;
            setTitle("图片(" +  (currentPosition + 1) + "/" + data.size() + ")");
            adapter = new PhotoViewAdapter(false, this);
            adapter.setImageUrls(data);
            setRightIvClickListener(R.drawable.delete_iv, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new PictureEvent(currentPosition + 1));
                    finish();
                }
            });
        }
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition, false);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                if (isComment) {
                    setTitle("图片(" + (currentPosition + 1) + "/" + commentData.size() + ")");
                } else {
                    setTitle("图片(" + (currentPosition + 1) + "/" + data.size() + ")");
                }
            }
        });
    }

}
