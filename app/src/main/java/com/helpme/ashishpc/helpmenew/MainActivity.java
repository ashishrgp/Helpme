package com.helpme.ashishpc.helpmenew;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String MyPref = "userdetails";
    public static final String UFName = "ufname";
    public static final String ULName = "ulname";
    public static final String UPhNum = "uphno";
    public static final String UAddress = "uaddress";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText UserFNameTxt, UserLNameTxt, UserPhNo, UserAddress;
    Button nxtBtn;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_SMS}
                        ,10);
            }
            return;
        }
        TelephonyManager telephonyManager=(TelephonyManager)getApplicationContext().getSystemService(TELEPHONY_SERVICE);
        number = telephonyManager.getLine1Number();
        UserFNameTxt = (EditText)this.findViewById(R.id.UserFName);
        UserLNameTxt = (EditText)this.findViewById(R.id.UserLName);
        UserAddress=(EditText)this.findViewById(R.id.UserAddress);
        nxtBtn = (Button)this.findViewById(R.id.BtnNext);
        sharedPreferences = this.getSharedPreferences(MyPref, MODE_PRIVATE);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.putString(UFName, UserFNameTxt.getText().toString()).apply();
                editor.putString(ULName, UserLNameTxt.getText().toString()).apply();
                editor.putString(UPhNum, number).apply();
                editor.putString(UAddress, UserAddress.getText().toString()).apply();
                Intent intent = new Intent(MainActivity.this,EContactActivity.class);
                MainActivity.this.startActivity(intent);
                //Toast.makeText(getApplicationContext(), number,Toast.LENGTH_LONG).show();
            }
        });
    }
}
