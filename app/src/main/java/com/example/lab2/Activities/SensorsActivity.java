package com.example.lab2.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lab2.R;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textX, textY, textZ, textLat, textLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensors);

        textX = findViewById(R.id.textX);
        textY = findViewById(R.id.textY);
        textZ = findViewById(R.id.textZ);

        textLat = findViewById(R.id.textLat);
        textLong = findViewById(R.id.textLong);

        SensorManager SM = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor accelerometer = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SM.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                }, 10);
            }
        }else{
            setupLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 10:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setupLocation();
                return;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textX.setText(String.format("X = %s", event.values[0]));
        textY.setText(String.format("Y = %s", event.values[1]));
        textZ.setText(String.format("Z = %s", event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setupLocation(){

        LocationManager LM = (LocationManager) getSystemService(LOCATION_SERVICE);

        LocationListener location = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                textLat.setText(String.format("LAT = %.5f", location.getLatitude()));
                textLong.setText(String.format("LONG = %.5f", location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        };
        LM.requestLocationUpdates("gps", 5000, 0, location);
    }
}
