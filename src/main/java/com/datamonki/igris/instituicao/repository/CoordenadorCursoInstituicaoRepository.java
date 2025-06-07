package com.datamonki.igris.instituicao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.instituicao.model.CoordenadorCursoInstituicao;


@Repository
public interface CoordenadorCursoInstituicaoRepository extends JpaRepository<CoordenadorCursoInstituicao, Integer>{
	
	@Query("SELECT cci FROM CoordenadorCursoInstituicao cci WHERE cci.instituicao.id = :instituicaoId")
    List<CoordenadorCursoInstituicao> findByInstituicaoId(Integer instituicaoId);
}
