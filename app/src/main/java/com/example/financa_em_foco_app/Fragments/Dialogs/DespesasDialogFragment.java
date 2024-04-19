package com.example.financa_em_foco_app.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.financa_em_foco_app.Models.Despesa;
import com.example.financa_em_foco_app.R;
import com.example.financa_em_foco_app.databinding.FragmentDespesasDialogBinding;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDespesasDialogBinding.inflate(inflater, container, false);
        configuraTela();
        return binding.getRoot();
    }

    private void configuraTela() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        binding.editTextData.setFocusable(false);
        binding.editTextData.setInputType(InputType.TYPE_NULL);

        binding.editTextData.setOnClickListener(v -> mostrarData());
        binding.botaoAdicionar.setOnClickListener(v -> adicionarTransacao());
    }

    private void mostrarData() {
        final Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
            String dataSelecionada = String.format(Locale.US, "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
            binding.editTextData.setText(dataSelecionada);
        }, ano, mes, dia);

        datePickerDialog.show();
    }

    private Boolean validCampos() {
        String valor = binding.editTextValor.getText().toString();
        String data = binding.editTextData.getText().toString();
        String descricao = binding.editTextDescricao.getText().toString();

        if (descricao.isEmpty()) {
            binding.editTextDescricao.setError("Insira a descrição.");
            binding.editTextDescricao.requestFocus();
            binding.progressBar.setVisibility(View.GONE);
            binding.botaoAdicionar.setEnabled(true);
            return false;
        }

        if (data.isEmpty()) {
            binding.editTextData.setError("Insira a data.");
            binding.editTextData.requestFocus();
            binding.progressBar.setVisibility(View.GONE);
            binding.botaoAdicionar.setEnabled(true);
            return false;
        }

        if (valor.isEmpty()) {
            binding.editTextValor.setError("Insira o valor.");
            binding.editTextValor.requestFocus();
            binding.progressBar.setVisibility(View.GONE);
            binding.botaoAdicionar.setEnabled(true);
            return false;
        }

        if (valor.length() > 10) {
            binding.editTextValor.setError("Valor inválido.");
            binding.editTextValor.requestFocus();
            binding.progressBar.setVisibility(View.GONE);
            binding.botaoAdicionar.setEnabled(true);
            return false;
        }

        return true;
    }

    private void adicionarTransacao() {
        binding.botaoAdicionar.setEnabled(false);

        if (!validCampos()) {
            return;
        }

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
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();

            Date data = dataFormat.parse(dataString);
            String id = mDatabase.push().getKey();
            String usuarioId = mAuth.getCurrentUser().getUid();

            if (id != null) {
                Despesa despesa = new Despesa(id, data, descricao, valor, tipo, usuarioId);

                mDatabase.child("Despesas").child(id).setValue(despesa).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        binding.botaoAdicionar.setEnabled(true);
                        binding.progressBar.setVisibility(View.GONE);
                        dialog.dismiss();
                        dismiss();
                    } else {
                        binding.botaoAdicionar.setEnabled(true);
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Falha ao adicionar transação.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        dismiss();
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