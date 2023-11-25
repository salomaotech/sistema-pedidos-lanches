package com.salomaotech.autoatendimento.services;

import com.salomaotech.autoatendimento.model.dto.PedidoDTO;
import com.salomaotech.autoatendimento.model.entities.Pedido;
import com.salomaotech.autoatendimento.model.entities.PedidoItem;
import com.salomaotech.autoatendimento.model.repositories.PedidoRepository;
import com.salomaotech.autoatendimento.util.AgrupaList;
import com.salomaotech.autoatendimento.util.ConverteNumeroParaMoedaBr;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoDTO> getPedidos() {

        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoDTO> pedidosDTO = new ArrayList();

        for (Pedido pedido : pedidos) {

            pedidosDTO.add(popularDTO(pedido));

        }

        return pedidosDTO;

    }

    public PedidoDTO getPedido(long id) {

        Optional<Pedido> pedido = pedidoRepository.findById(id);
        PedidoDTO pedidoDTO;

        if (pedido.isPresent()) {

            pedidoDTO = popularDTO(pedido.get());

        } else {

            pedidoDTO = null;

        }

        return pedidoDTO;

    }

    public boolean finalizaPedido(long id) {

        Optional<Pedido> pedido = pedidoRepository.findById(id);

        if (pedido.isPresent()) {

            pedido.get().setFinalizado(true);
            pedidoRepository.save(pedido.get());
            return true;

        } else {

            return false;

        }

    }

    private PedidoDTO popularDTO(Pedido pedido) {

        /* formato da data */
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        /* lista os itens do pedido */
        List<String> itens = new ArrayList();

        for (PedidoItem pedidoItem : pedido.getPedidoItemList()) {

            itens.add(pedidoItem.getNome());

        }

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setData(pedido.getData().format(formato));
        pedidoDTO.setValor(ConverteNumeroParaMoedaBr.converter(pedido.getValor().toString()));
        pedidoDTO.setItens(AgrupaList.agrupar(itens));
        pedidoDTO.setFinalizado(pedido.isFinalizado());
        return pedidoDTO;

    }

}
