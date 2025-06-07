package com.datamonki.igris.usuario.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.academico.repository.DisciplinaRepository;
import com.datamonki.igris.common.exception.IdNotFoundException;
import com.datamonki.igris.security.CustomUserDetailService;
import com.datamonki.igris.usuario.dto.UsuarioDisciplinaDto;
import com.datamonki.igris.usuario.dto.UsuarioSecurityDto;
import com.datamonki.igris.usuario.model.UsuarioDisciplina;
import com.datamonki.igris.usuario.repository.UsuarioDisciplinaRepository;
import com.datamonki.igris.usuario.repository.UsuarioRepository;
import com.datamonki.igris.usuario.service.UsuarioDisciplinaService;

import jakarta.transaction.Transactional;

@Service
public class UsuarioDisciplinaServiceImpl implements UsuarioDisciplinaService {

    @Autowired
    private UsuarioDisciplinaRepository usuarioDisciplinaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    private void verificarIdUsuarioDisciplina(Integer id) {
        if (!usuarioDisciplinaRepository.existsById(id)) {
            throw new IdNotFoundException(
                    "Não foi possível encontrar UsuarioDisciplina com o Id '" + id + "', verifique e tente novamente");
        }
    }

    private void verificarIdUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IdNotFoundException(
                    "Não foi possível encontrar usuário com o Id '" + id + "', verifique e tente novamente");
        }
    }

    private void verificarIdDisciplina(Integer id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new IdNotFoundException(
                    "Não foi possível encontrar disciplina com o Id '" + id + "', verifique e tente novamente");
        }
    }

    @Override
    @Transactional
    public UsuarioDisciplina create(UsuarioDisciplinaDto dto) {
        verificarIdUsuario(dto.idUsuario());
        verificarIdDisciplina(dto.idDisciplina());

        UsuarioDisciplina usuarioDisciplina = new UsuarioDisciplina();
        usuarioDisciplina.setUsuario(usuarioRepository.findById(dto.idUsuario()).get());
        usuarioDisciplina.setDisciplina(disciplinaRepository.findById(dto.idDisciplina()).get());
        usuarioDisciplinaRepository.save(usuarioDisciplina);
        return usuarioDisciplina;
    }

    @Override
    @Transactional
    public UsuarioDisciplina createByUsuarioProfessor(UsuarioDisciplinaDto dto) {
        UsuarioSecurityDto usuarioLogado = CustomUserDetailService.getAuthenticatedUser();
        verificarIdDisciplina(dto.idDisciplina());

        UsuarioDisciplina usuarioDisciplina = new UsuarioDisciplina();
        usuarioDisciplina.setUsuario(usuarioRepository.findById(usuarioLogado.getId()).get());
        usuarioDisciplina.setDisciplina(disciplinaRepository.findById(dto.idDisciplina()).get());
        usuarioDisciplinaRepository.save(usuarioDisciplina);
        return usuarioDisciplina;
    }

    @Override
    @Transactional
    public List<UsuarioDisciplina> createAllByUsuarioProfessor(
            List<UsuarioDisciplinaDto> dtos) {
        UsuarioSecurityDto usuarioLogado = CustomUserDetailService.getAuthenticatedUser();

        List<UsuarioDisciplina> usuarioDisciplinas = new ArrayList<>();
        for (UsuarioDisciplinaDto dto : dtos) {
            verificarIdDisciplina(dto.idDisciplina());

            UsuarioDisciplina usuarioDisciplina = new UsuarioDisciplina();
            usuarioDisciplina.setUsuario(usuarioRepository.findById(usuarioLogado.getId()).get());
            usuarioDisciplina.setDisciplina(disciplinaRepository.findById(dto.idDisciplina()).get());
            usuarioDisciplinas.add(usuarioDisciplina);
        }
        usuarioDisciplinaRepository.saveAll(usuarioDisciplinas);
        return usuarioDisciplinas;
    }

    @Override
    public List<UsuarioDisciplina> getAll() { 
        List<UsuarioDisciplina> lista = usuarioDisciplinaRepository.findAll();
        return lista;
    }

    @Override
    public UsuarioDisciplina getById(Integer id) {
        verificarIdUsuarioDisciplina(id);
        UsuarioDisciplina ud = usuarioDisciplinaRepository.findById(id).get();
        return ud;
    }

    @Override
    public List<UsuarioDisciplina> getByUsuarioId(Integer idUsuario) {
        verificarIdUsuario(idUsuario);
        List<UsuarioDisciplina> lista = usuarioDisciplinaRepository.findByUsuarioIdUsuario(idUsuario);
        return lista;
    }

    @Override
    public List<UsuarioDisciplina> deleteAll() {
        List<UsuarioDisciplina> lista = usuarioDisciplinaRepository.findAll();
        usuarioDisciplinaRepository.deleteAll();
        return lista;
    }

    @Override
    public UsuarioDisciplina deleteById(Integer id) {
        verificarIdUsuarioDisciplina(id);
        UsuarioDisciplina ud = usuarioDisciplinaRepository.findById(id).get();
        usuarioDisciplinaRepository.deleteById(id);
        return ud;
    }

    @Override
    public List<UsuarioDisciplina> deleteByUsuarioId(Integer idUsuario) {
        verificarIdUsuario(idUsuario);
        List<UsuarioDisciplina> lista = usuarioDisciplinaRepository.findByUsuarioIdUsuario(idUsuario);
        usuarioDisciplinaRepository.deleteAll(lista);
        return lista;
    }

    @Override
    public List<UsuarioDisciplina> deleteByDisciplinaId(Integer idDisciplina) {
        verificarIdDisciplina(idDisciplina);
        // Criar método no repository para buscar por idDisciplina se necessário
        List<UsuarioDisciplina> lista = usuarioDisciplinaRepository.findByDisciplinaIdDisciplina(idDisciplina);
        usuarioDisciplinaRepository.deleteAll(lista);
        return lista;
    }
}