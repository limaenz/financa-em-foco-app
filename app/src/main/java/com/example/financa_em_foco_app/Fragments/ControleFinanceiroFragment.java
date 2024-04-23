package com.example.financa_em_foco_app.Fragments;

import android.app.AlertDialog;
import android.graphics.Color;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

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

        PieChart mPieChart = (PieChart) binding.piechart;

        mPieChart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Sleep", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Work", 35, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));

        mPieChart.startAnimation();
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

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Despesa transacao = itemSnapshot.getValue(Despesa.class);
                    despesas.add(transacao);
                }

                if (despesas.size() == 0) binding.textViewNaoPossuiDespesas.setVisibility(View.VISIBLE);

                if (despesas.size() > 0) {
                    binding.piechart.setVisibility(View.VISIBLE);
                    binding.textViewNaoPossuiDespesas.setVisibility(View.INVISIBLE);
                }

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