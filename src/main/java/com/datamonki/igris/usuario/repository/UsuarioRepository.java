package com.datamonki.igris.usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.datamonki.igris.usuario.model.Usuario;

import jakarta.transaction.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);

    @Query("SELECT u FROM Usuario u JOIN u.perfis up JOIN up.perfil p JOIN up.instituicao i " +
           "WHERE p.nome = :perfilNome AND i.id = :instituicaoId")
    List<Usuario> findByPerfilNomeAndInstituicaoId(@Param("perfilNome") String perfilNome,
                                                   @Param("instituicaoId") Integer instituicaoId);

    Boolean existsByLogin(String login);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.senha = :senha WHERE u.login = :login")
    void updateSenha(@Param("login") String login, @Param("senha") String senha);

    @Query("""
        SELECT CASE WHEN COUNT(up) > 0 THEN true ELSE false END
        FROM UsuarioPerfil up
        WHERE up.usuario.login = :login
        AND up.perfil.nome = :perfil
        AND up.instituicao.nome = :instituicao
    """)
    boolean existsByLoginAndPerfilAndInstituicao(@Param("login") String login,
                                                 @Param("perfil") String perfil,
                                                 @Param("instituicao") String instituicao);
}
