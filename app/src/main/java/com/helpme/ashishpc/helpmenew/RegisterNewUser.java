package com.helpme.ashishpc.helpmenew;

/**
 * Created by ashish pc on 08-Jul-16.
 */
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;



public class RegisterNewUser extends StringRequest {
    private static final String url = "http://7a9d3d92.ngrok.io/register.php";
    private Map<String, String> params;
    public RegisterNewUser(String FName, String LName, String PhNo, String Address, String CName1, String CPh1, String CName2, String CPh2, String CName3, String CPh3, Response.Listener<String> listener)
    {
        super(Method.POST, url, listener,null);
        params=new HashMap<>();
        params.put("FName", FName);
        params.put("LName", LName);
        params.put("PhNo",PhNo);
        params.put("Address",Address);
        params.put("CName1",CName1);
        params.put("CPh1",CPh1);
        params.put("CName2",CName2);
        params.put("CPh2",CPh2);
        params.put("CName3",CName3);
        params.put("CPh3",CPh3);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
