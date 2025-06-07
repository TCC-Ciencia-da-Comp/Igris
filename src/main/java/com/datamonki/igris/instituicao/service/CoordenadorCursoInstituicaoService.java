package com.datamonki.igris.instituicao.service;

import java.util.List;

import com.datamonki.igris.instituicao.dto.CoordenadorCursoInstituicaoDto;
import com.datamonki.igris.instituicao.model.CoordenadorCursoInstituicao;

public interface CoordenadorCursoInstituicaoService {

    //Criar coordenador-curso-instituicao
    CoordenadorCursoInstituicao create(CoordenadorCursoInstituicaoDto dto);

    //Criar todos os coordenadores-cursos-instituicoes
    List<CoordenadorCursoInstituicao> createAll(List<CoordenadorCursoInstituicaoDto> dtos);

    //Listar todos os coordenadores-cursos-instituicoes
    List<CoordenadorCursoInstituicao> getAll();

    //Listar coordenadores-cursos-instituicoes por instituicao
    List<CoordenadorCursoInstituicao> getByInstituicaoId(Integer instituicaoId);

    //Deletar coordenador-curso-instituicao por id
    CoordenadorCursoInstituicao deleteById(Integer id);
}
