package com.datamonki.igris.usuario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.usuario.model.Usuario; 
import com.datamonki.igris.usuario.model.UsuarioDisciplina;

@Repository
public interface UsuarioDisciplinaRepository extends JpaRepository<UsuarioDisciplina, Integer> {

    // Buscar todas as disciplinas associadas a um usuário (pela entidade Usuario)
    List<UsuarioDisciplina> findByUsuario(Usuario usuario);

    // Buscar todas as disciplinas associadas a um usuário pelo id do usuário
    List<UsuarioDisciplina> findByUsuarioIdUsuario(Integer idUsuario);

    // Buscar todas as relações pelo id da disciplina
    List<UsuarioDisciplina> findByDisciplinaIdDisciplina(Integer idDisciplina);
}
