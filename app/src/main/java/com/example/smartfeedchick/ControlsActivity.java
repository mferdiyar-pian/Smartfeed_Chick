package com.example.smartfeedchick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ControlsActivity extends AppCompatActivity {

    private TextView txtSpeed, txtPosition;
    private SeekBar seekBarSpeed;
    private boolean isFeeding = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_controls);

        txtSpeed = findViewById(R.id.txtSpeed);
        seekBarSpeed = findViewById(R.id.seekBarSpeed);

        // Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Settings
        findViewById(R.id.btnSettings).setOnClickListener(v ->
                Toast.makeText(this, "Pengaturan", Toast.LENGTH_SHORT).show());

        // Speed SeekBar
        seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Map 0-30 → 0.0 - 3.0 m/s
                float speed = progress / 10.0f;
                txtSpeed.setText(String.format("%.1f", speed));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Start / Stop Feeding Cycle
        Button btnStart = findViewById(R.id.btnStartFeeding);
        btnStart.setOnClickListener(v -> {
            if (!isFeeding) {
                isFeeding = true;
                btnStart.setText("⏹  STOP FEEDING CYCLE");
                Toast.makeText(this, "Siklus pakan dimulai", Toast.LENGTH_SHORT).show();
            } else {
                isFeeding = false;
                btnStart.setText("▶  START FEEDING CYCLE");
                Toast.makeText(this, "Siklus pakan dihentikan", Toast.LENGTH_SHORT).show();
            }
        });

        // Emergency Stop
        findViewById(R.id.btnEmergencyStop).setOnClickListener(v -> {
            isFeeding = false;
            btnStart.setText("▶  START FEEDING CYCLE");
            Toast.makeText(this, "🛑 DARURAT: Feeder dihentikan!", Toast.LENGTH_LONG).show();
        });

        // Return to Base
        findViewById(R.id.btnReturnBase).setOnClickListener(v ->
                Toast.makeText(this, "Kembali ke posisi awal...", Toast.LENGTH_SHORT).show());

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_controls);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                finish();
            } else if (id == R.id.nav_monitoring) {
                startActivity(new Intent(this, MonitoringActivity.class));
                finish();
            } else if (id == R.id.nav_analytics) {
                startActivity(new Intent(this, AnalyticsActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }
}