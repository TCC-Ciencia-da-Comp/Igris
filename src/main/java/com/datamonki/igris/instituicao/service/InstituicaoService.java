package com.datamonki.igris.instituicao.service;

import java.util.List;

import com.datamonki.igris.instituicao.dto.InstituicaoDto;
import com.datamonki.igris.instituicao.model.Instituicao;

public interface InstituicaoService {

    // Criar nova instituição
    Instituicao create(InstituicaoDto instituicaoDto);

    // Obter todas as instituições
    List<Instituicao> getAll();

    // Obter instituição por ID
    Instituicao getById(Integer idInstituicao);

    // Obter instituição por nome
    List<Instituicao> getByNome(String nome);

    // Atualizar instituição
    Instituicao update(Integer idInstituicao, InstituicaoDto instituicaoDto);

    // Deletar instituição por ID
    Instituicao deleteById(Integer idInstituicao);

    // Deletar todas as instituições
    List<Instituicao> deleteAll();
}
