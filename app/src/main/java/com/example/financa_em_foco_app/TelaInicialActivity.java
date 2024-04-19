package com.example.financa_em_foco_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.financa_em_foco_app.Fragments.ControleFinanceiroFragment;
import com.example.financa_em_foco_app.Fragments.ObjetivosFragment;
import com.example.financa_em_foco_app.Fragments.ContaFragment;
import com.example.financa_em_foco_app.databinding.ActivityTelaInicialBinding;

public class TelaInicialActivity extends AppCompatActivity {
    private ActivityTelaInicialBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configuraTela();
        configurarNavegacaoInferior();

    }

    private void configuraTela() {
        binding = ActivityTelaInicialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        substituirFragmento(new ControleFinanceiroFragment());
        binding.bottomNavigation.setSelectedItemId(R.id.controleFinanceiro);
    }

    private void configurarNavegacaoInferior() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.controleFinanceiro) {
                substituirFragmento(new ControleFinanceiroFragment());
            } else if (itemId == R.id.conta) {
                substituirFragmento(new ContaFragment());
            } else if (itemId == R.id.objetivos) {
                substituirFragmento(new ObjetivosFragment());
            }

            return true;
        });
    }

    private void substituirFragmento(Fragment fragmento) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmento).commit();

    }
}