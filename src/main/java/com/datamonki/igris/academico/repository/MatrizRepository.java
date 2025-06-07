package com.datamonki.igris.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.datamonki.igris.academico.model.Matriz; 

@Repository
public interface MatrizRepository extends JpaRepository<Matriz, Integer> {

    List<Matriz> findByTurmaIdTurma(Integer idTurma);

    List<Matriz> findByDisciplinaIdDisciplina(Integer idDisciplina);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM matriz WHERE id_turma = :id_turma", nativeQuery = true)
    void deleteByTurmaId(Integer idTurma);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM matriz WHERE id_disciplina = :id_disciplina", nativeQuery = true)
    void deleteByDisciplinaId(Integer idDisciplina);
}

