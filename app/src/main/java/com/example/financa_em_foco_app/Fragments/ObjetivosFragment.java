package com.example.financa_em_foco_app.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.financa_em_foco_app.Adapters.ObjetivoAdapter;
import com.example.financa_em_foco_app.Fragments.Dialogs.ObjetivosDialogFragment;
import com.example.financa_em_foco_app.Models.Objetivo;
import com.example.financa_em_foco_app.R;
import com.example.financa_em_foco_app.databinding.FragmentObjetivosBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ObjetivosFragment extends Fragment {
    private FragmentObjetivosBinding binding;
    private FirebaseAuth mAuth;
    private ObjetivosDialogFragment objetivosFragment;
    private RecyclerView recyclerView;
    private List<Objetivo> objetivos = new ArrayList<>();
    private ObjetivoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentObjetivosBinding.inflate(inflater, container, false);
        configuraTela();
        return binding.getRoot();
    }

    private void configuraTela() {
        mAuth = FirebaseAuth.getInstance();
        binding.buttonAdicionarObjetivo.setOnClickListener(v -> mostrarModal());
        recyclerView = binding.recyclerViewObjetivos;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ObjetivoAdapter(getContext(), objetivos);
        recyclerView.setAdapter(adapter);
        carregarObjetivos();
    }

    private void mostrarModal() {
        if (objetivosFragment == null || !objetivosFragment.isVisible()) {
            objetivosFragment = new ObjetivosDialogFragment();
            objetivosFragment.show(getChildFragmentManager(), objetivosFragment.getTag());
        }
    }

    private void carregarObjetivos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        ObjetivoAdapter adapter = new ObjetivoAdapter(getContext(), objetivos);

        binding.recyclerViewObjetivos.setAdapter(adapter);
        dialog.show();

        DatabaseReference objetivosRef = FirebaseDatabase.getInstance().getReference().child("Objetivos");

        objetivosRef.orderByChild("idUsuario").equalTo(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                objetivos.clear();

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Objetivo objetivo = itemSnapshot.getValue(Objetivo.class);
                    objetivos.add(objetivo);
                }

                if (objetivos.isEmpty()) {
                    binding.textViewNaoPossuiDespesas.setVisibility(View.VISIBLE);
                }

                if (!objetivos.isEmpty())
                    binding.textViewNaoPossuiDespesas.setVisibility(View.INVISIBLE);

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