package com.example.financa_em_foco_app.Fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.financa_em_foco_app.Adapters.DespesaAdapter;
import com.example.financa_em_foco_app.Fragments.Dialogs.DespesasDialogFragment;
import com.example.financa_em_foco_app.Models.Despesa;
import com.example.financa_em_foco_app.R;
import com.example.financa_em_foco_app.databinding.FragmentControleFinanceiroBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ControleFinanceiroFragment extends Fragment {
    private FragmentControleFinanceiroBinding binding;
    private FirebaseAuth mAuth;
    private DespesasDialogFragment despesasFragment;
    private RecyclerView recyclerView;
    private DespesaAdapter adapter;
    private List<Despesa> despesas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentControleFinanceiroBinding.inflate(inflater, container, false);
        configuraTela();
        return binding.getRoot();
    }

    private void configuraTela() {
        mAuth = FirebaseAuth.getInstance();
        binding.buttonAdicionar.setOnClickListener(v -> mostrarModal());
        recyclerView = binding.recyclerViewDespesas;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DespesaAdapter(getContext(), despesas);
        recyclerView.setAdapter(adapter);
        carregarTransacoes();
    }

    private void mostrarModal() {
        if (despesasFragment == null || !despesasFragment.isVisible()) {
            despesasFragment = new DespesasDialogFragment();
            despesasFragment.show(getChildFragmentManager(), despesasFragment.getTag());
        }
    }

    private void carregarTransacoes() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        DespesaAdapter adapter = new DespesaAdapter(getContext(), despesas);

        binding.recyclerViewDespesas.setAdapter(adapter);
        dialog.show();

        DatabaseReference despesasRef = FirebaseDatabase.getInstance().getReference().child("Despesas");

        despesasRef.orderByChild("idUsuario").equalTo(mAuth.getUid()).limitToLast(4).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                despesas.clear();

                float valorTotalGasto = 0;
                float valorTotalGanho = 0;

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Despesa transacao = itemSnapshot.getValue(Despesa.class);
                    despesas.add(transacao);

                    if (transacao.tipo.equals("Gasto")) {
                        valorTotalGasto += transacao.valor;
                    } else if (transacao.tipo.equals("Ganho")) {
                        valorTotalGanho += transacao.valor;
                    }
                }

                if (despesas.isEmpty())
                    binding.textViewNaoPossuiDespesas.setVisibility(View.VISIBLE);

                if (!despesas.isEmpty()) {
                    binding.piechart.setVisibility(View.VISIBLE);
                    binding.textViewNaoPossuiDespesas.setVisibility(View.INVISIBLE);
                }

                PieChart mPieChart = binding.piechart;
                mPieChart.clear();

                ArrayList<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(valorTotalGasto, "Gasto"));
                entries.add(new PieEntry(valorTotalGanho, "Ganho"));

                PieDataSet dataSet = new PieDataSet(entries, "");
                dataSet.setColors(Color.parseColor("#AD88C6"), Color.parseColor("#7469B6"));
                dataSet.setValueTextSize(13f);
                dataSet.setValueTextColor(Color.WHITE);
                dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);

                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter(binding.piechart));

                mPieChart.setData(data);
                mPieChart.getDescription().setEnabled(false);
                mPieChart.setUsePercentValues(true);
                mPieChart.setDrawEntryLabels(false);
                mPieChart.setExtraOffsets(5, 10, 5, 10);
                mPieChart.animateY(1000);

                Legend legend = binding.piechart.getLegend();
                legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setTextSize(16f);
                legend.setTextColor(Color.BLACK);
                legend.setForm(Legend.LegendForm.CIRCLE);
                legend.setTypeface(Typeface.DEFAULT_BOLD);
                legend.setXEntrySpace(25f);
                legend.setYOffset(30f);

                mPieChart.invalidate();

                if (despesas.size() == 4) binding.textViewVerMais.setVisibility(View.VISIBLE);

                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
    }
}