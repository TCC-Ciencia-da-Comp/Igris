package com.datamonki.igris.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.academico.model.Turno; 

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {

}
