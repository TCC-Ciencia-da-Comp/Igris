package com.datamonki.igris.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurrentUserDto {

    private final String login;
    private final String nome;
    private final String perfilAtivo;
    private final Integer idInstituicaoAtiva;
    private final String instituicaoAtiva;
    private final String email;

    public CurrentUserDto(UsuarioSecurityDto usuario) {
        this.login = usuario.getLogin();
        this.nome = usuario.getNome();
        this.perfilAtivo = usuario.getPerfilAtivo();
        this.idInstituicaoAtiva = usuario.getIdInstituicao();
        this.instituicaoAtiva = usuario.getInstituicaoAtiva();
        this.email = usuario.getEmail();
    }
}
