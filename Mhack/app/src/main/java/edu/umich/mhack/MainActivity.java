package edu.umich.mhack;

import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Size;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.camera2.*;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    private double latitude = 0.0;
    private double longitude = 0.0;
    private TextView txtToChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.start_cam) {
            return true;
        }
        else if (id == R.id.Service){
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if(location != null){
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
            else {
                LocationListener locationListener = new LocationListener() {

                    // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    // Provider被enable时触发此函数，比如GPS被打开
                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    // Provider被disable时触发此函数，比如GPS被关闭
                    @Override
                    public void onProviderDisabled(String provider) {

                    }

                    //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                    @Override
                    public void onLocationChanged(Location location) {
                        if (location != null) {
                            Log.e("Map", "Location changed : Lat: "
                                    + location.getLatitude() + " Lng: "
                                    + location.getLongitude());
                        }
                    }
                };
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 0,locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(location != null){
                    latitude = location.getLatitude(); //经度
                    longitude = location.getLongitude(); //纬度
                    txtToChange = (TextView) findViewById(R.id.textView3);
                    txtToChange.setText("yo,"+latitude+", "+longitude);
                }

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
