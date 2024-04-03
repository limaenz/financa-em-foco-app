package com.example.financa_em_foco_app.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.financa_em_foco_app.databinding.FragmentDespesasDialogBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class DespesasDialogFragment extends DialogFragment {
    private FragmentDespesasDialogBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDespesasDialogBinding.inflate(inflater, container, false);
        configuraTela();
        return binding.getRoot();
    }

    private void configuraTela() {
        mAuth = FirebaseAuth.getInstance();
        binding.editTextData.setOnClickListener(v -> mostrarData());
    }

    private void mostrarData() {
        final Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year, monthOfYear, dayOfMonth) -> {
            String dataSelecionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            binding.editTextData.setText(dataSelecionada);
        }, ano, mes, dia);

        datePickerDialog.show();
    }

}