package com.datamonki.igris.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.academico.model.DiaSemana;

@Repository
public interface DiaSemanaRepository extends JpaRepository<DiaSemana, Integer> {

}
