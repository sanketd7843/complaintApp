package com.example.complaintapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.complaintapplication.Adapter.HistoryAdapter;
import com.example.complaintapplication.models.Constants;
import com.example.complaintapplication.models.HistoryAPI;
import com.example.complaintapplication.models.SharedHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    ProgressBar progressBar;
    String id;
    SharedHelper _appPrefs;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        onStart();
        _appPrefs = new SharedHelper(getApplicationContext());
        id = _appPrefs.getuserId().toString();
        Log.e(id, "onCreate: ");

    }

    @Override
    protected void onStart() {
        progressBar = findViewById(R.id.historyLoader);
        progressBar.setVisibility(View.VISIBLE);
        super.onStart();
        String URL = Constants.base_url + "getUserComplaints";
        final RecyclerView recyclerView = findViewById(R.id.helpRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                Log.e("tag", String.valueOf(response));
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                HistoryAPI[] getHistoryApis = gson.fromJson(response, HistoryAPI[].class);

                recyclerView.setAdapter(new HistoryAdapter(HistoryActivity.this, getHistoryApis));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", id);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

}