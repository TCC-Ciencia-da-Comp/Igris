package com.datamonki.igris.academico.service;

import java.util.List;

import com.datamonki.igris.academico.dto.DisciplinaDto;
import com.datamonki.igris.academico.model.Disciplina; 

public interface DisciplinaService {
    
    //Criar disciplina
    Disciplina create(DisciplinaDto disciplinaDto);

    //Listar todas as disciplinas
    List<Disciplina> getAll();

    //Listar disciplina por id
    Disciplina getById(Integer idDisciplina);

    //Listar disciplinas por nome
    List<Disciplina> getByNome(String nome);

    //Listar disciplinas por ordem alfabética
    List<Disciplina> getByOrderNome();

    //Atualizar disciplina
    Disciplina update(Integer idDisciplina, DisciplinaDto disciplinaDto);

    //Deletar disciplina por id
    Disciplina deleteById(Integer idDisciplina);

    //Deletar todas as disciplinas
    List<Disciplina> deleteAll();
}