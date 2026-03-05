package com.example.smartfeedchick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);

        // Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Info button
        findViewById(R.id.btnInfo).setOnClickListener(v ->
                Toast.makeText(this, "SmartFeed Chick v1.0.0", Toast.LENGTH_SHORT).show());

        // ===== AKUN & KEAMANAN =====
        findViewById(R.id.rowProfil).setOnClickListener(v ->
                Toast.makeText(this, "Membuka profil...", Toast.LENGTH_SHORT).show());

        findViewById(R.id.rowNotifikasi).setOnClickListener(v ->
                Toast.makeText(this, "Membuka pengaturan notifikasi...", Toast.LENGTH_SHORT).show());

        // ===== KONFIGURASI SISTEM =====
        findViewById(R.id.rowKalibrasi).setOnClickListener(v ->
                Toast.makeText(this, "Kalibrasi perangkat dimulai...", Toast.LENGTH_SHORT).show());

        // Tema toggle
        SwitchCompat switchTema = findViewById(R.id.switchTema);
        TextView txtTemaMode = findViewById(R.id.txtTemaMode);
        if (switchTema != null && txtTemaMode != null) {
            switchTema.setOnCheckedChangeListener((btn, isChecked) -> {
                if (isChecked) {
                    txtTemaMode.setText("Mode Terang aktif");
                    Toast.makeText(this, "Mode Terang", Toast.LENGTH_SHORT).show();
                } else {
                    txtTemaMode.setText("Mode Gelap aktif");
                    Toast.makeText(this, "Mode Gelap", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // ===== BANTUAN & LAINNYA =====
        findViewById(R.id.rowBantuan).setOnClickListener(v ->
                Toast.makeText(this, "Membuka Pusat Bantuan...", Toast.LENGTH_SHORT).show());

        findViewById(R.id.rowTentang).setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Tentang Aplikasi")
                        .setMessage("SmartFeed Chick\nVersi 1.0.0\n\n© 2024 SmartPoultry Systems.\nIntelligent Farming Solutions.")
                        .setPositiveButton("Tutup", null)
                        .show());

        // ===== LOGOUT =====
        Button btnLogout = findViewById(R.id.btnLogout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v ->
                    new AlertDialog.Builder(this)
                            .setTitle("Keluar")
                            .setMessage("Apakah kamu yakin ingin keluar dari akun?")
                            .setPositiveButton("Keluar", (dialog, which) -> {
                                // Kembali ke halaman Login dan hapus semua activity
                                Intent intent = new Intent(this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            })
                            .setNegativeButton("Batal", null)
                            .show());
        }

        // ===== BOTTOM NAVIGATION =====
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_profile);
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
                    startActivity(new Intent(this, MonitoringActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_analytics) {
                    startActivity(new Intent(this, AnalyticsActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_profile) {
                    // Sudah di halaman ini
                    return true;
                }
                return false;
            });
        }
    }
}
