package com.example.financa_em_foco_app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financa_em_foco_app.databinding.ActivityTelaInicialBinding;

public class TelaInicialActivity extends AppCompatActivity {
    private ActivityTelaInicialBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaInicialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabAdd.setOnClickListener(
                view -> Toast.makeText(
                        TelaInicialActivity.this,
                        "Despesa criada! 😁",
                        Toast.LENGTH_SHORT).show()
        );
    }
}