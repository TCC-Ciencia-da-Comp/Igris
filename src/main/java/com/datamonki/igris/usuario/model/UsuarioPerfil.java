package com.datamonki.igris.usuario.model;

import com.datamonki.igris.instituicao.model.Instituicao;

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


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuario_perfil")
public class UsuarioPerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario_perfil")
    private Long idUsuarioPerfil;

    @Column(name="email_usuario")
    private String emailUsuario;
    
    @Column(name="ativo")
    private Boolean ativo;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "id_instituicao", referencedColumnName = "id_instituicao")
    private Instituicao instituicao; 
    
}
