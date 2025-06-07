package com.datamonki.igris.usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamonki.igris.common.response.ApiResponse;
import com.datamonki.igris.usuario.dto.UsuarioDto;
import com.datamonki.igris.usuario.model.Usuario;
import com.datamonki.igris.usuario.service.UsuarioService;

@RestController
@RequestMapping("/public/usuarios")
public class PublicUsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	
	@PostMapping
	public ResponseEntity<ApiResponse<Usuario>> save(@RequestBody UsuarioDto usuarioDto) {
		Usuario usuario = usuarioService.save(usuarioDto);
		ApiResponse<Usuario> response = new ApiResponse.Builder<Usuario>(HttpStatus.CREATED.value(), "Usuário criado com sucesso").data(usuario).build();

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}