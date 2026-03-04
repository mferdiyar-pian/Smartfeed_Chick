package com.example.smartfeedchick;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private ImageView imgTogglePassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        imgTogglePassword = findViewById(R.id.imgTogglePassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnContact = findViewById(R.id.btnContact);
        TextView txtForgot = findViewById(R.id.txtForgotPassword);

        // Toggle password visibility
        imgTogglePassword.setOnClickListener(v -> {
            if (isPasswordVisible) {
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isPasswordVisible = false;
            } else {
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isPasswordVisible = true;
            }
            etPassword.setSelection(etPassword.getText().length());
        });

        // Login button
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty()) {
                etUsername.setError("Username tidak boleh kosong");
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("Password tidak boleh kosong");
                return;
            }

            // TODO: Tambahkan logika autentikasi di sini
            // Contoh langsung ke MainActivity:
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
        });

        // Sign Up
        btnSignUp.setOnClickListener(v -> {
            Toast.makeText(this, "Halaman Sign Up", Toast.LENGTH_SHORT).show();
            // TODO: startActivity(new Intent(this, RegisterActivity.class));
        });

        // Contact Admin
        btnContact.setOnClickListener(v -> {
            Toast.makeText(this, "Hubungi Administrator", Toast.LENGTH_SHORT).show();
        });

        // Forgot Password
        txtForgot.setOnClickListener(v -> {
            Toast.makeText(this, "Reset Password", Toast.LENGTH_SHORT).show();
        });
    }
}