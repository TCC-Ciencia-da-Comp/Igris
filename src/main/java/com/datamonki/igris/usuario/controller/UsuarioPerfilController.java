package com.datamonki.igris.usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datamonki.igris.common.response.ApiResponse;
import com.datamonki.igris.usuario.dto.UsuarioPerfilDto;
import com.datamonki.igris.usuario.model.UsuarioPerfil;
import com.datamonki.igris.usuario.service.UsuarioPerfilService;

@Controller
@RequestMapping("/public/dar-acesso")
public class UsuarioPerfilController {

	@Autowired
	private UsuarioPerfilService usuarioPerfilService;

	@PostMapping
	public ResponseEntity<ApiResponse<UsuarioPerfil>> save(@RequestBody UsuarioPerfilDto dto) {
		UsuarioPerfil usuarioPerfil = usuarioPerfilService.adicionarPerfilAoUsuario(dto);

		ApiResponse<UsuarioPerfil> response = new ApiResponse.Builder<UsuarioPerfil>(HttpStatus.CREATED.value(), "Acesso criado com sucesso").data(usuarioPerfil).build();
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse<String>> delete(
			@RequestParam("usuarioEmail") String usuarioEmail,
			@RequestParam("idRole") Integer idRole,
			@RequestParam("idInstituicao") Integer idInstituicao,
			@RequestParam("emailUsuario") String emailUsuario){
		UsuarioPerfilDto dto = new UsuarioPerfilDto(usuarioEmail, idRole, idInstituicao, emailUsuario);
		String resultado = usuarioPerfilService.removerRoleDoUsuario(dto);

		ApiResponse<String> response = new ApiResponse.Builder<String>(HttpStatus.OK.value(), "Acesso removido com sucesso").data(resultado).build();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}