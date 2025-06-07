package com.datamonki.igris.common.notification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;



@Service
public class GeradorDeLinkService {
	@Value("${server.address}")
	private String enderecoServidor;
	
	@Value("${server.port}")
	private String portaServidor;
	
    @Value("${server.servlet.context-path}")
    private String contextPath;
    
    private static String enderecoServidorStatic;
    private static String portaServidorStatic;
    private static String contextPathStatic;
    
    @PostConstruct
    public void init() {
        // Inicializa os campos estáticos com os valores injetados
        enderecoServidorStatic = enderecoServidor;
        portaServidorStatic = portaServidor;
        contextPathStatic = contextPath;
    }
    
    // Aplicando cache ao método gerarLink
    @Cacheable(value = "linksCache", key = "#endPoint", unless = "#result == null")
    public static String gerarLink(String endPoint) {
        // Gerando o link baseado no endpoint
        return "http://" + enderecoServidorStatic + ":" + portaServidorStatic + contextPathStatic + endPoint;
    }
    
    
    @Cacheable(value = "uuidCache", key = "#uuid", unless = "#result == null")
    public String gerarLinkTrocaSenha(String endPoint, String uuid) {
        return "http://" + enderecoServidor + ":" + portaServidor + contextPath + endPoint + uuid;
    }


    
    // Método para limpar o cache
    @CacheEvict(value = "linksCache", key = "#endPoint")
    public void limparCache(String endPoint) {
        System.out.println("Cache limpo para o endpoint: " + endPoint);
    }
}
