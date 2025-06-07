package com.datamonki.igris.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.datamonki.igris.common.response.ApiResponse;  
import com.datamonki.igris.usuario.dto.PerfilDto;
import com.datamonki.igris.usuario.model.Perfil;
import com.datamonki.igris.usuario.service.PerfilService;

@Controller
@RequestMapping("/public/acessos")
public class PerfilController {

	@Autowired
	private PerfilService perfilService;

	@PostMapping
	public ResponseEntity<ApiResponse<Perfil>> save(@RequestBody PerfilDto perfilDto) {
		Perfil perfil = perfilService.create(perfilDto);
		ApiResponse<Perfil> response = new ApiResponse.Builder<Perfil>(HttpStatus.CREATED.value(), "Perfil criado com sucesso").data(perfil).build();

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<Perfil>>> getAll() {
		List<Perfil> perfis = perfilService.getAll();
		ApiResponse<List<Perfil>> response = new ApiResponse.Builder<List<Perfil>>(HttpStatus.OK.value(), "Perfis encontrados com sucesso").data(perfis).build();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}