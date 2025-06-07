package com.datamonki.igris.instituicao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.instituicao.model.ConfigFaculdade;


@Repository
public interface ConfigFaculdadeRepository extends JpaRepository<ConfigFaculdade, Integer> {

}
