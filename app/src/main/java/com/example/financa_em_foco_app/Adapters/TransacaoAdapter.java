package com.example.financa_em_foco_app.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financa_em_foco_app.Models.Transacao;
import com.example.financa_em_foco_app.ViewHolder.TransacaoViewHolder;
import com.example.financa_em_foco_app.databinding.ItemTransacaoBinding;

import java.util.List;

public class TransacaoAdapter extends RecyclerView.Adapter<TransacaoViewHolder> {
    private List<Transacao> transacoesList;

    public TransacaoAdapter(List<Transacao> transacoesList) {
        this.transacoesList = transacoesList;
    }

    @NonNull
    @Override
    public TransacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransacaoBinding binding = ItemTransacaoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TransacaoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransacaoViewHolder holder, int position) {
        Transacao transacao = transacoesList.get(position);
        holder.bind(transacao);
    }

    @Override
    public int getItemCount() {
        return transacoesList.size();
    }

}

