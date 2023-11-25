package com.salomaotech.autoatendimento.services;

import com.salomaotech.autoatendimento.model.entities.Produto;
import com.salomaotech.autoatendimento.model.entities.PedidoItem;
import com.salomaotech.autoatendimento.model.entities.Pedido;
import com.salomaotech.autoatendimento.model.impressao.PedidoPdf;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.salomaotech.autoatendimento.model.repositories.PedidoRepository;

public class RegistraPagamentoService {

    private final PedidoRepository pedidoRepository;
    private final HttpSession session;
    private final String qrcode;

    public RegistraPagamentoService(PedidoRepository pedidoRepository, HttpSession session, String qrcode) {

        this.pedidoRepository = pedidoRepository;
        this.session = session;
        this.qrcode = qrcode;

    }

    public void registrar() {

        CarrinhoService carrinhoService = new CarrinhoService(session);
        List<PedidoItem> pedidoItemList = new ArrayList();
        Pedido pedido = new Pedido();

        /* itera os produtos da sessão obs: (estão no modo detached) */
        for (Produto produto : carrinhoService.getProdutosCarrinho()) {

            PedidoItem pedidoItem = new PedidoItem();
            pedidoItem.setNome(produto.getNome());
            pedidoItem.setDescricao(produto.getDescricao());
            pedidoItem.setValor(produto.getValor());
            pedidoItem.setPedido(pedido);
            pedidoItemList.add(pedidoItem);

        }

        pedido.setData(LocalDateTime.now());
        pedido.setValor(carrinhoService.valorTotal());
        pedido.setPedidoItemList(pedidoItemList);

        /* salva pedido, remove qr code antigo, limpa a sessão */
        pedidoRepository.save(pedido);
        UploadService.remove(qrcode);
        session.invalidate();

        /* salva o PDF do pedido */
        PedidoPdf pedidoPdf = new PedidoPdf(pedido);
        pedidoPdf.gerar();

    }

}
