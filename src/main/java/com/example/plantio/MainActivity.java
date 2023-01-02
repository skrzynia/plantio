package com.example.plantio;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    TextView temp;
    TextView humidity;
    TextView pressure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WebView webView = (WebView) findViewById(R.id.web);
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webView.loadUrl("http://192.168.0.200:8080/?action=stream");

        temp = (TextView) findViewById(R.id.textView);
        humidity = (TextView) findViewById(R.id.textView2);
        pressure = (TextView) findViewById(R.id.textView3);




        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                while (!isInterrupted())
                {
                    try {
                        Thread.sleep(1500);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MQTTconnector tconnector = new MQTTconnector();
                                temp.setText(tconnector.getTemp());
                                humidity.setText(tconnector.getHumidity());
                                pressure.setText(tconnector.getPressure());
                                System.out.println(tconnector.getTemp() + " " + tconnector.getHumidity() + " " + tconnector.getPressure());
                            }
                        });
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();


    }


}