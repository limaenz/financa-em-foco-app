package com.example.financa_em_foco_app.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financa_em_foco_app.Models.Transacao;
import com.example.financa_em_foco_app.databinding.ItemTransacaoBinding;

public class TransacaoViewHolder extends RecyclerView.ViewHolder {
    private ItemTransacaoBinding binding;

    public TransacaoViewHolder(@NonNull ItemTransacaoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Transacao transacao) {
        binding.descricaoTextView.setText(transacao.descricao);
        binding.valorTextView.setText("R$ " + String.valueOf(transacao.valor));
    }

}
