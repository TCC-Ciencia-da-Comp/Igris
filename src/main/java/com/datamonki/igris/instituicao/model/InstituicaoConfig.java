package com.datamonki.igris.instituicao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "instituicao_config")
public class InstituicaoConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_instituicao_config")
	private Integer idInstituicaoConfig;
	
	@ManyToOne
	private ConfigFaculdade configFaculdade;
	
	@ManyToOne
	private Instituicao instituicao;
	
	private String valor;
}
