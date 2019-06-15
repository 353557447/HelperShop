package com.shuiwangzhijia.shuidian.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.dialog.LoadingDialog;
import com.shuiwangzhijia.shuidian.event.RefreshDataEvent;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.DoubleUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUtils;
import com.shuiwangzhijia.shuidian.view.RxDialogSureCancel;
import com.google.gson.Gson;
import com.socks.library.KLog;


import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseLazyFragment extends Fragment {
    protected Context mContext;
    private boolean isPrepared;
    //是否可见状态
    private boolean isVisible;
    private boolean isFirstLoad = true;

    protected Gson mGson;
    private FrameLayout mBaseContainer;
    private LayoutInflater mLayoutInflater;
    private InputMethodManager mIms;
    private Unbinder mUnbinder;
    private LoadingDialog mLoadingDialog;
    protected int mPage = 0;
    protected int mLimit = 10;
    protected boolean isLoadMore;
    protected String mToken;
    protected RxDialogSureCancel mRxDialogSureCancel;
    private Dialog mBottomDialog;
    public int page=-1;
    public int pageSize=10;
    public boolean loading;

    public BaseLazyFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mBaseContainer == null) {
            mBaseContainer = (FrameLayout) inflater.inflate(R.layout.fragment_base_lazy, container, false);
            mLayoutInflater = inflater;
            intParentView();
            mUnbinder = ButterKnife.bind(this, mBaseContainer);
            commonDataCofig();
            initView(mBaseContainer);
            //视图已经初始化完毕
            isPrepared = true;
            initData();
            lazyLoad();
            initEvent();
        }
        return mBaseContainer;
    }

    private void intParentView() {
        if (getClass().isAnnotationPresent(FndViewInject.class)) {
            FndViewInject annotation = getClass().getAnnotation(FndViewInject.class);
            int contentViewId = annotation.contentViewId();
            mLayoutInflater.inflate(contentViewId, mBaseContainer, true);
        } else {
            throw new RuntimeException("请设置ViewInject注解");
        }
    }

    private void commonDataCofig() {
        mGson = new Gson();
        mToken = CommonUtils.getToken();
        initCancelSureRxdialog();
    }

    protected abstract void initView(View view);

    protected void initData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser && isPrepared) {
            lazyLoadData();
            onVisible();
        } else if (!isVisibleToUser && isPrepared) {
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden)
            onVisible();
        else
            onInvisible();
    }

    private void lazyLoad() {
        if (isFirstLoad && isVisible) {
            lazyLoadData();
            onVisible();
            isFirstLoad = false;
        }
    }

    protected abstract void lazyLoadData();

    protected abstract void initEvent();


    protected void onVisible() {

    }


    protected void onInvisible() {

    }


    protected void skipActivity(Class clz) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(mContext, clz);
            startActivity(intent);
        }
    }

    protected void skipActivity(Class clz, Bundle bundle) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(mContext, clz);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    protected void skipActivityForResult(Class clz, int requestCode) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(mContext, clz);
            startActivityForResult(intent, requestCode);
        }
    }

    protected void skipActivityForResult(Class clz, Bundle bundle, int requestCode) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(mContext, clz);
            intent.putExtras(bundle);
            startActivityForResult(intent, requestCode);
        }
    }

    protected void closeActivity() {
        ((Activity) mContext).finish();
        hideSoftKeyBoard();
    }


    protected void showToast(String text) {
        ToastUtils.show(text);
    }

    protected void showErrorToast() {
        ToastUtils.show("服务器异常");
    }

    protected void showLoadingDialog() {
        if (!((Activity) mContext).isFinishing()) {
            dismissLoadingDialog();
            mLoadingDialog = new LoadingDialog(mContext);
            mLoadingDialog.show();
        }
    }

    protected void dismissLoadingDialog() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void hideSoftKeyBoard() {
        View localView = getActivity().getCurrentFocus();
        if (this.mIms == null) {
            this.mIms = ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.mIms != null)) {
            this.mIms.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    protected void initCancelSureRxdialog() {
        if (mRxDialogSureCancel == null) {
            mRxDialogSureCancel = new RxDialogSureCancel(mContext);
            mRxDialogSureCancel.getLogoView().setVisibility(View.GONE);
            mRxDialogSureCancel.setCancelListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRxDialogSureCancel.dismiss();
                }
            });
        }
    }


    //底部弹出窗口
    protected void showBottomDialog(View mBottomDialogView) {
        if (mBottomDialog == null)
            mBottomDialog = new Dialog(mContext, R.style.BottomDialog);
        // mBottomDialogView = LayoutInflater.from(this).inflate(layout, null);
        mBottomDialog.setContentView(mBottomDialogView);
        ViewGroup.LayoutParams layoutParams = mBottomDialogView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        mBottomDialogView.setLayoutParams(layoutParams);
        mBottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        //设置外部可以点击
        mBottomDialog.setCanceledOnTouchOutside(true);
        mBottomDialog.show();
    }

    protected void dismissBottomDialog() {
        if (mBottomDialog != null)
            mBottomDialog.dismiss();
    }

    private LinearLayout mCenterView;
    private ImageView mCenterViewIv;
    private TextView mCenterViewTv;

    protected void setCentreViewShow(int imageResource, String hint) {
        if (mCenterView == null) {
            mCenterView = (LinearLayout) mLayoutInflater.inflate(R.layout.view_center, mBaseContainer, false);
            mCenterViewIv = mCenterView.findViewById(R.id.center_view_iv);
            mCenterViewTv = mCenterView.findViewById(R.id.center_view_tv);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            if (mBaseContainer != null) {
                mBaseContainer.addView(mCenterView, layoutParams);
            }
        }
        mCenterViewIv.setImageResource(imageResource);
        mCenterViewTv.setText(hint);
        mCenterView.setVisibility(View.VISIBLE);
    }

    protected void setCentreViewDismiss() {
        if (mCenterView != null) {
            mCenterView.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(Object messageEvent) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void refreshDataEvent(RefreshDataEvent event) {
        if (this.getClass().getSimpleName().equals(event.getAim())) {
            String methodName = event.getMethodName();
            try {
                Method method = this.getClass().getMethod(methodName);
                method.setAccessible(true);
                method.invoke(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void setImg(ImageView iv, String imgPath,String elsePath) {
        if (imgPath != null && imgPath.length() != 0) {
            Glide.with(this).load(Constant.url +elsePath+ imgPath).placeholder(R.drawable.morentouxiang).into(iv);
            KLog.e("BaseLazyFragment图片地址:"+Constant.url + imgPath);
        } else {
            //占位图
            iv.setImageResource(R.drawable.morentouxiang);
        }
    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        int scrollState = recyclerView.getScrollState();
        if (scrollState == 0) return false;
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    public boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        } else {
            return false;
        }
    }
}
