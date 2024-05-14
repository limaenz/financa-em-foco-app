package com.example.financa_em_foco_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financa_em_foco_app.Adapters.DespesaAdapter;
import com.example.financa_em_foco_app.Fragments.Dialogs.DespesasDialogFragment;
import com.example.financa_em_foco_app.Models.Despesa;
import com.example.financa_em_foco_app.databinding.ActivityVerMaisDespesasBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VerMaisDespesasActivity extends AppCompatActivity {
    private ActivityVerMaisDespesasBinding binding;
    private FirebaseAuth mAuth;
    private DespesasDialogFragment despesasFragment;
    private RecyclerView recyclerView;
    private List<Despesa> transacoes = new ArrayList<>();
    private DespesaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerMaisDespesasBinding.inflate((getLayoutInflater()));
        configuraTela();
        setContentView(binding.getRoot());
    }

    private void configuraTela() {
        mAuth = FirebaseAuth.getInstance();
        recyclerView = binding.recyclerViewObjetivos;
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new DespesaAdapter(getApplicationContext(), transacoes);
        recyclerView.setAdapter(adapter);
        binding.backButton.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        carregarTransacoes();
    }

    private void carregarTransacoes() {
        DespesaAdapter adapter = new DespesaAdapter(getApplicationContext(), transacoes);
        binding.recyclerViewObjetivos.setAdapter(adapter);

        DatabaseReference despesasRef = FirebaseDatabase.getInstance().getReference().child("Despesas");
        despesasRef.orderByChild("idUsuario").equalTo(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transacoes.clear();

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Despesa transacao = itemSnapshot.getValue(Despesa.class);
                    transacoes.add(transacao);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}