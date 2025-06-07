package com.datamonki.igris.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datamonki.igris.academico.dto.TurmaDto;
import com.datamonki.igris.academico.model.Turma;
import com.datamonki.igris.academico.service.TurmaService;
import com.datamonki.igris.common.response.ApiResponse;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;
    
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Turma>> create(@RequestBody TurmaDto turmaDto) {
        Turma turma = turmaService.create(turmaDto);
        ApiResponse<Turma> response = new ApiResponse.Builder<Turma>(HttpStatus.CREATED.value(), "Turma criada com sucesso")
            .data(turma)
            .build();
            
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Turma>> getById(@PathVariable Integer id) {
        Turma turma = turmaService.getById(id); 
        ApiResponse<Turma> response = new ApiResponse.Builder<Turma>(HttpStatus.OK.value(), "Turma encontrada com sucesso")
            .data(turma)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Turma>>> getAll() {
        List<Turma> turmas = turmaService.getAll();

        ApiResponse<List<Turma>> response = new ApiResponse.Builder<List<Turma>>(HttpStatus.OK.value(), "Turmas encontradas com sucesso")
            .data(turmas)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ApiResponse<List<Turma>>> getByNome(@PathVariable String nome) {
        List<Turma> turmas = turmaService.getByNome(nome);

        ApiResponse<List<Turma>> response = new ApiResponse.Builder<List<Turma>>(HttpStatus.OK.value(), "Turmas encontradas com sucesso")
            .data(turmas)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<Turma>> delete(@PathVariable Integer id) {
        Turma turma = turmaService.deleteById(id);

        ApiResponse<Turma> response = new ApiResponse.Builder<Turma>(HttpStatus.OK.value(), "Turma deletada com sucesso")
            .data(turma)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<List<Turma>>> delete() {
        List<Turma> turmas = turmaService.deleteAll();

        ApiResponse<List<Turma>> response = new ApiResponse.Builder<List<Turma>>(HttpStatus.OK.value(), "Turmas deletadas com sucesso")
            .data(turmas)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Turma>> update(@PathVariable Integer id, @RequestBody TurmaDto turmaDto) {
        Turma turma = turmaService.update(id, turmaDto);

        ApiResponse<Turma> response = new ApiResponse.Builder<Turma>(HttpStatus.OK.value(), "Turma atualizada com sucesso")
            .data(turma)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping("/curso/{id}")
    public ResponseEntity<ApiResponse<List<Turma>>> getByIdCurso(@PathVariable Integer id) {
        List<Turma> turmas = turmaService.getByIdCurso(id);

        ApiResponse<List<Turma>> response = new ApiResponse.Builder<List<Turma>>(HttpStatus.OK.value(), "Turmas encontradas com sucesso")
            .data(turmas)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping("/curso/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<List<Turma>>> deleteByIdCurso(@PathVariable Integer id) {
        List<Turma> turmas = turmaService.deleteByIdCurso(id);

        ApiResponse<List<Turma>> response = new ApiResponse.Builder<List<Turma>>(HttpStatus.OK.value(), "Turmas deletadas com sucesso")
            .data(turmas)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}