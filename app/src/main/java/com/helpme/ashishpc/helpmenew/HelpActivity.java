package com.helpme.ashishpc.helpmenew;

/**
 * Created by ashish pc on 08-Jul-16.
 */
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    String PhNo, ENo1, ENo2, ENo3;
    Double latitude, longitude;
    Button helpBtn,findbutton;
    JSONObject jsonObject;
    String ENums[]=new String[]{"Sample","Sample", "Sample"};
    //protected LocationManager locationManager;
    //protected LocationListener locationListener;
    //public Criteria criteria;
    //public String bestProvider;
    private LocationManager locationManager;
    private LocationListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        //t = (TextView) findViewById(R.id.textView);
        helpBtn = (Button) findViewById(R.id.helpBtn);
        findbutton= (Button) findViewById(R.id.findBtn);

        locationManager =(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                 latitude=location.getLatitude();
                longitude = location.getLongitude();
                //t.setText(location.getLongitude() + " " + location.getLatitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();

      findbutton.setOnClickListener(new View.OnClickListener()
      {
          @Override
          public void onClick(View v) {
              sendMap();
          }
      });
    }
    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_SMS}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelephonyManager telephonyManager=(TelephonyManager)getApplicationContext().getSystemService(TELEPHONY_SERVICE);
                PhNo = telephonyManager.getLine1Number();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_SMS}
                                        , 10);
                            }
                            return;
                        }
                        // locationManager.requestLocationUpdates("gps", 1000, 0, listener);

                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
                        if (latitude != null) {


                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                jsonObject = jsonArray.getJSONObject(0);
                                ENo1 = jsonArray.getJSONObject(0).getString("contact1_phone");
                                ENo2 = jsonArray.getJSONObject(0).getString("contact2_phone");
                                ENo3 = jsonArray.getJSONObject(0).getString("contact3_phone");
                                if (ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_SMS}
                                                , 10);
                                    }
                                    return;
                                }
                                String message = "Help me!! I am at location " + "http://maps.google.com/?q=" + latitude + "," + longitude;
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(ENo1, null, message, null, null);
                                smsManager.sendTextMessage(ENo2, null, message, null, null);
                                smsManager.sendTextMessage(ENo3, null, message, null, null);
                                Toast.makeText(getApplicationContext(), "SMS Sent", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
                        }
                    }



                };
                GetEContacts getEContacts = new GetEContacts(PhNo, responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(HelpActivity.this);
                requestQueue.add(getEContacts);
            }
        });

    }
    protected void sendMap(){
        Uri uri = Uri.parse("http://maps.google.com/?q=<32.733428>,<-97.112703>");
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }
}