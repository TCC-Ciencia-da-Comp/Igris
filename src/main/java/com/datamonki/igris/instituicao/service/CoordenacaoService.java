package com.datamonki.igris.instituicao.service;

import java.util.List;

import com.datamonki.igris.instituicao.dto.CoordenacaoDto;
import com.datamonki.igris.instituicao.model.Coordenacao;

public interface CoordenacaoService {

    //Criar coordenacao
    Coordenacao create(CoordenacaoDto coordenacaoDto, Integer idInstituicao);

    //Listar todas as coordenacoes
    List<Coordenacao> getAll(); 

    //Listar coordenacao por id
    Coordenacao getById(Integer idCoordenacao);

    //Atualizar coordenacao
    Coordenacao update(Integer idCoordenacao, CoordenacaoDto coordenacaoDto, Integer idInstituicao);

    //Deletar coordenacao por id
    Coordenacao deleteById(Integer idCoordenacao);

    //Deletar todas as coordenacoes
    List<Coordenacao> deleteAll();
}
