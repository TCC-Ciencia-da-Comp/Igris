package com.datamonki.igris.instituicao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamonki.igris.common.exception.IdNotFoundException;
import com.datamonki.igris.common.exception.ValidationException;
import com.datamonki.igris.instituicao.model.ConfigFaculdade;
import com.datamonki.igris.instituicao.repository.ConfigFaculdadeRepository;
import com.datamonki.igris.instituicao.service.ConfigFaculdadeService;
import com.datamonki.igris.usuario.dto.ConfigDto;

import jakarta.transaction.Transactional;

@Service
public class ConfigFaculdadeServiceImpl implements ConfigFaculdadeService { 

    @Autowired
    private ConfigFaculdadeRepository configFaculdadeRepository;

    private void verificar(ConfigDto configDto){
        List<String> mensagens = new ArrayList<>();

        if (configDto.campo().isBlank()) {
            mensagens.add("O campo 'campo' está vazio");
        } else if (configDto.campo().length() < 3 || configDto.campo().length() > 255) {
            mensagens.add("O campo 'campo' deve conter entre 3 e 255 caracteres");
        }

        if (configDto.descricao().isBlank()) {
            mensagens.add("O campo 'descricao' está vazio");
        }

        if (!mensagens.isEmpty()) {
            throw new ValidationException(mensagens);
        }
    }

    private void verificarId(Integer id) {
        if (!configFaculdadeRepository.existsById(id)) {
            throw new IdNotFoundException("Configuração com id '" + id + "' não encontrada.");
        }
    }

    @Override
    @Transactional
    public ConfigFaculdade create(ConfigDto configDto){
        verificar(configDto);

        ConfigFaculdade configFaculdade = new ConfigFaculdade();
        configFaculdade.setCampo(configDto.campo());
        configFaculdade.setDescricao(configDto.descricao());

        configFaculdadeRepository.save(configFaculdade);
        return configFaculdade;
    }

    @Override
    public List<ConfigFaculdade> getAll(){
        return configFaculdadeRepository.findAll();
    }

    @Override
    public ConfigFaculdade getById(Integer idConfig){
        verificarId(idConfig);

        return configFaculdadeRepository.findById(idConfig).get();
    }

    @Override
    @Transactional
    public ConfigFaculdade update(Integer idConfig, ConfigDto configDto){
        verificarId(idConfig);
        verificar(configDto);

        ConfigFaculdade configFaculdade = new ConfigFaculdade();
        configFaculdade.setIdConfigFaculdade(idConfig);
        configFaculdade.setCampo(configDto.campo());
        configFaculdade.setDescricao(configDto.descricao());

        configFaculdadeRepository.save(configFaculdade);
        return configFaculdade;
    }

    @Override
    @Transactional
    public ConfigFaculdade deleteById(Integer idConfig){
        verificarId(idConfig);

        ConfigFaculdade configFaculdade = configFaculdadeRepository.findById(idConfig).get();
        configFaculdadeRepository.deleteById(idConfig);

        return configFaculdade;
    }

    @Override
    @Transactional
    public List<ConfigFaculdade> deleteAll(){
        List<ConfigFaculdade> configFaculdades = configFaculdadeRepository.findAll();
        configFaculdadeRepository.deleteAll();

        return configFaculdades;
    }
}
