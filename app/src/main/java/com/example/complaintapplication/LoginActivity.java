package com.example.complaintapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.complaintapplication.models.Constants;
import com.example.complaintapplication.models.Datum;
import com.example.complaintapplication.models.SharedHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private SharedHelper _appPrefs;
    ProgressBar loader;
    EditText mobno, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobno = findViewById(R.id.loginMobNo);
        password = findViewById(R.id.loginPass);
        TextView forgetpw = findViewById(R.id.forgetPw);
        Button login = (Button) findViewById(R.id.loginBtn);
        loader = findViewById(R.id.loginLoader);

        _appPrefs = new SharedHelper(getApplicationContext());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lognApi();
            }
        });

        forgetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, fotgetPwActivity.class);
                startActivity(i);
            }
        });

    }
        private void lognApi(){
            loader.setVisibility(View.VISIBLE);
            RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url + "loginUser", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("json data is", response);
                    GsonBuilder builder = new GsonBuilder();
                    Gson mGson = builder.create();
                    LoginApi userList = mGson.fromJson(response, LoginApi.class);
                    List<Datum> sam = userList.getDetails();
                    if (userList.getSuccess() == 2) {

                        Gson gson = new Gson();
                        String json = gson.toJson(sam);
                        JsonElement jelement = new JsonParser().parse(json);
                        JsonArray jarray = jelement.getAsJsonArray();
                        JsonObject jobject = jarray.get(0).getAsJsonObject();
                        String uid = jobject.get("id").getAsString();
                        String ufullname = jobject.get("fullname").getAsString();
                        String umobile_no = jobject.get("mobile_no").getAsString();
                        String upassword = jobject.get("password").getAsString();
                        String ugender = jobject.get("gender").getAsString();
                        String ucity = jobject.get("city").getAsString();
                        String uaddress = jobject.get("address").getAsString();
                        String uaadhar_card = jobject.get("aadhar_card").getAsString();
                        String uemail = jobject.get("email").getAsString();
                        String udob = jobject.get("dob").getAsString();
                        String ustatus = jobject.get("status").getAsString();
                        //Integer verified = jobject.get("verified").getAsInt();

                        //Log.e("the value is", verified.toString());
                        _appPrefs.saveUserdetails(uid, ufullname, umobile_no, upassword, ugender, ucity, uaddress, uaadhar_card, uemail, udob, ustatus, true);
                        loader.setVisibility(View.INVISIBLE);

                        //new logic
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else if (userList.getSuccess() == 1) {
                        loader.setVisibility(View.INVISIBLE);
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Alert")
                                .setMessage(userList.getMessage())
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                    } else {
                        loader.setVisibility(View.INVISIBLE);
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Incorrect Credentials")
                                .setMessage("Please Check Your Credentials")
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.i("Error", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobile_no", mobno.getText().toString());
                    params.put("password", password.getText().toString());
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);

        }
        public void onBackPressed(){
            Integer backpress = 0;
            backpress = (backpress + 1);
            Intent i = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(i);
            finish();

        }

    }
