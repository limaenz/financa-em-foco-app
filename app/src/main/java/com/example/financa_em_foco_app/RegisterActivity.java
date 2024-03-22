package com.example.financa_em_foco_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financa_em_foco_app.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configuraTela();
        mAuth = FirebaseAuth.getInstance();
    }

    private void configuraTela() {
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        binding.buttonCriarConta.setOnClickListener(v -> criaConta());
    }

    private boolean ehEmailValido(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean ehSenhaValida(String password) {
        return password.length() >= 6;
    }

    private void mostrarErroEmail() {
        binding.editTextEmail.setError("Digite um email válido");
        binding.editTextEmail.requestFocus();
    }

    private void mostrarErroSenha() {
        binding.editTextPassword.setError("A senha deve ter no mínimo 6 caracteres");
        binding.editTextPassword.requestFocus();
    }

    private void handleRegistroComSucesso() {
        Toast.makeText(
                RegisterActivity.this,
                "Conta criada com sucesso.",
                Toast.LENGTH_SHORT
        ).show();
        startActivity(new Intent(this, TelaInicialActivity.class));
        finish();
    }

    private void handleRegistroComErro() {
        Toast.makeText(
                RegisterActivity.this,
                "Falha na autenticação.",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void registrarUsuario(String email, String senha) {
        mAuth.createUserWithEmailAndPassword(
                email,
                senha
        ).addOnCompleteListener(this, task -> {
            binding.buttonCriarConta.setEnabled(true);
            if (task.isSuccessful()) {
                handleRegistroComSucesso();
            } else {
                handleRegistroComErro();
            }
        });
    }

    private void criaConta() {
        String email = binding.editTextEmail.getText().toString().trim();
        String senha = binding.editTextPassword.getText().toString().trim();

        if (!ehEmailValido(email)) {
            mostrarErroEmail();
            return;
        }

        if (!ehSenhaValida(senha)) {
            mostrarErroSenha();
            return;
        }

        binding.buttonCriarConta.setEnabled(false);
        registrarUsuario(email, senha);
    }
}
