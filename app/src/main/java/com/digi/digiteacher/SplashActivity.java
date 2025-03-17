package com.digi.digiteacher;

import static com.digi.digiteacher.Constants.PREF_NAME;
import static com.digi.digiteacher.Constants.TOKEN_KEY;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, SPLASH_DELAY);
    }

    private void checkLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, null);

        if (token != null) {
            navigateToDashboard();
        } else {
            navigateToMain();
        }
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
