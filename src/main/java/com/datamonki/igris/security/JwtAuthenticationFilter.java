package com.datamonki.igris.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.datamonki.igris.usuario.dto.UsuarioSecurityDto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = null;

        // Extrai o token do cookie
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null) {
            try {
                String login = jwtUtil.extractUsername(token);
                String nome = jwtUtil.extractClaim(token, "nome", String.class);
                String perfil = jwtUtil.extractClaim(token, "perfil", String.class);
                String instituicao = jwtUtil.extractClaim(token, "instituicao", String.class);
                Integer idInstituicao = jwtUtil.extractClaim(token, "idInstituicao", Integer.class);
                Integer id = jwtUtil.extractClaim(token, "idInstituicao", Integer.class);
                String email = jwtUtil.extractClaim(token, "email", String.class);
                if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsuarioSecurityDto user = new UsuarioSecurityDto(id, email, nome, "", perfil, instituicao, idInstituicao, email);

                    if (jwtUtil.validateToken(token, email)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (Exception e) {
                System.err.println("Erro ao autenticar token: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
