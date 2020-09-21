package com.example.complaintapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class fotgetPwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotget_pw);

        EditText emailverify = findViewById(R.id.forgetEmail);
        Button sendotp = findViewById(R.id.sendOTP);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(fotgetPwActivity.this, VerifyOTPActivity.class);
                startActivity(i);
            }
        });


    }
}