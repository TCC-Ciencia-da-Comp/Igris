package com.datamonki.igris.academico.service.impl; 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.academico.dto.MatrizDto;
import com.datamonki.igris.academico.model.Matriz;
import com.datamonki.igris.academico.repository.DisciplinaRepository;
import com.datamonki.igris.academico.repository.MatrizRepository;
import com.datamonki.igris.academico.repository.TurmaRepository;
import com.datamonki.igris.academico.service.MatrizService;
import com.datamonki.igris.common.exception.IdNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class MatrizServiceImpl implements MatrizService {

    @Autowired
    private MatrizRepository matrizRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    private void verificarIdMatriz(Integer id) {
        if (!matrizRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possivel encontrar matriz com o Id '" + id + "', verifique e tente novamente"); 
        }
    }

    private void verificarIdDisciplina(Integer id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possivel encontrar disciplina com o Id '" + id + "', verifique e tente novamente"); 
        }
    }

    private void verificarIdTurma(Integer id) {
        if (!turmaRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possivel encontrar turma com o Id '" + id + "', verifique e tente novamente"); 
        }
    }

    
    @Override
    @Transactional
    public Matriz create(MatrizDto matrizDto) {
        verificarIdDisciplina(matrizDto.idDisciplina());
        verificarIdTurma(matrizDto.idTurma());

        Matriz matriz = new Matriz();
        matriz.setDisciplina(disciplinaRepository.findById(matrizDto.idDisciplina()).get());
        matriz.setTurma(turmaRepository.findById(matrizDto.idTurma()).get());
        matrizRepository.save(matriz);
        return matriz;
    }

    @Override
    public List<Matriz> getAll() {
        return matrizRepository.findAll();
    }

    @Override
    public Matriz getById(Integer idMatriz) {
        verificarIdMatriz(idMatriz);
        Matriz matriz = matrizRepository.findById(idMatriz).get();
        return matriz;
    }

    @Override
    public List<Matriz> getByIdTurma(Integer idTurma) {
        verificarIdTurma(idTurma);
        return matrizRepository.findByTurmaIdTurma(idTurma);
    }

    @Override
    public List<Matriz> getByIdDisciplina(Integer idDisciplina) {
        verificarIdDisciplina(idDisciplina);
        return matrizRepository.findByDisciplinaIdDisciplina(idDisciplina);
    }
    
    @Override
    @Transactional
    public Matriz update(Integer idMatriz, MatrizDto matrizDto) {
        verificarIdMatriz(idMatriz);
        verificarIdDisciplina(matrizDto.idDisciplina());
        verificarIdTurma(matrizDto.idTurma());

        Matriz matriz = new Matriz();
        matriz.setIdMatriz(idMatriz);
        matriz.setDisciplina(disciplinaRepository.findById(matrizDto.idDisciplina()).get());
        matriz.setTurma(turmaRepository.findById(matrizDto.idTurma()).get());
        matrizRepository.save(matriz);
        return matriz;
    }

    @Override
    public List<Matriz> deleteAll() {
        List<Matriz> matrizes = matrizRepository.findAll();
        matrizRepository.deleteAll();
        return matrizes;
    }

    @Override
    public Matriz deleteById(Integer idMatriz) {
        verificarIdMatriz(idMatriz);
        Matriz matriz = matrizRepository.findById(idMatriz).get();
        matrizRepository.deleteById(idMatriz);
        return matriz;
    }

    @Override
    public List<Matriz> deleteByIdTurma(Integer idTurma) {
        verificarIdTurma(idTurma);
        List<Matriz> matrizes = matrizRepository.findByTurmaIdTurma(idTurma);
        matrizRepository.deleteByTurmaId(idTurma);
        return matrizes;
    }

    @Override
    public List<Matriz> deleteByIdDisciplina(Integer idDisciplina) {
        verificarIdDisciplina(idDisciplina);
        List<Matriz> matrizes = matrizRepository.findByDisciplinaIdDisciplina(idDisciplina);
        matrizRepository.deleteByDisciplinaId(idDisciplina);
        return matrizes;
    }   
}
