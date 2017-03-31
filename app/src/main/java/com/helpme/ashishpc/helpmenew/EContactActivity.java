package com.helpme.ashishpc.helpmenew;

/**
 * Created by ashish pc on 08-Jul-16.
 */
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EContactActivity extends AppCompatActivity {
    public static final String MyPref = "userdetails";
    public static final String UFName = "ufname";
    public static final String ULName = "ulname";
    public static final String UPhNum = "uphno";
    public static final String UAddress = "uaddress";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText ContactName1, ContactPhone1, ContactName2, ContactPhone2, ContactName3, ContactPhone3;
    Button BackBtn, FinishBtn;
    String UserFName, UserLName,  UserAddress, EmCName1, EmCName2, EmCName3;
    String UserPhNum, EmCPh1, EmCPh2, EmCPh3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_econtact);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_SMS}
                        ,10);
            }
            return;
        }
        sharedPreferences = this.getSharedPreferences(MyPref, MODE_PRIVATE);
        UserFName = sharedPreferences.getString(UFName, null);
        UserLName = sharedPreferences.getString(ULName, null);
        UserPhNum = sharedPreferences.getString(UPhNum, null);
        UserAddress = sharedPreferences.getString(UAddress,null);
        ContactName1 = (EditText)this.findViewById(R.id.CName1);
        ContactPhone1 = (EditText)this.findViewById(R.id.CPhone1);
        ContactName2 = (EditText)this.findViewById(R.id.CName2);
        ContactPhone2 = (EditText)this.findViewById(R.id.CPhone2);
        ContactName3 = (EditText)this.findViewById(R.id.CName3);
        ContactPhone3 = (EditText)this.findViewById(R.id.CPhone3);
        BackBtn = (Button)this.findViewById(R.id.bbutton);
        FinishBtn = (Button)this.findViewById(R.id.Fbutton);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(EContactActivity.this, MainActivity.class);
                EContactActivity.this.startActivity(intent);
            }
        });
        FinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmCName1 = ContactName1.getText().toString();
                EmCPh1 = ContactPhone1.getText().toString();
                EmCName2 = ContactName2.getText().toString();
                EmCPh2 = ContactPhone2.getText().toString();
                EmCName3 = ContactName3.getText().toString();
                EmCPh3 = ContactPhone3.getText().toString();
                /*Intent intent = new Intent(EContactActivity.this,HelpActivity.class);
                EContactActivity.this.startActivity(intent);*/

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Toast.makeText(EContactActivity.this,response,Toast.LENGTH_LONG).show();
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success)
                            {
                                //Toast.makeText(EContactActivity.this,UserPhNum,Toast.LENGTH_LONG).show();
                                AlertDialog.Builder messageBox = new AlertDialog.Builder(EContactActivity.this);
                                messageBox.setTitle("Success");
                                messageBox.setCancelable(true);
                                messageBox.setMessage("Registered");
                                messageBox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(EContactActivity.this,HelpActivity.class);
                                        EContactActivity.this.startActivity(intent);
                                    }
                                });
                                messageBox.show();
                            }
                            else Toast.makeText(EContactActivity.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterNewUser registerNewUser = new RegisterNewUser(UserFName, UserLName, UserPhNum, UserAddress, EmCName1, EmCPh1, EmCName2, EmCPh2, EmCName3, EmCPh3, responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(EContactActivity.this);
                requestQueue.add(registerNewUser);

                /*TelephonyManager telephonyManager=(TelephonyManager)getApplicationContext().getSystemService(TELEPHONY_SERVICE);
                String number = telephonyManager.getLine1Number();
                Toast.makeText(getApplicationContext(),number, Toast.LENGTH_LONG).show();*/
            }
        });
    }
}