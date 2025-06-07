package com.datamonki.igris.academico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.academico.model.Curso;


@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    // Busca pelo nome
    Optional<Curso> findByNome(String nome);

    // Busca um curso pelo nome ignorando o case(nao diferencia maiusculo de minusculo) 
    List<Curso> findByNomeContainingIgnoreCase(String nome);

    // Buscar curso ordenado por nome
    List<Curso> findByOrderByNomeAsc();
    
    // Ajuste a consulta para a nova estrutura
    @Query("SELECT c FROM Curso c " +
           "JOIN CoordenadorCursoInstituicao cci ON cci.curso = c " +
           "WHERE cci.instituicao.id = :instituicaoId")
    List<Curso> findCursosByInstituicaoId(Integer instituicaoId);

}