package com.example.financa_em_foco_app.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.financa_em_foco_app.Adapters.TransacaoAdapter;
import com.example.financa_em_foco_app.Models.Transacao;
import com.example.financa_em_foco_app.R;
import com.example.financa_em_foco_app.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private FirebaseAuth mAuth;
    private DespesasDialogFragment despesasFragment;
    private RecyclerView recyclerView;
    private TransacaoAdapter adapter;
    private List<Transacao> transacoesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        configuraTela();
        return binding.getRoot();
    }

    private void configuraTela() {
        mAuth = FirebaseAuth.getInstance();
        binding.buttonAdicionar.setOnClickListener(v -> mostrarModal());
        recyclerView = binding.listTransacoes;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TransacaoAdapter(transacoesList);
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

        TransacaoAdapter adapter = new TransacaoAdapter(transacoesList);
        binding.listTransacoes.setAdapter(adapter);
        dialog.show();

        DatabaseReference transacoesRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Transacoes");

        transacoesRef
                .orderByChild("idUsuario")
                .equalTo(mAuth.getUid())
                .limitToLast(4)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transacoesList.clear();

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Transacao transacao = itemSnapshot.getValue(Transacao.class);
                    transacoesList.add(transacao);
                }

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