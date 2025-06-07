package com.datamonki.igris.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.academico.model.DisciplinaCurso;

@Repository
public interface DisciplinaCursoRepository extends JpaRepository<DisciplinaCurso, Integer> {

    // Buscar disciplinaCurso pela disciplina
    List<DisciplinaCurso> findByDisciplinaIdDisciplina(Integer idDisciplina);

    // Buscar disciplinaCurso pelo curso
    List<DisciplinaCurso> findByCursoIdCurso(Integer idCurso);

    // Deletar disciplinaCurso pelo curso
    void deleteByCursoIdCurso(Integer idCurso);

    // Deletar disciplinaCurso pela disciplina
    void deleteByDisciplinaIdDisciplina(Integer idDisciplina);
}
