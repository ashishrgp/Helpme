package com.helpme.ashishpc.helpmenew;

/**
 * Created by ashish pc on 08-Jul-16.
 */
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class GetEContacts extends StringRequest {
    private static final String url = "http://7a9d3d92.ngrok.io/getecontacts.php";
    private Map<String, String> params;
    public GetEContacts(String PhNo, Response.Listener<String> listener)
    {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("PhNo", PhNo);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}