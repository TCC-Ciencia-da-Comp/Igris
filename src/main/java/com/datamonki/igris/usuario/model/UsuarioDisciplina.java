package com.datamonki.igris.usuario.model; 

import com.datamonki.igris.academico.model.Disciplina; 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="usuario_disciplina")
@Getter
@Setter
public class UsuarioDisciplina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuarioDisciplina;
	
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name="id_disciplina", nullable = false)	
	private Disciplina disciplina;

}
