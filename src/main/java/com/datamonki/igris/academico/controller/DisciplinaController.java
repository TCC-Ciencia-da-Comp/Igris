package com.datamonki.igris.academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamonki.igris.academico.dto.DisciplinaDto;
import com.datamonki.igris.academico.model.Disciplina;
import com.datamonki.igris.academico.service.DisciplinaService;
import com.datamonki.igris.common.response.ApiResponse;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {
    
    @Autowired
    private DisciplinaService disciplinaService;
    
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Disciplina>> create(@RequestBody DisciplinaDto disciplinaDto){
        Disciplina disciplina = disciplinaService.create(disciplinaDto);

        ApiResponse<Disciplina> response = new ApiResponse.Builder<Disciplina>(HttpStatus.CREATED.value(), "Disciplina criada com sucesso")
            .data(disciplina)
            .build();
            
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Disciplina>> getById(@PathVariable Integer id){
        Disciplina disciplina = disciplinaService.getById(id);

        ApiResponse<Disciplina> response = new ApiResponse.Builder<Disciplina>(HttpStatus.OK.value(), "Disciplina encontrada com sucesso")
            .data(disciplina)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping
	public ResponseEntity<ApiResponse<List<Disciplina>>> getAll(){ 
        List<Disciplina> disciplinas = disciplinaService.getAll();

        ApiResponse<List<Disciplina>> response = new ApiResponse.Builder<List<Disciplina>>(HttpStatus.OK.value(), "Disciplinas encontradas com sucesso")
            .data(disciplinas)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping("/nome/{nome}")
    public ResponseEntity<ApiResponse<List<Disciplina>>> getByNome(@PathVariable String nome) {
        List<Disciplina> disciplinas = disciplinaService.getByNome(nome);

        ApiResponse<List<Disciplina>> response = new ApiResponse.Builder<List<Disciplina>>(HttpStatus.OK.value(), "Disciplinas encontradas com sucesso")
            .data(disciplinas)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/nome/order")
    public ResponseEntity<ApiResponse<List<Disciplina>>> getByOrderNome() {
        List<Disciplina> disciplinas = disciplinaService.getByOrderNome();

        ApiResponse<List<Disciplina>> response = new ApiResponse.Builder<List<Disciplina>>(HttpStatus.OK.value(), "Disciplinas encontradas com sucesso")
            .data(disciplinas)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Disciplina>> delete(@PathVariable Integer id){
        Disciplina disciplina = disciplinaService.deleteById(id);

        ApiResponse<Disciplina> response = new ApiResponse.Builder<Disciplina>(HttpStatus.OK.value(), "Disciplina deletada com sucesso")
            .data(disciplina)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<List<Disciplina>>> delete(){
        List<Disciplina> disciplinas = disciplinaService.deleteAll();

        ApiResponse<List<Disciplina>> response = new ApiResponse.Builder<List<Disciplina>>(HttpStatus.OK.value(), "Disciplinas deletadas com sucesso")
            .data(disciplinas)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Disciplina>> update(@PathVariable Integer id, @RequestBody DisciplinaDto disciplinaDto){
        Disciplina disciplina = disciplinaService.update(id, disciplinaDto);

        ApiResponse<Disciplina> response = new ApiResponse.Builder<Disciplina>(HttpStatus.OK.value(), "Disciplina atualizada com sucesso")
            .data(disciplina)
            .build();
            
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}