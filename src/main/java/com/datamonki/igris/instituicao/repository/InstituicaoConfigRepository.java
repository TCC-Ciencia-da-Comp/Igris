package com.datamonki.igris.instituicao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.instituicao.model.InstituicaoConfig;


@Repository
public interface InstituicaoConfigRepository extends JpaRepository<InstituicaoConfig, Integer>{
	
}
