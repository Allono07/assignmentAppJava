package com.devdroid.assignmentapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class BranchEventApi {

    private static final String BRANCH_API_URL = "https://api2.branch.io/v2/event/standard";
    private static final String API_KEY = "key_live_ftpwWzSyD6GNxqgtoelAxkoiCxm9Ap5V"; // Replace with your actual API key

    public static void postEvent(Context context, String event, JSONObject data) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, BRANCH_API_URL,
                response -> {
                    // Handle successful response
                    Log.d("BranchEventApi", "Event posted successfully: " + response);
                },
                error -> {
                    // Handle error
                    Log.e("BranchEventApi", "Error posting event: " + error.getMessage());
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + API_KEY);
                return headers;
            }
        };

        requestQueue.add(request);
    }
}
