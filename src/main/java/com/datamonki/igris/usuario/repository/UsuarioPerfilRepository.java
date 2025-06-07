package com.datamonki.igris.usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.usuario.model.Usuario;
import com.datamonki.igris.usuario.model.UsuarioPerfil;

@Repository
public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, Integer> {

    // Buscar usuários por nome de perfil
    @Query("SELECT up.usuario FROM UsuarioPerfil up WHERE up.perfil.nome = :perfilNome")
    List<Usuario> findUsuarioByPerfilNome(String perfilNome);

    // Buscar perfis por login de usuário
    List<UsuarioPerfil> findByUsuarioLogin(String login);

    // Buscar perfil único por login do usuário, nome do perfil e ID da instituição
    Optional<UsuarioPerfil> findByUsuarioLoginAndPerfilNomeAndInstituicaoIdInstituicao(String login, String perfilNome, Integer idInstituicao);

    Boolean existsByEmailUsuario(String emailUsuario);
}
