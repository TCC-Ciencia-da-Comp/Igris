package com.datamonki.igris.usuario.model;

import lombok.Getter;

@Getter
public enum PerfilEnum {
	ACESSO_ADMIN ("acessoAdmin"),
	ACESSO_COORDENADOR("acessoCoordenador"),
	ACESSO_PROFESSOR("acessoProfessor");
	
	private final String nome;
	
    // Construtor que define a descrição de cada valor
    PerfilEnum(String nome) {
        this.nome = nome;
    }

    
}
