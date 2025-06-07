package com.datamonki.igris.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datamonki.igris.common.response.ApiResponse;
import com.datamonki.igris.security.dto.AuthRequestDto;
import com.datamonki.igris.usuario.dto.SenhaDto;
import com.datamonki.igris.usuario.dto.UsuarioSecurityDto;
import com.datamonki.igris.usuario.service.UsuarioService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/public/auth")
public class PublicAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

	@Autowired
	private UsuarioService usuarioService;
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public PublicAuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {  
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.login(), authRequestDto.senha()));

            UsuarioSecurityDto userDetails = (UsuarioSecurityDto) authentication.getPrincipal();

            String token = jwtUtil.generateToken(
                userDetails.getUsername(),            
                userDetails.getNome(),               
                userDetails.getPerfilAtivo(),         
                userDetails.getInstituicaoAtiva(),  
                userDetails.getIdInstituicao(),
                userDetails.getId(),
                userDetails.getEmail()
            );

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // colocar false em dev se necessário
            cookie.setPath("/");
            cookie.setMaxAge(10 * 60 * 60); // 10 horas

            response.addCookie(cookie);

            ApiResponse<String> responseApi = ApiResponse.success("Logado com sucesso", token);
            return ResponseEntity.status(HttpStatus.OK).body(responseApi);
        } catch (Exception e) {
            ApiResponse<String> responseApi = new ApiResponse.Builder<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Falha ao logar").build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseApi); 
        }
    }

	@PostMapping("/esqueci-senha")
	public ResponseEntity<ApiResponse<Void>> esqueciSenha(@RequestParam String email){
		try {
			usuarioService.esqueciSenha(email); 
			ApiResponse<Void> response = new ApiResponse.Builder<Void>(HttpStatus.OK.value(), "Email enviado com sucesso").build();
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			ApiResponse<Void> responseApi = new ApiResponse.Builder<Void>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Falha ao enviar email").build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseApi);
		}
	}
    
	@GetMapping("/trocar-senha/{token}")
	public ResponseEntity<ApiResponse<Void>> validarTokenTrocarSenha(@PathVariable String token, @RequestBody SenhaDto senhaDto) {
		try {
			usuarioService.validarToken(token, senhaDto); 
			ApiResponse<Void> response = new ApiResponse.Builder<Void>(HttpStatus.OK.value(), "Token validado com sucesso").build();
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			ApiResponse<Void> responseApi = new ApiResponse.Builder<Void>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Falha ao validar token").build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseApi);
		}
	}
}