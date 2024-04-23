package com.example.financa_em_foco_app.Models;

import java.util.Date;

public class Objetivo {
    public String id;
    public Date data;
    public String descricao;
    public double valorTotal;
    public String idUsuario;
    public double valorAtual;

    public Objetivo() {
    }

    public Objetivo(String id, Date data, String descricao, double valorTotal, String idUsuario) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.idUsuario = idUsuario;
    }
}
