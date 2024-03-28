package com.example.financa_em_foco_app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.financa_em_foco_app.Fragments.HomeFragment;
import com.example.financa_em_foco_app.Fragments.ProfileFragment;
import com.example.financa_em_foco_app.databinding.ActivityTelaInicialBinding;

public class TelaInicialActivity extends AppCompatActivity {
    private ActivityTelaInicialBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configuraTela();
        configuraBotaoAdicionar();
        configurarNavegacaoInferior();

    }

    private void configuraTela() {
        binding = ActivityTelaInicialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        substituirFragmento(new HomeFragment());
    }

    private void configuraBotaoAdicionar() {
        binding.buttonAdicionar.setOnClickListener(view -> Toast.makeText(TelaInicialActivity.this, "Despesa criada! 😁", Toast.LENGTH_SHORT).show());
    }

    private void configurarNavegacaoInferior() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                substituirFragmento(new HomeFragment());
            } else if (itemId == R.id.profile) {
                substituirFragmento(new ProfileFragment());
            }

            return true;
        });
    }

    private void substituirFragmento(Fragment fragmento) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmento).commit();

    }
}