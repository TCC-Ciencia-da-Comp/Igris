package com.datamonki.igris.usuario.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.datamonki.igris.common.exception.ValidationException;
import com.datamonki.igris.common.notification.model.EmailRecuperacao; 
import com.datamonki.igris.common.notification.service.EnviarEmailService;
import com.datamonki.igris.common.notification.service.GeradorDeLinkService;
import com.datamonki.igris.instituicao.model.Instituicao;
import com.datamonki.igris.instituicao.repository.InstituicaoRepository;
import com.datamonki.igris.security.CustomUserDetailService;
import com.datamonki.igris.usuario.dto.SenhaDto;
import com.datamonki.igris.usuario.dto.UsuarioDto;
import com.datamonki.igris.usuario.dto.UsuarioOverViewDto;
import com.datamonki.igris.usuario.dto.UsuarioSecurityDto;
import com.datamonki.igris.usuario.dto.UsuarioTrocaSenhaDto;
import com.datamonki.igris.usuario.model.Perfil;
import com.datamonki.igris.usuario.model.PerfilEnum;
import com.datamonki.igris.usuario.model.Usuario;
import com.datamonki.igris.usuario.model.UsuarioPerfil;
import com.datamonki.igris.usuario.repository.PerfilRepository;
import com.datamonki.igris.usuario.repository.UsuarioPerfilRepository;
import com.datamonki.igris.usuario.repository.UsuarioRepository;
import com.datamonki.igris.usuario.service.UsuarioService;


@Service
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EnviarEmailService emailService;
	
	@Autowired
	private GeradorDeLinkService geradorDeLinkService;
	
	@Autowired
    private CacheManager cacheManager;  // CacheManager para acessar o cache

	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private InstituicaoRepository instituicaoRepository;
	
	
	private void verificarUsuario(UsuarioDto usuarioDto) {
		List<String> messages = new ArrayList<>();
		if (usuarioDto.login().isBlank()) {
			messages.add("O campo  email está vazio");
		}else if (usuarioDto.password().isBlank()) {
			messages.add("O campo  password está vazio");
		}else if(usuarioRepository.findByLogin(usuarioDto.login()).isPresent()) {
			messages.add("Email já cadastrado");
		}else if(usuarioDto.password().length() < 5) {
			messages.add("A senha precisa ter mais de 5 caracateres");
		}	
		
		if (!messages.isEmpty()) {
			throw new ValidationException(messages);
		}
	
	}
	
	@Override
	public Usuario save (UsuarioDto usuarioDto){
		verificarUsuario(usuarioDto);
		String senhaCodificada = passwordEncoder.encode(usuarioDto.password());
		Usuario usuario = new Usuario();
		usuario.setLogin(usuarioDto.login());
		usuario.setNome(usuarioDto.nome());
		usuario.setSenha(senhaCodificada);
		usuarioRepository.save(usuario);
		return usuario;
		
	}
	
	@Override
	public Usuario saveComPerfil (UsuarioDto usuarioDto, PerfilEnum nomePerfl, Integer idInstituicao){
		verificarUsuario(usuarioDto);
		String senhaCodificada = passwordEncoder.encode(usuarioDto.password());
		Usuario usuario = new Usuario();
		usuario.setLogin(usuarioDto.login());
		usuario.setNome(usuarioDto.nome());
		usuario.setSenha(senhaCodificada);
		usuarioRepository.save(usuario);
		
		
		Perfil perfil = perfilRepository.findByNome(nomePerfl.name())//IMPORTANTE definirá o que serpá 
		        .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

		Instituicao instituicao = instituicaoRepository.findById(idInstituicao)
				 .orElseThrow(() -> new RuntimeException("Instituicao não encontrada"));
		
		    UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
		    usuarioPerfil.setUsuario(usuario);
		    usuarioPerfil.setPerfil(perfil);
		    usuarioPerfil.setInstituicao(instituicao);
		    usuarioPerfilRepository.save(usuarioPerfil);

		return usuario;
		
	}

	@Override
	public List<Usuario> getAll() {
		
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios;
	}
	
	//Usuários simples que tem apenas informações padrão exibidas para controle
	@Override
	public List<UsuarioOverViewDto> getAllUsuariosSimples(){
		List <UsuarioOverViewDto> usuarios = usuarioRepository.findAll().stream()
										.map(usuario->new UsuarioOverViewDto(usuario)).
										collect(Collectors.toList());
		
		return usuarios;
	}
	
	@Override
	public List<UsuarioOverViewDto> getAllUsuariosCoordenador(Integer idInstituicao){ 
		//Usuario usuarioLogado = AuthController.getAuthenticatedUser();
		List <UsuarioOverViewDto> usuarios = usuarioRepository.findByPerfilNomeAndInstituicaoId(PerfilEnum.ACESSO_COORDENADOR.getNome(), idInstituicao).stream()
				.map(usuario->new UsuarioOverViewDto(usuario)).
				collect(Collectors.toList());
		
		return usuarios;
	}
	
	//Será exibido usado com o usuário autenticado que deseje mudar sua senha
	@Override
	public Void updateSenha(UsuarioTrocaSenhaDto dto) {

		
		UsuarioSecurityDto usuarioLogado = CustomUserDetailService.getAuthenticatedUser();
		
		
	    if (!dto.senhaNova().equals(dto.senhaConfirmada())) {
	        throw new Error("As senhas estão diferentes");
	    }
	    	    
	    String senhaArmazenadaNoBanco = usuarioLogado.getSenha(); 
	    
	    if (!passwordEncoder.matches(dto.senhaAntiga(), senhaArmazenadaNoBanco)) {
	        throw new Error("A senha antiga está incorreta");
	    }

	    String senhaCodificada = passwordEncoder.encode(dto.senhaNova());

	    usuarioRepository.updateSenha(usuarioLogado.getLogin(), senhaCodificada);

	    return null;
	}
	
	@Override
	public Void esqueciSenha(String email){
		if(!usuarioPerfilRepository.existsByEmailUsuario(email)) {
			throw new Error("Email não cadastrado na base");
		}
		
		Usuario usuario = usuarioRepository.findByLogin(email).get();
		
		
		 UUID uuid = UUID.randomUUID();
		 String endPoint = "/public/auth/trocar-senha/";
		
		 String emailCodificado = Base64.getUrlEncoder().encodeToString(email.getBytes());
		 
		 String token = uuid.toString() + "-"+ emailCodificado;
		 
		 String linkRecuperacao = geradorDeLinkService.gerarLinkTrocaSenha( endPoint, token);
		
		 EmailRecuperacao emailRecuperacao = new EmailRecuperacao(email, usuario.getNome(), linkRecuperacao);
		 try {
			 emailService.enviarEmailRecuperacao(emailRecuperacao);
		 }catch(Exception ex) {
			 throw new Error(ex.getMessage());
		 }
		 
		 return null;
	}
	
	@Override
	public Void validarToken (String token, SenhaDto senhaDto){

		// Acessa o cache para verificar se o UUID está armazenado
	    Cache cache = cacheManager.getCache("uuidCache");
	    
	    // Verifica se o UUID está presente no cache
	    if (cache != null && cache.get(token) != null) {
	    	if(!senhaDto.senhaNova().equals(senhaDto.senhaConfirmada())) {
	    		throw new Error("As senhas diferem");
	    	}
	    		    	
	    	
	    	String[] partes = token.split("-"); 
		    // String uuid = String.join("-", Arrays.copyOfRange(partes, 0, 5));
		    // A parte do email codificado é o resto
		    String emailCodificado = partes[5];
		    // Decodifica o email
		    String email = new String(Base64.getUrlDecoder().decode(emailCodificado));
	    	
		    String senhaCodificada = passwordEncoder.encode(senhaDto.senhaConfirmada());
		    
		    usuarioRepository.updateSenha(email, senhaCodificada);
		    
	        return null;
	    } else {
	        throw new Error("UUID inválido ou expirado.");
	    }

	}
	
}
