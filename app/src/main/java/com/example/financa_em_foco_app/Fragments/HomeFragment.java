package com.example.financa_em_foco_app.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.financa_em_foco_app.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        configuraTela();
        return binding.getRoot();
    }

    private void configuraTela() {
        mAuth = FirebaseAuth.getInstance();

        binding.buttonAdicionar.setOnClickListener(v -> mostrarModal());
    }

    private void mostrarModal() {
        DespesasDialogFragment despesasFragment = new DespesasDialogFragment();
        despesasFragment.show(getChildFragmentManager(), despesasFragment.getTag());
    }
}