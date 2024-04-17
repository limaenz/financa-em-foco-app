package com.example.financa_em_foco_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financa_em_foco_app.DetalhesDespesasActivity;
import com.example.financa_em_foco_app.Models.Transacao;
import com.example.financa_em_foco_app.ViewHolder.TransacaoViewHolder;
import com.example.financa_em_foco_app.databinding.ItemTransacaoBinding;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TransacaoAdapter extends RecyclerView.Adapter<TransacaoViewHolder> {
    private List<Transacao> transacoesList;
    private Context context;

    public TransacaoAdapter(Context context, List<Transacao> transacoesList) {
        this.transacoesList = transacoesList;
        this.context = context;
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

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
        );

        String dataFormatada = dateFormat
                .format(transacao.data);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context,
                    DetalhesDespesasActivity.class);

            intent.putExtra(
                    "Descricao", transacao.descricao);

            intent.putExtra(
                    "Valor", transacao.valor);

            intent.putExtra(
                    "Data", dataFormatada);

            intent.putExtra(
                    "Tipo", transacao.tipo);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return transacoesList.size();
    }

}

