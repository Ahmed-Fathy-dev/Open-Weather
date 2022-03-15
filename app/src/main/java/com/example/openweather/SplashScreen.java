package com.example.openweather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SplashScreen extends AppCompatActivity {
    private static final int TIMEPIECES = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, WeatherActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIMEPIECES);


        //deprecated
        //  Handler handler = new Handler();
       /* handler.postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }, TIMEPIECES);*/
    }
}