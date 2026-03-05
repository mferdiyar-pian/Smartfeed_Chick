package com.example.smartfeedchick;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Random;

public class MonitoringActivity extends AppCompatActivity {

    private TextView txtTemperature, txtHumidity, txtAmmonia;
    private Handler handler = new Handler();
    private Random random = new Random();

    // Simulate live sensor updates every 5 seconds
    private Runnable liveUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            updateSensorValues();
            handler.postDelayed(this, 5000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_monitoring);

        txtTemperature = findViewById(R.id.txtTemperature);
        txtHumidity    = findViewById(R.id.txtHumidity);
        txtAmmonia     = findViewById(R.id.txtAmmonia);

        // Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Fan Control button
        Button btnFan = findViewById(R.id.btnFanControl);
        btnFan.setOnClickListener(v ->
                Toast.makeText(this, "Fan Control dibuka", Toast.LENGTH_SHORT).show());

        // Full Report button
        Button btnReport = findViewById(R.id.btnFullReport);
        btnReport.setOnClickListener(v ->
                Toast.makeText(this, "Membuat laporan lengkap...", Toast.LENGTH_SHORT).show());

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        
        // Memastikan item Monitoring terpilih (warna hijau/aktif)
        bottomNav.setSelectedItemId(R.id.nav_monitoring);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, DashboardActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_controls) {
                startActivity(new Intent(this, ControlsActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_monitoring) {
                // Sudah di halaman monitoring
                return true;
            } else if (id == R.id.nav_analytics) {
                startActivity(new Intent(this, AnalyticsActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // Start live updates
        handler.post(liveUpdateRunnable);
    }

    private void updateSensorValues() {
        // Simulate slight fluctuation in sensor values
        float temp = 28.0f + random.nextFloat() * 1.5f;
        int humidity = 60 + random.nextInt(6);
        int ammonia = 20 + random.nextInt(5);

        txtTemperature.setText(String.format("%.1f°C", temp));
        txtHumidity.setText(humidity + "%");
        txtAmmonia.setText(ammonia + " ppm");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(liveUpdateRunnable);
    }
}