package com.datamonki.igris.instituicao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

import com.datamonki.igris.common.exception.IdNotFoundException;
import com.datamonki.igris.common.exception.ValidationException;
import com.datamonki.igris.instituicao.dto.CoordenacaoDto;
import com.datamonki.igris.instituicao.model.Coordenacao;
import com.datamonki.igris.instituicao.repository.CoordenacaoRepository;
import com.datamonki.igris.instituicao.service.CoordenacaoService;

import jakarta.transaction.Transactional;

@Service
public class CoordenacaoServiceImpl implements CoordenacaoService { 

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    // @Autowired
    // private InstituicaoRepository instituicaoRepository;  // Supondo que você tenha um serviço para Instituicao.

    private void verificar(CoordenacaoDto coordenacaoDto) {
        List<String> messages = new ArrayList<>();
        if (coordenacaoDto.nome().isBlank()) {
            messages.add("A coluna nome está vazia");
        } else if (coordenacaoDto.nome().length() < 3 || coordenacaoDto.nome().length() > 255) {
            messages.add("A coluna nome deve estar entre 3 a 255 caracteres");
        }
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }

    private void verificarId(Integer id) {
        if (!coordenacaoRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possível encontrar a coordenação com o Id '" + id + "', verifique e tente novamente");
        }
    }

    @Override
    @Transactional
    public Coordenacao create(CoordenacaoDto coordenacaoDto, Integer idInstituicao) {
        verificar(coordenacaoDto);

        // Recupera a instituição associada de forma segura
        // Instituicao instituicao = instituicaoRepository.findById(idInstituicao)
        //     .orElseThrow(() -> new IdNotFoundException("Instituição com o ID " + idInstituicao + " não encontrada"));

        Coordenacao coordenacao = new Coordenacao();
        coordenacao.setNome(coordenacaoDto.nome());
        coordenacao.setAtivo(coordenacaoDto.ativo());
        coordenacaoRepository.save(coordenacao);
        return coordenacao;
    }

    @Override
    public List<Coordenacao> getAll() {
        return coordenacaoRepository.findAll();
    }

    @Override
    public Coordenacao getById(Integer idCoordenacao) {
        verificarId(idCoordenacao);

        return coordenacaoRepository.findById(idCoordenacao).get();
    }

    @Override
    @Transactional
    public Coordenacao update(Integer idCoordenacao, CoordenacaoDto coordenacaoDto, Integer idInstituicao) {
        verificar(coordenacaoDto);
        verificarId(idCoordenacao);

        // Recupera a instituição associada
        // Instituicao instituicao = instituicaoRepository.findById(idInstituicao)
        //     .orElseThrow(() -> new IdNotFoundException("Instituição com o ID " + idInstituicao + " não encontrada"));

        // Recupera a coordenacao e lança exceção caso não seja encontrada
        Coordenacao coordenacao = coordenacaoRepository.findById(idCoordenacao)
            .orElseThrow(() -> new IdNotFoundException("Coordenação com o ID " + idCoordenacao + " não encontrada"));

        coordenacao.setNome(coordenacaoDto.nome());
        coordenacao.setAtivo(coordenacaoDto.ativo());

        coordenacaoRepository.save(coordenacao);
        return coordenacao;
    }

    @Override
    @Transactional
    public Coordenacao deleteById(Integer idCoordenacao) {  
        verificarId(idCoordenacao);

        Coordenacao coordenacao = coordenacaoRepository.findById(idCoordenacao).get();
        coordenacaoRepository.deleteById(idCoordenacao);
        return coordenacao;
    }

    @Override
    public List<Coordenacao> deleteAll() {
        List<Coordenacao> coordenacoes = coordenacaoRepository.findAll();
        coordenacaoRepository.deleteAll();
        return coordenacoes;
    }
}
