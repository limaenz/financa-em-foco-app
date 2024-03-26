package com.example.financa_em_foco_app;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financa_em_foco_app.databinding.ActivityEsqueciMinhaSenhaBinding;
import com.google.firebase.auth.FirebaseAuth;

public class EsqueciMinhaSenhaActivity extends AppCompatActivity {
    private ActivityEsqueciMinhaSenhaBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configuraTela();
        mAuth = FirebaseAuth.getInstance();
    }

    private void configuraTela() {
        binding = ActivityEsqueciMinhaSenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        binding.buttonEnviar.setOnClickListener(v -> esqueciMinhaSenha());
    }

    private void esqueciMinhaSenha() {
        String email = binding.editTextEmail.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError("Digite um e-mail válido.");
            binding.editTextEmail.requestFocus();
            return;
        }
        binding.buttonEnviar.setEnabled(false);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, task -> {
            binding.buttonEnviar.setEnabled(true);
            if (task.isSuccessful()) {
                Toast.makeText(
                        EsqueciMinhaSenhaActivity.this,
                        "E-mail de redefinição de senha enviado com sucesso.",
                        Toast.LENGTH_SHORT
                ).show();

                getOnBackPressedDispatcher().onBackPressed();
            } else {
                Toast.makeText(
                        EsqueciMinhaSenhaActivity.this,
                        "Falha ao enviar e-mail de redefinição de senha.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}