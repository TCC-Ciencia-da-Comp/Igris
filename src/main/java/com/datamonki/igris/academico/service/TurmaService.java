package com.datamonki.igris.academico.service;

import java.util.List;

import com.datamonki.igris.academico.dto.TurmaDto;
import com.datamonki.igris.academico.model.Turma;

public interface TurmaService {

    //Criar turma
    Turma create(TurmaDto turmaDto);

    //Listar todas as turmas
    List<Turma> getAll();

    //Listar turma por id
    Turma getById(Integer idTurma);

    //Listar turmas por nome
    List<Turma> getByNome(String nome);

    //Atualizar turma
    Turma update(Integer idTurma, TurmaDto turmaDto);

    //Deletar turma por id
    Turma deleteById(Integer idTurma);

    //Listar turmas por curso
    List<Turma> deleteAll();

    //Listar turmas por curso
    List<Turma> getByIdCurso(Integer idCurso);

    //Verificar se a turma está em uso
    List<Turma> deleteByIdCurso(Integer idCurso);  
}