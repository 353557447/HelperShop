package com.shuiwangzhijia.shuidian.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.adapter.LocationAdapter;
import com.shuiwangzhijia.shuidian.adapter.SelectCityAdapter;
import com.shuiwangzhijia.shuidian.base.BaseAct;
import com.shuiwangzhijia.shuidian.bean.AddressBean;
import com.shuiwangzhijia.shuidian.bean.CityBean;
import com.shuiwangzhijia.shuidian.bean.LocationBean;
import com.shuiwangzhijia.shuidian.bean.TencentLocationBean;
import com.shuiwangzhijia.shuidian.event.AddressEvent;
import com.shuiwangzhijia.shuidian.http.RetrofitUtils;
import com.shuiwangzhijia.shuidian.utils.CityParseXml;
import com.shuiwangzhijia.shuidian.utils.LocationUtils;
import com.shuiwangzhijia.shuidian.utils.ToastUitl;
import com.shuiwangzhijia.shuidian.view.ClearEditText;
import com.shuiwangzhijia.shuidian.view.PinnedHeaderDecoration;
import com.shuiwangzhijia.shuidian.view.WaveSideBarView;
import com.socks.library.KLog;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.raster.model.Circle;
import com.tencent.mapsdk.raster.model.CircleOptions;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationInfoActivity extends BaseAct implements TencentLocationListener {
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.searchEdit)
    ClearEditText searchEdit;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.cancelBtn)
    TextView cancelBtn;
    @BindView(R.id.locationBtn)
    ImageView locationBtn;
    @BindView(R.id.llMap)
    LinearLayout llMap;
    @BindView(R.id.location)
    TextView locationTv;
    @BindView(R.id.cityRecyclerView)
    RecyclerView cityRecyclerView;
    @BindView(R.id.sideView)
    WaveSideBarView mSideBarView;
    @BindView(R.id.llCity)
    LinearLayout llCity;
    @BindView(R.id.rlMap)
    RelativeLayout rlMap;
    private TencentLocationManager mLocationManager;
    private String mRequestParams;
    private TencentLocation mLocation;
    private String ulnglat;
    private LocationAdapter locationAdapter;
    private TencentMap mTencentMap;
    private Marker mLocationMarker;
    private Circle mAccuracyCircle;
    private SelectCityAdapter cityAdapter;
    private List<CityBean> list;
    private boolean showCity;
    private CityBean cityBean;
    private ArrayList<LocationBean> data;
    private boolean isFirst = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);
        ButterKnife.bind(this);
        setTitle("确认店铺地址");
        setTitleBarBgColor(R.color.color_30adfd);
        initMap();
        initCity();
        initRecyclerView();
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String loc = editable.toString();
                if (TextUtils.isEmpty(loc)) {
                    ToastUitl.showToastCustom("请输入搜索的地址信息");
                    cancelBtn.setVisibility(View.GONE);
                    llMap.setVisibility(View.VISIBLE);
                    rlMap.setVisibility(View.VISIBLE);
                    llCity.setVisibility(View.GONE);
                } else {
                    cancelBtn.setVisibility(View.VISIBLE);
                    llCity.setVisibility(View.GONE);
                    llMap.setVisibility(View.VISIBLE);
                    rlMap.setVisibility(View.GONE);
                    LatLng latLng = mTencentMap.getMapCenter();
                    String boundary = "nearby(" + latLng.getLatitude() + "," + latLng.getLongitude() + ",1000)";
                    search(loc, boundary);
                }
            }
        });
    }

    private void initMap() {
        startLocation();
        mTencentMap = mapView.getMap();
        mTencentMap.setZoom(14);
 /*       mTencentMap.setOnMapClickListener(new TencentMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LatLng location = mTencentMap.getMapCenter();
                String boundary = "nearby(" + location.getLatitude() + "," + location.getLongitude() + ",1000)";
                search("小区", boundary);
            }
        });*/
        mTencentMap.setOnMapCameraChangeListener(new TencentMap.OnMapCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                LatLng location = mTencentMap.getMapCenter();
                String boundary = "nearby(" + location.getLatitude() + "," + location.getLongitude() + ",1000)";
                search("小区", boundary);
            }
        });
    }

    /**
     * 附近查询结果
     */
    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        mRecyclerView.addItemDecoration(divider);
        locationAdapter = new LocationAdapter(this);
        locationAdapter.setOnItemClickListener(new LocationAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LocationBean item = locationAdapter.getItem(position);
                AddressBean data = new AddressBean();
                data.setUlnglat(item.getLocation().getUlnglat());
                data.setDaddress(item.getAddress());
                LocationBean.AdInfoBean adInfoBean = item.getAd_info();
                data.setCity(adInfoBean.getCity());
                data.setDist(adInfoBean.getDistrict());
                data.setProvince(adInfoBean.getProvince());
                EventBus.getDefault().post(new AddressEvent(data, false));
                finish();
            }
        });
        mRecyclerView.setAdapter(locationAdapter);
    }

    /**
     * 初始化城市信息
     */
    private void initCity() {
        try {
            list = CityParseXml.parserXml(getAssets().open("citydict.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        cityRecyclerView.addItemDecoration(decoration);
        mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int pos = cityAdapter.getLetterPosition(letter);
                if (pos != -1) {
                    cityRecyclerView.scrollToPosition(pos);
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) cityRecyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }
        });
        cityAdapter = new SelectCityAdapter(this);
        cityAdapter.setData(list);
        cityRecyclerView.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickListener(new SelectCityAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                llCity.setVisibility(View.GONE);
                llMap.setVisibility(View.VISIBLE);
                rlMap.setVisibility(View.VISIBLE);
                cityBean = list.get(position);
                city.setText(cityBean.getCity());
                String boundary = "region(" + cityBean.getCity() + ",0)";
                search(cityBean.getCity(), boundary);
            }
        });
        showCity = true;
    }

    @Override
    public void onLocationChanged(TencentLocation location, int error,
                                  String reason) {
        Log.i("Tencent onLocation", "  error：" + error + " reason: " + reason);
        mLocation = location;
        if (isFirst) {
            isFirst = false;
//            String boundary = "region(" + location.getCity() + ",0)";
            stopLocation();
        }
        String boundary = "nearby(" + location.getLatitude() + "," + location.getLongitude() + ",1000)";
        if(isFirstCamaraChange)
        search(location.getCity(), boundary);
        // 定位成功
        StringBuilder sb = new StringBuilder();
        sb.append("定位参数=").append(mRequestParams).append("\n");
        sb.append("(纬度=").append(location.getLatitude()).append(",经度=")
                .append(location.getLongitude()).append(",精度=")
                .append(location.getAccuracy()).append("), 来源=")
                .append(location.getProvider()).append(", 地区=")
                .append(location.getProvince() + "-" + location.getCity() + "-" + location.getDistrict()).append(", 地址=")
                .append(location.getAddress());
        Log.i("Tencent", "定位成功：" + sb.toString());
        // 更新 status
        ulnglat = location.getLongitude() + "," + location.getLatitude();
        if (cityBean == null) {
            city.setText(location.getCity());
        }
        locationTv.setText(location.getCity());
        LatLng latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());
        updateMap(latLngLocation);
    }

    private boolean isFirstCamaraChange=true;
    private void updateMap(LatLng latLngLocation) {
        // 更新 location Marker
        if (mLocationMarker == null) {
            mLocationMarker = mTencentMap.addMarker(new MarkerOptions().
                    position(latLngLocation).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.zijiweizhi)));
        } else {
            mLocationMarker.setPosition(latLngLocation);
        }
        if (mAccuracyCircle == null) {
            mAccuracyCircle = mTencentMap.addCircle(new CircleOptions().
                    center(latLngLocation).
                    radius(1000).
                    fillColor(0x442788FF).
                    strokeColor(0x882788FF).
                    strokeWidth(2));
        } else {
            mAccuracyCircle.setCenter(latLngLocation);
        }
        if(isFirstCamaraChange) {
            mTencentMap.animateTo(latLngLocation);
            mTencentMap.setCenter(latLngLocation);
        }
        isFirstCamaraChange=false;
    }

    @Override
    public void onStatusUpdate(String name, int status, String desc) {

        KLog.e("Tencent", "定位onStatusUpdate：" + name + "   status: " + status + "  " + desc);
        // ignore
        Log.i("Tencent", "定位onStatusUpdate：" + name + "   status: " + status + "  " + desc);
    }

    // ====== location callback

    private void startLocation() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
        request.setInterval(5000);
        mLocationManager = TencentLocationManager.getInstance(this);
        // 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
        mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        mLocationManager.startIndoorLocation();
        int error = mLocationManager.requestLocationUpdates(request, this);
        Log.i("Tencent", "定位error：" + error);
        mRequestParams = request.toString() + ", 坐标系=" + LocationUtils.toString(mLocationManager.getCoordinateType());
    }

    private void stopLocation() {
        if (mLocationManager != null)
            mLocationManager.removeUpdates(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocation();
    }

    @OnClick({R.id.city, R.id.location, R.id.cancelBtn, R.id.locationBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.city:
                showCity = true;
                llCity.setVisibility(showCity ? View.VISIBLE : View.GONE);
                llMap.setVisibility(!showCity ? View.VISIBLE : View.GONE);
                break;
            case R.id.cancelBtn:
                llCity.setVisibility(View.GONE);
                llMap.setVisibility(View.VISIBLE);
                rlMap.setVisibility(View.VISIBLE);
            case R.id.locationBtn:
                startLocation();
            case R.id.location:
                String boundary = "region(" + locationTv.getText().toString() + ",0)";
                search(locationTv.getText().toString(), boundary);
                break;
        }
    }

    private void search(String keyword, String boundary) {
        //policy=1：本策略主要用于收货地址、上门服务地址的填写，提高了小区类、商务楼宇、大学等分类的排序，过滤行政区、
        RetrofitUtils.getInstances().createWithUrl("https://apis.map.qq.com/").searchLocationList(keyword, boundary, 1, 10, "BMQBZ-HIE6W-QIER6-OX3NF-HGITF-7UBKG").enqueue(new Callback<TencentLocationBean>() {

            @Override
            public void onResponse(Call<TencentLocationBean> call, Response<TencentLocationBean> response) {
                mRecyclerView.setVisibility(View.GONE);
                TencentLocationBean body = response.body();
//                TencentLocationBean bean = new Gson().fromJson(body, TencentLocationBean.class);
                List<LocationBean> data = body.getData();
                if (data == null || data.size() == 0) {
                    return;
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    locationAdapter.setData(data);
                    LocationBean locationBean = data.get(0);
                    LocationBean.LocationItemBean location = locationBean.getLocation();
                }
            }

            @Override
            public void onFailure(Call<TencentLocationBean> call, Throwable t) {
                Log.i("json", t.getMessage());
            }
        });
    }


}
