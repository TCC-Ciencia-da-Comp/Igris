package com.datamonki.igris.common.exception; 

import java.util.ArrayList;
import java.util.List;

//Classe que trata as excecoes de validacao de dados enviados para a api
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L; 

	private List<String> messages;

	public ValidationException(String message) {
		super(message); 
		this.messages = new ArrayList<>();
		this.messages.add(message);
	}

	public ValidationException(List<String> messages) {
		super(String.join(", ", messages));
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}
}