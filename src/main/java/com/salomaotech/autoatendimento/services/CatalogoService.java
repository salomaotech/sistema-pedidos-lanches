package com.salomaotech.autoatendimento.services;

import com.salomaotech.autoatendimento.model.dto.ProdutoDTO;
import com.salomaotech.autoatendimento.model.entities.Produto;
import com.salomaotech.autoatendimento.model.repositories.ProdutoRepository;
import com.salomaotech.autoatendimento.util.ConverteNumeroParaMoedaBr;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CatalogoService {

    private final ProdutoRepository produtoRepository;

    public CatalogoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoDTO> findAll(HttpSession session) {

        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoDTO> produtosDTO = new ArrayList<>();

        for (Produto produto : produtos) {

            produtosDTO.add(popularDTO(produto, session));

        }

        return produtosDTO;

    }

    public ProdutoDTO findById(long id, HttpSession session) {

        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {

            return popularDTO(produto.get(), session);

        } else {

            return new ProdutoDTO();

        }

    }

    private ProdutoDTO popularDTO(Produto produto, HttpSession session) {

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setDescricao(produto.getDescricao());
        produtoDTO.setValor(ConverteNumeroParaMoedaBr.converter(produto.getValor().toString()));
        produtoDTO.setQuantidade(produto.getQuantidade().toString());
        produtoDTO.setQuantidadeSelecionada(String.valueOf(new CarrinhoService(session).quantidadeItensById(produto.getId())));
        produtoDTO.setImagem(produto.getImagem());
        return produtoDTO;

    }

}
