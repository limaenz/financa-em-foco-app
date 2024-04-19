package com.example.financa_em_foco_app.ViewHolder;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financa_em_foco_app.Models.Despesa;
import com.example.financa_em_foco_app.R;
import com.example.financa_em_foco_app.databinding.ItemTransacaoBinding;

import java.text.DecimalFormat;

public class DespesaViewHolder extends RecyclerView.ViewHolder {
    private ItemTransacaoBinding binding;

    public DespesaViewHolder(@NonNull ItemTransacaoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Despesa transacao) {
        binding.descricaoTextView.setText(transacao.descricao);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String valorFormatado = decimalFormat.format(transacao.valor);

        binding.valorTextView.setText("R$ " + valorFormatado);

        if ("gasto".equalsIgnoreCase(transacao.tipo)) {
            int redColor = ContextCompat.getColor(binding.getRoot().getContext(), R.color.red);
            binding.valorTextView.setTextColor(redColor);
        }
    }
}
