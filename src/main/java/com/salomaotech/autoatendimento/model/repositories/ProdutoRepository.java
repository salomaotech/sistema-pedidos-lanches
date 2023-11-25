package com.salomaotech.autoatendimento.model.repositories;

import com.salomaotech.autoatendimento.model.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
