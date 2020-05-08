package com.cursofinancas.financas.service;

import java.util.List;

import com.cursofinancas.financas.model.entity.Lancamento;
import com.cursofinancas.financas.model.enums.StatusLancamento;

public interface LancamentoService {

	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento atualizar(Lancamento lancamento);
	
	void deletar(Lancamento lancamento);
	
	List<Lancamento> buscar( Lancamento lancamentoFiltro );
	
	void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	
	void validar(Lancamento lancamento);
	
}
