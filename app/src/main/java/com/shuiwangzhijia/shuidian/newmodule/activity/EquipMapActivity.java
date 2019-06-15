package com.shuiwangzhijia.shuidian.newmodule.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.base.BaseActivity;
import com.shuiwangzhijia.shuidian.base.FndViewInject;
import com.shuiwangzhijia.shuidian.utils.LocationUtils;
import com.shuiwangzhijia.shuidian.utils.MyUtils;
import com.shuiwangzhijia.shuidian.view.BaseBar;
import com.socks.library.KLog;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.Circle;
import com.tencent.mapsdk.raster.model.CircleOptions;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import butterknife.BindView;

@FndViewInject(contentViewId = R.layout.activity_equip_map, title = "设备地址")
public class EquipMapActivity extends BaseActivity implements TencentLocationListener {
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.locationIv)
    ImageView mLocationIv;
    @BindView(R.id.rlMap)
    RelativeLayout mRlMap;
    @BindView(R.id.euip_iv)
    ImageView mEuipIv;
    @BindView(R.id.equip_name)
    TextView mEquipName;
    @BindView(R.id.equip_address)
    TextView mEquipAddress;
    private TencentMap mTencentMap;
    private TencentLocationManager mLocationManager;
    private String mRequestParams;
    private Marker mLocationMarker;
    private Circle mAccuracyCircle;
    private boolean isFirst = true;
    private TencentLocation mLocation;
    private Marker mEquipMarker;

    @Override
    protected void beforeSetContentView() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        String equipImg = bundle.getString("equipImg");
        String equipName = bundle.getString("equipName");
        String equipAddress = bundle.getString("equipAddress");
       /* bundle.putString("longitude",dataBean.getLongitude());
        bundle.putString("latitude",dataBean.getLatitude());*/
        String longitude = bundle.getString("longitude");
        String latitude = bundle.getString("latitude");
        KLog.e(longitude);
        KLog.e(latitude);
        setImg(mEuipIv,equipImg,"");
        mEquipName.setText(equipName);
        mEquipAddress.setText(equipAddress);
        mBaseBar.setBarListener(new BaseBar.BarListener() {
            @Override
            public void onLeftClick() {
                closeActivity();
            }

            @Override
            public void onRightClick() {

            }
        });
        initMap(equipName,equipAddress,Double.parseDouble(longitude),Double.parseDouble(latitude));
    }

    private void initMap(String equipName,String equipAddress,double longitude, double latitude) {
        startLocation();
        mTencentMap = mMapView.getMap();
        mTencentMap.setZoom(20);
        //设备位置
        String tx = MyUtils.map_bd2tx(latitude, longitude);
        String[] txArray = tx.split(",");
        LatLng latLngLocation = new LatLng(Double.parseDouble(txArray[0]), Double.parseDouble(txArray[1]));
        mEquipMarker = mTencentMap.addMarker(new MarkerOptions().
                position(latLngLocation).
                title(equipName).
                icon(BitmapDescriptorFactory.fromResource(R.drawable.xuanzeweizhi)));
        mEquipMarker.setSnippet(equipAddress);
        mTencentMap.setCenter(latLngLocation);
        mTencentMap.animateTo(latLngLocation);
    }

    private void startLocation() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
        request.setInterval(5000);
        mLocationManager = TencentLocationManager.getInstance(this);
        // 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
        mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        mLocationManager.startIndoorLocation();
        int error = mLocationManager.requestLocationUpdates(request, this);
        KLog.e("Tencent", "定位error：" + error);
        mRequestParams = request.toString() + ", 坐标系=" + LocationUtils.toString(mLocationManager.getCoordinateType());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        KLog.e("Tencent", "定位onStatusUpdate：" + s + "   status: " + i + "  " + s1);
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        mLocation = tencentLocation;
        if (isFirst) {
            isFirst = false;
//            String boundary = "region(" + location.getCity() + ",0)";
          //  stopLocation();
        }

        LatLng latLngLocation = new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude());
        updateMap(latLngLocation);
    }


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


}
