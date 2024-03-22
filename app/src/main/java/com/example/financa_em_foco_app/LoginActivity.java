package com.example.financa_em_foco_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financa_em_foco_app.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configuraTela();
        mAuth = FirebaseAuth.getInstance();
    }

    private void configuraTela() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        binding.buttonEntrarNaMinhaConta.setOnClickListener(v -> fazerLogin());
        binding.textViewEsqueciMinhaSenha.setOnClickListener(v -> esqueciMinhaSenha());
    }

    private void fazerLogin() {
        String email = binding.editTextEmail.getText().toString().trim();
        String senha = binding.editTextSenha.getText().toString().trim();

        if (dadosDeLoginValidos(email, senha)) {
            autenticarUsuario(email, senha);
        }
    }

    private boolean dadosDeLoginValidos(String email, String senha) {
        if (email.isEmpty() || senha.isEmpty()) {
            binding.editTextEmail.setError("Digite seu e-mail e senha.");
            binding.editTextEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError("Digite um e-mail válido.");
            binding.editTextEmail.requestFocus();
            return false;
        }

        return true;
    }

    private void autenticarUsuario(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        handleLoginComSucesso();
                    } else {
                        handleLoginComErro();
                    }
                });
    }

    private void handleLoginComSucesso() {
        Toast.makeText(
                LoginActivity.this,
                "Login feito com sucesso.",
                Toast.LENGTH_SHORT
        ).show();
        startActivity(new Intent(this, TelaInicialActivity.class));
        finish();
    }

    private void handleLoginComErro() {
        Toast.makeText(
                LoginActivity.this,
                "Falha na autenticação.",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void esqueciMinhaSenha() {
        String email = binding.editTextEmail.getText().toString().trim();

        if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError("Digite um e-mail válido.");
            binding.editTextEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(
                            LoginActivity.this,
                            "E-mail de redefinição de senha enviado com sucesso.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            "Falha ao enviar e-mail de redefinição de senha.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
}