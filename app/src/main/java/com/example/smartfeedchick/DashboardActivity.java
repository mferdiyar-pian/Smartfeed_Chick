package com.example.smartfeedchick;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_dashboard);

        // Order Feed Button
        Button btnOrderFeed = findViewById(R.id.btnOrderFeed);
        btnOrderFeed.setOnClickListener(v -> {
            Toast.makeText(this, "Memesan pakan...", Toast.LENGTH_SHORT).show();
        });

        // Dismiss Alert Button
        Button btnDismiss = findViewById(R.id.btnDismiss);
        btnDismiss.setOnClickListener(v -> {
            Toast.makeText(this, "Alert ditutup", Toast.LENGTH_SHORT).show();
        });

        // View Logs
        findViewById(R.id.txtViewLogs).setOnClickListener(v -> {
            Toast.makeText(this, "Membuka log...", Toast.LENGTH_SHORT).show();
        });

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                // Sudah di home
            } else if (id == R.id.nav_analytics) {
                Toast.makeText(this, "Analytics", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_devices) {
                Toast.makeText(this, "Devices", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_profile) {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }
}