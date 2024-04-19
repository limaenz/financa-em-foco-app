package com.example.financa_em_foco_app.ViewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financa_em_foco_app.Models.Objetivo;
import com.example.financa_em_foco_app.databinding.ItemTransacaoBinding;

import java.text.DecimalFormat;

public class ObjetivoViewHolder extends RecyclerView.ViewHolder {
    private ItemTransacaoBinding binding;

    public ObjetivoViewHolder(@NonNull ItemTransacaoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Objetivo objetivo) {
        binding.descricaoTextView.setText(objetivo.descricao);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String valorFormatado = decimalFormat.format(objetivo.valor);
        binding.valorTextView.setText("R$ " + valorFormatado);
    }
}
