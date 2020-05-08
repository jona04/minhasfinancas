package com.cursofinancas.financas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursofinancas.financas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
