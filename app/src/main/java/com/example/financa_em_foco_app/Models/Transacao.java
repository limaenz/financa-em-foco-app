package com.example.financa_em_foco_app.Models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Transacao {
    public String id;
    public Date data;
    public String descricao;
    public double valor;
    public String tipo;

    public Transacao() {
    }

    public Transacao(
            String id,
            Date data,
            String descricao,
            double valor,
            String tipo
    ) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
    }
}
