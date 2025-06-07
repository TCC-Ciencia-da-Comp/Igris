package com.datamonki.igris.usuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.usuario.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer>{
	Optional<Perfil> findByNome(String name);
}
