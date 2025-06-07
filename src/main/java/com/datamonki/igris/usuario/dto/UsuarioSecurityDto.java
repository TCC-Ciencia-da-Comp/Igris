package com.datamonki.igris.usuario.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioSecurityDto implements UserDetails {

	private Integer id;
    private String login;
    private String nome;
    private String senha;
    private String perfilAtivo;  // Alterei para String, pois o perfil é representado por um nome.
    private String instituicaoAtiva;  // Alterei para String, pois a instituição é representada por um nome.
    private Integer idInstituicao;
    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.perfilAtivo != null) {
            return List.of(new SimpleGrantedAuthority(this.perfilAtivo)); // Mapeia o perfil para ROLE_
        }
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
