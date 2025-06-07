package com.datamonki.igris.instituicao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.common.exception.IdNotFoundException;
import com.datamonki.igris.common.exception.ValidationException;
import com.datamonki.igris.instituicao.dto.InstituicaoDto;
import com.datamonki.igris.instituicao.model.Instituicao;
import com.datamonki.igris.instituicao.repository.InstituicaoRepository;
import com.datamonki.igris.instituicao.service.InstituicaoService;

import jakarta.transaction.Transactional;

@Service
public class InstituicaoServiceImpl implements InstituicaoService { 

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    // Método de verificação para validar o InstituicaoDto
    private void verificar(InstituicaoDto instituicaoDto){
        List<String> messages = new ArrayList<>();
        if (instituicaoDto.nome().isBlank()) {
            messages.add("A coluna nome está vazia");
        } else if (instituicaoDto.nome().length() < 3 || instituicaoDto.nome().length() > 255) {
            messages.add("A coluna nome deve estar entre 3 a 255 caracteres");
        }
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }

    // Método para verificar se o ID existe
    private void verificarId(Integer id) {
        if (!instituicaoRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possível encontrar a instituição com o Id '" + id + "', verifique e tente novamente");
        }
    }

    // Método para verificar se o nome da instituição já existe
    private void verificarNome(String nome) {
        if (instituicaoRepository.findByNomeContainingIgnoreCase(nome).isEmpty()) {
            throw new IdNotFoundException("Não foi possível encontrar a instituição com o nome de '" + nome + "', verifique e tente novamente");
        }
    }

    // Criar nova instituição
    @Override
    @Transactional
    public Instituicao create(InstituicaoDto instituicaoDto) {
        verificar(instituicaoDto);

        Instituicao instituicao = new Instituicao();
        instituicao.setNome(instituicaoDto.nome());

        instituicaoRepository.save(instituicao);
        return instituicao;
    }

    // Obter todas as instituições
    @Override
    public List<Instituicao> getAll() {
        return instituicaoRepository.findAll();
    }

    // Obter instituição por ID
    @Override
    public Instituicao getById(Integer idInstituicao) {
        verificarId(idInstituicao);

        return instituicaoRepository.findById(idInstituicao).get();
    }

    // Obter instituição por nome
    @Override
    public List<Instituicao> getByNome(String nome) {
        verificarNome(nome);

        return instituicaoRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Atualizar instituição
    @Override
    @Transactional
    public Instituicao update(Integer idInstituicao, InstituicaoDto instituicaoDto) {
        verificar(instituicaoDto);
        verificarId(idInstituicao);

        Instituicao instituicao = new Instituicao();
        instituicao.setIdInstituicao(idInstituicao);
        instituicao.setNome(instituicaoDto.nome());
        instituicaoRepository.save(instituicao);
        return instituicao;
    }

    // Deletar instituição por ID
    @Override
    public Instituicao deleteById(Integer idInstituicao) {
        verificarId(idInstituicao);

        Instituicao instituicao = instituicaoRepository.findById(idInstituicao).get();
        // InstituicaoDto instituicaoDto = new InstituicaoDto(instituicao.getNome());
        instituicaoRepository.deleteById(idInstituicao);
        return instituicao;
    }

    // Deletar todas as instituições
    @Override
    public List<Instituicao> deleteAll() { 
        List<Instituicao> instituicoes = instituicaoRepository.findAll();
        instituicaoRepository.deleteAll();
        return instituicoes;
    }
}
