package com.datamonki.igris.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datamonki.igris.common.response.ApiResponse;
import com.datamonki.igris.security.dto.TrocaPerfilRequestDto;
import com.datamonki.igris.usuario.dto.CurrentUserDto;
import com.datamonki.igris.usuario.dto.UsuarioSecurityDto; 
import com.datamonki.igris.usuario.model.UsuarioPerfil;
import com.datamonki.igris.usuario.repository.UsuarioPerfilRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    @Autowired
    private UsuarioPerfilRepository usuarioPerfilRepository;
      
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    
    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // deixar true em produção
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
    
    @PostMapping("/trocar-perfil")
    public ResponseEntity<ApiResponse<Void>> trocarPerfil(@CookieValue("token") String token,
                                                    @RequestBody TrocaPerfilRequestDto request,
                                                    HttpServletResponse response) {
        try {
            String login = jwtUtil.extractUsername(token);
            String novoPerfil = request.novoPerfil();
            Integer idInstituicao = request.idInstituicao();

            Optional<UsuarioPerfil> up = usuarioPerfilRepository
                    .findByUsuarioLoginAndPerfilNomeAndInstituicaoIdInstituicao(login, novoPerfil, idInstituicao);

            if (up.isEmpty()) {
                ApiResponse<Void> responseApi = new ApiResponse.Builder<Void>(HttpStatus.FORBIDDEN.value(), "Perfil ou instituição inválidos").build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseApi);
            }

            UsuarioPerfil usuarioPerfil = up.get();

            String novoToken = jwtUtil.generateToken(
            	login,
                usuarioPerfil.getUsuario().getNome(),
                usuarioPerfil.getPerfil().getNome(),
                usuarioPerfil.getInstituicao().getNome(),
                usuarioPerfil.getInstituicao().getIdInstituicao(),
                usuarioPerfil.getUsuario().getIdUsuario(),
                usuarioPerfil.getEmailUsuario()
            );
            
            Cookie cookie = new Cookie("token", novoToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // false em dev se necessário
            cookie.setPath("/");
            cookie.setMaxAge(10 * 60 * 60);

            response.addCookie(cookie);

            ApiResponse<Void> responseApi = new ApiResponse.Builder<Void>(HttpStatus.OK.value(), "Perfil alterado com sucesso").build();
            return ResponseEntity.status(HttpStatus.OK).body(responseApi);
        } catch (Exception e) {
            ApiResponse<Void> responseApi = new ApiResponse.Builder<Void>(HttpStatus.UNAUTHORIZED.value(), e.getMessage()).build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseApi);
        }
    }
    

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<CurrentUserDto>> getCurrentUser(@CookieValue(value = "token", required = false) String token) {
        if (token == null) {
            ApiResponse<CurrentUserDto> response = new ApiResponse.Builder<CurrentUserDto>(HttpStatus.UNAUTHORIZED.value(), "Usuário não autenticado").build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);   
        }

        try {
            String login = jwtUtil.extractUsername(token);
            String nome = jwtUtil.extractClaim(token, "nome", String.class);
            String perfil = jwtUtil.extractClaim(token, "perfil", String.class);
            String instituicao = jwtUtil.extractClaim(token, "instituicao", String.class);
            Integer idInstituicao = jwtUtil.extractClaim(token, "idInstituicao", Integer.class);
            Integer id = jwtUtil.extractClaim(token, "id", Integer.class);
            String email = jwtUtil.extractClaim(token, "email", String.class);
            
            UsuarioSecurityDto user = new UsuarioSecurityDto(id, login, nome, "", perfil, instituicao, idInstituicao, email);
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            CurrentUserDto currentUserDto = new CurrentUserDto(user);
            ApiResponse<CurrentUserDto> response = new ApiResponse.Builder<CurrentUserDto>(HttpStatus.OK.value(), "Usuário autenticado").data(currentUserDto).build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<CurrentUserDto> response = new ApiResponse.Builder<CurrentUserDto>(HttpStatus.UNAUTHORIZED.value(), "Token inválido ou expirado").build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/perfis-disponiveis")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> listarPerfis() {
    	UsuarioSecurityDto usuarioLogado = CustomUserDetailService.getAuthenticatedUser();
        List<UsuarioPerfil> perfis = usuarioPerfilRepository.findByUsuarioLogin(usuarioLogado.getLogin());

        List<Map<String, Object>> dados = perfis.stream().map(up -> {
            Map<String, Object> map = new HashMap<>();
            map.put("perfil", up.getPerfil().getNome());
            map.put("instituicao", up.getInstituicao().getNome());
            map.put("idInstituicao", up.getInstituicao().getIdInstituicao());
            map.put("email", up.getEmailUsuario());
            return map;
        }).toList();

        ApiResponse<List<Map<String, Object>>> response = new ApiResponse.Builder<List<Map<String, Object>>>(HttpStatus.OK.value(), "Perfis encontrados").data(dados).build(); 
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    

    
}





