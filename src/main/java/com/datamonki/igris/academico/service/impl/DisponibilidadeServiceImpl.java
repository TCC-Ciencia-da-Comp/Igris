package com.datamonki.igris.academico.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.academico.dto.DisponibilidadeDto;
import com.datamonki.igris.academico.dto.DisponibilidadeLimitadaDto;
import com.datamonki.igris.academico.model.Disponibilidade;
import com.datamonki.igris.academico.repository.DisponibilidadeRepository;
import com.datamonki.igris.academico.repository.TurnoRepository;
import com.datamonki.igris.academico.repository.DiaSemanaRepository;
import com.datamonki.igris.academico.service.DisponibilidadeService;
import com.datamonki.igris.common.exception.IdNotFoundException;
import com.datamonki.igris.common.exception.ValidationException;
import com.datamonki.igris.instituicao.repository.InstituicaoRepository;
import com.datamonki.igris.security.CustomUserDetailService;
import com.datamonki.igris.usuario.dto.UsuarioSecurityDto;
import com.datamonki.igris.usuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class DisponibilidadeServiceImpl implements DisponibilidadeService {
	
	@Autowired
	private DisponibilidadeRepository disponibilidadeRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private InstituicaoRepository instituicaoRepository;

	@Autowired
	private TurnoRepository turnoRepository;

	@Autowired
	private DiaSemanaRepository diaSemanaRepository;


	private void verificarProfessorId(Integer id) {
		if(!usuarioRepository.existsById(id)) {
			throw new IdNotFoundException("Não há professor registrado com o id: "+ id + " verifique e tente novamente");
		}
	}
	
	//Verificar se os atributos passados estão corretos e se os ids de fato existem no banco
	private void verificar(DisponibilidadeDto disponibilidadeDto, Integer idInstituicao) {
	    List<String> messages = new ArrayList<>();

	    // Verificar se o professor existe
	    if (!usuarioRepository.existsById(disponibilidadeDto.usuarioId())) {
	        messages.add("Não há professor registrado com o id: " + disponibilidadeDto.usuarioId() + ". Verifique e tente novamente.");
	    }

	    // Verificar se o turno existe
	    if (!turnoRepository.existsById(disponibilidadeDto.idTurno())) { 
	        messages.add("Não há turno registrado com o id: " + disponibilidadeDto.idTurno() + ". Verifique e tente novamente.");
	    }

	    // Verificar se o dia da semana existe
	    if (!diaSemanaRepository.existsById(disponibilidadeDto.idDiaSemana())) {
	        messages.add("Não há dia da semana registrado com o id: " + disponibilidadeDto.idDiaSemana() + ". Verifique e tente novamente.");
	    }

	    // Verificar se a disponibilidade já está registrada
	    if (disponibilidadeRepository.verifyRepeticao(disponibilidadeDto.usuarioId(), 
	            disponibilidadeDto.idDiaSemana(), disponibilidadeDto.idTurno(), 
	            disponibilidadeDto.semestre(), disponibilidadeDto.ano(), 
	            idInstituicao)) {
	        messages.add("Disponibilidade já registrada.");
	    }

	    // Verificar se o semestre é válido
	    if (disponibilidadeDto.semestre() < 1 || disponibilidadeDto.semestre() > 2) {
	        messages.add("Só são permitidos primeiro e segundo semestre.");
	    }

	    // Se houver mensagens, lançar exceção
	    if (!messages.isEmpty()) {
	        throw new ValidationException(messages);
	    }
	}
	
	
	//Verificar se os atributos passados estão corretos e se os ids de fato existem no banco
	private void verificarProf(DisponibilidadeLimitadaDto disponibilidadeDto, Integer idInstituicao, Integer usuarioId) {
	    List<String> messages = new ArrayList<>();

	    // Verificar se o turno existe
	    if (!turnoRepository.existsById(disponibilidadeDto.idTurno())) {
	        messages.add("Não há turno registrado com o id: " + disponibilidadeDto.idTurno() + ". Verifique e tente novamente.");
	    }

	    // Verificar se o dia da semana existe
	    if (!diaSemanaRepository.existsById(disponibilidadeDto.idDiaSemana())) {
	        messages.add("Não há dia da semana registrado com o id: " + disponibilidadeDto.idDiaSemana() + ". Verifique e tente novamente.");
	    }

	    // Verificar se a disponibilidade já está registrada
	    if (disponibilidadeRepository.verifyRepeticao(usuarioId, 
	            disponibilidadeDto.idDiaSemana(), disponibilidadeDto.idTurno(), 
	            disponibilidadeDto.semestre(), disponibilidadeDto.ano(), 
	            idInstituicao)) {
	        messages.add("Disponibilidade já registrada.");
	    }

	    // Verificar se o semestre é válido
	    if (disponibilidadeDto.semestre() < 1 || disponibilidadeDto.semestre() > 2) {
	        messages.add("Só são permitidos primeiro e segundo semestre.");
	    }

	    // Se houver mensagens, lançar exceção
	    if (!messages.isEmpty()) {
	        throw new ValidationException(messages);
	    }
	}
	
	@Override
	@Transactional
	public Disponibilidade createByProfessor(DisponibilidadeLimitadaDto disponibilidadeDto){  
		
		UsuarioSecurityDto usuarioLogado = CustomUserDetailService.getAuthenticatedUser();
		Integer idInstituicao = usuarioLogado.getIdInstituicao();
		verificarProf(disponibilidadeDto, idInstituicao, usuarioLogado.getId());		
		
		
		Disponibilidade disponibilidade = new Disponibilidade();

		disponibilidade.setUsuarioProfessor(usuarioRepository.findByLogin(usuarioLogado.getLogin()).get()); 
		disponibilidade.setDiaSemana(diaSemanaRepository.findById(disponibilidadeDto.idDiaSemana()).get());
		disponibilidade.setTurno(turnoRepository.findById(disponibilidadeDto.idTurno()).get());
		disponibilidade.setSemestre(disponibilidadeDto.semestre());
		disponibilidade.setAno(disponibilidadeDto.ano());
		disponibilidade.setInstituicao(instituicaoRepository.findById(idInstituicao).get());
		disponibilidadeRepository.save(disponibilidade);
	
		return disponibilidade;
	}

	@Override
	@Transactional
	public List<Disponibilidade> createAllByProfessor(List<DisponibilidadeLimitadaDto> disponibilidadesDto){
		UsuarioSecurityDto usuarioLogado = CustomUserDetailService.getAuthenticatedUser();
		
		
		List<Disponibilidade> disponibilidades = new ArrayList<>();
		
		Integer idInstituicao = usuarioLogado.getIdInstituicao();
		
		for (DisponibilidadeLimitadaDto dto: disponibilidadesDto) {
			
			verificarProf(dto, idInstituicao, usuarioLogado.getId());
			
			Disponibilidade disponibilidade = new Disponibilidade();
			disponibilidade.setUsuarioProfessor(usuarioRepository.findByLogin(usuarioLogado.getLogin()).get());
			disponibilidade.setDiaSemana(diaSemanaRepository.findById(dto.idDiaSemana()).get());
			disponibilidade.setTurno(turnoRepository.findById(dto.idTurno()).get());
			disponibilidade.setSemestre(dto.semestre());
			disponibilidade.setAno(dto.ano());
			disponibilidade.setInstituicao(instituicaoRepository.findById(idInstituicao).get());
			disponibilidades.add(disponibilidade);
		}
		disponibilidadeRepository.saveAll(disponibilidades);
		
		return disponibilidades;
	}
	
	@Override
	@Transactional
	public Disponibilidade createByCoordenador(DisponibilidadeDto disponibilidadeDto){
		
		UsuarioSecurityDto usuarioLogado = CustomUserDetailService.getAuthenticatedUser();
		Integer idInstituicao = usuarioLogado.getIdInstituicao();
		verificar(disponibilidadeDto, idInstituicao);		
		
		
		Disponibilidade disponibilidade = new Disponibilidade();
		disponibilidade.setUsuarioProfessor(usuarioRepository.findById(disponibilidadeDto.usuarioId()).get()); 
		disponibilidade.setDiaSemana(diaSemanaRepository.findById(disponibilidadeDto.idDiaSemana()).get());
		disponibilidade.setTurno(turnoRepository.findById(disponibilidadeDto.idTurno()).get());
		disponibilidade.setSemestre(disponibilidadeDto.semestre());
		disponibilidade.setAno(disponibilidadeDto.ano());
		disponibilidade.setInstituicao(instituicaoRepository.findById(idInstituicao).get());
		disponibilidadeRepository.save(disponibilidade);
	
		return disponibilidade;
	}

	@Override
	@Transactional
	public List<Disponibilidade> createAllByCoordenador(List<DisponibilidadeDto> disponibilidadesDto){
		UsuarioSecurityDto usuarioLogado = CustomUserDetailService.getAuthenticatedUser();
		
		
		List<Disponibilidade> disponibilidades = new ArrayList<>(); 
		
		Integer idInstituicao = usuarioLogado.getIdInstituicao();
		
		for (DisponibilidadeDto dto: disponibilidadesDto) {
			
			verificar(dto, idInstituicao);
			
			Disponibilidade disponibilidade = new Disponibilidade();
			disponibilidade.setUsuarioProfessor(usuarioRepository.findById(dto.usuarioId()).get());
			disponibilidade.setDiaSemana(diaSemanaRepository.findById(dto.idDiaSemana()).get());
			disponibilidade.setTurno(turnoRepository.findById(dto.idTurno()).get());
			disponibilidade.setSemestre(dto.semestre());
			disponibilidade.setAno(dto.ano());
			disponibilidade.setInstituicao(instituicaoRepository.findById(idInstituicao).get());
			disponibilidades.add(disponibilidade);
		}
		disponibilidadeRepository.saveAll(disponibilidades);
		
		return disponibilidades;
	}
		
	@Override
	public List<Disponibilidade> getAllByIdProfessorAnoSemestreInstituicao(Integer idProfessor, Integer semestre, Integer ano, Integer idInstituicao){
		List<Disponibilidade> disponibilidades = disponibilidadeRepository.findDisponibilidadeByProfessorIdAnoSemestreInstituicao(
				idProfessor,ano, semestre, idInstituicao);
		return disponibilidades;
	}
	
	@Override
	public List<Disponibilidade> getAllByAnoSemestreInstituicao(Integer semestre, Integer ano, Integer idInstituicao){
		List<Disponibilidade> disponibilidades = disponibilidadeRepository.findDisponibilidadeByAnoSemestreInstituicao(
				ano, semestre, idInstituicao);
		return disponibilidades;
	}
	
	@Override
	public List<Disponibilidade> getAll(){
		List<Disponibilidade> disponibilidades = disponibilidadeRepository.findAll();
		return disponibilidades;
	}
	
	@Override
	public List<Disponibilidade> getByIdProfessor(Integer idProfessor){
		List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByIdProfessor(idProfessor);
		return disponibilidades;
	}
	
	@Override
	public List<Disponibilidade> deleteByIdProfessor(Integer idProfessor){
		verificarProfessorId(idProfessor);
		if (disponibilidadeRepository.verifyDisponibilidadeProfessor(idProfessor)){
			List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByIdProfessor(idProfessor);
			disponibilidadeRepository.deleteByIdProfessor(idProfessor);

			return disponibilidades;
		}

		return null;
	}
	
	@Override
	public List<Disponibilidade> deleteByIdProfessorAnoSemestre(Integer idProfessor, Integer semestre, Integer ano){
		verificarProfessorId(idProfessor);

		if (disponibilidadeRepository.verifyDisponibilidadeProfessor(idProfessor)){
			List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByIdProfessorAnoSemestre(idProfessor, ano, semestre); 
			disponibilidadeRepository.deleteByIdProfessorAnoSem(idProfessor, ano, semestre);

			return disponibilidades;
		}

		return null;
	}

	@Override
	public List<Disponibilidade> deleteAll(){
		List<Disponibilidade> disponibilidades = disponibilidadeRepository.findAll();
		disponibilidadeRepository.deleteAll();
		return disponibilidades;
	}	
	
}