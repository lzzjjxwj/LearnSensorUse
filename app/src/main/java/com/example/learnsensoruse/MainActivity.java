package com.example.learnsensoruse;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvX;
    private TextView tvY;
    private TextView tvZ;
    private TextView tvInfo;
    // private TextView tvDis;
    SensorManager sensorManager;
    Sensor myAccelerometer,proximity_Sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvX = (TextView) findViewById(R.id.tvX);
        tvY = (TextView) findViewById(R.id.tvY);
        tvZ = (TextView) findViewById(R.id.tvZ);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        // tvDis = (TextView) findViewById(R.id.tvDis);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        myAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximity_Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        String string = "名字："+myAccelerometer.getName()+"\n电池："+myAccelerometer.getPower()+"\n类型："
                +myAccelerometer.getType()+"\nVendor:"+myAccelerometer.getVendor()+
                "\n版本："+myAccelerometer.getVersion()+"\n幅度:"+myAccelerometer.getMaximumRange();
        tvInfo.setText(string);
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener,proximity_Sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float []values = event.values;
            tvX.setText("X轴方向上的加速度为:"+values[0]);
            tvY.setText("Y轴方向上的加速度为:"+values[1]);
            tvZ.setText("Z轴方向上的加速度为:"+values[2]);
            float move = Math.abs(event.values[0]);
            if (move <Math.abs(event.values[1])){
                move = Math.abs(event.values[1]);
            }if (move <Math.abs(event.values[2]) ){
                move = Math.abs(event.values[2]);
            }
            if (move >15){
                Toast.makeText(MainActivity.this,"摇一摇",Toast.LENGTH_SHORT).show();
            }
            Log.d(String.valueOf(this), String.valueOf(values.length));
            // tvDis.setText("手机距离物体的距离为："+values[3]);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null){
            sensorManager.unregisterListener(sensorEventListener);
        }
    }
}
