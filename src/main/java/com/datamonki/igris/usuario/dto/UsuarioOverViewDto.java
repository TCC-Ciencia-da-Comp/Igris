package com.datamonki.igris.usuario.dto;


import com.datamonki.igris.usuario.model.Usuario;


//Feito com o objetivo de exibir uma visão geral do usuário, mas sem expor informações sensíveis
public record UsuarioOverViewDto(Integer id, String login, String nome) {

    // Construtor personalizado para converter o modelo Usuario em UsuarioDTO
    public UsuarioOverViewDto(Usuario usuario) {
        this(usuario.getIdUsuario(),
             usuario.getLogin(),
             usuario.getNome());
             }
}





/*
 * package com.datamonki.GradeMaker.usuario.dto;

import java.util.stream.Collectors;

import com.datamonki.GradeMaker.instituicao.model.Instituicao;
import com.datamonki.GradeMaker.usuario.model.Perfil;
import com.datamonki.GradeMaker.usuario.model.Usuario;
import com.datamonki.GradeMaker.usuario.model.UsuarioPerfil;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


//Feito com o objetivo de exibir uma visão geral do usuário, mas sem expor informações sensíveis
public record UsuarioOverViewDto(Integer id, String email, String nome,  Set<PerfilInstituicaoDto> perfis) {

    // Construtor personalizado para converter o modelo Usuario em UsuarioDTO
    public UsuarioOverViewDto(Usuario usuario) {
        this(usuario.getId(),
             usuario.getEmail(),
             usuario.getNome(),
             usuario.getPerfis().stream()  // Acessa a lista de UsuarioPerfil
                    .map(usuarioPerfil -> new PerfilInstituicaoDto(
                        usuarioPerfil.getPerfil().getNome(),    // Nome do perfil
                        usuarioPerfil.getInstituicao().getId(), //Id da instituicao
                        usuarioPerfil.getInstituicao().getNome() // Nome da instituição
                    ))
                    .collect(Collectors.toSet()));  // Retorna um Set com os nomes dos perfis e instituições
    }
}


@Getter
@Setter
class PerfilInstituicaoDto {
    private final String perfilName;
    private final Integer idInstituicao;
    private final String instituicaoNome;

    public PerfilInstituicaoDto(String perfilName,Integer idInstituicao, String instituicaoNome) {
        this.perfilName = perfilName;
        this.idInstituicao = idInstituicao;
        this.instituicaoNome = instituicaoNome;
    }
}

 * 
 * 
 * 
 */