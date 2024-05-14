/*
Enzo de Lima França - RA: 223571
Rodrigo Cortez de Barros Corrá - RA: 223121
Ullian de Oliveira Souza - RA: 223071
Giovana da Silva Siqueira de Oliveira - RA: 223526
Leonardo Salles - RA: 212155
*/

package com.example.financa_em_foco_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financa_em_foco_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonCriarConta.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.buttonJaTenhoConta.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }
}