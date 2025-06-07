package com.datamonki.igris.academico.service;

import java.util.List;

import com.datamonki.igris.academico.dto.DisciplinaCursoDto;
import com.datamonki.igris.academico.model.DisciplinaCurso;

public interface DisciplinaCursoService {
    
    //Criar disciplina-curso
    DisciplinaCurso create(DisciplinaCursoDto DisciplinaCursoDto); 

    //Listar todas as disciplinas-cursos
    List<DisciplinaCurso> getAll();

    //Listar disciplina-curso por id
    DisciplinaCurso getById(Integer idDisciplinaCurso);

    //Listar disciplinas-cursos por disciplina
    List<DisciplinaCurso> getByIdDisciplina(Integer idDisciplina);

    //Listar disciplinas-cursos por curso
    List<DisciplinaCurso> getByIdCurso(Integer idCurso);

    //Atualizar disciplina-curso
    DisciplinaCurso update(Integer idDisciplinaCurso, DisciplinaCursoDto DisciplinaCursoDto);

    //Deletar todas as disciplinas-cursos
    List<DisciplinaCurso> deleteAll();

    //Deletar disciplina-curso por id
    DisciplinaCurso deleteById(Integer idDisciplinaCurso);

    //Deletar disciplinas-cursos por curso
    List<DisciplinaCurso> deleteByCursoId(Integer idCurso);

    //Deletar disciplinas-cursos por disciplina
    List<DisciplinaCurso> deleteByDisciplinaId(Integer idDisciplina);
}