package com.example.financa_em_foco_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financa_em_foco_app.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configuraTela();
    }

    private void configuraTela() {
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        new Handler(getMainLooper()).postDelayed(this::verificaLogin, 3000);
    }

    private void verificaLogin() {
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, TelaInicialActivity.class));
        } else {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}