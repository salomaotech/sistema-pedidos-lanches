package com.salomaotech.autoatendimento.controller;

import com.salomaotech.autoatendimento.model.dto.LoginDTO;
import com.salomaotech.autoatendimento.model.dto.PedidoDTO;
import com.salomaotech.autoatendimento.model.dto.ProdutoDTO;
import com.salomaotech.autoatendimento.model.entities.Pedido;
import com.salomaotech.autoatendimento.model.entities.Produto;
import com.salomaotech.autoatendimento.model.repositories.PedidoRepository;
import com.salomaotech.autoatendimento.model.repositories.ProdutoRepository;
import com.salomaotech.autoatendimento.services.LoginService;
import com.salomaotech.autoatendimento.services.PedidoService;
import com.salomaotech.autoatendimento.services.UploadService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping("/addProduto")
    public ModelAndView addProduto(ProdutoDTO produtoDTO, MultipartFile file) {

        if (!file.isEmpty()) {

            UploadService.remove(produtoDTO.getImagem());
            produtoDTO.setImagem(UploadService.storage(file));

        }

        Produto produto = produtoDTO.toProduto();
        produtoRepository.save(produto);
        return new ModelAndView("redirect:/pagina-novo-produto/id/" + produto.getId());

    }

    @PostMapping("/deleteProduto")
    public ModelAndView deleteProduto(ProdutoDTO produtoDTO) {

        Optional<Produto> produto = produtoRepository.findById(produtoDTO.getId());

        if (produto.isPresent()) {

            UploadService.remove(produto.get().getImagem());
            produtoRepository.delete(produto.get());
            return new ModelAndView("redirect:/pagina-novo-produto");

        } else {

            return new ModelAndView("redirect:/pagina-erro-404");

        }

    }

    @PostMapping("/loginSistema")
    public ModelAndView loginSistema(LoginDTO loginDTO, HttpSession session) {

        LoginService loginService = new LoginService();

        if (loginService.authenticateUser(loginDTO, session)) {

            return new ModelAndView("redirect:/");

        } else {

            return new ModelAndView("redirect:/pagina-login");

        }

    }

    @PostMapping("/finalizaPedido")
    public ModelAndView finalizaPedido(@RequestParam("id") Long id) {

        if (new PedidoService(pedidoRepository).finalizaPedido(id)) {

            return new ModelAndView("redirect:/pagina-pedidos");

        } else {

            return new ModelAndView("redirect:/pagina-erro-404");

        }

    }

    @PostMapping("/deletePedido")
    public ModelAndView deletePedido(PedidoDTO pedidoDTO) {

        Optional<Pedido> pedido = pedidoRepository.findById(pedidoDTO.getId());

        if (pedido.isPresent()) {

            pedidoRepository.delete(pedido.get());
            return new ModelAndView("redirect:/pagina-pedidos");

        } else {

            return new ModelAndView("redirect:/pagina-erro-404");

        }

    }

}
