package com.example.financa_em_foco_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financa_em_foco_app.Fragments.Dialogs.RegistrarObjetivoDialogFragment;
import com.example.financa_em_foco_app.databinding.ActivityDetalhesObjetivoBinding;

public class DetalhesObjetivoActivity extends AppCompatActivity {
    private ActivityDetalhesObjetivoBinding binding;
    private RegistrarObjetivoDialogFragment objetivosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalhesObjetivoBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            binding.textViewId
                    .setText(bundle
                            .getString("Id"));

            binding.textViewDescricao
                    .setText(bundle
                            .getString("Descricao"));

            binding.textViewValorAtual
                    .setText(String.format("Valor atual: %s", bundle
                            .getString("Valor atual")));

            binding.textViewValorTotal
                    .setText(String.format("Valor total: %s", bundle
                            .getString("Valor total")));

            binding.textViewDataFinal
                    .setText(String.format("Data final: %s", bundle
                            .getString("Data")));
        }

        binding.buttonRegistrarDeposito.setOnClickListener(x -> mostrarModal());
    }

    private void mostrarModal() {
        if (objetivosFragment == null || !objetivosFragment.isVisible()) {
            objetivosFragment = new RegistrarObjetivoDialogFragment(binding.textViewId.getText().toString());
            objetivosFragment.show(getSupportFragmentManager(), objetivosFragment.getTag());
        }
    }
}