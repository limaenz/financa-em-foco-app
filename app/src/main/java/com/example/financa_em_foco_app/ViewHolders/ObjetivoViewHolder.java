package com.example.financa_em_foco_app.ViewHolders;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

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

        if (diffDays < 30) {
            int corAmarela = ContextCompat.getColor(binding.getRoot().getContext(), R.color.yellow);
            binding.textViewTempoRestante.setTextColor(corAmarela);
        }

        if (diffDays > 30) {
            int corAmarela = ContextCompat.getColor(binding.getRoot().getContext(), R.color.black);
            binding.textViewTempoRestante.setTextColor(corAmarela);
        }

        if (diffDays <= 0) {
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

        String textoValorAtual = "R$ " + valorAtualFormatado;
        String textoValorTotal = " de R$ " + valorTotalFormatado;

        SpannableStringBuilder textoFormatado = new SpannableStringBuilder();
        textoFormatado.append(textoValorAtual);
        textoFormatado.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                0, textoValorAtual.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textoFormatado.append(textoValorTotal);

        binding.textViewDescricao.setText(objetivo.descricao);
        binding.textViewValor.setText(textoFormatado);

        binding.progressBar.setProgress((int) Math.round(porcentagem));
        binding.textViewPorcentagem.setText(porcentagemFormatada);
    }
}
