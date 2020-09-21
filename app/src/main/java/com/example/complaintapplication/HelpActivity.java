package com.example.complaintapplication;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.complaintapplication.models.Constants;

public class HelpActivity extends AppCompatActivity {

    WebView web;
    String TAG = "testing";
    ProgressBar loading ;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        web = findViewById(R.id.frameweb);
        loading = findViewById(R.id.helpLoader);
        back = findViewById(R.id.backHelp);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        web.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }


            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(HelpActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        web.loadUrl(Constants.base_url_help);
    }
}