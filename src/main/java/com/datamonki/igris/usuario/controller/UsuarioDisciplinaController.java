package com.datamonki.igris.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datamonki.igris.common.response.ApiResponse;
import com.datamonki.igris.usuario.dto.UsuarioDisciplinaDto;
import com.datamonki.igris.usuario.model.UsuarioDisciplina;
import com.datamonki.igris.usuario.service.UsuarioDisciplinaService;

@RestController
@RequestMapping("/usuario-disciplina")
public class UsuarioDisciplinaController {

    @Autowired
    private UsuarioDisciplinaService usuarioDisciplinaService;

    // Criar relação usuário-disciplina (um único registro)
    @PostMapping("/coordenador")
    public ResponseEntity<ApiResponse<UsuarioDisciplina>> create(@RequestBody UsuarioDisciplinaDto dto) {
        UsuarioDisciplina usuarioDisciplina = usuarioDisciplinaService.create(dto);
        
        ApiResponse<UsuarioDisciplina> response = new ApiResponse.Builder<UsuarioDisciplina>(HttpStatus.CREATED.value(), "Relação usuário-disciplina criada com sucesso").data(usuarioDisciplina).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/professor")
    public ResponseEntity<ApiResponse<UsuarioDisciplina>> createByUsuarioProfessor(@RequestBody UsuarioDisciplinaDto dto) {
        UsuarioDisciplina usuarioDisciplina = usuarioDisciplinaService.createByUsuarioProfessor(dto);

        ApiResponse<UsuarioDisciplina> response = new ApiResponse.Builder<UsuarioDisciplina>(HttpStatus.CREATED.value(), "Relação usuário-disciplina criada com sucesso").data(usuarioDisciplina).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

        
    // Criar várias relações usuário-disciplina (lista)
    @PostMapping("/professor/lista")
    public ResponseEntity<ApiResponse<List<UsuarioDisciplina>>> createAllByUsuarioProfessor(@RequestBody List<UsuarioDisciplinaDto> dtos) {
        List<UsuarioDisciplina> usuarioDisciplinas = usuarioDisciplinaService.createAllByUsuarioProfessor(dtos);

        ApiResponse<List<UsuarioDisciplina>> response = new ApiResponse.Builder<List<UsuarioDisciplina>>(HttpStatus.CREATED.value(), "Relações usuário-disciplina criadas com sucesso").data(usuarioDisciplinas).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Buscar todas as relações
    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioDisciplina>>> getAll() {
        List<UsuarioDisciplina> usuarioDisciplinas = usuarioDisciplinaService.getAll();

        ApiResponse<List<UsuarioDisciplina>> response = new ApiResponse.Builder<List<UsuarioDisciplina>>(HttpStatus.OK.value(), "Relações usuário-disciplina encontradas com sucesso").data(usuarioDisciplinas).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Buscar relação pelo id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDisciplina>> getById(@PathVariable Integer id) {
        UsuarioDisciplina usuarioDisciplina = usuarioDisciplinaService.getById(id);

        ApiResponse<UsuarioDisciplina> response = new ApiResponse.Builder<UsuarioDisciplina>(HttpStatus.OK.value(), "Relação usuário-disciplina encontrada com sucesso").data(usuarioDisciplina).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Buscar relações por id de usuário
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<UsuarioDisciplina>>> getByUsuarioId(@PathVariable Integer idUsuario) {
        List<UsuarioDisciplina> usuarioDisciplinas = usuarioDisciplinaService.getByUsuarioId(idUsuario);

        ApiResponse<List<UsuarioDisciplina>> response = new ApiResponse.Builder<List<UsuarioDisciplina>>(HttpStatus.OK.value(), "Relações usuário-disciplina encontradas com sucesso").data(usuarioDisciplinas).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Deletar todas as relações
    @DeleteMapping
    public ResponseEntity<ApiResponse<List<UsuarioDisciplina>>> deleteAll() {
        List<UsuarioDisciplina> usuarioDisciplinas = usuarioDisciplinaService.deleteAll();

        ApiResponse<List<UsuarioDisciplina>> response = new ApiResponse.Builder<List<UsuarioDisciplina>>(HttpStatus.OK.value(), "Relações usuário-disciplina deletadas com sucesso").data(usuarioDisciplinas).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Deletar relação pelo id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDisciplina>> deleteById(@PathVariable Integer id) {
        UsuarioDisciplina usuarioDisciplina = usuarioDisciplinaService.deleteById(id);

        ApiResponse<UsuarioDisciplina> response = new ApiResponse.Builder<UsuarioDisciplina>(HttpStatus.OK.value(), "Relação usuário-disciplina deletada com sucesso").data(usuarioDisciplina).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Deletar todas relações por usuário
    @DeleteMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<UsuarioDisciplina>>> deleteByUsuarioId(@PathVariable Integer idUsuario) {
        List<UsuarioDisciplina> usuarioDisciplinas = usuarioDisciplinaService.deleteByUsuarioId(idUsuario);

        ApiResponse<List<UsuarioDisciplina>> response = new ApiResponse.Builder<List<UsuarioDisciplina>>(HttpStatus.OK.value(), "Relações usuário-disciplina deletadas com sucesso").data(usuarioDisciplinas).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Deletar todas relações por disciplina
    @DeleteMapping("/disciplina/{idDisciplina}")
    public ResponseEntity<ApiResponse<List<UsuarioDisciplina>>> deleteByDisciplinaId(@PathVariable Integer idDisciplina) {
        List<UsuarioDisciplina> usuarioDisciplinas = usuarioDisciplinaService.deleteByDisciplinaId(idDisciplina);

        ApiResponse<List<UsuarioDisciplina>> response = new ApiResponse.Builder<List<UsuarioDisciplina>>(HttpStatus.OK.value(), "Relações usuário-disciplina deletadas com sucesso").data(usuarioDisciplinas).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
