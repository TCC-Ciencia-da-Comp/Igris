package com.datamonki.igris.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.datamonki.igris.usuario.dto.UsuarioSecurityDto;
import com.datamonki.igris.usuario.model.Usuario;
import com.datamonki.igris.usuario.model.UsuarioPerfil;
import com.datamonki.igris.usuario.repository.UsuarioRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        UsuarioPerfil usuarioPerfil = usuario.getPerfis().stream()
            .findFirst()
            .orElseThrow(() -> new UsernameNotFoundException("Perfil não encontrado"));

        String nome = usuarioPerfil.getUsuario().getNome();
        String nomePerfil = usuarioPerfil.getPerfil().getNome();
        String nomeInstituicao = usuarioPerfil.getInstituicao().getNome();
        Integer idInstituicao = usuarioPerfil.getInstituicao().getIdInstituicao();
        Integer id = usuarioPerfil.getUsuario().getIdUsuario();
        String email = usuarioPerfil.getEmailUsuario();
        
        return new UsuarioSecurityDto(
        	id,
            usuario.getLogin(),
            nome,
            usuario.getSenha(),
            nomePerfil,
            nomeInstituicao,
            idInstituicao,
            email
            
        );
    }

	public static UsuarioSecurityDto getAuthenticatedUser() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof UsuarioSecurityDto) {
	        return (UsuarioSecurityDto) authentication.getPrincipal();
	    }
	    throw new RuntimeException("Usuário não autenticado");
	}
}


//@Override
//public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
//	if("admin".equals(userName)) {
//		return User.builder()
//				.username("admin")
//				.password(new BCryptPasswordEncoder().encode("admin123"))
//				.roles("ADMIN")
//				.build();
//	}
//	throw new UsernameNotFoundException("Usuário não encontrado");
//}