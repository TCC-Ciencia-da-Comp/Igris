package com.datamonki.igris.common.exception;

//Classe que trata as excecoes de id não encontrado no banco de dados
public class IdNotFoundException extends RuntimeException {
	
	public IdNotFoundException(String message) { 
		super(message);
	}

	public IdNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}