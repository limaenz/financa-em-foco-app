package com.example.financa_em_foco_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financa_em_foco_app.DetalhesObjetivoActivity;
import com.example.financa_em_foco_app.Models.Objetivo;
import com.example.financa_em_foco_app.ViewHolders.ObjetivoViewHolder;
import com.example.financa_em_foco_app.databinding.ItemObjetivoBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
        ItemObjetivoBinding binding = ItemObjetivoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ObjetivoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjetivoViewHolder holder, int position) {
        Objetivo objetivo = objetivos.get(position);
        holder.bind(objetivo);

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String valorTotalFormatado = decimalFormat.format(objetivo.valorTotal);
        String valorAtualFormatado = decimalFormat.format(objetivo.valorAtual);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
        );

        String dataFormatada = dateFormat
                .format(objetivo.data);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context,
                    DetalhesObjetivoActivity.class);

            intent.putExtra(
                    "Id", objetivo.id);

            intent.putExtra(
                    "Descricao", objetivo.descricao);

            intent.putExtra(
                    "Valor total", valorTotalFormatado);

            intent.putExtra(
                    "Valor atual", valorAtualFormatado);

            intent.putExtra(
                    "Data", dataFormatada);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return objetivos.size();
    }
}
