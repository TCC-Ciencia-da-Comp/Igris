package com.datamonki.igris.academico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.academico.model.Disciplina;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {
    // Busca pelo nome
    Optional<Disciplina> findByNome(String nome);

    // Busca uma disciplina pelo nome ignorando o case
    List<Disciplina> findByNomeContainingIgnoreCase(String nome);

    // Buscar disciplina ordenado por nome
    List<Disciplina> findByOrderByNomeAsc();
}
