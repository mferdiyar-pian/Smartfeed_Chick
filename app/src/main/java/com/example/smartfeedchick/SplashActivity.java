package com.example.smartfeedchick;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView txtPercent;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide action bar for splash screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);
        txtPercent = findViewById(R.id.txtPercent);

        // Animate progress bar from 0 to 100
        simulateLoading();
    }

    private void simulateLoading() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progress <= 100) {
                    progressBar.setProgress(progress);
                    txtPercent.setText(progress + "%");
                    progress += 2;
                    handler.postDelayed(this, 50); // Update every 50ms
                } else {
                    // Navigate to LoginActivity after loading completes
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 50);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}