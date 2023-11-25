package com.salomaotech.autoatendimento.controller;

import com.salomaotech.autoatendimento.model.entities.Produto;
import com.salomaotech.autoatendimento.model.repositories.ProdutoRepository;
import com.salomaotech.autoatendimento.services.CarrinhoService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostRestController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping("/addProdutoCarrinho")
    public String addProdutoCarrinho(@RequestParam("id") long id, HttpSession session) {

        List<Produto> carrinho = (List<Produto>) session.getAttribute("carrinho");

        if (carrinho == null) {

            carrinho = new ArrayList<>();

        }

        Optional<Produto> produtoOptional = produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {

            carrinho.add(produtoOptional.get());
            session.setAttribute("carrinho", carrinho);

        }

        return String.valueOf(new CarrinhoService(session).quantidadeItensById(id));

    }

    @PostMapping("/removeProdutoCarrinho")
    public String removeProdutoCarrinho(@RequestParam("id") long id, HttpSession session) {

        List<Produto> carrinho = (List<Produto>) session.getAttribute("carrinho");

        if (carrinho != null) {

            for (Produto produto : carrinho) {

                if (produto.getId() == id) {

                    carrinho.remove(produto);
                    break;

                }

            }

            session.setAttribute("carrinho", carrinho);

        }

        return String.valueOf(new CarrinhoService(session).quantidadeItensById(id));

    }

}
