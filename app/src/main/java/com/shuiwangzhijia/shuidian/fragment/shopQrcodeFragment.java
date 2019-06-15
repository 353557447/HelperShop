package com.shuiwangzhijia.shuidian.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseFragment;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.ShareShopBean;
import com.shuiwangzhijia.shuidian.dialog.ShareDialog;
import com.shuiwangzhijia.shuidian.event.SavePictureEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE.path;



public class shopQrcodeFragment extends BaseFragment {
    @BindView(R.id.shopqr_rootview)
    LinearLayout mShopqrRootview;
    @BindView(R.id.share)
    TextView mShare;
    @BindView(R.id.save)
    TextView mSave;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.circle_iv)
    CircleImageView mCircleIv;
    @BindView(R.id.picture)
    ImageView mPicture;
    private Unbinder unbinder;
    private Bitmap mBitmap;
    private static String sAbsolutePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_shopqrcode, container, false);
            unbinder = ButterKnife.bind(this, mRootView);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            unbinder = ButterKnife.bind(this, mRootView);
        }
        initData();
        return mRootView;
    }

    private void initData() {
        showLoad();
        RetrofitUtils.getInstances().create().shareShop(CommonUtils.getToken()).enqueue(new Callback<EntityObject<ShareShopBean>>() {
            @Override
            public void onResponse(Call<EntityObject<ShareShopBean>> call, Response<EntityObject<ShareShopBean>> response) {
                hintLoad();
                EntityObject<ShareShopBean> body = response.body();
                if (body.getCode() == 200) {
                    ShareShopBean result = body.getResult();
                    String sname = result.getSname();
                    mName.setText(sname);
                    Glide.with(mContext).load(Constant.url+result.getHeader_pic()).placeholder(R.drawable.morenshuidian).into(mCircleIv);
                    Glide.with(mContext).load(Constant.url + result.getWxcode()).placeholder(R.drawable.wutupian).into(mPicture);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<ShareShopBean>> call, Throwable t) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(SavePictureEvent event) {

    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 其次把文件插入到系统图库
        MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, "title", "description");
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }


    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.share, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.share:
                //分享
                //首先保存图片
                mBitmap = getViewBitmap(mShopqrRootview);
                savePicture(mBitmap);
                handleShare();
                break;
            case R.id.save:
                mBitmap = getViewBitmap(mShopqrRootview);
                // 其次把文件插入到系统图库
                saveImageToGallery(getActivity(), mBitmap);
                ToastUitl.showToastCustom("保存本地成功");
                break;
        }
    }

    private void savePicture(Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (appDir.exists()){
            deleteDir(appDir.getAbsolutePath());
        }
        appDir.mkdir();
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        sAbsolutePath = file.getAbsolutePath();
        KLog.d(sAbsolutePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleShare() {
        ShareDialog shareDialog = new ShareDialog(getActivity(), new ShareDialog.OnShareConfirmClickListener() {
            @Override
            public void onShare(Dialog dialog, int type) {
                //centreCommonDialog.dismiss();
                dialog.dismiss();
                Platform platform = null;
                Platform.ShareParams shareParams = null;
                switch (type) {
                    case 0:
                        platform = ShareSDK.getPlatform(Wechat.NAME);
                        shareParams = new Platform.ShareParams();
                        shareParams.setImageData(mBitmap);  //用于微信和微信朋友圈
                        break;
                    case 1:
                        platform = ShareSDK.getPlatform(WechatMoments.NAME);
                        shareParams = new Platform.ShareParams();
                        shareParams.setImageData(mBitmap);  //用于微信和微信朋友圈
                        break;
                    case 2:
                        platform = ShareSDK.getPlatform(QQ.NAME);
                        shareParams = new Platform.ShareParams();
                        shareParams.setImagePath(sAbsolutePath);  //用于QQ和QQ空间
                        break;
                    case 3:
                        platform = ShareSDK.getPlatform(QZone.NAME);
                        shareParams = new Platform.ShareParams();
                        shareParams.setImagePath(sAbsolutePath);  //用于QQ和QQ空间
                        break;

                }
                shareParams.setShareType(Platform.SHARE_IMAGE);
                platform.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        KLog.d("1111");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        KLog.d("2222" + throwable.toString());
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        KLog.d("3333");
                    }
                });
                platform.share(shareParams);
            }
        });
        if (!shareDialog.isShowing()) {
            shareDialog.show();
        }
    }
}
