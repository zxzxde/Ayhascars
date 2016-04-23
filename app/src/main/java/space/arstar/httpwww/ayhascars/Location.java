package space.arstar.httpwww.ayhascars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Location extends AppCompatActivity {
  //  MapView mapView;
    private TextView tv2;
    private EditText et1;
    //private TextWatcher textWatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // SDKInitializer.initialize(this);

        setContentView(R.layout.activity_location);
        tv2= (TextView) findViewById(R.id.tv2);
        et1= (EditText) findViewById(R.id.et1);
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv2.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

//    private void init() {
//        //使用自定义的title，注意顺序
//        setContentView(R.layout.activity_location);
//        mapView= (MapView) findViewById(R.id.LoView);
//
//    }
}
