package com.datamonki.igris.academico.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.academico.dto.DisciplinaDto;
import com.datamonki.igris.academico.model.Disciplina;
import com.datamonki.igris.academico.repository.DisciplinaRepository; 
import com.datamonki.igris.academico.service.DisciplinaService;
import com.datamonki.igris.common.exception.IdNotFoundException;
import com.datamonki.igris.common.exception.ValidationException;

import jakarta.transaction.Transactional;

@Service
public class DisciplinaServiceImpl implements DisciplinaService { 

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    private void verificar(DisciplinaDto disciplinaDto){
        List<String> messages = new ArrayList<>();
        if (disciplinaDto.nome().isBlank()) {
            messages.add("A coluna nome está vazia");
        } else if (disciplinaDto.nome().length() < 3 || disciplinaDto.nome().length() > 255) {
            messages.add("A coluna nome deve estar entre 3 a 255 caracteres");
        }
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }

    private void verificarId(Integer id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possivel encontrar disciplina com o Id '" + id + "', verifique e tente novamente"); 
        }
    }

    private void verificarNome(String nome) {
        if (disciplinaRepository.findByNomeContainingIgnoreCase(nome).isEmpty()) {
            throw new IdNotFoundException("Não foi possivel encontrar a disciplina com o nome de '" + nome + "', verifique e tente novamente"); 
        }
    }

    
    @Override
    @Transactional
    public Disciplina create(DisciplinaDto disciplinaDto){
        verificar(disciplinaDto);

        Disciplina disciplina = new Disciplina();
        disciplina.setNome(disciplinaDto.nome());

        disciplinaRepository.save(disciplina);

        return disciplina;
    }

    @Override
    public List<Disciplina> getAll(){
        List<Disciplina> disciplinas = disciplinaRepository.findAll();

        return disciplinas;
    }

    @Override
    public Disciplina getById(Integer idDisciplina){
        verificarId(idDisciplina);

        Disciplina disciplina = disciplinaRepository.findById(idDisciplina).get();

        return disciplina;
    }

    @Override
    public List<Disciplina> getByNome(String nome){
        verificarNome(nome);

        List<Disciplina> disciplina = disciplinaRepository.findByNomeContainingIgnoreCase(nome);

        return disciplina;
    }

    @Override
    public List<Disciplina> getByOrderNome(){
        List<Disciplina> disciplinas = disciplinaRepository.findByOrderByNomeAsc();

        return disciplinas;
    }

    @Override
    @Transactional
    public Disciplina update(Integer idDisciplina, DisciplinaDto disciplinaDto){
        verificar(disciplinaDto);
        verificarId(idDisciplina);

        Disciplina disciplina = new Disciplina();
        disciplina.setIdDisciplina(idDisciplina);
        disciplina.setNome(disciplinaDto.nome());
        disciplinaRepository.save(disciplina);

        return disciplina;
    }

    @Override
    public Disciplina deleteById(Integer idDisciplina){
        verificarId(idDisciplina);

        Disciplina disciplina = disciplinaRepository.findById(idDisciplina).get();
        disciplinaRepository.deleteById(idDisciplina);

        return disciplina;
    }

    @Override
    public List<Disciplina> deleteAll(){
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        disciplinaRepository.deleteAll();
        
        return disciplinas;
    }
}