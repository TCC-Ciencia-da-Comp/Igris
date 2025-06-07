package com.datamonki.igris.usuario.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.common.exception.ValidationException;
import com.datamonki.igris.instituicao.model.Instituicao;
import com.datamonki.igris.instituicao.repository.InstituicaoRepository;
import com.datamonki.igris.usuario.dto.UsuarioPerfilDto;
import com.datamonki.igris.usuario.model.Perfil;
import com.datamonki.igris.usuario.model.Usuario; 
import com.datamonki.igris.usuario.model.UsuarioPerfil;
import com.datamonki.igris.usuario.repository.PerfilRepository;
import com.datamonki.igris.usuario.repository.UsuarioPerfilRepository;
import com.datamonki.igris.usuario.repository.UsuarioRepository;
import com.datamonki.igris.usuario.service.UsuarioPerfilService;

import jakarta.transaction.Transactional;

@Service
public class UsuarioPerfilServiceImpl implements UsuarioPerfilService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private InstituicaoRepository instituicaoRepository;
	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;
	

	private void verificar(UsuarioPerfilDto dto) {
	    List<String> messages = new ArrayList<>();

	    if (dto.usuarioLogin() == null) {
	        messages.add("O campo email do usuário está vazio");
	    }

	    if (dto.perfilId() == null) {
	        messages.add("O campo role está vazio");
	    }
	    
	    if (dto.emailUsuario() == null) {
	        messages.add("O campo está está vazio");
	    }

	    if (dto.usuarioLogin() != null && usuarioRepository.findByLogin(dto.usuarioLogin()).isEmpty()) {
	        messages.add("Usuário não cadastrado");
	    }

	    if (dto.perfilId() != null && perfilRepository.findById(dto.perfilId()).isEmpty()) {
	        messages.add("Perfil não cadastrado");
	    }

	    if (dto.instituicaoId() == null || instituicaoRepository.findById(dto.instituicaoId()).isEmpty()) {
	        messages.add("Instituição não encontrada");
	    }

	    if (!messages.isEmpty()) {
	        throw new ValidationException(messages);
	    }
	}

	@Override
	@Transactional
	public UsuarioPerfil adicionarPerfilAoUsuario(UsuarioPerfilDto dto) { 
	    verificar(dto); // Verifica se os dados são válidos
	    
	    Usuario usuario = usuarioRepository.findByLogin(dto.usuarioLogin())
	        .orElseThrow(() -> new ValidationException(List.of("Usuário não encontrado")));
	    Perfil perfil = perfilRepository.findById(dto.perfilId())
	        .orElseThrow(() -> new ValidationException(List.of("Perfil não encontrado")));
	    Instituicao instituicao = instituicaoRepository.findById(dto.instituicaoId())
	        .orElseThrow(() -> new ValidationException(List.of("Instituição não encontrada")));

	    // Cria a relação na tabela intermediária 'usuario_perfil'
	    UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
	    usuarioPerfil.setUsuario(usuario);
	    usuarioPerfil.setPerfil(perfil);
	    usuarioPerfil.setInstituicao(instituicao);
	    usuarioPerfil.setEmailUsuario(dto.emailUsuario());
	    usuarioPerfil.setAtivo(true);
	    usuarioPerfilRepository.save(usuarioPerfil);

	    return usuarioPerfil;
	}


	//Uma maneira diferente de remover um elemento da tabela intermediária com spring
	@Override
	@Transactional
    public String removerRoleDoUsuario(UsuarioPerfilDto dto) {
		verificar(dto);
		Perfil perfil = perfilRepository.findById(dto.perfilId()).get();
		String perfilNome = perfil.getNome();
		
		UsuarioPerfil usuarioPerfil = usuarioPerfilRepository.findByUsuarioLoginAndPerfilNomeAndInstituicaoIdInstituicao(
			dto.usuarioLogin(), perfilNome, dto.instituicaoId())
			.orElseThrow(() -> new ValidationException(List.of("Relação usuário-perfil não encontrada")));
		
		usuarioPerfilRepository.delete(usuarioPerfil);
		return dto.usuarioLogin(); 
	}
	
	
}
