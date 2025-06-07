package com.datamonki.igris.usuario.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.usuario.dto.UsuarioOverViewDto;
import com.datamonki.igris.usuario.model.PerfilEnum;
import com.datamonki.igris.usuario.repository.UsuarioRepository;
import com.datamonki.igris.usuario.service.ProfessorService;

@Service
public class ProfessorServiceImpl implements ProfessorService {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public List<UsuarioOverViewDto> getAllUsuariosProfessor(Integer idInstituicao){
		//Usuario usuarioLogado = AuthController.getAuthenticatedUser();	
		List <UsuarioOverViewDto> usuarios = usuarioRepository.findByPerfilNomeAndInstituicaoId(PerfilEnum.ACESSO_PROFESSOR.getNome(), idInstituicao).stream()
				.map(usuario->new UsuarioOverViewDto(usuario)).
				collect(Collectors.toList());
		
		return usuarios;
	}
	
}
