package com.datamonki.igris.instituicao.controller;

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

import com.datamonki.igris.common.response.ApiResponse;
import com.datamonki.igris.instituicao.dto.InstituicaoDto;
import com.datamonki.igris.instituicao.model.Instituicao;
import com.datamonki.igris.instituicao.service.InstituicaoService;

@RestController
@RequestMapping("/instituicao")
public class InstituicaoController {

    @Autowired
    private InstituicaoService instituicaoService;

    // Endpoint para criar uma nova instituição
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Instituicao>> create(@RequestBody InstituicaoDto instituicaoDto) {
        Instituicao instituicao = instituicaoService.create(instituicaoDto);

        ApiResponse<Instituicao> response = new ApiResponse.Builder<Instituicao>(HttpStatus.CREATED.value(), "Instituição criada com sucesso")
                .data(instituicao)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint para obter uma instituição pelo ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Instituicao>> getById(@PathVariable Integer id) {
        Instituicao instituicao = instituicaoService.getById(id);

        ApiResponse<Instituicao> response = new ApiResponse.Builder<Instituicao>(HttpStatus.OK.value(), "Instituição encontrada com sucesso")
                .data(instituicao)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoint para obter todas as instituições
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<List<Instituicao>>> getAll() {
        List<Instituicao> instituicaos = instituicaoService.getAll();

        ApiResponse<List<Instituicao>> response = new ApiResponse.Builder<List<Instituicao>>(HttpStatus.OK.value(), "Instituições encontradas com sucesso")
                .data(instituicaos)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoint para obter instituição pelo nome
    @GetMapping("/nome/{nome}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
        public ResponseEntity<ApiResponse<List<Instituicao>>> getByNome(@PathVariable String nome) { 
        List<Instituicao> instituicoes = instituicaoService.getByNome(nome);

        ApiResponse<List<Instituicao>> response = new ApiResponse.Builder<List<Instituicao>>(HttpStatus.OK.value(), "Instituições encontradas com sucesso")
                .data(instituicoes)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoint para atualizar uma instituição existente
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Instituicao>> update(@PathVariable Integer id, @RequestBody InstituicaoDto instituicaoDto) {
        Instituicao instituicao = instituicaoService.update(id, instituicaoDto);

        ApiResponse<Instituicao> response = new ApiResponse.Builder<Instituicao>(HttpStatus.OK.value(), "Instituição atualizada com sucesso")
                .data(instituicao)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoint para deletar uma instituição pelo ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Instituicao>> deleteById(@PathVariable Integer id) {
        Instituicao instituicao = instituicaoService.deleteById(id);

        ApiResponse<Instituicao> response = new ApiResponse.Builder<Instituicao>(HttpStatus.OK.value(), "Instituição deletada com sucesso")
                .data(instituicao)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoint para deletar todas as instituições
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")    
    public ResponseEntity<ApiResponse<List<Instituicao>>> deleteAll() {
        List<Instituicao> instituicaos = instituicaoService.deleteAll();

        ApiResponse<List<Instituicao>> response = new ApiResponse.Builder<List<Instituicao>>(HttpStatus.OK.value(), "Instituições deletadas com sucesso")
                .data(instituicaos)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
