package com.example.financa_em_foco_app.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financa_em_foco_app.Models.Transacao;
import com.example.financa_em_foco_app.databinding.ItemTransacaoBinding;

import java.text.DecimalFormat;

public class TransacaoViewHolder extends RecyclerView.ViewHolder {
    private ItemTransacaoBinding binding;

    public TransacaoViewHolder(@NonNull ItemTransacaoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Transacao transacao) {
        binding.descricaoTextView.setText(transacao.descricao);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String valorFormatado = decimalFormat.format(transacao.valor);
        binding.valorTextView.setText("R$ " + valorFormatado);
    }
}
