package com.datamonki.igris.usuario.service;

import java.util.List;

import com.datamonki.igris.usuario.dto.UsuarioDisciplinaDto;
import com.datamonki.igris.usuario.model.UsuarioDisciplina;

import jakarta.transaction.Transactional;

public interface UsuarioDisciplinaService {

    //Criar uma relação de usuário e disciplina
    @Transactional
    UsuarioDisciplina create(UsuarioDisciplinaDto dto);

    //Criar uma relação de usuário e disciplina
    @Transactional
    UsuarioDisciplina createByUsuarioProfessor(UsuarioDisciplinaDto dto);
    
    //Criar todas as relações de usuário e disciplina
    @Transactional
    List<UsuarioDisciplina> createAllByUsuarioProfessor(List<UsuarioDisciplinaDto> dtos);
    
    //Buscar todas as relações de usuário e disciplina
    List<UsuarioDisciplina> getAll();

    //Buscar uma relação de usuário e disciplina
    UsuarioDisciplina getById(Integer id);

    //Buscar todas as relações de usuário e disciplina
    List<UsuarioDisciplina> getByUsuarioId(Integer idUsuario);

    //Deletar todas as relações de usuário e disciplina
    List<UsuarioDisciplina> deleteAll();
    
    //Deletar uma relação de usuário e disciplina
    UsuarioDisciplina deleteById(Integer id);

    //Deletar todas as relações de um usuário
    List<UsuarioDisciplina> deleteByUsuarioId(Integer idUsuario);

    //Deletar todas as relações de uma disciplina
    List<UsuarioDisciplina> deleteByDisciplinaId(Integer idDisciplina);
    
}
