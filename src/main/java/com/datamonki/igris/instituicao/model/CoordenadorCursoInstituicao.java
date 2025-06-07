package com.datamonki.igris.instituicao.model;

import com.datamonki.igris.academico.model.Curso;
import com.datamonki.igris.usuario.model.Usuario;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "coordenador_curso_instituicao")
public class CoordenadorCursoInstituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id_coordenador_curso_instituicao")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_coordenacao", referencedColumnName = "id_coordenacao")
    private Coordenacao coordenacao;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "id_instituicao", referencedColumnName = "id_instituicao")
    private Instituicao instituicao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuarioCoordenador;
    
    
    @Column(name = "ativo")
    private Boolean ativo;
}
