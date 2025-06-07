package com.datamonki.igris.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamonki.igris.academico.dto.CursoDto;
import com.datamonki.igris.academico.model.Curso;
import com.datamonki.igris.academico.service.CursoService;
import com.datamonki.igris.common.response.ApiResponse;

//Classe que representa o controller, responsavel pelas requisicoes de curso para a api
@RestController
@RequestMapping("/curso")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@PostMapping
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
	public ResponseEntity<ApiResponse<Curso>> create(@RequestBody CursoDto cursoDto){
		Curso curso = cursoService.create(cursoDto);

		ApiResponse<Curso> response = new ApiResponse.Builder<Curso>(HttpStatus.CREATED.value(), "Curso criado com sucesso")
			.data(curso)
			.build();
			
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
	public ResponseEntity<ApiResponse<Curso>> getById(@PathVariable Integer id){
		Curso curso = cursoService.getById(id);

		ApiResponse<Curso> response = new ApiResponse.Builder<Curso>(HttpStatus.OK.value(), "Curso encontrado com sucesso")
			.data(curso)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
	public ResponseEntity<ApiResponse<List<Curso>>> getAll(){ 
		List<Curso> cursos = cursoService.getAll();

		ApiResponse<List<Curso>> response = new ApiResponse.Builder<List<Curso>>(HttpStatus.OK.value(), "Cursos encontrados com sucesso")
			.data(cursos)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
	@GetMapping("/nome/{nome}")
	public ResponseEntity<ApiResponse<List<Curso>>> getByNome(@PathVariable String nome) {
		List<Curso> cursos = cursoService.getByNome(nome);

		ApiResponse<List<Curso>> response = new ApiResponse.Builder<List<Curso>>(HttpStatus.OK.value(), "Cursos encontrados com sucesso")
			.data(cursos)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
	@GetMapping("/nome/order")
	public ResponseEntity<ApiResponse<List<Curso>>> getByOrderNome() {
		List<Curso> cursos = cursoService.getByOrderNome();

		ApiResponse<List<Curso>> response = new ApiResponse.Builder<List<Curso>>(HttpStatus.OK.value(), "Cursos encontrados com sucesso")
			.data(cursos)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ACESSO_COORDENADOR')")
	public ResponseEntity<ApiResponse<Curso>> delete(@PathVariable Integer id){
		Curso curso = cursoService.deleteById(id);

		ApiResponse<Curso> response = new ApiResponse.Builder<Curso>(HttpStatus.OK.value(), "Curso deletado com sucesso")
			.data(curso)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
	public ResponseEntity<ApiResponse<List<Curso>>> delete(){
		List<Curso> cursos = cursoService.deleteAll();

		ApiResponse<List<Curso>> response = new ApiResponse.Builder<List<Curso>>(HttpStatus.OK.value(), "Cursos deletados com sucesso")
			.data(cursos)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
	public ResponseEntity<ApiResponse<Curso>> update(@PathVariable Integer id, @RequestBody CursoDto cursoDto){
		Curso curso = cursoService.update(id, cursoDto); 

		ApiResponse<Curso> response = new ApiResponse.Builder<Curso>(HttpStatus.OK.value(), "Curso atualizado com sucesso")
			.data(curso)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/instituicao/{idInstituicao}")
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
	public ResponseEntity<ApiResponse<List<Curso>>> getByIdInstituicao(@PathVariable Integer idInstituicao){ 
		List<Curso> cursos = cursoService.getByIdInstituicao(idInstituicao);

		ApiResponse<List<Curso>> response = new ApiResponse.Builder<List<Curso>>(HttpStatus.OK.value(), "Cursos encontrados com sucesso")
			.data(cursos)
			.build();
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}