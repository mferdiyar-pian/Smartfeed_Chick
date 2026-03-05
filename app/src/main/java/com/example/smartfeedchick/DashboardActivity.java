package com.example.smartfeedchick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
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

        // Tombol-tombol di Dashboard
        findViewById(R.id.btnOrderFeed).setOnClickListener(v -> 
            Toast.makeText(this, "Memesan pakan...", Toast.LENGTH_SHORT).show());
        
        findViewById(R.id.btnDismiss).setOnClickListener(v -> 
            Toast.makeText(this, "Alert ditutup", Toast.LENGTH_SHORT).show());

        findViewById(R.id.txtViewLogs).setOnClickListener(v -> 
            Toast.makeText(this, "Membuka log...", Toast.LENGTH_SHORT).show());

        // Konfigurasi Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_home);

            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    return true;
                } else if (id == R.id.nav_controls) {
                    startActivity(new Intent(this, ControlsActivity.class));
                    return true;
                } else if (id == R.id.nav_monitoring) {
                    startActivity(new Intent(this, MonitoringActivity.class));
                    return true;
                } else if (id == R.id.nav_analytics) {
                    startActivity(new Intent(this, AnalyticsActivity.class));
                    return true;
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(this, SettingsActivity.class));
                    return true;
                }
                return false;
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            bottomNav.getMenu().findItem(R.id.nav_home).setChecked(true);
        }
    }
}
