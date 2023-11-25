package com.salomaotech.autoatendimento.controller;

import com.salomaotech.autoatendimento.model.dto.LoginDTO;
import com.salomaotech.autoatendimento.model.dto.PagamentoDTO;
import com.salomaotech.autoatendimento.model.dto.PedidoDTO;
import com.salomaotech.autoatendimento.model.dto.ProdutoDTO;
import com.salomaotech.autoatendimento.model.entities.Produto;
import com.salomaotech.autoatendimento.model.repositories.PedidoRepository;
import com.salomaotech.autoatendimento.model.repositories.ProdutoRepository;
import com.salomaotech.autoatendimento.services.CarrinhoService;
import com.salomaotech.autoatendimento.services.LoginService;
import com.salomaotech.autoatendimento.services.PedidoService;
import com.salomaotech.autoatendimento.services.CatalogoService;
import com.salomaotech.autoatendimento.services.UploadService;
import com.salomaotech.autoatendimento.util.ConverteNumeroParaMoedaBr;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GetController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/")
    public ModelAndView home(HttpSession session) {

        ModelAndView view = new ModelAndView("pagina-catalogo");
        view.addObject("produtos", new CatalogoService(produtoRepository).findAll(session));
        return view;

    }

    @GetMapping("/pagina-novo-produto")
    public ModelAndView paginaNovoProduto() {

        ModelAndView view = new ModelAndView("pagina-novo-produto");
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setImagem("/images/produto-padrao.png");
        view.addObject("produto", produtoDTO);
        return view;

    }

    @GetMapping("/pagina-novo-produto/id/{id}")
    public ModelAndView paginaEditaProduto(@PathVariable(value = "id") long id) {

        ModelAndView view = new ModelAndView("pagina-novo-produto");
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {

            view.addObject("produto", new ProdutoDTO().toProdutoDTO(produto.get()));
            return view;

        } else {

            return new ModelAndView("redirect:/pagina-erro-404");

        }

    }

    @GetMapping("/pagina-delete-produto/produto/{id}")
    public ModelAndView paginaRemoveProduto(@PathVariable(value = "id") long id) {

        ModelAndView view = new ModelAndView("pagina-delete-produto");
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {

            view.addObject("produto", new ProdutoDTO().toProdutoDTO(produto.get()));
            return view;

        } else {

            return new ModelAndView("redirect:/pagina-erro-404");

        }

    }

    @GetMapping("/pagina-catalogo")
    public ModelAndView paginaExibeCatalogo(@RequestParam(name = "id", required = false, defaultValue = "0") long id, HttpSession session) {

        ModelAndView view = new ModelAndView("pagina-catalogo");
        view.addObject("id", id);

        if (id == 0) {

            view.addObject("produtos", new CatalogoService(produtoRepository).findAll(session));

        } else {

            view.addObject("produtos", new CatalogoService(produtoRepository).findById(id, session));

        }

        return view;

    }

    @GetMapping("/pagina-carrinho-compra")
    public ModelAndView paginaExibeCarrinhoCompra(HttpSession session) {

        CarrinhoService carrinhoService = new CarrinhoService(session);
        ModelAndView view = new ModelAndView("pagina-carrinho-compra");
        view.addObject("total", ConverteNumeroParaMoedaBr.converter(carrinhoService.valorTotal().toString()));
        view.addObject("produtos", carrinhoService.getProdutosCarrinhoAgrupado());
        return view;

    }

    @GetMapping("/pagina-checkout-inicio")
    public ModelAndView paginaExibeCheckoutInicio(HttpSession session) {

        ModelAndView view = new ModelAndView("pagina-checkout-inicio");
        view.addObject("pagamento", new PagamentoDTO(session));
        return view;

    }

    @GetMapping("/pagina-checkout-aprovado")
    public ModelAndView paginaExibeCheckoutAprovado() {

        return new ModelAndView("pagina-checkout-aprovado");

    }

    @GetMapping("/pagina-checkout-reprovado")
    public ModelAndView paginaExibeCheckoutReprovado() {

        return new ModelAndView("pagina-checkout-reprovado");

    }

    @GetMapping("/uploads/{arquivo:.+}")
    public ResponseEntity<Resource> carregarImagem(@PathVariable String arquivo) {

        return UploadService.resource(arquivo);

    }

    @GetMapping("/pagina-login")
    public ModelAndView paginaExibeLogin() {

        ModelAndView view = new ModelAndView("pagina-login");
        view.addObject("logindto", new LoginDTO());
        return view;

    }

    @GetMapping("/logout")
    public ModelAndView paginaExibeLout(HttpSession session) {

        new LoginService().logoutUser(session);
        ModelAndView view = new ModelAndView("pagina-login");
        view.addObject("logindto", new LoginDTO());
        return view;

    }

    @GetMapping("/pagina-erro-404")
    public ModelAndView paginaExibeErro404() {

        return new ModelAndView("pagina-erro-404");

    }

    @GetMapping("/pagina-pedidos")
    public ModelAndView paginaExibePedidos(@RequestParam(required = false, defaultValue = "0") long id) {

        List<PedidoDTO> pedidosDTO = new ArrayList();
        ModelAndView view = new ModelAndView("/pagina-pedidos");

        if (id == 0) {

            pedidosDTO = new PedidoService(pedidoRepository).getPedidos();

        } else {

            try {

                PedidoDTO pedidoDTO = new PedidoService(pedidoRepository).getPedido(id);

                if (!isNull(pedidoDTO)) {

                    pedidosDTO.add(pedidoDTO);

                }

            } catch (NumberFormatException ex) {

            }

        }

        view.addObject("pedidos", pedidosDTO);
        return view;

    }

    @GetMapping("/pagina-finaliza-pedido/id/{id}")
    public ModelAndView paginaFinalizaPedido(@PathVariable(value = "id") long id) {

        ModelAndView view = new ModelAndView("pagina-finaliza-pedido");
        PedidoDTO pedidoDTO = new PedidoService(pedidoRepository).getPedido(id);

        if (!isNull(pedidoDTO)) {

            view.addObject("pedido", pedidoDTO);
            return view;

        } else {

            return new ModelAndView("redirect:/pagina-erro-404");

        }

    }

    @GetMapping("/pagina-delete-pedido/id/{id}")
    public ModelAndView paginaRemovePedido(@PathVariable(value = "id") long id) {

        ModelAndView view = new ModelAndView("pagina-delete-pedido");
        PedidoDTO pedidoDTO = new PedidoService(pedidoRepository).getPedido(id);

        if (!isNull(pedidoDTO)) {

            view.addObject("pedido", pedidoDTO);
            return view;

        } else {

            return new ModelAndView("redirect:/pagina-erro-404");

        }

    }

}
