package com.example.financa_em_foco_app.Models;

import java.util.Date;

public class Objetivo {
    public String id;
    public Date data;
    public String descricao;
    public double valor;
    public String idUsuario;

    public Objetivo() {
    }

    public Objetivo(String id, Date data, String descricao, double valor, String idUsuario) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.idUsuario = idUsuario;
    }
}
