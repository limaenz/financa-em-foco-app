package com.example.financa_em_foco_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financa_em_foco_app.Models.Objetivo;
import com.example.financa_em_foco_app.ViewHolders.ObjetivoViewHolder;
import com.example.financa_em_foco_app.databinding.ItemTransacaoBinding;

import java.util.List;

public class ObjetivoAdapter extends RecyclerView.Adapter<ObjetivoViewHolder> {
    private List<Objetivo> objetivos;
    private Context context;

    public ObjetivoAdapter(Context context, List<Objetivo> objetivos) {
        this.objetivos = objetivos;
        this.context = context;
    }

    @NonNull
    @Override
    public ObjetivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransacaoBinding binding = ItemTransacaoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ObjetivoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjetivoViewHolder holder, int position) {
        Objetivo objetivo = objetivos.get(position);
        holder.bind(objetivo);
    }

    @Override
    public int getItemCount() {
        return objetivos.size();
    }
}
