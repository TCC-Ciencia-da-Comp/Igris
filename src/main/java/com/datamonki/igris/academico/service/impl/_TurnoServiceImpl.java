// package com.datamonki.igris.academico.service.impl;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.datamonki.igris.academico.dto.TurnoDto;
// import com.datamonki.igris.academico.model.Turno;
// import com.datamonki.igris.academico.repository.TurnoRepository;
// import com.datamonki.igris.academico.service.TurnoService;
// import com.datamonki.igris.common.exception.IdNotFoundException;
// import com.datamonki.igris.common.exception.ValidationException;
// import com.datamonki.igris.common.response.ApiResponse;
// import com.datamonki.igris.common.response.ListResponse;

// @Service
// public class _TurnoServiceImpl implements _TurnoService { 

//     @Autowired
//     private _TurnoRepository turnoRepository;
    
//     private void verificar(TurnoDto turnoDto) {
//         List<String> messages = new ArrayList<>();

//         if (!messages.isEmpty()) {
//             throw new ValidationException(messages);
//         }
//     }

//     private void verificarId(Integer id) {
//         if (!turnoRepository.existsById(id)) {
//             throw new IdNotFoundException("Não foi possivel encontrar turno com o Id '" + id + "', verifique e tente novamente"); 
//         }
//     }

    
//     @Override
//     public ResponseEntity<ListResponse<Turno>> getAll(){

//         List<Turno> turnos = turnoRepository.findAll();
//         return ResponseEntity.status(HttpStatus.OK).body(ListResponse.success("Lista de Turno cadastrados", turnos));
//     }

//     @Override
//     public ResponseEntity<ApiResponse<Turno>> getById(Integer idTurno){
//         verificarId(idTurno);

//         Turno turno = turnoRepository.findById(idTurno).get();
//         return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Turno com o id '"+ idTurno +"' encontrado com sucesso", turno));
//     }

//     @Override
//     @Transactional
//     public ResponseEntity<ApiResponse<Turno>> updateStatus(Integer id, TurnoDto turnoDto) {
//         verificar(turnoDto);
//         verificarId(id);

//         Turno turno = turnoRepository.findById(id).get();
//         turno.setAtivo(turnoDto.ativo());
//         turnoRepository.save(turno);
        
//         String status = turnoDto.ativo() ? "ativado" : "desativado";
//         return ResponseEntity.status(HttpStatus.OK)
//             .body(ApiResponse.success("Turno com o id '" + id + "' foi " + status + " com sucesso", turno));
//     }
// }