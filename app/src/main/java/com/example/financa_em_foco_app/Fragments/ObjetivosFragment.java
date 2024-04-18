package com.example.financa_em_foco_app.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.financa_em_foco_app.databinding.FragmentObjetivosBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ObjetivosFragment extends Fragment {
    private FragmentObjetivosBinding binding;
    private FirebaseAuth mAuth;
    private ObjetivosDialogFragment objetivosFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentObjetivosBinding.inflate(inflater, container, false);
        configuraTela();
        return binding.getRoot();
    }

    private void configuraTela() {
        mAuth = FirebaseAuth.getInstance();
        binding.buttonAdicionarObjetivo.setOnClickListener(v -> mostrarModal());
    }

    private void mostrarModal() {
        if (objetivosFragment == null || !objetivosFragment.isVisible()) {
            objetivosFragment = new ObjetivosDialogFragment();
            objetivosFragment.show(getChildFragmentManager(), objetivosFragment.getTag());
        }
    }


}