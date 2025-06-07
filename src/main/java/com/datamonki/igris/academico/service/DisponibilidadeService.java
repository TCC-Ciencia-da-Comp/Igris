package com.datamonki.igris.academico.service;

import java.util.List;

import com.datamonki.igris.academico.dto.DisponibilidadeDto;
import com.datamonki.igris.academico.dto.DisponibilidadeLimitadaDto;
import com.datamonki.igris.academico.model.Disponibilidade;

public interface DisponibilidadeService {
	
	//Criar disponibilidade por professor
	Disponibilidade createByProfessor(DisponibilidadeLimitadaDto disponibilidadeDto);
	
	//Criar todas as disponibilidades por professor
	List<Disponibilidade> createAllByProfessor(List<DisponibilidadeLimitadaDto> disponibilidadesDto);  
	
	//Criar disponibilidade por coordenador
	Disponibilidade createByCoordenador(DisponibilidadeDto disponibilidadeDto);
	
	//Criar todas as disponibilidades por coordenador
	List<Disponibilidade> createAllByCoordenador(List<DisponibilidadeDto> disponibilidadesDto);
	
	//Listar todas as disponibilidades por professor, ano, semestre e instituição
	List<Disponibilidade> getAllByIdProfessorAnoSemestreInstituicao(Integer idProfessor, Integer semestre, Integer ano, Integer idInstituicao);
	
	//Listar todas as disponibilidades por ano, semestre e instituição
	List<Disponibilidade> getAllByAnoSemestreInstituicao(Integer semestre, Integer ano, Integer idInstituicao);
	
	//Listar todas as disponibilidades
	List<Disponibilidade> getAll(); 
	
	//Listar todas as disponibilidades por professor
	List<Disponibilidade> getByIdProfessor(Integer idProfessor);
	
	//Deletar todas as disponibilidades por professor
	List<Disponibilidade> deleteByIdProfessor(Integer idProfessor);
	
	//Deletar todas as disponibilidades por professor, ano e semestre
	List<Disponibilidade> deleteByIdProfessorAnoSemestre(Integer idProfessor, Integer semestre, Integer ano);

	//Deletar todas as disponibilidades
	List<Disponibilidade> deleteAll();
}