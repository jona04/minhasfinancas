package com.cursofinancas.financas.exception;

public class ErroDeAutenticacao extends RuntimeException {
	
	public ErroDeAutenticacao(String mensagem) {
		super(mensagem);
	}
}
