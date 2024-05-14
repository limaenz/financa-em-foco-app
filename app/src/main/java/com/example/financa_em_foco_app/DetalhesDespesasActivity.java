package com.example.financa_em_foco_app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financa_em_foco_app.Fragments.Dialogs.AtualizarDespesasDialogFragment;
import com.example.financa_em_foco_app.databinding.ActivityDetalhesDespesasBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalhesDespesasActivity extends AppCompatActivity {
    private ActivityDetalhesDespesasBinding binding;
    private AtualizarDespesasDialogFragment atualizarDespesasDialogFragment;

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

            binding.textViewId.setText(bundle.getString("Id"));
        }

        binding.buttonRegistrarDeposito.setOnClickListener(x -> mostrarModal());
        binding.buttonConcluirObjetivo.setOnClickListener(x -> excluirTransacao());
        binding.backButton.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void excluirTransacao() {
        String transacaoId = binding.textViewId.getText().toString();

        DatabaseReference objetivoRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Despesas")
                .child(transacaoId);

        objetivoRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(
                        DetalhesDespesasActivity.this,
                        "Transação removida com sucesso!", Toast.LENGTH_SHORT
                ).show();
                finish();
            } else {
                Toast.makeText(
                        DetalhesDespesasActivity.this,
                        "Falha ao excluir o transação.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void mostrarModal() {
        if (atualizarDespesasDialogFragment == null || !atualizarDespesasDialogFragment.isVisible()) {
            atualizarDespesasDialogFragment = new AtualizarDespesasDialogFragment(binding.textViewId.getText().toString());
            atualizarDespesasDialogFragment.show(getSupportFragmentManager(), atualizarDespesasDialogFragment.getTag());
        }
    }
}