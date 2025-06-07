package com.datamonki.igris.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datamonki.igris.academico.dto.DisponibilidadeDto;
import com.datamonki.igris.academico.dto.DisponibilidadeLimitadaDto;
import com.datamonki.igris.academico.model.Disponibilidade;
import com.datamonki.igris.academico.service.DisponibilidadeService;
import com.datamonki.igris.common.response.ApiResponse;
import com.datamonki.igris.security.CustomUserDetailService;
import com.datamonki.igris.usuario.dto.UsuarioSecurityDto;

@RestController
@RequestMapping("/disponibilidade")
public class DisponibilidadeController {
	
	@Autowired
	private DisponibilidadeService disponibilidadeService;
	
	//Faz a requisicao para criar uma disponibilidade
	@PostMapping("/coordenador")
	public ResponseEntity<ApiResponse<Disponibilidade>> createByCoordenador(@RequestBody DisponibilidadeDto disponibilidadeDto) {
		Disponibilidade disponibilidade = disponibilidadeService.createByCoordenador(disponibilidadeDto);

		ApiResponse<Disponibilidade> response = new ApiResponse.Builder<Disponibilidade>(HttpStatus.CREATED.value(), "Disponibilidade criada com sucesso")
			.data(disponibilidade)
			.build();
			
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Faz a requisicao para criar uma disponibilidade
	@PostMapping("/coordenador/lista")
	public ResponseEntity<ApiResponse<List<Disponibilidade>>> createAllByCoordenador(@RequestBody List<DisponibilidadeDto> disponibilidadesDto) {
		List<Disponibilidade> disponibilidades = disponibilidadeService.createAllByCoordenador(disponibilidadesDto);

		ApiResponse<List<Disponibilidade>> response = new ApiResponse.Builder<List<Disponibilidade>>(HttpStatus.CREATED.value(), "Disponibilidades criadas com sucesso")
			.data(disponibilidades)
			.build();
			
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Faz a requisicao para criar uma disponibilidade
	@PostMapping("/professor")
	public ResponseEntity<ApiResponse<Disponibilidade>> createByProfessor(@RequestBody DisponibilidadeLimitadaDto disponibilidadeDto) {
		Disponibilidade disponibilidade = disponibilidadeService.createByProfessor(disponibilidadeDto);

		ApiResponse<Disponibilidade> response = new ApiResponse.Builder<Disponibilidade>(HttpStatus.CREATED.value(), "Disponibilidade criada com sucesso")
			.data(disponibilidade)
			.build();
			
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Faz a requisicao para criar uma disponibilidade
	@PostMapping("/professor/lista")
	public ResponseEntity<ApiResponse<List<Disponibilidade>>> createAllByProfessor(@RequestBody List<DisponibilidadeLimitadaDto> disponibilidadesDto) {
		List<Disponibilidade> disponibilidades = disponibilidadeService.createAllByProfessor(disponibilidadesDto);

		ApiResponse<List<Disponibilidade>> response = new ApiResponse.Builder<List<Disponibilidade>>(HttpStatus.CREATED.value(), "Disponibilidades criadas com sucesso")
			.data(disponibilidades)
			.build();
			
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	//Get de todas as disponbilidades cadastradas 	
	@GetMapping ("professor/{semestre}/{ano}")
	public ResponseEntity<ApiResponse<List<Disponibilidade>>> getAllByIdProfessorLogadoAnoSemestre(
			 @PathVariable Integer semestre, @PathVariable Integer ano) {
		UsuarioSecurityDto usuarioLogado = CustomUserDetailService.getAuthenticatedUser();
		List<Disponibilidade> disponibilidades = disponibilidadeService.getAllByIdProfessorAnoSemestreInstituicao(usuarioLogado.getId() ,semestre, ano, usuarioLogado.getIdInstituicao()); 

		ApiResponse<List<Disponibilidade>> response = new ApiResponse.Builder<List<Disponibilidade>>(HttpStatus.OK.value(), "Disponibilidades encontradas com sucesso")
			.data(disponibilidades)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
		
	//Get de todas as disponbilidades cadastradas 	
	@GetMapping ("coordenador/{idProfessor}/{semestre}/{ano}")
	public ResponseEntity<ApiResponse<List<Disponibilidade>>> getByIdProfessorAnoSemestre(
			 @PathVariable Integer idProfessor,@PathVariable Integer semestre, @PathVariable Integer ano) {
		UsuarioSecurityDto usuarioLogado = CustomUserDetailService.getAuthenticatedUser();
		List<Disponibilidade> disponibilidades = disponibilidadeService.getAllByIdProfessorAnoSemestreInstituicao(idProfessor,semestre, ano,usuarioLogado.getIdInstituicao());

		ApiResponse<List<Disponibilidade>> response = new ApiResponse.Builder<List<Disponibilidade>>(HttpStatus.OK.value(), "Disponibilidades encontradas com sucesso")
			.data(disponibilidades)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	//Get de todas as disponbilidades cadastradas 	
	@GetMapping ("coordenador/{semestre}/{ano}")
	public ResponseEntity<ApiResponse<List<Disponibilidade>>> getAllByAnoSemestreInstituicao(
			 @PathVariable Integer semestre, @PathVariable Integer ano) {
		UsuarioSecurityDto usuarioLogado = CustomUserDetailService.getAuthenticatedUser();
		List<Disponibilidade> disponibilidades = disponibilidadeService.getAllByAnoSemestreInstituicao(semestre, ano,usuarioLogado.getIdInstituicao());

		ApiResponse<List<Disponibilidade>> response = new ApiResponse.Builder<List<Disponibilidade>>(HttpStatus.OK.value(), "Disponibilidades encontradas com sucesso")
			.data(disponibilidades)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	//Get de todas as disponbilidades cadastradas 	
	@GetMapping 
	public ResponseEntity<ApiResponse<List<Disponibilidade>>> getAll() {
		List<Disponibilidade> disponibilidades = disponibilidadeService.getAll(); 

		ApiResponse<List<Disponibilidade>> response = new ApiResponse.Builder<List<Disponibilidade>>(HttpStatus.OK.value(), "Disponibilidades encontradas com sucesso")
			.data(disponibilidades)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/professor/{idProfessor}")
	public ResponseEntity<ApiResponse<List<Disponibilidade>>> getByidProfessor(@PathVariable Integer idProfessor) {
		List<Disponibilidade> disponibilidades = disponibilidadeService.getByIdProfessor(idProfessor);

		ApiResponse<List<Disponibilidade>> response = new ApiResponse.Builder<List<Disponibilidade>>(HttpStatus.OK.value(), "Disponibilidades encontradas com sucesso")
			.data(disponibilidades)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/professor/{idProfessor}")	
	public ResponseEntity<ApiResponse<List<Disponibilidade>>> deleteByIdProfessor(@PathVariable Integer idProfessor) {
		List<Disponibilidade> disponibilidades = disponibilidadeService.deleteByIdProfessor(idProfessor);

		ApiResponse<List<Disponibilidade>> response = new ApiResponse.Builder<List<Disponibilidade>>(HttpStatus.OK.value(), "Disponibilidades deletadas com sucesso")
			.data(disponibilidades)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/professor")	
	public ResponseEntity<ApiResponse<List<Disponibilidade>>> deleteByIdProfessorAnoSemestre(
			@RequestParam("idProfessor") Integer idProfessor,
			@RequestParam("ano") Integer ano, 
			@RequestParam("semestre") Integer semestre) {
		List<Disponibilidade> disponibilidades = disponibilidadeService.deleteByIdProfessorAnoSemestre(idProfessor, semestre, ano);

		ApiResponse<List<Disponibilidade>> response = new ApiResponse.Builder<List<Disponibilidade>>(HttpStatus.OK.value(), "Disponibilidades deletadas com sucesso")
			.data(disponibilidades)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse<List<Disponibilidade>>> deleteAll() { 
		List<Disponibilidade> disponibilidades = disponibilidadeService.deleteAll();

		ApiResponse<List<Disponibilidade>> response = new ApiResponse.Builder<List<Disponibilidade>>(HttpStatus.OK.value(), "Disponibilidades deletadas com sucesso")
			.data(disponibilidades)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}