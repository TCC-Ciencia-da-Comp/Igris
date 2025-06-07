package com.datamonki.igris.usuario.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.common.exception.ValidationException; 
import com.datamonki.igris.usuario.dto.PerfilDto; 
import com.datamonki.igris.usuario.model.Perfil;
import com.datamonki.igris.usuario.repository.PerfilRepository;
import com.datamonki.igris.usuario.service.PerfilService;

@Service
public class PerfilServiceImpl implements PerfilService { 
	@Autowired
	private PerfilRepository perfilRepository;
	
	private void verificarRole(PerfilDto perfilDto) {
		List<String> messages = new ArrayList<>();
		if (perfilDto.nome().isBlank()) {
			messages.add("O campo  name está vazio");
		}else if (perfilDto.descricao().isBlank()) {
			messages.add("O campo  descricao está vazio");
		}else if(perfilDto.nome().length() < 5) {
			messages.add("O nome precisa ter mais de 5 caracateres");
		}else if(perfilRepository.findByNome(perfilDto.nome()).isPresent()) {
			messages.add("Usuário  já cadastrado");
		}	
		if (!messages.isEmpty()) {
			throw new ValidationException(messages);
		}
	}
	
	@Override
	public Perfil create(PerfilDto perfilDto){
		verificarRole(perfilDto);
		Perfil role = new Perfil();
		role.setNome(perfilDto.nome());
		role.setDescricao(perfilDto.descricao());
		perfilRepository.save(role);
		return role;
	}
	 
	@Override
	public List<Perfil> getAll(){ 
		List<Perfil> roles = perfilRepository.findAll();
		return roles;
	}
	
}
