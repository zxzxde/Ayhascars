package space.arstar.httpwww.ayhascars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity {
    private static final String LTAG = MainActivity.class.getSimpleName();
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LatLng currentPt;
    private Marker changle;
    private InfoWindow infoWindow;
    private Button locationbtn;
    //private LocationClient mLocClient;
    //boolean isFirstLoc = true; // 是否首次定位
    // public MyLocationListenner myListener = new MyLocationListenner();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        //setContentView(R.layout.activity_main);
//        Intent intent = getIntent();
//        if (intent.hasExtra("x") && intent.hasExtra("y")) {
//            // 当用intent参数时，设置中心点为指定点
//            Bundle b = intent.getExtras();
//            LatLng p = new LatLng(b.getDouble("y"), b.getDouble("x"));
//            mMapView = new MapView(this,
//                    new BaiduMapOptions().mapStatus(new MapStatus.Builder()
//                            .target(p).build()));
//        } else {
//            mMapView = new MapView(this, new BaiduMapOptions());
//        }
        currentPt = new LatLng(27.912167, 110.61111);
        mMapView = new MapView(this,
                new BaiduMapOptions().mapStatus(new MapStatus.Builder().zoom(21)
                        .target(currentPt).build()));
       //mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(3).build()));
        setContentView(mMapView);
//        mMapView = (MapView) findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);


        LatLng point=new LatLng(27.909007,110.615081);
        BitmapDescriptor bitmapDescriptor= BitmapDescriptorFactory.fromResource(R.drawable.changlelogo);
        OverlayOptions option=new MarkerOptions().position(point).icon(bitmapDescriptor).title("长乐批发总部");
      changle=(Marker)mBaiduMap.addOverlay(option);


//        locationbtn= (Button) findViewById(R.id.locatioonbtn);
//        locationbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1=new Intent(MainActivity.this,ShowLocation.class);
//                startActivity(intent1);
//            }
//        });
        //创建弹窗
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                Button button = new Button(getApplicationContext());
                button.setBackgroundResource(R.drawable.cangbaotu);
                InfoWindow.OnInfoWindowClickListener listener = null;
                if(marker==changle){
                    button.setText("长乐批发总部：\n" +
                            "溆浦县哈尔滨雪影啤酒总代理\n" +
                            "南岳山山泉水溆浦总经销\n" +
                            "联系电话：0745-3326031");
                   //button.setText("长乐简介");
                    button.setTextSize(14);
                    button.setTag("简介");
                    button.setTextColor(MainActivity.this.getResources().getColor(R.color.mblack));
                    listener=new InfoWindow.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick() {
                           // LatLng cl=marker.getPosition();
                            mBaiduMap.hideInfoWindow();
//                            Intent intent=new Intent(MainActivity.this,ChangLe.class);
//                            startActivity(intent);
                        }
                    };
                    LatLng cl=marker.getPosition();
                    infoWindow=new InfoWindow(BitmapDescriptorFactory.fromView(button),cl,-47,listener);
                    mBaiduMap.showInfoWindow(infoWindow);


                }
                return true;
            }
        });


//        mBaiduMap = mMapView.getMap();
//        mBaiduMap.setMyLocationEnabled(true);
//// 定位初始化
//        mLocClient = new LocationClient(this);
//        mLocClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true);// 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
//        mLocClient.setLocOption(option);
//        mLocClient.start();
//    }

//        @Override
//        protected void onPause () {
//            super.onPause();
//            mMapView.onPause();
//        }
//
//        @Override
//        protected void onResume () {
//            super.onResume();
//            mMapView.onResume();
//        }
//
//        @Override
//        protected void onDestroy () {
//            super.onDestroy();
//            mMapView.onDestroy();
//        }
//    public class MyLocationListenner implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            // map view 销毁后不在处理新接收的位置
//            if (location == null || mMapView == null) {
//                return;
//            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                            // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(100).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            }
//        }
//
//        public void onReceivePoi(BDLocation poiLocation) {
//        }
    }
    }
