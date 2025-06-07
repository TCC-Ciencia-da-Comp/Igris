// package com.datamonki.igris.academico.service.impl;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.datamonki.igris.academico.dto.DiaSemanaDto;
// import com.datamonki.igris.academico.model.DiaSemana;
// import com.datamonki.igris.academico.repository.DiaSemanaRepository;
// import com.datamonki.igris.academico.service.DiaSemanaService;
// import com.datamonki.igris.common.exception.IdNotFoundException;
// import com.datamonki.igris.common.exception.ValidationException;
// import com.datamonki.igris.common.response.ApiResponse;
// import com.datamonki.igris.common.response.ListResponse;

// @Service
// public class _DiaSemanaServiceImpl implements _DiaSemanaService { 

//     @Autowired
//     private DiaSemanaRepository diaSemanaRepository;
    
//     private void verificarId(Integer id) {
//         if (!diaSemanaRepository.existsById(id)) {
//             throw new IdNotFoundException("Não foi possivel encontrar Dia da Semana com o Id '" + id + "', verifique e tente novamente"); 
//         }
//     }

//     private void verificar(DiaSemanaDto diaSemanaDto) { 
//         List<String> messages = new ArrayList<>();
        
//         if (diaSemanaDto.ativo() == null) {
//             messages.add("O status ativo não pode ser nulo");
//         }
        
//         if (!messages.isEmpty()) {
//             throw new ValidationException(messages);
//         }
//     }

//     @Override
//     public ResponseEntity<ListResponse<DiaSemana>> getAll() { 
//         List<DiaSemana> diasSemana = diaSemanaRepository.findAll();
//         return ResponseEntity.status(HttpStatus.OK).body(ListResponse.success("Lista de Dias da Semana cadastrados", diasSemana));
//     }

//     @Override
//     public ResponseEntity<ApiResponse<DiaSemana>> getById(Integer id) {
//         verificarId(id);
        
//         DiaSemana diaSemana = diaSemanaRepository.findById(id).get();
//         return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Dia da Semana com o id '"+ id +"' encontrado com sucesso", diaSemana)); 
//     }

//     @Override
//     @Transactional
//     public ResponseEntity<ApiResponse<DiaSemana>> updateStatus(Integer id, DiaSemanaDto diaSemanaDto) {
//         verificar(diaSemanaDto);
//         verificarId(id);

//         DiaSemana diaSemana = diaSemanaRepository.findById(id).get();
//         diaSemana.setAtivo(diaSemanaDto.ativo());
//         diaSemanaRepository.save(diaSemana);
        
//         String status = diaSemanaDto.ativo() ? "ativado" : "desativado";
//         return ResponseEntity.status(HttpStatus.OK)
//             .body(ApiResponse.success("Dia da Semana com o id '" + id + "' foi " + status + " com sucesso", diaSemana)); 
//     }
// } 