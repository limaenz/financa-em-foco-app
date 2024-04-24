package com.example.financa_em_foco_app.ViewHolders;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financa_em_foco_app.Models.Objetivo;
import com.example.financa_em_foco_app.R;
import com.example.financa_em_foco_app.databinding.ItemObjetivoBinding;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class ObjetivoViewHolder extends RecyclerView.ViewHolder {
    private ItemObjetivoBinding binding;

    public ObjetivoViewHolder(@NonNull ItemObjetivoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Objetivo objetivo) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String valorTotalFormatado = decimalFormat.format(objetivo.valorTotal);
        String valorAtualFormatado = decimalFormat.format(objetivo.valorAtual);

        Calendar calendarHoje = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
        Calendar calendarObjetivo = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
        calendarObjetivo.setTime(objetivo.data);

        long diffMillis = calendarObjetivo.getTimeInMillis() - calendarHoje.getTimeInMillis();
        long diffDays = (diffMillis / (1000 * 60 * 60 * 24)) + 1;

        if (diffDays < 0) {
            binding.textViewTempoRestante.setText("Tempo expirado");
            int redColor = ContextCompat.getColor(binding.getRoot().getContext(), R.color.red);
            binding.textViewTempoRestante.setTextColor(redColor);
        }

        if (diffDays == 1)
            binding.textViewTempoRestante.setText("Falta " + diffDays + " dia");

        if (diffDays > 1)
            binding.textViewTempoRestante.setText("Faltam " + diffDays + " dias");

        double porcentagem = (objetivo.valorAtual / objetivo.valorTotal) * 100;
        String porcentagemFormatada = decimalFormat.format(porcentagem) + "%";

        binding.textViewDescricao.setText(objetivo.descricao);
        binding.textViewValorAtual.setText("R$ " + valorAtualFormatado);
        binding.textViewValorTotal.setText(" de R$ " + valorTotalFormatado);

        binding.progressBar.setProgress((int) Math.round(porcentagem));
        binding.textViewPorcentagem.setText(porcentagemFormatada);
    }
}
