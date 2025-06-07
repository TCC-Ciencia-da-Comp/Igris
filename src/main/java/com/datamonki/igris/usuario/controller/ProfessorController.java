package com.datamonki.igris.usuario.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamonki.igris.common.response.ApiResponse;
import com.datamonki.igris.security.CustomUserDetailService;
import com.datamonki.igris.usuario.dto.UsuarioDto;
import com.datamonki.igris.usuario.dto.UsuarioOverViewDto;
import com.datamonki.igris.usuario.dto.UsuarioSecurityDto;
import com.datamonki.igris.usuario.model.PerfilEnum;
import com.datamonki.igris.usuario.model.Usuario;
import com.datamonki.igris.usuario.service.ProfessorService;
import com.datamonki.igris.usuario.service.UsuarioService;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private UsuarioService usuarioService;
	
    private static final Logger logger = LoggerFactory.getLogger(ProfessorController.class);
	
	private UsuarioSecurityDto usuarioLogado() {
        return CustomUserDetailService.getAuthenticatedUser();
    }
	
	@GetMapping("/{idInstituicao}")
	@PreAuthorize("hasRole('ACESSO_ADMIN')")
	public ResponseEntity<ApiResponse<List<UsuarioOverViewDto>>> getAllProfessorForAnyInstitution(@PathVariable Integer idInstituicao){
		List<UsuarioOverViewDto> usuarios = professorService.getAllUsuariosProfessor(idInstituicao);
		ApiResponse<List<UsuarioOverViewDto>> response = new ApiResponse.Builder<List<UsuarioOverViewDto>>(HttpStatus.OK.value(), "Usuários encontrados com sucesso").data(usuarios).build();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@PreAuthorize("hasRole('ACESSO_ADMIN') or hasRole('ACESSO_COORDENADOR')")
	@GetMapping
	public ResponseEntity<ApiResponse<List<UsuarioOverViewDto>>> getAllProfessorForOwnInstitution(){
		UsuarioSecurityDto usuario = usuarioLogado();
		logger.info(usuario.getPerfilAtivo());
		List<UsuarioOverViewDto> usuarios = professorService.getAllUsuariosProfessor(usuario.getIdInstituicao());
		ApiResponse<List<UsuarioOverViewDto>> response = new ApiResponse.Builder<List<UsuarioOverViewDto>>(HttpStatus.OK.value(), "Usuários encontrados com sucesso").data(usuarios).build();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	

	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
	@PostMapping
	public ResponseEntity<ApiResponse<Usuario>> saveProfessorForOwnInstitution(@RequestBody UsuarioDto usuarioDto){
		UsuarioSecurityDto usuarioLogado = usuarioLogado();
		
		Usuario usuario = usuarioService.saveComPerfil(usuarioDto, PerfilEnum.ACESSO_PROFESSOR, usuarioLogado.getIdInstituicao());
		ApiResponse<Usuario> response = new ApiResponse.Builder<Usuario>(HttpStatus.CREATED.value(), "Usuário criado com sucesso").data(usuario).build();

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	
}
