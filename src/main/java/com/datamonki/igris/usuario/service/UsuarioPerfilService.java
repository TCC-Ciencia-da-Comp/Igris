package com.datamonki.igris.usuario.service;

import com.datamonki.igris.usuario.dto.UsuarioPerfilDto;
import com.datamonki.igris.usuario.model.UsuarioPerfil;

import jakarta.transaction.Transactional;

public interface UsuarioPerfilService {
	
	//Adicionar perfil ao usuário
	@Transactional
	UsuarioPerfil adicionarPerfilAoUsuario(UsuarioPerfilDto dto);

	//Remover perfil do usuário
	@Transactional
    String removerRoleDoUsuario(UsuarioPerfilDto dto);
	
}
