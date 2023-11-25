package com.salomaotech.autoatendimento.model.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class PedidoDTO {

    private long id;
    private String data;
    private String valor;
    private Map<String, Integer> itens = new LinkedHashMap();
    private boolean finalizado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Map<String, Integer> getItens() {
        return itens;
    }

    public void setItens(Map<String, Integer> itens) {
        this.itens = itens;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

}
