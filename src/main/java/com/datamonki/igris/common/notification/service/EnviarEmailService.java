package com.datamonki.igris.common.notification.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.datamonki.igris.common.notification.model.EmailRecuperacao;  

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EnviarEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void enviarEmailRecuperacao(EmailRecuperacao emailRecuperacao) throws MessagingException {
        Context context = new Context();
        context.setVariable("nome", emailRecuperacao.getNome());
        context.setVariable("url", emailRecuperacao.getUrl());
        String conteudoHtml = templateEngine.process("template-recuperacao", context);

        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(emailRecuperacao.getEmailPara());
        helper.setSubject("Redefinição de Senha"); 
        helper.setText(conteudoHtml, true); 

        mailSender.send(mensagem);

    }
}