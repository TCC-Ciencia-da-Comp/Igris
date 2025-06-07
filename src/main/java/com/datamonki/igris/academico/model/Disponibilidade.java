package com.datamonki.igris.academico.model;

import com.datamonki.igris.instituicao.model.Instituicao;
import com.datamonki.igris.usuario.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "disponibilidade")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Disponibilidade {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="id_disponibilidade")
    private Integer idDisponibilidade;
    
    @Column(name="semestre")
    @Min(1)
    @Max(2)
    private Integer semestre;
    
    @Column(name="ano")
    private Integer ano;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dia_semana")
    private DiaSemana diaSemana;

    @ManyToOne
    @JoinColumn(name="id_turno")
    private Turno turno;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuarioProfessor;

    @ManyToOne
    @JoinColumn(name = "id_instituicao")
    private Instituicao instituicao;

}