package com.datamonki.igris.academico.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "curso")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_curso")
    private Integer idCurso;

    @Column(name = "nome")
    private String nome;
    
    @ManyToMany 
    @JoinTable(
    	name="disciplina_curso",
    	joinColumns = @JoinColumn(name="id_curso"),
    	inverseJoinColumns = @JoinColumn(name="id_disciplina")
    )
    private Set<Disciplina> disciplinas;
 
}
