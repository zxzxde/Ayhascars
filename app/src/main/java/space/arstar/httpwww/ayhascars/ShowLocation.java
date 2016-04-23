package space.arstar.httpwww.ayhascars;

import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.baidu.mapapi.radar.RadarUploadInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ShowLocation extends AppCompatActivity implements RadarSearchListener {
    private BaiduMap baiduMap;
    private MapView mapView;
    private CheckBox motuo,chuzu,chengke;
    private Button apply,clear;
    private TextView shoumarker=null;
   // private final String makey = "d6qMw3jveMhh8Ozxv9M8XflB";

    LocationListener locationListener;
    RadarSearchManager radarSearchManager = null;//用来实例周边雷达管理
    LocationClient myClient;//定位客户端
    LatLng mypoint = null;//我的位置
    boolean isFirstLoc=true;
    BitmapDescriptor descriptor=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        DownLoadCustomConfig();
        setContentView(R.layout.activity_show_location);
        //CheckBox、button
        motuo= (CheckBox) findViewById(R.id.ismotuo);
        chuzu= (CheckBox) findViewById(R.id.ischuzu);
        chengke= (CheckBox) findViewById(R.id.ischengke);
        apply= (Button) findViewById(R.id.queren);
        clear= (Button) findViewById(R.id.clear);
        motuo.setChecked(false);
        chuzu.setChecked(false);
        chengke.setChecked(false);

        //


        //百度
        mapView = (MapView) findViewById(R.id.slmapview);
        baiduMap = mapView.getMap();
        //周边雷达
        //descriptor= BitmapDescriptorFactory.fromResource(R.drawable.changlelogo);
       // descriptor= BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        searnearby();




        getmypoint();//自定义：定位自己函数
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                if (location != null) {
//                    String a = String.valueOf(location.getAltitude());
//                    String b = String.valueOf(location.getLongitude());
//                    lati.setText("纬度：" + a);
//                    longti.setText("经度：" + b);
//                }
//
//
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };


        showMarkersInfo();//自定义：点击Markers显示其信息
    }

    private void showMarkersInfo() {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                baiduMap.hideInfoWindow();
                if(marker!=null){
                    shoumarker=new TextView(ShowLocation.this);
                    shoumarker.setBackgroundResource(R.drawable.cangbaotu);
                    shoumarker.setTextColor(ShowLocation.this.getResources().getColor(R.color.mblack));//注意点
                    shoumarker.setTextSize(20);
                    shoumarker.setGravity(Gravity.CENTER);
                    shoumarker.setText(marker.getExtraInfo().getString("des"));
                    baiduMap.showInfoWindow(new InfoWindow(shoumarker, marker.getPosition(), -47));
                    MapStatusUpdate update=MapStatusUpdateFactory.newLatLng(marker.getPosition());
                    baiduMap.setMapStatus(update);
                    return true;//注意点
                }
                else
                return  true;
            }
        });
    }


    private void getmypoint() {
        baiduMap.setMyLocationEnabled(true);//开启定位层
        myClient = new LocationClient(getApplicationContext());//实例化定位客户端

        myClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //这里开始到1111处
                if (bdLocation != null) {
                    mypoint = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                    MyLocationData myLocationData = new MyLocationData.Builder()
                            .accuracy(bdLocation.getRadius())
                            .direction(100)
                            .latitude(bdLocation.getLatitude())
                            .longitude(bdLocation.getLongitude())
                            .build();
                    if (baiduMap != null) {
                        baiduMap.setMyLocationData(myLocationData);
                    }//1111以上的作用是设置自身定位点的显示方式——蓝色雷达范围，但一定要开启定位层


                    if(isFirstLoc){
                        isFirstLoc=false;
                       // LatLng p=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                        MapStatus.Builder builder=new MapStatus.Builder();
                        builder.target(mypoint).zoom(18);
                        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    }
                }
            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        myClient.setLocOption(option);
        myClient.start();
    }

    private void    searnearby() {
        radarSearchManager = RadarSearchManager.getInstance();
        radarSearchManager.addNearbyInfoListener(this);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShowLocation.this, "开始查询", Toast.LENGTH_SHORT).show();
                baiduMap.clear();
                RadarUploadInfo info = new RadarUploadInfo();
                if (motuo.isChecked() == true && chuzu.isChecked() == false && chengke.isChecked() == false) {
                    Toast.makeText(ShowLocation.this, "选择的是摩托车司机", Toast.LENGTH_SHORT).show();
                    radarSearchManager.setUserID("motuo");

                    info.comments = "摩托车司机";
                    if (mypoint != null) {
                        info.pt = mypoint;
                    }
                    //上次自身信息
                    RadarNearbySearchOption radarNearbySearchOption = new RadarNearbySearchOption().centerPt(mypoint).pageNum(0).radius(50000000);
                    radarSearchManager.nearbyInfoRequest(radarNearbySearchOption);
                    radarSearchManager.uploadInfoRequest(info);
                }
                if (motuo.isChecked() == false && chuzu.isChecked() == true && chengke.isChecked() == false) {
                    radarSearchManager.setUserID("chuzu");

                    info.comments = "出租车司机";
                    if (mypoint != null) {
                        info.pt = mypoint;
                    }
                    //上传自身信息
                    RadarNearbySearchOption radarNearbySearchOption = new RadarNearbySearchOption().centerPt(mypoint).pageNum(0).radius(50000000);
                    radarSearchManager.nearbyInfoRequest(radarNearbySearchOption);
                    radarSearchManager.uploadInfoRequest(info);
                }
                if (motuo.isChecked() == false && chuzu.isChecked() == false && chengke.isChecked() == true) {
                    radarSearchManager.setUserID("chengke");

                    info.comments = "乘客";
                    if (mypoint != null) {
                        info.pt = mypoint;
                    }
                    //上次自身信息
                    RadarNearbySearchOption radarNearbySearchOption = new RadarNearbySearchOption().centerPt(mypoint).pageNum(0).radius(50000000);
                    radarSearchManager.nearbyInfoRequest(radarNearbySearchOption);
                    radarSearchManager.uploadInfoRequest(info);
                }
//                else{
//                    Toast.makeText(ShowLocation.this,"请仅选择一种身份",Toast.LENGTH_SHORT).show();
//                }
//                RadarNearbySearchOption radarNearbySearchOption = new RadarNearbySearchOption().centerPt(mypoint).pageNum(0).radius(50000000);
//                    radarSearchManager.nearbyInfoRequest(radarNearbySearchOption);
//                radarSearchManager.uploadInfoRequest(info);

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baiduMap.hideInfoWindow();
                radarSearchManager.clearUserInfo();
//                RadarNearbySearchOption radarNearbySearchOption = new RadarNearbySearchOption().centerPt(mypoint).pageNum(0).radius(50000000);
//                radarSearchManager.nearbyInfoRequest(radarNearbySearchOption);
            }
        });

        }

    @Override
    public void onGetNearbyInfoList(RadarNearbyResult radarNearbyResult, RadarSearchError radarSearchError) {
        if(radarSearchError==RadarSearchError.RADAR_NO_ERROR){
            Toast.makeText(ShowLocation.this,"获得查询结果",Toast.LENGTH_SHORT).show();
            for(int i=0;i<radarNearbyResult.infoList.size();i++){
              //  MarkerOptions options =new MarkerOptions().icon(descriptor).position(radarNearbyResult.infoList.get(i).pt);
                Bundle des=new Bundle();
                if(radarNearbyResult.infoList.get(i).comments==null||radarNearbyResult.infoList.get(i).comments.equals("")){
                    des.putString("des","没有备注备注");
                    Toast.makeText(ShowLocation.this,"查询失败",Toast.LENGTH_SHORT).show();
                }
//                if(radarNearbyResult.infoList.get(i).comments=="摩托车司机"){
//
//                    descriptor=BitmapDescriptorFactory.fromResource(R.drawable.motuoche);
//                    MarkerOptions options =new MarkerOptions().icon(descriptor).position(radarNearbyResult.infoList.get(i).pt);
//                    des.putString("des",radarNearbyResult.infoList.get(i).comments);
//                    options.extraInfo(des);
//                    baiduMap.addOverlay(options);
//
//                }
//                if(radarNearbyResult.infoList.get(i).comments=="出租车"){
//                    descriptor=BitmapDescriptorFactory.fromResource(R.drawable.chuzuche);
//                    MarkerOptions options =new MarkerOptions().icon(descriptor).position(radarNearbyResult.infoList.get(i).pt);
//                    des.putString("des",radarNearbyResult.infoList.get(i).comments);
//                    options.extraInfo(des);
//                    baiduMap.addOverlay(options);
//
//                }
//                if(radarNearbyResult.infoList.get(i).comments=="乘客"){
//                    descriptor=BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
//                    MarkerOptions options =new MarkerOptions().icon(descriptor).position(radarNearbyResult.infoList.get(i).pt);
//                    des.putString("des",radarNearbyResult.infoList.get(i).comments);
//                    options.extraInfo(des);
//                    baiduMap.addOverlay(options);
//
//                }
//               if(radarNearbyResult.infoList.get(i).userID=="motuo"){
//                   descriptor=BitmapDescriptorFactory.fromResource(R.drawable.motuoche);
//                   MarkerOptions options =new MarkerOptions().icon(descriptor).position(radarNearbyResult.infoList.get(i).pt);
//                   des.putString("des",radarNearbyResult.infoList.get(i).comments);
//                   options.extraInfo(des);
//                   baiduMap.addOverlay(options);
//               }
                descriptor=BitmapDescriptorFactory.fromResource(R.drawable.motuoche);
                    MarkerOptions options =new MarkerOptions().icon(descriptor).position(radarNearbyResult.infoList.get(i).pt);
                    des.putString("des",radarNearbyResult.infoList.get(i).comments);
                options.extraInfo(des);
                baiduMap.addOverlay(options);
            }
        }
        else {
            Toast.makeText(ShowLocation.this,"请求失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetUploadState(RadarSearchError radarSearchError) {

    }

    @Override
    public void onGetClearInfoState(RadarSearchError radarSearchError) {

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


    @Override
    protected void onDestroy() {
        myClient.stop();
        radarSearchManager.removeNearbyInfoListener(this);
        radarSearchManager.clearUserInfo();
        radarSearchManager.destroy();
        super.onDestroy();
    }
}
