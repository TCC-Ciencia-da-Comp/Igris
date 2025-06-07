package com.datamonki.igris.usuario.service;

import java.util.List;

import com.datamonki.igris.usuario.dto.PerfilDto;
import com.datamonki.igris.usuario.model.Perfil;

public interface PerfilService {
	
	//Criar perfil
	Perfil create(PerfilDto perfilDto);
	 
	//Listar todos os perfis
	List<Perfil> getAll();	
	
}
