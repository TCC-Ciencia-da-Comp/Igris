package com.datamonki.igris.academico.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.academico.dto.CursoDto;
import com.datamonki.igris.academico.model.Curso;
import com.datamonki.igris.academico.repository.CursoRepository;
import com.datamonki.igris.academico.service.CursoService;
import com.datamonki.igris.common.exception.IdNotFoundException;
import com.datamonki.igris.common.exception.ValidationException;

import jakarta.transaction.Transactional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    private void verificar(CursoDto cursoDto){
		List<String> messages = new ArrayList<>();
		
		if (cursoDto.nome() != null && !cursoDto.nome().isBlank()) {
			messages.add("A coluna nome está vazia");
		}else if (cursoDto.nome().isBlank()) {
			messages.add("A coluna nome está vazia");
		} else if (cursoDto.nome().length() < 3 || cursoDto.nome().length() > 255) {
			messages.add("A coluna nome deve estar entre 3 a 255 caracteres");
		}
		if (!messages.isEmpty()) {
			throw new ValidationException(messages);
		}
    }

	private void verificarId(Integer id) {
		if (!cursoRepository.existsById(id)) {
			throw new IdNotFoundException("Não foi possivel encontrar curso com o Id '" + id + "', verifique e tente novamente"); 
		}
	}

	private void verificarNome(String nome) {
		if (cursoRepository.findByNomeContainingIgnoreCase(nome).isEmpty()) {
			throw new IdNotFoundException("Não foi possivel encontrar o curso com o nome de '" + nome + "', verifique e tente novamente"); 
		}
	}

    
    @Override
    @Transactional
    public Curso create(CursoDto cursoDto){
        verificar(cursoDto);

        Curso curso = new Curso();
        curso.setNome(cursoDto.nome());

        cursoRepository.save(curso);
        
		return curso;
    }

    @Override
    public List<Curso> getAll(){
        List<Curso> cursos = cursoRepository.findAll();

        return cursos;
    }

    @Override
    public Curso getById(Integer idCurso){ 
        verificarId(idCurso);

        Curso curso = cursoRepository.findById(idCurso).get();

        return curso;
    }

    @Override
    public List<Curso> getByNome(String nome){
        verificarNome(nome);

        List<Curso> cursos = cursoRepository.findByNomeContainingIgnoreCase(nome);

        return cursos;
    }

    @Override
    public List<Curso> getByOrderNome(){
        List<Curso> cursos = cursoRepository.findByOrderByNomeAsc();

        return cursos;
    }

    @Override
    @Transactional
    public Curso update(Integer idCurso, CursoDto cursoDto){
        verificar(cursoDto);
        verificarId(idCurso);

        Curso curso = new Curso();
        curso.setIdCurso(idCurso);
        curso.setNome(cursoDto.nome());
        cursoRepository.save(curso);

        return curso;
    }

    @Override
    public Curso deleteById(Integer idCurso){
        verificarId(idCurso);

        Curso curso = cursoRepository.findById(idCurso).get();
        CursoDto cursoDto = new CursoDto(curso.getNome());
        cursoRepository.deleteById(idCurso);

        return curso;
    }

    @Override
    public List<Curso> deleteAll(){
        List<Curso> cursos = cursoRepository.findAll();
        cursoRepository.deleteAll();

        return cursos;
    }
    
    @Override
    public List<Curso> getByIdInstituicao(Integer idInstituicao){
    	List<Curso> cursos = cursoRepository.findCursosByInstituicaoId(idInstituicao);

        return cursos;
    }

}