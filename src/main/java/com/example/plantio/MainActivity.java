package com.example.plantio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SSHClass sshClass = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WebView webView = (WebView) findViewById(R.id.web);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://192.168.0.200:8080/?action=stream");


        WebView webView1 = (WebView) findViewById(R.id.wv);
        webView1.loadUrl("https://thingspeak.com/channels/1986256/widgets/575692");

        while(true)
        {
            MQTTconnector tconnector = new MQTTconnector();
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }





}