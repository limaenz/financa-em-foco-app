package com.example.financa_em_foco_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financa_em_foco_app.DetalhesDespesasActivity;
import com.example.financa_em_foco_app.Models.Despesa;
import com.example.financa_em_foco_app.ViewHolders.DespesaViewHolder;
import com.example.financa_em_foco_app.databinding.ItemTransacaoBinding;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DespesaAdapter extends RecyclerView.Adapter<DespesaViewHolder> {
    private List<Despesa> transacoesList;
    private Context context;

    public DespesaAdapter(Context context, List<Despesa> transacoesList) {
        this.transacoesList = transacoesList;
        this.context = context;
    }

    @NonNull
    @Override
    public DespesaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransacaoBinding binding = ItemTransacaoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DespesaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DespesaViewHolder holder, int position) {
        Despesa transacao = transacoesList.get(position);
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
                    "Id", transacao.id);

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

