package com.example.complaintapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VerifyOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);


        EditText enterotp = findViewById(R.id.enterOtp);
        Button sendotp = findViewById(R.id.verifyOTP);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerifyOTPActivity.this, UpdatePasswordActivity.class);
                startActivity(i);
            }
        });



    }
}