package com.example.complaintapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.complaintapplication.models.SharedHelper;

public class ProfileActivity extends AppCompatActivity {

    TextView name, dob, address, email, mobNo, largeName, smallEmail;
    private SharedHelper _appPrefs;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.textViewname);
        dob = findViewById(R.id.textViewdob);
        back = findViewById(R.id.backProfile);
        address = findViewById(R.id.textViewaddress);
        email = findViewById(R.id.textViewemail);
        mobNo = findViewById(R.id.textViewmobile);
        largeName = findViewById(R.id.textLargeName);
        smallEmail = findViewById(R.id.textSmallEmail);

        _appPrefs = new SharedHelper(getApplicationContext());

        name.setText(_appPrefs.getFullName());
        dob.setText(_appPrefs.getDob());
        address.setText(_appPrefs.getAddress());
        email.setText(_appPrefs.getEmail());
        mobNo.setText(_appPrefs.getMobNo());

        largeName.setText(_appPrefs.getFullName());
        smallEmail.setText(_appPrefs.getEmail());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}