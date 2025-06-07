package com.datamonki.igris.academico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.datamonki.igris.academico.model.Disponibilidade;

import jakarta.transaction.Transactional;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Integer> {
	@Query (value = "SELECT COUNT(*) FROM  disponibilidade WHERE id_professor=:idProfessor", nativeQuery = true)
    Integer getNumDisponibilidadeProfessor(@Param("idProfessor") Integer idProfessor);


    @Query (value = "SELECT  CASE WHEN COUNT(*) >0 THEN true ELSE false END " +
            "FROM  disponibilidade WHERE id_professor=:idProfessor", nativeQuery = true)
    Boolean verifyDisponibilidadeProfessor(@Param("idProfessor") Integer idProfessor);

    
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
            "FROM disponibilidade " +
            "WHERE id_usuario = :idUsuario AND id_dia_semana = :idDiaSemana AND id_turno = :idTurno " +
            "AND semestre = :semestre AND ano = :ano AND id_instituicao = :idInstituicao", nativeQuery = true)
    Boolean verifyRepeticao(@Param("idUsuario") Integer idUsuario,
                     @Param("idDiaSemana") Integer idDiaSemana,
                     @Param("idTurno") Integer idTurno,
                     @Param("semestre") Integer semestre,
                     @Param("ano") Integer ano,
                     @Param("idInstituicao") Integer idInstituicao);


    @Query (value = "SELECT * FROM  disponibilidade WHERE id_professor=:idProfessor", nativeQuery = true)
    List<Disponibilidade> findByIdProfessor(@Param("idProfessor") Integer idProfessor);


    @Query (value = "SELECT * FROM  disponibilidade WHERE id_professor=:idProfessor AND semestre=:semestre AND ano=:ano", nativeQuery = true)
    List<Disponibilidade> findByIdProfessorAnoSemestre(
                     @Param("idProfessor") Integer idProfessor, 
                     @Param("ano") Integer ano, 
                     @Param("semestre") Integer semestre);

    
    @Modifying
    @Transactional
    @Query (value = "DELETE FROM  disponibilidade WHERE id_professor=:idProfessor", nativeQuery = true)
    void deleteByIdProfessor(@Param("idProfessor") Integer idProfessor);

    
    
    @Modifying
    @Transactional
    @Query (value = "DELETE FROM  disponibilidade WHERE id_professor=:idProfessor AND semestre=:semestre AND ano=:ano", nativeQuery = true)
    void deleteByIdProfessorAnoSem(@Param("idProfessor") Integer idProfessor, @Param("ano") Integer ano, @Param("semestre") Integer semestre);

    
    
    @Query(value = "SELECT d.id_professor, d.id_dia_semana, d.id_turno, d.semestre, d.ano " +
        "FROM disponibilidade d " +
        "WHERE d.id_professor =:idProfessor", nativeQuery = true)
    List<Object[]> findDisponibilidadeByProfessorId(@Param("idProfessor") Integer idProfessor);
    
    
    @Query(value = "SELECT d.id_professor, d.id_dia_semana, d.id_turno, d.semestre, d.ano " +
            "FROM disponibilidade d " +
            "WHERE d.id_professor =:idProfessor AND d.ano=:ano AND d.semestre=:semestre", nativeQuery = true)
    List<Disponibilidade> findDisponibilidadeByProfessorIdAnoSemestre(@Param("idProfessor") Integer idProfessor,
    		@Param("ano") Integer ano, @Param("semestre") Integer semestre);

    
    @Query(value = "SELECT d.id_professor, d.id_dia_semana, d.id_turno, d.semestre, d.ano " +
            "FROM disponibilidade d " +
            "WHERE d.id_professor = :idProfessor " +
            "AND d.ano = :ano " +
            "AND d.semestre = :semestre " +
            "AND d.id_instituicao = :idInstituicao", nativeQuery = true)
    List<Disponibilidade> findDisponibilidadeByProfessorIdAnoSemestreInstituicao(
            @Param("idProfessor") Integer idProfessor,
            @Param("ano") Integer ano,
            @Param("semestre") Integer semestre,
            @Param("idInstituicao") Integer idInstituicao);


    @Query(value = "SELECT d.id_professor, d.id_dia_semana, d.id_turno, d.semestre, d.ano " +
            "FROM disponibilidade d " +
            "WHERE d.ano = :ano " +
            "AND d.semestre = :semestre " +
            "AND d.id_instituicao = :idInstituicao", nativeQuery = true)
    List<Disponibilidade> findDisponibilidadeByAnoSemestreInstituicao(
            @Param("ano") Integer ano,
            @Param("semestre") Integer semestre,
            @Param("idInstituicao") Integer idInstituicao);
    
}
