package com.salomaotech.autoatendimento.model.dto;

import com.salomaotech.autoatendimento.model.entities.Produto;
import java.math.BigDecimal;

public class ProdutoDTO {

    private long id;
    private String nome;
    private String descricao;
    private String valor;
    private String quantidade;
    private String quantidadeSelecionada;
    private String imagem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getQuantidadeSelecionada() {
        return quantidadeSelecionada;
    }

    public void setQuantidadeSelecionada(String quantidadeSelecionada) {
        this.quantidadeSelecionada = quantidadeSelecionada;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public BigDecimal getValorAsBigDecimal() {

        try {

            return new BigDecimal(valor.replace(",", "."));

        } catch (Exception ex) {

            return new BigDecimal(0);

        }

    }

    public BigDecimal getQuantidadeAsBigDecimal() {

        try {

            return new BigDecimal(quantidade.replace(",", "."));

        } catch (Exception ex) {

            return new BigDecimal(0);

        }

    }

    public ProdutoDTO toProdutoDTO(Produto produto) {

        this.setId(produto.getId());
        this.setNome(produto.getNome());
        this.setDescricao(produto.getDescricao());
        this.setValor(produto.getValor().toString());
        this.setQuantidade(produto.getQuantidade().toString());
        this.setQuantidadeSelecionada("0");
        this.setImagem(produto.getImagem());
        return this;

    }

    public Produto toProduto() {

        Produto produto = new Produto();
        produto.setId(getId());
        produto.setNome(getNome());
        produto.setDescricao(getDescricao());
        produto.setValor(getValorAsBigDecimal());
        produto.setQuantidade(getQuantidadeAsBigDecimal());
        produto.setImagem(getImagem());
        return produto;

    }

}
