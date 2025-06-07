package com.datamonki.igris.academico.service;

import java.util.List;

import com.datamonki.igris.academico.dto.MatrizDto;
import com.datamonki.igris.academico.model.Matriz; 

public interface MatrizService {
    
    //Criar matriz
    Matriz create(MatrizDto matrizDto);

    //Listar todas as matrizes
    List<Matriz> getAll();

    //Listar matriz por id
    Matriz getById(Integer idMatriz);

    //Listar matrizes por turma
    List<Matriz> getByIdTurma(Integer idTurma);

    //Listar matrizes por disciplina
    List<Matriz> getByIdDisciplina(Integer idDisciplina);
    
    //Atualizar matriz
    Matriz update(Integer idMatriz, MatrizDto matrizDto);

    //Deletar todas as matrizes
    List<Matriz> deleteAll();

    //Deletar matriz por id
    Matriz deleteById(Integer idMatriz);

    //Deletar matrizes por turma
    List<Matriz> deleteByIdTurma(Integer idTurma);

    //Deletar matrizes por disciplina
    List<Matriz> deleteByIdDisciplina(Integer idDisciplina);
}
