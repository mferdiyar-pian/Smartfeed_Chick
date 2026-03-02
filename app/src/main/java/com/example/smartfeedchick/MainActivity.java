package com.example.smartfeedchick;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnStop;
    private TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hubungkan ke layout
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        txtStatus = findViewById(R.id.txtStatus);

        // Tombol START
        btnStart.setOnClickListener(v -> {
            txtStatus.setText("Status: ON");
        });

        // Tombol STOP
        btnStop.setOnClickListener(v -> {
            txtStatus.setText("Status: OFF");
        });
    }
}