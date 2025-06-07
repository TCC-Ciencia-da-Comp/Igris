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
import com.datamonki.igris.instituicao.dto.CoordenacaoDto;
import com.datamonki.igris.instituicao.model.Coordenacao;
import com.datamonki.igris.instituicao.service.CoordenacaoService;

// Classe que representa o controller, responsável pelas requisições de coordenação para a API
@RestController
@RequestMapping("/coordenacao")
public class CoordenacaoController {

    @Autowired
    private CoordenacaoService coordenacaoService;

    // Endpoint para criar uma coordenação
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Coordenacao>> create(@RequestBody CoordenacaoDto coordenacaoDto, @RequestBody Integer idInstituicao) {
        Coordenacao coordenacao = coordenacaoService.create(coordenacaoDto, idInstituicao);

        ApiResponse<Coordenacao> response = new ApiResponse.Builder<Coordenacao>(HttpStatus.CREATED.value(), "Coordenação criada com sucesso")
                .data(coordenacao)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint para buscar uma coordenação por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Coordenacao>> getById(@PathVariable Integer id) {
        Coordenacao coordenacao = coordenacaoService.getById(id);

        ApiResponse<Coordenacao> response = new ApiResponse.Builder<Coordenacao>(HttpStatus.OK.value(), "Coordenação encontrada com sucesso")
                .data(coordenacao)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoint para listar todas as coordenações
    @GetMapping
    public ResponseEntity<ApiResponse<List<Coordenacao>>> getAll() {
        List<Coordenacao> coordenacoes = coordenacaoService.getAll();

        ApiResponse<List<Coordenacao>> response = new ApiResponse.Builder<List<Coordenacao>>(HttpStatus.OK.value(), "Coordenações encontradas com sucesso")
                .data(coordenacoes)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // Endpoint para atualizar uma coordenação
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Coordenacao>> update(@PathVariable Integer id, @RequestBody CoordenacaoDto coordenacaoDto, @RequestBody Integer idInstituicao) {
        Coordenacao coordenacao = coordenacaoService.update(id, coordenacaoDto, idInstituicao);

        ApiResponse<Coordenacao> response = new ApiResponse.Builder<Coordenacao>(HttpStatus.OK.value(), "Coordenação atualizada com sucesso")
                .data(coordenacao)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoint para deletar uma coordenação pelo ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<Coordenacao>> delete(@PathVariable Integer id) {
        Coordenacao coordenacao = coordenacaoService.deleteById(id);

        ApiResponse<Coordenacao> response = new ApiResponse.Builder<Coordenacao>(HttpStatus.OK.value(), "Coordenação deletada com sucesso")
                .data(coordenacao)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoint para deletar todas as coordenações (em caso de limpeza de dados, por exemplo)
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ACESSO_ADMIN')")
    public ResponseEntity<ApiResponse<List<Coordenacao>>> deleteAll() {
        List<Coordenacao> coordenacoes = coordenacaoService.deleteAll();

        ApiResponse<List<Coordenacao>> response = new ApiResponse.Builder<List<Coordenacao>>(HttpStatus.OK.value(), "Coordenações deletadas com sucesso")
                .data(coordenacoes)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
