package com.datamonki.igris.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.datamonki.igris.academico.dto.DisciplinaCursoDto;
import com.datamonki.igris.academico.model.DisciplinaCurso;
import com.datamonki.igris.academico.service.DisciplinaCursoService;
import com.datamonki.igris.common.response.ApiResponse;

@RestController
@RequestMapping("/disciplina-curso")
public class DisciplinaCursoController {
    
    @Autowired
    private DisciplinaCursoService disciplinaCursoService;
    
    @PostMapping
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<DisciplinaCurso>> create(@RequestBody DisciplinaCursoDto disciplinaCursoDto) {
        DisciplinaCurso disciplinaCurso = disciplinaCursoService.create(disciplinaCursoDto);

        ApiResponse<DisciplinaCurso> response = new ApiResponse.Builder<DisciplinaCurso>(HttpStatus.CREATED.value(), "Disciplina-Curso criada com sucesso")
            .data(disciplinaCurso)
            .build();
            
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DisciplinaCurso>> getById(@PathVariable Integer id) {
        DisciplinaCurso disciplinaCurso = disciplinaCursoService.getById(id);

        ApiResponse<DisciplinaCurso> response = new ApiResponse.Builder<DisciplinaCurso>(HttpStatus.OK.value(), "Disciplina-Curso encontrada com sucesso")
            .data(disciplinaCurso)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<DisciplinaCurso>>> getAll() {
        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoService.getAll();

        ApiResponse<List<DisciplinaCurso>> response = new ApiResponse.Builder<List<DisciplinaCurso>>(HttpStatus.OK.value(), "Disciplina-Cursos encontradas com sucesso")
            .data(disciplinaCursos)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping("/disciplina/{id}")
    public ResponseEntity<ApiResponse<List<DisciplinaCurso>>> getByIdDisciplina(@PathVariable Integer id) {
        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoService.getByIdDisciplina(id);

        ApiResponse<List<DisciplinaCurso>> response = new ApiResponse.Builder<List<DisciplinaCurso>>(HttpStatus.OK.value(), "Disciplina-Cursos encontradas com sucesso")
            .data(disciplinaCursos)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping("/curso/{id}")
    public ResponseEntity<ApiResponse<List<DisciplinaCurso>>> getByIdCurso(@PathVariable Integer id) {
        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoService.getByIdCurso(id);

        ApiResponse<List<DisciplinaCurso>> response = new ApiResponse.Builder<List<DisciplinaCurso>>(HttpStatus.OK.value(), "Disciplina-Cursos encontradas com sucesso")
            .data(disciplinaCursos)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<DisciplinaCurso>> delete(@PathVariable Integer id) {
        DisciplinaCurso disciplinaCurso = disciplinaCursoService.deleteById(id);

        ApiResponse<DisciplinaCurso> response = new ApiResponse.Builder<DisciplinaCurso>(HttpStatus.OK.value(), "Disciplina-Curso deletada com sucesso")
            .data(disciplinaCurso)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<List<DisciplinaCurso>>> delete() {
        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoService.deleteAll();

        ApiResponse<List<DisciplinaCurso>> response = new ApiResponse.Builder<List<DisciplinaCurso>>(HttpStatus.OK.value(), "Disciplina-Cursos deletadas com sucesso")
            .data(disciplinaCursos)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<DisciplinaCurso>> update(@PathVariable Integer id, @RequestBody DisciplinaCursoDto disciplinaCursoDto) {
        DisciplinaCurso disciplinaCurso = disciplinaCursoService.update(id, disciplinaCursoDto);

        ApiResponse<DisciplinaCurso> response = new ApiResponse.Builder<DisciplinaCurso>(HttpStatus.OK.value(), "Disciplina-Curso atualizada com sucesso")
            .data(disciplinaCurso)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping("/curso/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<List<DisciplinaCurso>>> deleteByCursoId(@PathVariable Integer id) {
        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoService.deleteByCursoId(id);

        ApiResponse<List<DisciplinaCurso>> response = new ApiResponse.Builder<List<DisciplinaCurso>>(HttpStatus.OK.value(), "Disciplina-Cursos deletadas com sucesso")
            .data(disciplinaCursos)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping("/disciplina/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<List<DisciplinaCurso>>> deleteByDisciplinaId(@PathVariable Integer id) {
        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoService.deleteByDisciplinaId(id);

        ApiResponse<List<DisciplinaCurso>> response = new ApiResponse.Builder<List<DisciplinaCurso>>(HttpStatus.OK.value(), "Disciplina-Cursos deletadas com sucesso")
            .data(disciplinaCursos)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}