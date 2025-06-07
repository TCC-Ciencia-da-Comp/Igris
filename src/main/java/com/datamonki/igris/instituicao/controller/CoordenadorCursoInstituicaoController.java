package com.datamonki.igris.instituicao.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.datamonki.igris.common.response.ApiResponse;
import com.datamonki.igris.instituicao.dto.CoordenadorCursoInstituicaoDto;
import com.datamonki.igris.instituicao.model.CoordenadorCursoInstituicao;
import com.datamonki.igris.instituicao.service.CoordenadorCursoInstituicaoService;

@RestController
@RequestMapping("/coordenador-curso-instituicao")
public class CoordenadorCursoInstituicaoController {

    @Autowired
    private CoordenadorCursoInstituicaoService coordenadorCursoInstituicaoService;

    // Método para salvar um coordenador de curso em uma instituição
    @PostMapping
    public ResponseEntity<ApiResponse<CoordenadorCursoInstituicao>> create(@RequestBody CoordenadorCursoInstituicaoDto dto) {
        CoordenadorCursoInstituicao coordenadorCursoInstituicao = coordenadorCursoInstituicaoService.create(dto);

        ApiResponse<CoordenadorCursoInstituicao> response = new ApiResponse.Builder<CoordenadorCursoInstituicao>(HttpStatus.CREATED.value(), "Coordenador de curso em instituição criado com sucesso")
                .data(coordenadorCursoInstituicao)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Método para salvar múltiplos coordenadores de cursos em instituições
    @PostMapping("/save-all")
    public ResponseEntity<ApiResponse<List<CoordenadorCursoInstituicao>>> createAll(@RequestBody List<CoordenadorCursoInstituicaoDto> dtos) {
        List<CoordenadorCursoInstituicao> coordenadorCursoInstituicaos = coordenadorCursoInstituicaoService.createAll(dtos);

        ApiResponse<List<CoordenadorCursoInstituicao>> response = new ApiResponse.Builder<List<CoordenadorCursoInstituicao>>(HttpStatus.CREATED.value(), "Coordenadores de cursos em instituições criados com sucesso")
                .data(coordenadorCursoInstituicaos)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Método para obter todos os coordenadores de cursos em instituições
    @GetMapping
    public ResponseEntity<ApiResponse<List<CoordenadorCursoInstituicao>>> getAll() {
        List<CoordenadorCursoInstituicao> coordenadorCursoInstituicaos = coordenadorCursoInstituicaoService.getAll();

        ApiResponse<List<CoordenadorCursoInstituicao>> response = new ApiResponse.Builder<List<CoordenadorCursoInstituicao>>(HttpStatus.OK.value(), "Coordenadores de cursos em instituições encontrados com sucesso")
                .data(coordenadorCursoInstituicaos)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Método para obter coordenadores de cursos de uma instituição específica
    @GetMapping("/{instituicaoId}")
    public ResponseEntity<ApiResponse<List<CoordenadorCursoInstituicao>>> getByInstituicaoId(@PathVariable Integer instituicaoId) {
        List<CoordenadorCursoInstituicao> coordenadorCursoInstituicaos = coordenadorCursoInstituicaoService.getByInstituicaoId(instituicaoId);

        ApiResponse<List<CoordenadorCursoInstituicao>> response = new ApiResponse.Builder<List<CoordenadorCursoInstituicao>>(HttpStatus.OK.value(), "Coordenadores de cursos em instituições encontrados com sucesso")
                .data(coordenadorCursoInstituicaos)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Método para deletar um coordenador de curso em uma instituição pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CoordenadorCursoInstituicao>> deleteById(@PathVariable Integer id) {
        CoordenadorCursoInstituicao coordenadorCursoInstituicao = coordenadorCursoInstituicaoService.deleteById(id);

        ApiResponse<CoordenadorCursoInstituicao> response = new ApiResponse.Builder<CoordenadorCursoInstituicao>(HttpStatus.OK.value(), "Coordenador de curso em instituição deletado com sucesso")
                .data(coordenadorCursoInstituicao)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
