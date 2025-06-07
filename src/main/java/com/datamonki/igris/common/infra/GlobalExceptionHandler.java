package com.datamonki.igris.common.infra;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.datamonki.igris.common.exception.IdNotFoundException;
import com.datamonki.igris.common.exception.ValidationException;
import com.datamonki.igris.common.response.ApiErrorResponse;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Tratar o caso do "Endpoint não encontrado" (404)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorResponse> handle404(NoHandlerFoundException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.NOT_FOUND.value(),
                "Endpoint não encontrado"
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        } else {
            errorBuilder.path(e.getRequestURL());
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.NOT_FOUND);
    }

    // Tratamento de exceções de validação
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(ValidationException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        if (e.getMessages() != null && !e.getMessages().isEmpty()) {
            e.getMessages().forEach(errorBuilder::addDetail);
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleIdNotFoundException(IdNotFoundException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(BadRequestException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(RuntimeException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiErrorResponse> handleJwtException(JwtException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.UNAUTHORIZED.value(),
                "Token inválido ou expirado"
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.UNAUTHORIZED.value(),
                "Falha na autenticação: " + e.getMessage()
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.FORBIDDEN.value(),
                "Acesso negado: " + e.getMessage()
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(EntityNotFoundException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.NOT_FOUND.value(),
                "Entidade não encontrada: " + e.getMessage()
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação. Verifique os campos enviados."
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        e.getBindingResult().getFieldErrors().forEach(error ->
            errorBuilder.addFieldError(error.getField(), error.getDefaultMessage())
        );
        
        e.getBindingResult().getGlobalErrors().forEach(error ->
            errorBuilder.addDetail(error.getObjectName() + ": " + error.getDefaultMessage())
        );
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.BAD_REQUEST);
    }

    // O handler genérico deve ser o último
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e, WebRequest request) {
        // Aqui seria importante logar a exceção: log.error("Erro inesperado:", e);
        ApiErrorResponse.Builder errorBuilder = new ApiErrorResponse.Builder(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado no servidor."
        );
        
        if (request instanceof ServletWebRequest servletRequest) {
            errorBuilder.path(servletRequest.getRequest().getRequestURI());
        }
        
        // Em ambiente de desenvolvimento, pode ser útil incluir a mensagem de erro
        errorBuilder.addDetail(e.getMessage());
        
        return new ResponseEntity<>(errorBuilder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
