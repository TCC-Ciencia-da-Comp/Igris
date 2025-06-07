package com.datamonki.igris.instituicao.service;

import java.util.List;

import com.datamonki.igris.instituicao.model.ConfigFaculdade;
import com.datamonki.igris.usuario.dto.ConfigDto;


public interface ConfigFaculdadeService {

    //Criar configuração de faculdade
    ConfigFaculdade create(ConfigDto configDto);

    //Listar todas as configurações de faculdade
    List<ConfigFaculdade> getAll();

    //Listar configuração de faculdade por id
    ConfigFaculdade getById(Integer idConfig);

    //Atualizar configuração de faculdade
    ConfigFaculdade update(Integer idConfig, ConfigDto configDto);

    //Deletar configuração de faculdade por id
    ConfigFaculdade deleteById(Integer idConfig); 

    //Deletar todas as configurações de faculdade
    List<ConfigFaculdade> deleteAll();
}
