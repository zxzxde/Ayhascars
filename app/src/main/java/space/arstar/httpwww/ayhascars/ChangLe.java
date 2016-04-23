package space.arstar.httpwww.ayhascars;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ChangLe extends AppCompatActivity {
//    private static final int accuracyCircleFillColor = 0xAAFFFF88;
//    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private TextView showll;
    private Button teststatus;
    private Marker changle;
    private InfoWindow infoWindow;
    int aa=0;
    //定位相关
    public LocationClient mlocationClient=null;
    public BDLocationListener myLocationListenner=new MyLocationListenner();
    //public MyLocationListenner myLocationListenner=new MyLocationListenner();
    private LocationMode mLocationMode;//定位模式
    BitmapDescriptor bitmapDescriptor;//Marker图标
    MapView mapView;
    BaiduMap baiduMap;


    //UI相关
//    RadioGroup.OnCheckedChangeListener radioButtonListener;
//    Button requestLocButton;
    boolean isFirstLoc=true;//是否第一次定位


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        DownLoadCustomConfig();

        setContentView(R.layout.activity_chang_le);
        showll= (TextView) findViewById(R.id.showme);
        teststatus= (Button) findViewById(R.id.teststatus);
        mLocationMode= LocationMode.NORMAL;
        bitmapDescriptor= BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);

        //baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mLocationMode, true, bitmapDescriptor, accuracyCircleFillColor, accuracyCircleStrokeColor));
       // baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mLocationMode,true,bitmapDescriptor));
        //地图初始化
        mapView= (MapView) findViewById(R.id.locationView);
        baiduMap=mapView.getMap();
       // baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        //开启定位层
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mLocationMode, true, bitmapDescriptor));
        //定位初始化
        mlocationClient=new LocationClient(getApplicationContext());//声明LocationClient类
        mlocationClient.registerLocationListener(myLocationListenner);//注册监听函数



        setChangleMarker();//设置长乐标志

        TouchSetMarker();//双击添加标记

        clearMarkers();

        //LocationClientOption类，该类用来设置定位SDK的定位方式，e.g.：
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setOpenGps(true);//打开GPS
        option.setCoorType("bd09ll");//设置坐标类型/可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(3000);//设置扫描跨度/可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        //option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        //option.setOpenGps(true);//可选，默认false,设置是否使用gps
        //option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
       // option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要


        mlocationClient.setLocOption(option);

        //第四步，开始定位
        mlocationClient.start();
        //setContentView(mapView);

        //测试点击实时变化textview;
        teststatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int aa=0;
//                aa+=1;
//                MapStatus mapStatus=baiduMap.getMapStatus();
//                Rect rect=new Rect(20 ,22,22,22);
                baiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        File file=new File("sdcard/jietu.png");
                        FileOutputStream fos;
                        Toast.makeText(ChangLe.this,"正在截图...",Toast.LENGTH_SHORT).show();
                        try {
                            fos=new FileOutputStream(file);
                            if(bitmap.compress(Bitmap.CompressFormat.PNG,100,fos)){
                                fos.flush();
                                fos.close();
                                Toast.makeText(ChangLe.this,"截图成功",Toast.LENGTH_SHORT).show();
                            }
                        } catch (FileNotFoundException e) {
                            Toast.makeText(ChangLe.this,"存储失败...",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
//                baiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
//                    @Override
//                    public void onSnapshotReady(Bitmap bitmap) {
//                        File file =new File("sdcard/Pictures/Screenshots/myfirstSnapshot.png");
//                        FileOutputStream fos;
//                        try {
//                            fos=new FileOutputStream(file);
//                            if(bitmap.compress(Bitmap.CompressFormat.PNG,100,fos)){
//                                fos.flush();
//                                fos.close();
//                Toast.makeText(ChangLe.this,"截图成功",Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        Toast.makeText(ChangLe.this,"正在截图...",Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
        });
        //测试地图状态监听
//        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
//            @Override
//            public void onMapStatusChangeStart(MapStatus mapStatus) {
//                showll.setText(String.valueOf(aa));
//            }
//
//            @Override
//            public void onMapStatusChange(MapStatus mapStatus) {
//                showll.setText(String.valueOf(aa));
//            }
//
//            @Override
//            public void onMapStatusChangeFinish(MapStatus mapStatus) {
//                showll.setText(String.valueOf(aa));
//            }
//        });

    }

    private void clearMarkers() {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker!=changle){
                    marker.remove();
                }
                else if(marker==changle){
                Button btn=new Button(getApplication());
                    btn.setBackgroundResource(R.drawable.cangbaotu);
                    btn.setText("长乐批发总部：\n" +
                            "溆浦县哈尔滨雪影啤酒总代理\n" +
                            "南岳山山泉水溆浦总经销\n" +
                            "联系电话：0745-3326031");
                    btn.setTextSize(14);
                    btn.setTag("简介");
                    btn.setTextColor(ChangLe.this.getResources().getColor(R.color.mblack));
                    InfoWindow.OnInfoWindowClickListener listener=new InfoWindow.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick() {
                            baiduMap.hideInfoWindow();
                        }
                    };
                    LatLng LL=marker.getPosition();
                    infoWindow=new InfoWindow(BitmapDescriptorFactory.fromView(btn),LL,-47,listener);
                    baiduMap.showInfoWindow(infoWindow);
                }
                return true;
            }
        });
    }

    private void TouchSetMarker() {
        baiduMap.setOnMapDoubleClickListener(new BaiduMap.OnMapDoubleClickListener() {
            @Override
            public void onMapDoubleClick(LatLng latLng) {
                BitmapDescriptor bitmapDescriptor=BitmapDescriptorFactory.fromResource(R.drawable.changlelogo);
                OverlayOptions TouchOptions=new MarkerOptions().position(latLng).icon(bitmapDescriptor).title("长乐批发总部");
                baiduMap.addOverlay(TouchOptions);
            }
        });
    }

    private void setChangleMarker() {
        LatLng changleP=new LatLng(27.909007,110.615081);
        BitmapDescriptor bitmapDescriptor=BitmapDescriptorFactory.fromResource(R.drawable.changlelogo);
        OverlayOptions overlayOptions=new MarkerOptions().position(changleP).icon(bitmapDescriptor).title("长乐批发总部");

        changle= (Marker) baiduMap.addOverlay(overlayOptions);

        //ChangLe.this.setContentView(mapView);
    }

    private void DownLoadCustomConfig() {
         String director="/sdcard/fyzn";
        String CCPath=director+"/"+"custom";
        File Dir=new File(director);
        File CustomConfig=new File(CCPath);
        if(!Dir.exists()){
            Dir.mkdir();
        }
        if(!CustomConfig.exists()){
            InputStream inputStream=getResources().openRawResource(R.raw.custom_config);
            try {
                FileOutputStream fos=new FileOutputStream(CCPath);
                byte[] bytes=new byte[1024];
                int count=0;
                while ((count=inputStream.read(bytes))>0){
                    fos.write(bytes,0,count);
                }
                fos.close();
                inputStream.close();
                mapView.setCustomMapStylePath(CCPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(CustomConfig.exists()){
//            CustomConfig.delete();
//            InputStream inputStream=getResources().openRawResource(R.raw.custom_config);
//            try {
//                FileOutputStream fos=new FileOutputStream(CCPath);
//                byte[] bytes=new byte[1024];
//                int count=0;
//                while ((count=inputStream.read(bytes))>0){
//                    fos.write(bytes,0,count);
//                }
//                fos.close();
//                inputStream.close();
//                mapView.setCustomMapStylePath(CCPath);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            mapView.setCustomMapStylePath(CCPath);
        }

    }

    //定位SDK函数
    // 第三步，实现BDLocationListener接口
    //BDLocationListener接口有1个方法需要实现： 1.接收异步返回的定位结果，参数是BDLocation类型参数。
    private class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(bdLocation.getTime());
            sb.append("\nerror code : ");
            sb.append(bdLocation.getLocType());
            sb.append("\nlatitude : ");
            sb.append(bdLocation.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(bdLocation.getLongitude());
            sb.append("\nradius : ");
            sb.append(bdLocation.getRadius());

            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(bdLocation.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(bdLocation.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(bdLocation.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(bdLocation.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(bdLocation.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
               // showll.setText(sb.toString());


            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(bdLocation.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(bdLocation.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
               // showll.setText(sb.toString());
            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
                //showll.setText(sb.toString());
            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                //showll.setText(sb.toString());
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
                //showll.setText(sb.toString());
            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                //showll.setText(sb.toString());
            }
            sb.append("\nlocationdescribe : ");
            sb.append(bdLocation.getLocationDescribe());// 位置语义化信息
            List<Poi> list = bdLocation.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }


            if(bdLocation==null||mapView==null){
                return;
            }
            MyLocationData myLocationData=new MyLocationData.Builder().accuracy(bdLocation.getRadius())
                    .direction(120)
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
            baiduMap.setMyLocationData(myLocationData);
            if(isFirstLoc){
                isFirstLoc=false;
                LatLng p=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatus.Builder builder=new MapStatus.Builder();
                builder.target(p).zoom(18);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

    }



    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mlocationClient.stop();
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }





}
