package com.datamonki.igris.usuario.service;

import java.util.List;

import com.datamonki.igris.usuario.dto.SenhaDto;
import com.datamonki.igris.usuario.dto.UsuarioDto;
import com.datamonki.igris.usuario.dto.UsuarioOverViewDto;
import com.datamonki.igris.usuario.dto.UsuarioTrocaSenhaDto;
import com.datamonki.igris.usuario.model.PerfilEnum;
import com.datamonki.igris.usuario.model.Usuario;

public interface UsuarioService {
	
	//Criar usuário
	Usuario save(UsuarioDto usuarioDto);
	
	//Criar usuário com perfil
	Usuario saveComPerfil(UsuarioDto usuarioDto, PerfilEnum nomePerfl, Integer idInstituicao);
	
	//Listar todos os usuários
	List<Usuario> getAll();
	
	//Listar todos os usuários simples
	List<UsuarioOverViewDto> getAllUsuariosSimples();
	
	//Listar todos os usuários coordenador
	List<UsuarioOverViewDto> getAllUsuariosCoordenador(Integer idInstituicao);
	
	//Atualizar senha
	Void updateSenha(UsuarioTrocaSenhaDto dto);
	
	//Esquecer senha
	Void esqueciSenha(String email);
	
	//Validar token
	Void validarToken(String token, SenhaDto senhaDto);
}
