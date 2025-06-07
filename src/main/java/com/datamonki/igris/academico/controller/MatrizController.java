package com.datamonki.igris.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datamonki.igris.academico.dto.MatrizDto;
import com.datamonki.igris.academico.model.Matriz;
import com.datamonki.igris.academico.service.MatrizService;
import com.datamonki.igris.common.response.ApiResponse;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/matriz")
public class MatrizController {
    
    @Autowired
    private MatrizService matrizService;
    
    @PostMapping
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<Matriz>> create(@RequestBody MatrizDto matrizDto) {
        Matriz matriz = matrizService.create(matrizDto);

        ApiResponse<Matriz> response = new ApiResponse.Builder<Matriz>(HttpStatus.CREATED.value(), "Matriz criada com sucesso")
            .data(matriz)
            .build();
            
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<Matriz>> getById(@PathVariable Integer id) {
        Matriz matriz = matrizService.getById(id);

        ApiResponse<Matriz> response = new ApiResponse.Builder<Matriz>(HttpStatus.OK.value(), "Matriz encontrada com sucesso")
            .data(matriz)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/turma/{id}")
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<List<Matriz>>> getByIdTurma(@PathVariable Integer id) {
        List<Matriz> matrizes = matrizService.getByIdTurma(id);

        ApiResponse<List<Matriz>> response = new ApiResponse.Builder<List<Matriz>>(HttpStatus.OK.value(), "Matrizes encontradas com sucesso")
            .data(matrizes)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/disciplina/{id}")
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<List<Matriz>>> getByIdDisciplina(@PathVariable Integer id) {
        List<Matriz> matrizes = matrizService.getByIdDisciplina(id);

        ApiResponse<List<Matriz>> response = new ApiResponse.Builder<List<Matriz>>(HttpStatus.OK.value(), "Matrizes encontradas com sucesso")
            .data(matrizes)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
	@PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<List<Matriz>>> getAll() {
        List<Matriz> matrizes = matrizService.getAll();

        ApiResponse<List<Matriz>> response = new ApiResponse.Builder<List<Matriz>>(HttpStatus.OK.value(), "Matrizes encontradas com sucesso")
            .data(matrizes)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<Matriz>> delete(@PathVariable Integer id) {
        Matriz matriz = matrizService.deleteById(id);

        ApiResponse<Matriz> response = new ApiResponse.Builder<Matriz>(HttpStatus.OK.value(), "Matriz deletada com sucesso")
            .data(matriz)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<List<Matriz>>> delete() {
        List<Matriz> matrizes = matrizService.deleteAll();

        ApiResponse<List<Matriz>> response = new ApiResponse.Builder<List<Matriz>>(HttpStatus.OK.value(), "Matrizes deletadas com sucesso")
            .data(matrizes)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/turma/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<List<Matriz>>> deleteByIdTurma(@PathVariable Integer id) {
        List<Matriz> matrizes = matrizService.deleteByIdTurma(id);

        ApiResponse<List<Matriz>> response = new ApiResponse.Builder<List<Matriz>>(HttpStatus.OK.value(), "Matrizes deletadas com sucesso")
            .data(matrizes)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/disciplina/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN','ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<List<Matriz>>> deleteByIdDisciplina(@PathVariable Integer id) {
        List<Matriz> matrizes = matrizService.deleteByIdDisciplina(id);

        ApiResponse<List<Matriz>> response = new ApiResponse.Builder<List<Matriz>>(HttpStatus.OK.value(), "Matrizes deletadas com sucesso")
            .data(matrizes)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN', 'ACESSO_COORDENADOR')")
    public ResponseEntity<ApiResponse<Matriz>> update(@PathVariable Integer id, @RequestBody MatrizDto matrizDto) {
        Matriz matriz = matrizService.update(id, matrizDto);

        ApiResponse<Matriz> response = new ApiResponse.Builder<Matriz>(HttpStatus.OK.value(), "Matriz atualizada com sucesso")
            .data(matriz)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}