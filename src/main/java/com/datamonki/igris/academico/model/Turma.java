package com.datamonki.igris.academico.model;

import com.datamonki.igris.instituicao.model.Instituicao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "turma")
@AllArgsConstructor 
@NoArgsConstructor
@Getter
@Setter
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_turma")
    private Integer idTurma;

    @Column(name="nome")
    private String nome;

    @Column(name="semestre")
    private Integer semestre;

    @Column(name="ano")
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    @JsonIgnoreProperties({"disciplinas"})
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "id_turno", nullable = false)
    private Turno turno;

    @ManyToOne
    @JoinColumn(name = "id_instituicao")
    private Instituicao instituicao;
    
}