package com.datamonki.igris.academico.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.academico.dto.TurmaDto;
import com.datamonki.igris.academico.model.Turma;
import com.datamonki.igris.academico.repository.CursoRepository;
import com.datamonki.igris.academico.repository.TurnoRepository;
import com.datamonki.igris.academico.repository.TurmaRepository;
import com.datamonki.igris.academico.service.TurmaService;
import com.datamonki.igris.common.exception.IdNotFoundException;
import com.datamonki.igris.common.exception.ValidationException;

import jakarta.transaction.Transactional;

@Service
public class TurmaServiceImpl implements TurmaService { 

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TurnoRepository turnoRepository; 

    private void verificar(TurmaDto turmaDto){
        List<String> messages = new ArrayList<>();
        
        if (turmaDto.nome().isBlank()) {
            messages.add("A coluna nome está vazia");
        } else if (turmaDto.nome().length() < 1 || turmaDto.nome().length() > 255) {
            messages.add("A coluna nome deve estar entre 1 a 255 caracteres");
        }

        if (turmaDto.semestre() < 1 || turmaDto.semestre() > 2) {
            messages.add("O semestre deve ser 1 ou 2");
        }

        if (turmaDto.ano() < 2010 || turmaDto.ano() > 2050) {
            messages.add("O ano deve estar entre 2010 e 2050");
        }

        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }

    private void verificarIdTurma(Integer id) {
        if (!turmaRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possivel encontrar turma com o Id '" + id + "', verifique e tente novamente"); 
        }
    }

    private void verificarIdCurso(Integer id) {
        if (!cursoRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possivel encontrar curso com o Id '" + id + "', verifique e tente novamente"); 
        }
    }

    private void verificarIdTurno(Integer id) {
        if (!turnoRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possivel encontrar turno com o Id '" + id + "', verifique e tente novamente"); 
        }
    }

    @Override
    @Transactional
    public Turma create(TurmaDto turmaDto){
        verificarIdCurso(turmaDto.idCurso());
        verificarIdTurno(turmaDto.idTurno());
        verificar(turmaDto);

        Turma turma = new Turma();
        turma.setNome(turmaDto.nome());
        turma.setSemestre(turmaDto.semestre());
        turma.setAno(turmaDto.ano());
        turma.setCurso(cursoRepository.findById(turmaDto.idCurso()).get());
        turma.setTurno(turnoRepository.findById(turmaDto.idTurno()).get());
        
        turmaRepository.save(turma);
        return turma;
    }

    @Override
    public List<Turma> getAll(){
        return turmaRepository.findAll();
    }

    @Override
    public Turma getById(Integer idTurma){
        verificarIdTurma(idTurma);

        Turma turma = turmaRepository.findById(idTurma).get();
        return turma;
    }   

    @Override
    public List<Turma> getByNome(String nome){
        if (turmaRepository.findByNome(nome).isEmpty()) {
            throw new IdNotFoundException("Não foi possivel encontrar turma com o nome '" + nome + "', verifique e tente novamente"); 
        }

        List<Turma> turmas = turmaRepository.findByNome(nome);
        return turmas;
    }

    @Override
    @Transactional
    public Turma update(Integer idTurma, TurmaDto turmaDto){ 
        verificarIdTurma(idTurma);
        verificarIdCurso(turmaDto.idCurso());
        verificarIdTurno(turmaDto.idTurno());
        verificar(turmaDto);

        Turma turma = turmaRepository.findById(idTurma).get();
        turma.setNome(turmaDto.nome());
        turma.setSemestre(turmaDto.semestre());
        turma.setAno(turmaDto.ano());
        turma.setCurso(cursoRepository.findById(turmaDto.idCurso()).get());
        turma.setTurno(turnoRepository.findById(turmaDto.idTurno()).get());
        
        turmaRepository.save(turma);
        return turma;
    }
    
    @Override
    public Turma deleteById(Integer idTurma){
        verificarIdTurma(idTurma);
        
        Turma turma = turmaRepository.findById(idTurma).get();
        turmaRepository.deleteById(idTurma);
        return turma;
    }
    
    @Override
    public List<Turma> deleteAll(){
        List<Turma> turmas = turmaRepository.findAll();
        turmaRepository.deleteAll();
        return turmas;
    }

    @Override
    public List<Turma> getByIdCurso(Integer idCurso) {  
        verificarIdCurso(idCurso);
        
        List<Turma> turmas = turmaRepository.findByCursoIdCurso(idCurso);
        return turmas;
    }

    @Override
    @Transactional
    public List<Turma> deleteByIdCurso(Integer idCurso) {  
        verificarIdCurso(idCurso);
        
        List<Turma> turmas = turmaRepository.findByCursoIdCurso(idCurso);
        turmaRepository.deleteByCursoId(idCurso);
        return turmas;
    }
}