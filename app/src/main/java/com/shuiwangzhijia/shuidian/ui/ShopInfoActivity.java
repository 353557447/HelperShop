package com.shuiwangzhijia.shuidian.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.App;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.base.Constant;
import com.shuiwangzhijia.shuidian.bean.AddressBean;
import com.shuiwangzhijia.shuidian.bean.AlbumItem;
import com.shuiwangzhijia.shuidian.bean.ShopInfoData;
import com.shuiwangzhijia.shuidian.dialog.SelectImageDialog;
import com.shuiwangzhijia.shuidian.event.AddressEvent;
import com.shuiwangzhijia.shuidian.http.EntityObject;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CommonUtils;
import com.shuiwangzhijia.shuidian.utils.ImageUtil;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopInfoActivity extends BaseAct {
    @BindView(R.id.shopImageTv)
    ImageView shopImageTv;
    @BindView(R.id.phoneNumTv)
    TextView phoneNumTv;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.llArea)
    LinearLayout llArea;
    @BindView(R.id.editDetailAddress)
    EditText editDetailAddress;
    @BindView(R.id.saveBtn)
    TextView saveBtn;
    private ShopInfoData data;
    private String photoPath;
    private File photoFile;
    public static final int REQUEST_CAMERA = 106; // 拍照
    private static final int CODE_FOR_WRITE_PERMISSION = 666;
    private String url;//上传后的url
    private String address, lnglat;
    private String province;
    private String city;
    private String dist;
    private AddressBean mAddressBean;

    public static void startAtc(Context context, ShopInfoData data) {
        Intent intent = new Intent(context, ShopInfoActivity.class);
        intent.putExtra("data", (Serializable) data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        ButterKnife.bind(this);
        data = (ShopInfoData) (getIntent().getSerializableExtra("data"));
        initData();
        setTitle("基本资料");
    }

    private void initData() {
        Glide.with(this).load(Constant.CACHE_IMAGE_URL+data.getHeader_pic()).placeholder(R.drawable.morentouxiang).into(shopImageTv);
        phoneNumTv.setText(data.getAccount());
        shopName.setText(data.getSname());
        province = data.getProvice();
        city = data.getCity();
        dist = data.getDist();
        area.setText(province + "-" + city + "-" + dist);
        editDetailAddress.setText(data.getAddress());
        address = data.getAddress();
        lnglat = data.getLnglat();
        url = data.getHeader_pic();
    }

    @OnClick({R.id.shopImageTv, R.id.llArea, R.id.saveBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopImageTv:
                SelectImageDialog dialog = new SelectImageDialog(ShopInfoActivity.this, new SelectImageDialog.OnItemClickListener() {
                    @Override
                    public void onClick(SelectImageDialog dialog, int index) {
                        dialog.dismiss();
                        switch (index) {
                            case SelectImageDialog.TYPE_CAMERA:
                                takeAPicture();
                                break;

                            case SelectImageDialog.TYPE_PHOTO:
                                Intent intent = new Intent(ShopInfoActivity.this, SelectImageActivity.class);
                                //控制选择的图片数量
                                intent.putExtra(SelectImageActivity.LIMIT, 1);
                                startActivityForResult(intent, SelectImageActivity.REQUEST_COMMMENT_IMAGE);
                                break;
                        }
                    }
                });
                if (!dialog.isShowing()) {
                    dialog.show();
                }
                break;
            case R.id.llArea:
                startActivity(new Intent(this, LocationInfoActivity.class));
                break;
            case R.id.saveBtn:
                if (TextUtils.isEmpty(url)) {
                    ToastUitl.showToastCustom("请选择头像!");
                    return;
                }
                address = editDetailAddress.getText().toString();
                if (TextUtils.isEmpty(address)) {
                    ToastUitl.showToastCustom("请输入详细地址!");
                    return;
                }
                RetrofitUtils.getInstances().create().updateShopInfoData(url, data.getSname(), address, lnglat, province, city, dist).enqueue(new Callback<EntityObject<ShopInfoData>>() {
                    @Override
                    public void onResponse(Call<EntityObject<ShopInfoData>> call, Response<EntityObject<ShopInfoData>> response) {
                        EntityObject<ShopInfoData> body = response.body();
                        if (body.getCode() == 200) {
                            ToastUitl.showToastCustom("保存成功!");
                            finish();
                        } else {
                            ToastUitl.showToastCustom(body.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<EntityObject<ShopInfoData>> call, Throwable t) {

                    }
                });

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(AddressEvent event) {
        if (event.data != null) {
            mAddressBean = event.data;
            KLog.e(mAddressBean.toString());
            province = mAddressBean.getProvince();
            city = mAddressBean.getCity();
            dist = mAddressBean.getDist();
            address = mAddressBean.getDaddress();
            editDetailAddress.setText(mAddressBean.getDaddress());
            lnglat = mAddressBean.getUlnglat();
            area.setText(province + "-" + city + "-" + dist);
        }
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(ShopInfoActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#30ADFD")
                .cancelTextColor("#666666")
                .province("广东省")
                .city("深圳市")
                .district("福田区")
                .textColor(getResources().getColor(R.color.color_999999))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                area.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == SelectImageActivity.REQUEST_COMMMENT_IMAGE) {
            //从相册选照片
            ArrayList<AlbumItem> list = (ArrayList<AlbumItem>) data.getSerializableExtra("images");
            if (list == null)
                return;
            photoPath = list.get(0).getFilePath();
            Glide.with(this).load(photoPath).placeholder(R.drawable.morentouxiang).into(shopImageTv);
            uploadImage(photoPath);
        } else if (requestCode == REQUEST_CAMERA) {
            //拍照照片
            AlbumItem newItem = new AlbumItem();
            newItem.setFilePath(photoPath);
            Glide.with(this).load(photoPath).placeholder(R.drawable.morentouxiang).into(shopImageTv);
            uploadImage(photoPath);
        } else if (requestCode == Constant.IMAGE_CROP) {
            //经过裁剪后的图片
            if (data != null) {
            }
        }
    }

    public void uploadImage(String imagePath) {
        String currentPath = Constant.CACHE_DIR_IMAGE + CommonUtils.getUUID() + ".jpg";
        try {
            //压缩处理
            ImageUtil.getInstance().compressFromImageFile(new File(imagePath), 480, 480, currentPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(currentPath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
        RetrofitUtils.getInstances().create().uploadImage(body).enqueue(new Callback<EntityObject<AlbumItem>>() {
            @Override
            public void onResponse(Call<EntityObject<AlbumItem>> call, Response<EntityObject<AlbumItem>> response) {
                EntityObject<AlbumItem> result = response.body();
                if (result.getCode() == 200) {
                    url = result.getResult().getUrl();
                    CommonUtils.putHeaderpic(url);
                    Glide.with(ShopInfoActivity.this).load(Constant.CACHE_IMAGE_URL + url).placeholder(R.drawable.morentouxiang).into(shopImageTv);
                }
            }

            @Override
            public void onFailure(Call<EntityObject<AlbumItem>> call, Throwable t) {

            }
        });
    }

    private void takeAPicture() {
        photoPath = Constant.CACHE_DIR_IMAGE + CommonUtils.getUUID() + ".jpg";
        photoFile = new File(photoPath);
        if (!photoFile.exists()) {
            try {
                boolean flag = photoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(photoFile));
        startActivityForResult(intentCamera, REQUEST_CAMERA);
    }

    private static Uri getUriForFile(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(App.getInstance(), "com.fnd.waterstore.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}
