package com.example.complaintapplication;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.complaintapplication.models.SharedHelper;

public class MainActivity extends AppCompatActivity {

    CardView complaint, help, profile, history;
    ImageView logout;
    AlertDialog.Builder builder;
    private SharedHelper _appPrefs;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        complaint = findViewById(R.id.complaint);
        history = findViewById(R.id.history);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);
        help = findViewById(R.id.help);
        _appPrefs = new SharedHelper(getApplicationContext());

        sp = getSharedPreferences("ComplaintApp", MODE_PRIVATE);

        builder = new AlertDialog.Builder(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Do you want logout ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sp.edit().clear().commit();
                                Toast.makeText(getApplicationContext(), "Logout Successful",
                                        Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Close?");
                alert.show();
            }
        });


        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ComplaintActivity.class);
                startActivity(i);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(i);
            }
        });

    }
}
