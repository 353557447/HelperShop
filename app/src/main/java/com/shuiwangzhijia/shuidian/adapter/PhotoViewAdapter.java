package com.shuiwangzhijia.shuidian.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.bean.AlbumItem;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by wangsuli on 2018/9/3.
 */
public class PhotoViewAdapter extends PagerAdapter {
    public static final String TAG = PhotoViewAdapter.class.getSimpleName();

    public List<AlbumItem> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<AlbumItem> imageUrls) {
        this.imageUrls = imageUrls;
        notifyDataSetChanged();
    }

    private List<AlbumItem> imageUrls;

    public List<String> getCommentData() {
        return commentData;
    }

    public void setCommentData(List<String> commentData) {
        this.commentData = commentData;
        notifyDataSetChanged();
    }

    private List<String> commentData;
    private Activity activity;
    private boolean isComment;

    public PhotoViewAdapter(boolean isComment, Activity activity) {
        this.isComment=isComment;
        if (isComment) {
            commentData = new ArrayList<>();
        } else {
            imageUrls = new ArrayList<>();
        }
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(activity);
        if (isComment) {
            Glide.with(activity).load(Constant.COMMENT_IMAGE_URL + commentData.get(position)).placeholder(R.color.color_eeeeee).into(photoView);
        } else {
            AlbumItem item = imageUrls.get(position);
            Uri uri = Uri.fromFile(new File(item.getFilePath()));
            Glide.with(activity).load(uri).into(photoView);
        }
        container.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        return photoView;
    }

    @Override
    public int getCount() {
        if(isComment){
            return commentData != null ? commentData.size() : 0;
        }else{
            return imageUrls != null ? imageUrls.size() : 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
