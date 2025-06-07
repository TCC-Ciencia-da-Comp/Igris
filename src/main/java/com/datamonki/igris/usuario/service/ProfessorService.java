package com.datamonki.igris.usuario.service;

import java.util.List;

import com.datamonki.igris.usuario.dto.UsuarioOverViewDto;

public interface ProfessorService {
	
	//Listar todos os professores
	List<UsuarioOverViewDto> getAllUsuariosProfessor(Integer idInstituicao);

}
