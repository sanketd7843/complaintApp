package com.example.complaintapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erikagtierrez.multiple_media_picker.Gallery;
import com.example.complaintapplication.models.CityApi;
import com.example.complaintapplication.models.Constants;
import com.example.complaintapplication.models.ServicesApi;
import com.example.complaintapplication.models.SharedHelper;
import com.example.complaintapplication.models.SignUpApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ComplaintActivity extends AppCompatActivity {
    private static final String TAG = "Testing";
    SharedHelper _appPrefs;
    String city, typeOfService;
    CityApi[] data;
    List<String> cities = new ArrayList<>();
    List<String> typeOfServiceArray = new ArrayList<>();

    List<Integer> citiesId = new ArrayList<>();
    List<Integer> serviceId = new ArrayList<>();

    AlertDialog.Builder builder;

    Spinner citySpinner, typeOfServiceSpinner;
    private int mYear, mMonth, mDay;
    EditText mobno, name, password, emailid, aadhar, address, description;
    String dob = "", selectedCity = "", gender = "Male", selectedService = "", selectedVideo = "", selectedPhoto = "", serviceText = "";

    ImageView selectedImage, complaintImage, back;
    ProgressBar loading ;
    static final int OPEN_MEDIA_PICKER = 1;  // Request code

    Button submitComplaint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);


        builder = new AlertDialog.Builder(this);

        loading = findViewById(R.id.complaintLoader);
        citySpinner = findViewById(R.id.complaintCity);
        typeOfServiceSpinner = findViewById(R.id.complaintTypeOfService);
        address = findViewById(R.id.complaintAddress);
        description = findViewById(R.id.complaintDescription);
        complaintImage = findViewById(R.id.complaintImage);
        //selectedImage = findViewById(R.id.selectedImage);
        back =  findViewById(R.id.backComplaint);
        submitComplaint = findViewById(R.id.btnComplaint);
        _appPrefs = new SharedHelper(getApplicationContext());

        GetCitiesApi();
        getTypeOfService();

        submitComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComplaintApi();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        complaintImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ComplaintActivity.this, Gallery.class);
                // Set the title
                intent.putExtra("title","Select media");
                // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
                intent.putExtra("mode",1);
                intent.putExtra("maxSelection",1); // Optional
                startActivityForResult(intent,OPEN_MEDIA_PICKER);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_MEDIA_PICKER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> selectionResult = data.getStringArrayListExtra("result");
                Log.e("path", selectionResult.get(0));

                String filePath = selectionResult.get(0);
                if(filePath.contains(".mp4") || filePath.contains(".mkv") || filePath.contains(".3gp")){
                    selectedVideo = filePath;
                }
                else{
                    selectedPhoto = filePath;
                }
            }
        }
    }


    private void ComplaintApi() {

        Log.e("1", _appPrefs.getFullName());
        Log.e("2", _appPrefs.getuserId().toString());
        Log.e("3", selectedCity);
        Log.e("4", selectedVideo);
        Log.e("5", selectedPhoto);
        Log.e("6", selectedService);
        Log.e("7", address.getText().toString());
        Log.e("8", _appPrefs.getMobNo());

        if ((_appPrefs.getFullName().length() == 0) ||
                (_appPrefs.getuserId().toString().length() == 0) ||
                (selectedCity.length() == 0) ||
            (selectedVideo.length() == 0 && selectedPhoto.length() == 0) ||
            (selectedService.length() == 0) ||
            (address.getText().toString().length() == 0) ||
            (_appPrefs.getMobNo().length() == 0)){
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
            if(selectedVideo.length() == 0){
                loading.setVisibility(View.VISIBLE);
                Ion.with(ComplaintActivity.this)
                        .load(Constants.base_url + "RegisterComplaint")
                        .setTimeout(60 * 60 * 1000)
                        //.setMultipartFile("aadhar_card", new File(FilePath))
                        .setMultipartParameter("user_name", _appPrefs.getFullName())
                        .setMultipartParameter("user_id", _appPrefs.getuserId().toString())
                        .setMultipartParameter("city_id", selectedCity)
                        .setMultipartParameter("description", description.getText().toString())
                        .setMultipartParameter("problem_type_id", selectedService)
                        .setMultipartParameter("service_text", serviceText)
                        .setMultipartParameter("status", "1")
                        .setMultipartParameter("address", address.getText().toString())
                        .setMultipartParameter("mobile_no", _appPrefs.getMobNo())
                        .setMultipartFile("image", new File(selectedPhoto))
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                Log.i("hello", result);
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                SignUpApi userList = mGson.fromJson(result, SignUpApi.class);
                                if (userList.getSuccess() == 1) {
                                    loading.setVisibility(View.GONE);
                                    new AlertDialog.Builder(ComplaintActivity.this)
                                            .setTitle("Success")
                                            .setMessage("The complaint is registered")
                                            .setPositiveButton(android.R.string.ok, null)
                                            .show();
                                }
                            }
                        });
            }
            else{
                loading.setVisibility(View.VISIBLE);
                Ion.with(ComplaintActivity.this)
                        .load(Constants.base_url + "RegisterComplaint")
                        .setTimeout(60 * 60 * 1000)
                        //.setMultipartFile("aadhar_card", new File(FilePath))
                        .setMultipartParameter("user_name", _appPrefs.getFullName())
                        .setMultipartParameter("user_id", _appPrefs.getuserId().toString())
                        .setMultipartParameter("city_id", selectedCity)
                        .setMultipartParameter("description", description.getText().toString())
                        .setMultipartParameter("problem_type_id", selectedService)
                        .setMultipartParameter("status", "1")
                        .setMultipartParameter("service_text", serviceText)
                        .setMultipartParameter("address", address.getText().toString())
                        .setMultipartParameter("mobile_no", _appPrefs.getMobNo())
                        .setMultipartFile("video", new File(selectedVideo))
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                Log.i("hello", result);
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                SignUpApi userList = mGson.fromJson(result, SignUpApi.class);
                                if (userList.getSuccess() == 1) {
                                    loading.setVisibility(View.GONE);
                                    new AlertDialog.Builder(ComplaintActivity.this)
                                            .setTitle("Success")
                                            .setMessage("The complaint is registered")
                                            .setPositiveButton(android.R.string.ok, null)
                                            .show();
                                }
                            }
                        });
            }

        }
    }

    private void getTypeOfService() {
        loading.setVisibility(View.VISIBLE);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, typeOfServiceArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfServiceSpinner.setAdapter(adapter);

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url + "getService", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.setVisibility(View.GONE);
                //Log.i("hello", response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                ServicesApi[] userList = mGson.fromJson(response, ServicesApi[].class);

                for (int i = 0; i < userList.length; ++i) {
                    typeOfServiceArray.add(userList[i].getCity());
                    serviceId.add(userList[i].getId());
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


        typeOfServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                selectedService = (serviceId.get(pos)).toString();
                Log.e("selected", selectedService);
                serviceText = (typeOfServiceArray.get(pos)).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void GetCitiesApi() {
        loading.setVisibility(View.VISIBLE);
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

}