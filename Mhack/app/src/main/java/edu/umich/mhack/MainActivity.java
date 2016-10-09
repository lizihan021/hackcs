package edu.umich.mhack;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.*;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.StrictMode;
import android.support.annotation.Size;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.camera2.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Button;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnTouchListener;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private double latitude = 0.0;
    private double longitude = 0.0;
    private double jingdu1 = 0.0;
    private double weidu1 = 0.0;
    private TextView txtToChange;
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private SensorManager manager;
    private LocationManager locationManager;
    private Button button;
    private float degree;
    private final String DEBUG_TAG   = "Activity01";
    private int yes = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This is actually a useless action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        button = (Button) this.findViewById(R.id.button2);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude(); //经度
                        longitude = location.getLongitude(); //纬度
                    }
                } catch (SecurityException e) {
                }
                yes = shoot(jingdu1, weidu1);
            }
        });

        new Thread(new Runnable(){
            @Override
            public void run() {
                while(true) {
                    try {
                        Socket socket = null;
                        InetAddress serverAddr = InetAddress.getByName("52.40.173.239");
                        socket = new Socket(serverAddr, 54321);

                PrintWriter out1 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
         //       PrintWriter out2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
         //       PrintWriter out3 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                out1.println(longitude + "yoyo");
         //       out2.println(latitude + "");
         //       out3.println(yes + "");*/
                BufferedReader br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg1 = br1.readLine();/*
                BufferedReader br2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg2 = br2.readLine();
                BufferedReader br3 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg3 = br3.readLine();*/

                txtToChange = (TextView) findViewById(R.id.textView3);
                txtToChange.setText(msg1);
                /*if ( msg1 != null )
                    jingdu1 = Double.parseDouble(msg1);*/
                /*
                if ( msg2 != null )
                    weidu1 = Double.parseDouble(msg2);

                if ( msg3 != null && Integer.parseInt(msg3) == 1) {
                    txtToChange = (TextView) findViewById(R.id.textView3);
                    txtToChange.setText("yo, you are dead!");
                    try{Thread.sleep(5000);}
                    catch(InterruptedException ex){}
                }
                */
                        //关闭流

                        br1.close(); //br2.close(); br3.close();
                        out1.close(); //out2.close(); out3.close();
                        //关闭Socket
                        socket.close();
                    } catch (Exception e) {
                        // TODO: handle exception
                        Log.e(DEBUG_TAG, e.toString());
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }).start();

    }

    private int shoot(double jing1, double wei1)
    {
        double dx = jing1 - longitude;
        double dy = wei1 - latitude;
        float angel = 0;
        if(dy > 0 && dx > 0)
            angel = (float)Math.toDegrees(Math.atan(dx/dy));
        else if(dy < 0 && dx > 0)
            angel = (float)Math.toDegrees(Math.atan(dx/dy)) + 180;
        else if(dy > 0 && dx < 0)
            angel = (float)Math.toDegrees(Math.atan(dx/dy)) + 360;
        else if(dy < 0 && dx < 0)
            angel = (float)Math.toDegrees(Math.atan(dx/dy)) + 180;
        else if(dx == 0 && dy == 0)
            return 1;
        else if(dx == 0)
            angel = 0;
        else if(dy == 0 && dx > 0)
            angel = 90;
        else
            angel = 270;

        txtToChange = (TextView) findViewById(R.id.textView3);
        String out = "yo, " + angel;
        txtToChange.setText(out);

        if (Math.abs(angel - degree) < 3)
            return 1;
        else
            return 0;
    }


    @Override
    protected void onResume() {
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        manager.registerListener(sensorListener, sensor,
                SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }


    SensorEventListener sensorListener = new SensorEventListener() {

        private float predegree = 0;

        public void onSensorChanged(SensorEvent event) {
            degree = event.values[0];// 数组中的第一个数是方向值
            txtToChange = (TextView) findViewById(R.id.textView1);
            String out = "yo, " + degree;
            txtToChange.setText(out);

        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

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
                latitude = location.getLatitude(); //经度
                longitude = location.getLongitude(); //纬度
            }
            txtToChange = (TextView) findViewById(R.id.textView3);
            String out = "yo, " + latitude + ", " + longitude;
            txtToChange.setText(out);
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(sensorListener);
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

        if (id == R.id.Service) {
            try {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude(); //经度
                    longitude = location.getLongitude(); //纬度
                }
            } catch (SecurityException e) {
            }

            txtToChange = (TextView) findViewById(R.id.textView3);
            String out = "yo, " + latitude + ", " + longitude;
            txtToChange.setText(out);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
