package com.example.financa_em_foco_app.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.financa_em_foco_app.LoginActivity;
import com.example.financa_em_foco_app.Models.Transacao;
import com.example.financa_em_foco_app.databinding.FragmentDespesasDialogBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DespesasDialogFragment extends DialogFragment {
    private FragmentDespesasDialogBinding binding;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDespesasDialogBinding.inflate(inflater, container, false);
        configuraTela();
        return binding.getRoot();
    }

    private void configuraTela() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        binding.editTextData.setOnClickListener(v -> mostrarData());
        binding.botaoAdicionar.setOnClickListener(v -> adicionarTransacao());
    }

    private void mostrarData() {
        final Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year, monthOfYear, dayOfMonth) -> {
            String dataSelecionada = String.format(Locale.US, "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
            binding.editTextData.setText(dataSelecionada);
        }, ano, mes, dia);

        datePickerDialog.show();
    }

    private void adicionarTransacao() {
        binding.botaoAdicionar.setEnabled(false);
        binding.progressBar.setVisibility(View.VISIBLE);

        String descricao = binding.editTextDescricao.getText().toString();
        double valor = Double.parseDouble(binding.editTextValor.getText().toString());

        String tipo;
        if (binding.radioButtonGanho.isChecked())
            tipo = binding.radioButtonGanho.getText().toString();
        else tipo = binding.radioButtonGasto.getText().toString();
        String dataString = binding.editTextData.getText().toString();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            Date data = dataFormat.parse(dataString);
            String id = mDatabase.push().getKey();

            if (id != null) {
                Transacao transacao = new Transacao(id, data, descricao, valor, tipo);

                mDatabase.child("Transacoes").child(id).setValue(transacao)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                binding.botaoAdicionar.setEnabled(true);
                                binding.progressBar.setVisibility(View.GONE);
                                dismiss();
                            } else {
                                binding.botaoAdicionar.setEnabled(true);
                                binding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(
                                        getContext(),
                                        "Falha ao adicionar transação.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
            }
        } catch (ParseException e) {
            e.printStackTrace();
            binding.botaoAdicionar.setEnabled(true);
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}