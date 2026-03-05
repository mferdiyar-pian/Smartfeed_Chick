package com.example.smartfeedchick;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Random;

public class AnalyticsActivity extends AppCompatActivity {

    private TextView txtPH;
    private Handler handler = new Handler();
    private Random random = new Random();

    private Runnable liveUpdate = new Runnable() {
        @Override
        public void run() {
            float ph = 7.0f + random.nextFloat() * 0.6f;
            txtPH.setText(String.format("%.1f", ph));
            handler.postDelayed(this, 4000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_analytics);

        txtPH = findViewById(R.id.txtPH);

        // Auto-Filtration toggle
        SwitchCompat switchFiltration = findViewById(R.id.switchFiltration);
        if (switchFiltration != null) {
            switchFiltration.setOnCheckedChangeListener((btn, isChecked) ->
                    Toast.makeText(this,
                            isChecked ? "Auto-Filtration diaktifkan" : "Auto-Filtration dimatikan",
                            Toast.LENGTH_SHORT).show());
        }

        // pH Neutralizer toggle
        SwitchCompat switchNeutralizer = findViewById(R.id.switchNeutralizer);
        if (switchNeutralizer != null) {
            switchNeutralizer.setOnCheckedChangeListener((btn, isChecked) ->
                    Toast.makeText(this,
                            isChecked ? "pH Neutralizer diaktifkan" : "pH Neutralizer dimatikan",
                            Toast.LENGTH_SHORT).show());
        }

        // View All Logs
        TextView txtViewAll = findViewById(R.id.txtViewAll);
        if (txtViewAll != null) {
            txtViewAll.setOnClickListener(v ->
                    Toast.makeText(this, "Membuka semua log...", Toast.LENGTH_SHORT).show());
        }

        // Konfigurasi Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            // Kita atur ID yang terpilih lewat kode agar tidak error di XML
            bottomNav.setSelectedItemId(R.id.nav_analytics);

            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_analytics) {
                    return true;
                } else if (id == R.id.nav_home) {
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_controls) {
                    startActivity(new Intent(this, ControlsActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_monitoring) {
                    startActivity(new Intent(this, MonitoringActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_profile) {
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
        }

        // Start live pH update
        handler.post(liveUpdate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(liveUpdate);
    }
}