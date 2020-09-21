package com.example.complaintapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.complaintapplication.models.CityApi;
import com.example.complaintapplication.models.Constants;
import com.example.complaintapplication.models.SharedHelper;
import com.example.complaintapplication.models.SignUpApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SignUpActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button DatePicker;
    SharedHelper _appPrefs;
    Button txtDate;
    String city;
    CityApi[] data;
    List<String> cities = new ArrayList<>();
    List<Integer> citiesId = new ArrayList<>();
    Spinner citySpinner;
    private int mYear, mMonth, mDay;
    EditText mobno, name, password, emailid, aadhar, address;
    String dob = "", selectedCity = "", gender = "Male";
    ProgressBar loading ;
    ImageView back;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        builder = new AlertDialog.Builder(this);

        DatePicker = findViewById(R.id.signupDOB);
        txtDate = findViewById(R.id.signupDOB);
        DatePicker.setOnClickListener(this);
        citySpinner = findViewById(R.id.CitySpinner);
        back     = findViewById(R.id.backSignup);


         mobno = findViewById(R.id.signupMobNo);
         password = findViewById(R.id.signupPass);
         name = findViewById(R.id.signupName);
         emailid = findViewById(R.id.signupEmail);
         aadhar = findViewById(R.id.signupAadharNo);
         TextView dob = findViewById(R.id.signupDOB);
         address = findViewById(R.id.signupAddress);
         loading = findViewById(R.id.signUpLoader);
         Button signup = findViewById(R.id.signupBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //gender
        RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.radioGender);
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
        String gender = radioSexButton.getText().toString();
        GetCitiesApi();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterApi();
            }
        });

        onStart();

        _appPrefs = new SharedHelper(getApplicationContext());
        city = _appPrefs.getCity();
    }


    private void GetCitiesApi() {

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url + "getCity", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.setVisibility(View.GONE);
                //Log.i("hello", response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                CityApi[] userList = mGson.fromJson(response, CityApi[].class);

                for (int i = 0; i < userList.length; ++i) {
                    cities.add(userList[i].getCity());
                    citiesId.add(userList[i].getId());
                    adapter.notifyDataSetChanged();
                    //Log.e("hey there",userList[i].getCity());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                selectedCity = (citiesId.get(pos)).toString();
                Log.e("selected", selectedCity);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void RegisterApi() {
        Log.e("1", emailid.getText().toString());
        Log.e("2", password.getText().toString());
        Log.e("3", name.getText().toString());
        Log.e("4", selectedCity);
        Log.e("5", gender);
        Log.e("6", aadhar.getText().toString());
        Log.e("7", dob);

        if ((emailid.getText().toString().length() == 0) ||
                (password.getText().toString().length() == 0) ||
                (name.getText().toString().length() == 0) ||
                (mobno.getText().toString().length() == 0) ||
                (selectedCity.length() == 0) ||
                (gender.length() == 0) ||
                (aadhar.getText().toString().length() == 0) ||
                (dob.length() == 0) ) {
            //Setting message manually and performing action on button click
            builder.setMessage("Please Complete All The Fields")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Alert");
            alert.show();
        } else {
            loading.setVisibility(View.VISIBLE);
            RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url + "RegisterUser", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("hello", response);
                    GsonBuilder builder = new GsonBuilder();
                    Gson mGson = builder.create();
                    SignUpApi userList = mGson.fromJson(response, SignUpApi.class);
                    if (userList.getSuccess() == 1) {
                        loading.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Registered Successfully Please Login", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                    if (Integer.parseInt(userList.getSuccess().toString()) == 0) {
                        loading.setVisibility(View.INVISIBLE);
                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("User Exists")
                                .setMessage("The user with same number already exists")
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("fullname", name.getText().toString());
                    params.put("mobile_no", mobno.getText().toString());
                    params.put("password", password.getText().toString());
                    params.put("gender", gender);
                    params.put("city", selectedCity);
                    params.put("aadhar_card", aadhar.getText().toString());
                    params.put("status", "1");
                    params.put("email", emailid.getText().toString());
                    params.put("dob", dob);
                    params.put("address", "hello");

                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onClick(View v) {

        if (v == DatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            dob = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}