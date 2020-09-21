package com.example.complaintapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdatePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        EditText newPassword = findViewById(R.id.newpw);
        EditText confirmPassword = findViewById(R.id.confirmPassword);

        Button updatePassword = findViewById(R.id.updatePassword);

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdatePasswordActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
}