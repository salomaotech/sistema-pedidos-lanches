package com.salomaotech.autoatendimento.controller;

import com.salomaotech.autoatendimento.model.pagamento.PixConsultaPagamento;
import com.salomaotech.autoatendimento.services.CarrinhoService;
import com.salomaotech.autoatendimento.services.RegistraPagamentoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.salomaotech.autoatendimento.model.repositories.PedidoRepository;

@RestController
public class GetRestController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/carregarNumeroProdutosCarrinho")
    public int carregarNumeroProdutosCarrinho(HttpSession session) {

        return new CarrinhoService(session).quantidadeItens();

    }

    @GetMapping("/carregarStatusPagamento")
    public String carregarStatusPagamento(@RequestParam("txid") String txid, @RequestParam("qrcode") String qrcode, HttpSession session) {

        String status;
        boolean isPago = PixConsultaPagamento.consultar(txid);

        if (isPago) {

            new RegistraPagamentoService(pedidoRepository, session, qrcode).registrar();

            status = "CONCLUIDA";

        } else {

            status = "Aguardando pagamento...";

        }

        return status;

    }

}
