package com.example.dietplanner;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.wear.widget.BoxInsetLayout;

import com.example.dietplanner.databinding.ActivityMainBinding;

public class MainActivity extends Activity implements SensorEventListener {

    private static final String TAG = "MainActivity";
    private TextView mTextView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor mHeartRate = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sensorManager.registerListener(this,mHeartRate,SensorManager.SENSOR_DELAY_NORMAL);
        mTextView = binding.text;
    }

    @Override
    public void onSensorChanged (SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            String msg = "" + (int)sensorEvent.values[0];
            mTextView.setText(msg);
            Log.d(TAG, msg);
        }
    }

    @Override
    public void onAccuracyChanged (Sensor sensor, int i) {
        Log.d(TAG, "onAccuracyChanged - accuracy: " + i);
    }

}