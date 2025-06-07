package com.datamonki.igris.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.datamonki.igris.common.response.ApiResponse;
import com.datamonki.igris.usuario.dto.UsuarioDto;
import com.datamonki.igris.usuario.dto.UsuarioOverViewDto;
import com.datamonki.igris.usuario.dto.UsuarioTrocaSenhaDto;
import com.datamonki.igris.usuario.model.PerfilEnum;
import com.datamonki.igris.usuario.model.Usuario;
import com.datamonki.igris.usuario.service.UsuarioService; 

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<UsuarioOverViewDto>>> getAll() {
		List<UsuarioOverViewDto> usuarios = usuarioService.getAllUsuariosSimples();
		ApiResponse<List<UsuarioOverViewDto>> response = new ApiResponse.Builder<List<UsuarioOverViewDto>>(HttpStatus.OK.value(), "Usuários encontrados com sucesso").data(usuarios).build();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@PostMapping("/cadastrar-admin")
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
	public ResponseEntity<ApiResponse<Usuario>> saveAdmin(@RequestBody UsuarioDto usuarioDto) {
		Usuario usuario = usuarioService.saveComPerfil(usuarioDto, PerfilEnum.ACESSO_ADMIN, 1);
		ApiResponse<Usuario> response = new ApiResponse.Builder<Usuario>(HttpStatus.CREATED.value(), "Usuário criado com sucesso").data(usuario).build();
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@PostMapping("/trocar-senha")
	public ResponseEntity<ApiResponse<Void>> updateSeha(@RequestBody UsuarioTrocaSenhaDto dto) {
		usuarioService.updateSenha(dto);
		ApiResponse<Void> response = new ApiResponse.Builder<Void>(HttpStatus.OK.value(), "Senha atualizada com sucesso").build();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
