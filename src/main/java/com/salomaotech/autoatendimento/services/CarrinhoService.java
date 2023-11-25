package com.salomaotech.autoatendimento.services;

import com.salomaotech.autoatendimento.model.entities.Produto;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CarrinhoService {

    private List<Produto> produtosCarrinho;

    public CarrinhoService(HttpSession session) {

        /* captura os produtos da sessão */
        produtosCarrinho = (List<Produto>) session.getAttribute("carrinho");

        if (produtosCarrinho == null) {

            produtosCarrinho = new ArrayList<>();

        }

    }

    public int quantidadeItens() {

        return produtosCarrinho.size();

    }

    public int quantidadeItensById(long id) {

        int quantidade = 0;

        for (Produto produto : produtosCarrinho) {

            if (produto.getId() == id) {

                quantidade++;

            }

        }

        return quantidade;

    }

    public BigDecimal valorTotal() {

        BigDecimal total = new BigDecimal(0);

        for (Produto produto : produtosCarrinho) {

            total = total.add(produto.getValor());

        }

        return total;

    }

    public List<Produto> getProdutosCarrinhoAgrupado() {

        Map<Long, Produto> produtosMap = new LinkedHashMap<>();

        for (Produto produto : produtosCarrinho) {

            BigDecimal quantidade = new BigDecimal(1);

            if (produtosMap.containsKey(produto.getId())) {

                quantidade = quantidade.add(produtosMap.get(produto.getId()).getQuantidade());

            }

            Produto produtoCopia = new Produto();
            produtoCopia.setId(produto.getId());
            produtoCopia.setNome(produto.getNome());
            produtoCopia.setDescricao(produto.getDescricao());
            produtoCopia.setValor(produto.getValor().multiply(quantidade));
            produtoCopia.setQuantidade(quantidade);
            produtoCopia.setImagem(produto.getImagem());

            /* adiciona a cópia do produto, porém atualizada */
            produtosMap.put(produto.getId(), produtoCopia);

        }

        return new ArrayList<>(produtosMap.values());

    }

    public List<Produto> getProdutosCarrinho() {
        return produtosCarrinho;
    }

}
