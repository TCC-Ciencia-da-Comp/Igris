package com.datamonki.igris.academico.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.academico.dto.DisciplinaCursoDto;
import com.datamonki.igris.academico.model.DisciplinaCurso;
import com.datamonki.igris.academico.repository.CursoRepository;
import com.datamonki.igris.academico.repository.DisciplinaCursoRepository;
import com.datamonki.igris.academico.repository.DisciplinaRepository;
import com.datamonki.igris.academico.service.DisciplinaCursoService;
import com.datamonki.igris.common.exception.IdNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class DisciplinaCursoServiceImpl implements DisciplinaCursoService {

    @Autowired
    private DisciplinaCursoRepository disciplinaCursoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    private void verificarIdDisciplinaCurso(Integer id) {
        if (!disciplinaCursoRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possivel encontrar disciplina-curso com o Id '" + id + "', verifique e tente novamente"); 
        }
    }

    private void verificarIdDisciplina(Integer id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possivel encontrar disciplina com o Id '" + id + "', verifique e tente novamente"); 
        }
    }

    private void verificarIdCurso(Integer id) {
        if (!cursoRepository.existsById(id)) {
            throw new IdNotFoundException("Não foi possivel encontrar curso com o Id '" + id + "', verifique e tente novamente"); 
        }
    }
    
    
    @Override
    @Transactional
    public DisciplinaCurso create(DisciplinaCursoDto disciplinaCursoDto){ 
        verificarIdDisciplina(disciplinaCursoDto.idDisciplina());
        verificarIdCurso(disciplinaCursoDto.idCurso());

        DisciplinaCurso disciplinaCurso = new DisciplinaCurso();
        disciplinaCurso.setDisciplina(disciplinaRepository.findById(disciplinaCursoDto.idDisciplina()).get());
        disciplinaCurso.setCurso(cursoRepository.findById(disciplinaCursoDto.idCurso()).get());

        disciplinaCursoRepository.save(disciplinaCurso);

        return disciplinaCurso;
    }

    @Override
    public List<DisciplinaCurso> getAll(){
        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoRepository.findAll();

        return disciplinaCursos;
    }

    @Override
    public DisciplinaCurso getById(Integer idDisciplinaCurso){
        verificarIdDisciplinaCurso(idDisciplinaCurso);

        DisciplinaCurso disciplinaCurso = disciplinaCursoRepository.findById(idDisciplinaCurso).get();

        return disciplinaCurso;
    }

    @Override
    public List<DisciplinaCurso> getByIdDisciplina(Integer idDisciplina){
        verificarIdDisciplina(idDisciplina);

        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoRepository.findByDisciplinaIdDisciplina(idDisciplina);

        return disciplinaCursos;
    }

    @Override
    public List<DisciplinaCurso> getByIdCurso(Integer idCurso){
        verificarIdCurso(idCurso);

        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoRepository.findByCursoIdCurso(idCurso);

        return disciplinaCursos;
    }

    @Override
    @Transactional
    public DisciplinaCurso update(Integer idDisciplinaCurso, DisciplinaCursoDto disciplinaCursoDto){
        verificarIdDisciplinaCurso(idDisciplinaCurso);
        verificarIdDisciplina(disciplinaCursoDto.idDisciplina());
        verificarIdCurso(disciplinaCursoDto.idCurso());

        DisciplinaCurso disciplinaCurso = new DisciplinaCurso();
        disciplinaCurso.setIdDisciplinaCurso(idDisciplinaCurso);
        disciplinaCurso.setDisciplina(disciplinaRepository.findById(disciplinaCursoDto.idDisciplina()).get());
        disciplinaCurso.setCurso(cursoRepository.findById(disciplinaCursoDto.idCurso()).get());
        
        disciplinaCursoRepository.save(disciplinaCurso);

        return disciplinaCurso;
    }

    @Override
    public List<DisciplinaCurso> deleteAll(){
        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoRepository.findAll();
        disciplinaCursoRepository.deleteAll();

        return disciplinaCursos;
    }

    @Override
    public DisciplinaCurso deleteById(Integer idDisciplinaCurso){
        verificarIdDisciplinaCurso(idDisciplinaCurso);

        DisciplinaCurso disciplinaCurso = disciplinaCursoRepository.findById(idDisciplinaCurso).get();
        disciplinaCursoRepository.deleteById(idDisciplinaCurso);

        return disciplinaCurso;
    }

    @Override
    public List<DisciplinaCurso> deleteByCursoId(Integer idCurso){
        verificarIdCurso(idCurso);

        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoRepository.findByCursoIdCurso(idCurso);
        disciplinaCursoRepository.deleteByCursoIdCurso(idCurso);

        return disciplinaCursos;
    }

    @Override
    public List<DisciplinaCurso> deleteByDisciplinaId(Integer idDisciplina){
        verificarIdDisciplina(idDisciplina);

        List<DisciplinaCurso> disciplinaCursos = disciplinaCursoRepository.findByDisciplinaIdDisciplina(idDisciplina);
        disciplinaCursoRepository.deleteByDisciplinaIdDisciplina(idDisciplina);

        return disciplinaCursos;
    }
}