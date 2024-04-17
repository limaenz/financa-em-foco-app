package com.example.financa_em_foco_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financa_em_foco_app.databinding.ActivityDetalhesDespesasBinding;

public class DetalhesDespesasActivity extends AppCompatActivity {
    private ActivityDetalhesDespesasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalhesDespesasBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            binding.descricaoTextView
                    .setText(bundle
                    .getString("Descricao"));

            binding.valorTextView
                    .setText(String.format("Valor: R$ %s", bundle
                            .getDouble("Valor")));

            binding.dataTextView
                    .setText(String.format("Data: %s", bundle
                            .getString("Data")));

            binding.tipoTextView
                    .setText(String.format("Tipo: %s", bundle
                            .getString("Tipo")));

        }
    }
}