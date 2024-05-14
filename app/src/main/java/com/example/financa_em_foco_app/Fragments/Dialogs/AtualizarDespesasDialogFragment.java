package com.example.financa_em_foco_app.Fragments.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.financa_em_foco_app.Models.Despesa;
import com.example.financa_em_foco_app.Models.Objetivo;
import com.example.financa_em_foco_app.databinding.FragmentAtualizarDespesasDialogBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AtualizarDespesasDialogFragment extends DialogFragment {
    private String idTransacao;
    private FragmentAtualizarDespesasDialogBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public AtualizarDespesasDialogFragment(String idTransacao) {
        this.idTransacao = idTransacao;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAtualizarDespesasDialogBinding.inflate(inflater, container, false);
        configuraTela();
        return binding.getRoot();
    }

    private void configuraTela() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        binding.botaoAtualizar.setOnClickListener(v -> atualizarTransacao());
    }

    private void atualizarTransacao() {
        binding.botaoAtualizar.setEnabled(false);
        binding.progressBar.setVisibility(View.VISIBLE);

        double valorDeposito = Double.parseDouble(binding.editTextValor.getText().toString());

        DatabaseReference transcaoRef = mDatabase.child("Despesas").child(idTransacao);
        transcaoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Despesa transacao  = dataSnapshot.getValue(Despesa.class);

                    if (transacao != null) {
                        if (!validaCampos(transacao))
                            return;

                        transacao.valor = valorDeposito;

                        transcaoRef.setValue(transacao).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                binding.botaoAtualizar.setEnabled(true);
                                binding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Transação atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                                dismiss();
                                getActivity().getOnBackPressedDispatcher().onBackPressed();
                            } else {
                                binding.botaoAtualizar.setEnabled(true);
                                binding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Falha ao atualizar transação.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                binding.botaoAtualizar.setEnabled(true);
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Falha ao atualizar transação.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validaCampos(Despesa transacao) {
        double valorDeposito = Double
                .parseDouble(binding.editTextValor
                        .getText()
                        .toString());

        if (valorDeposito <= 0) {
            binding.editTextValor.setError("Valor inválido.");
            binding.editTextValor.requestFocus();
            binding.progressBar.setVisibility(View.GONE);
            binding.botaoAtualizar.setEnabled(true);
            return false;
        }

        return true;
    }
}