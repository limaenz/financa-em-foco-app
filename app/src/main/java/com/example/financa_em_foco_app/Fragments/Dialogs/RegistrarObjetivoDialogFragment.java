package com.example.financa_em_foco_app.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.financa_em_foco_app.Fragments.ObjetivosFragment;
import com.example.financa_em_foco_app.MainActivity;
import com.example.financa_em_foco_app.Models.Objetivo;
import com.example.financa_em_foco_app.R;
import com.example.financa_em_foco_app.RegisterActivity;
import com.example.financa_em_foco_app.databinding.FragmentRegistrarObjetivoDialogBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistrarObjetivoDialogFragment extends DialogFragment {
    private String idObjetivo;
    private FragmentRegistrarObjetivoDialogBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public RegistrarObjetivoDialogFragment(String idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrarObjetivoDialogBinding.inflate(inflater, container, false);
        configuraTela();
        return binding.getRoot();
    }

    private void configuraTela() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        binding.botaoAdicionar.setOnClickListener(v -> adicionarDeposito());
    }

    private void adicionarDeposito() {
        binding.botaoAdicionar.setEnabled(false);
        binding.progressBar.setVisibility(View.VISIBLE);

        double valorDeposito = Double.parseDouble(binding.editTextValor.getText().toString());

        DatabaseReference objetivoRef = mDatabase.child("Objetivos").child(idObjetivo);
        objetivoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Objetivo objetivo = dataSnapshot.getValue(Objetivo.class);
                    if (objetivo != null) {
                        objetivo.valorAtual = objetivo.valorAtual + valorDeposito;

                        objetivoRef.setValue(objetivo).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                binding.botaoAdicionar.setEnabled(true);
                                binding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Depósito adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                                dismiss();
                                getActivity().getOnBackPressedDispatcher().onBackPressed();
                            } else {
                                binding.botaoAdicionar.setEnabled(true);
                                binding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Falha ao adicionar depósito.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                binding.botaoAdicionar.setEnabled(true);
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Falha ao adicionar depósito.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}