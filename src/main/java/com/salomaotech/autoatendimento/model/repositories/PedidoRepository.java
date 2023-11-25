package com.salomaotech.autoatendimento.model.repositories;

import com.salomaotech.autoatendimento.model.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
