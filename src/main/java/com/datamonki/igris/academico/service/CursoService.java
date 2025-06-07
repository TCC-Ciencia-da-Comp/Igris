package com.datamonki.igris.academico.service;

import java.util.List;

import com.datamonki.igris.academico.dto.CursoDto;
import com.datamonki.igris.academico.model.Curso;

public interface CursoService {

    //Criar curso
    Curso create(CursoDto cursoDto);

    //Listar todos os cursos
    List<Curso> getAll();

    //Listar curso por id
    Curso getById(Integer idCurso);

    //Listar cursos por nome
    List<Curso> getByNome(String nome);

    //Listar cursos por ordem alfabética
    List<Curso> getByOrderNome();

    //Atualizar curso
    Curso update(Integer idCurso, CursoDto cursoDto);

    //Deletar curso por id
    Curso deleteById(Integer idCurso);

    //Deletar todos os cursos
    List<Curso> deleteAll();
    
    //Listar cursos por instituição
    List<Curso> getByIdInstituicao(Integer idInstituicao);
}