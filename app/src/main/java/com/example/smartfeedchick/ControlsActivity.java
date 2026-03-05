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

    private TextView txtSpeed;
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

        // Settings (Profile/Settings Page)
        findViewById(R.id.btnSettings).setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));

        // Speed SeekBar
        if (seekBarSpeed != null) {
            seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Map 0-30 → 0.0 - 3.0 m/s
                    float speed = progress / 10.0f;
                    if (txtSpeed != null) txtSpeed.setText(String.format("%.1f", speed));
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        // Start / Stop Feeding Cycle
        Button btnStart = findViewById(R.id.btnStartFeeding);
        if (btnStart != null) {
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
        }

        // Emergency Stop
        findViewById(R.id.btnEmergencyStop).setOnClickListener(v -> {
            isFeeding = false;
            if (btnStart != null) btnStart.setText("▶  START FEEDING CYCLE");
            Toast.makeText(this, "🛑 DARURAT: Feeder dihentikan!", Toast.LENGTH_LONG).show();
        });

        // Return to Base
        findViewById(R.id.btnReturnBase).setOnClickListener(v ->
                Toast.makeText(this, "Kembali ke posisi awal...", Toast.LENGTH_SHORT).show());

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_controls);
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_controls) {
                    return true;
                } else if (id == R.id.nav_monitoring) {
                    startActivity(new Intent(this, MonitoringActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_analytics) {
                    startActivity(new Intent(this, AnalyticsActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(this, SettingsActivity.class));
                    finish();
                    return true;
                }
                return false;
            });
        }
    }
}
