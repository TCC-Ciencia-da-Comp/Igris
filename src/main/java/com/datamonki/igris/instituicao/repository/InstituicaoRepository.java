package com.datamonki.igris.instituicao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.instituicao.model.Instituicao; 


@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Integer>{

    // Busca pelo nome
    Optional<Instituicao> findByNome(String nome);

    // Busca um curso pelo nome ignorando o case(nao diferencia maiusculo de minusculo) 
    List<Instituicao> findByNomeContainingIgnoreCase(String nome);

    // Buscar curso ordenado por nome
    List<Instituicao> findByOrderByNomeAsc();
}
