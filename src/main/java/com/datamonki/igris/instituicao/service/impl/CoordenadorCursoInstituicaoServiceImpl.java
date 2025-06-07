package com.datamonki.igris.instituicao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.academico.repository.CursoRepository;
import com.datamonki.igris.common.exception.IdNotFoundException;
import com.datamonki.igris.common.exception.ValidationException;
import com.datamonki.igris.instituicao.dto.CoordenadorCursoInstituicaoDto;
import com.datamonki.igris.instituicao.model.CoordenadorCursoInstituicao;
import com.datamonki.igris.instituicao.repository.CoordenacaoRepository;
import com.datamonki.igris.instituicao.repository.CoordenadorCursoInstituicaoRepository;
import com.datamonki.igris.instituicao.repository.InstituicaoRepository;
import com.datamonki.igris.instituicao.service.CoordenadorCursoInstituicaoService;
import com.datamonki.igris.usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class CoordenadorCursoInstituicaoServiceImpl implements CoordenadorCursoInstituicaoService { 

    @Autowired
    private CoordenadorCursoInstituicaoRepository coordenadorCursoInstituicaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    // Método para verificar se o coordenador existe
    // private void verificarCoordenador(Integer idCoordenador) {
    //     if (!usuarioRepository.existsById(idCoordenador)) {
    //         throw new IdNotFoundException("Não há coordenador registrado com o id: " + idCoordenador + ". Verifique e tente novamente.");
    //     }
    // }

    // Método para verificar se a coordenação, curso e instituição existem
    private void verificarRelacionamentos(CoordenadorCursoInstituicaoDto dto) {
        List<String> messages = new ArrayList<>();

        if (!coordenacaoRepository.existsById(dto.idCoordenacao())) {
            messages.add("Não há coordenação registrada com o id: " + dto.idCoordenacao() + ". Verifique e tente novamente.");
        }
        if (!cursoRepository.existsById(dto.idCurso())) {
            messages.add("Não há curso registrado com o id: " + dto.idCurso() + ". Verifique e tente novamente.");
        }
        if (!instituicaoRepository.existsById(dto.idInstituicao())) {
            messages.add("Não há instituição registrada com o id: " + dto.idInstituicao() + ". Verifique e tente novamente.");
        }
        if (!usuarioRepository.existsById(dto.idUsuario())) {
            messages.add("Não há coordenador registrado com o id: " + dto.idUsuario() + ". Verifique e tente novamente.");
        }

        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }

    @Override
    @Transactional
    public CoordenadorCursoInstituicao create(CoordenadorCursoInstituicaoDto dto) {
        // Verifica os relacionamentos e a existência dos dados
        verificarRelacionamentos(dto);

        CoordenadorCursoInstituicao coordenadorCursoInstituicao = new CoordenadorCursoInstituicao();
        coordenadorCursoInstituicao.setCoordenacao(coordenacaoRepository.findById(dto.idCoordenacao()).get());
        coordenadorCursoInstituicao.setCurso(cursoRepository.findById(dto.idCurso()).get());
        coordenadorCursoInstituicao.setInstituicao(instituicaoRepository.findById(dto.idInstituicao()).get());
        coordenadorCursoInstituicao.setUsuarioCoordenador(usuarioRepository.findById(dto.idUsuario()).get());
        coordenadorCursoInstituicao.setAtivo(dto.ativo());

        coordenadorCursoInstituicaoRepository.save(coordenadorCursoInstituicao);

        return coordenadorCursoInstituicao;
    }

    @Override
    @Transactional
    public List<CoordenadorCursoInstituicao> createAll(List<CoordenadorCursoInstituicaoDto> dtos) {
        List<CoordenadorCursoInstituicao> coordenadores = new ArrayList<>();
        for (CoordenadorCursoInstituicaoDto dto : dtos) {
            verificarRelacionamentos(dto);
            CoordenadorCursoInstituicao coordenadorCursoInstituicao = new CoordenadorCursoInstituicao();
            coordenadorCursoInstituicao.setCoordenacao(coordenacaoRepository.findById(dto.idCoordenacao()).get());
            coordenadorCursoInstituicao.setCurso(cursoRepository.findById(dto.idCurso()).get());
            coordenadorCursoInstituicao.setInstituicao(instituicaoRepository.findById(dto.idInstituicao()).get());
            coordenadorCursoInstituicao.setUsuarioCoordenador(usuarioRepository.findById(dto.idUsuario()).get());
            coordenadorCursoInstituicao.setAtivo(dto.ativo());
            coordenadores.add(coordenadorCursoInstituicao);
        }
        coordenadorCursoInstituicaoRepository.saveAll(coordenadores);

        return coordenadores;
    }

    @Override
    public List<CoordenadorCursoInstituicao> getAll() {
        return coordenadorCursoInstituicaoRepository.findAll();
    }

    @Override
    public List<CoordenadorCursoInstituicao> getByInstituicaoId(Integer instituicaoId) {
        return coordenadorCursoInstituicaoRepository.findByInstituicaoId(instituicaoId);
    }

    @Override
    @Transactional
    public CoordenadorCursoInstituicao deleteById(Integer id) {
        // Verificar se o registro existe antes de deletar
        CoordenadorCursoInstituicao coordenador = coordenadorCursoInstituicaoRepository.findById(id)
            .orElseThrow(() -> new IdNotFoundException("Não foi possível encontrar o coordenador com o id: " + id));
            
        coordenadorCursoInstituicaoRepository.deleteById(id);
        return coordenador;
    }
}
    