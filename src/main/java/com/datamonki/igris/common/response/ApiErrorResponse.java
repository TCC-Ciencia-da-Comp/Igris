package com.datamonki.igris.common.response; // Ou seu pacote desejado

import java.time.OffsetDateTime; // Opcional: para omitir campos nulos
import java.time.ZoneOffset; // Opcional: para definir a ordem dos campos no JSON
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "message", "errorCode", "timestamp", "path", "requestId", "details", "fieldErrors" })
public class ApiErrorResponse {

    private final int status; // Código de status HTTP
    private final String message; // Mensagem geral do erro
    private final String errorCode; // Código de erro específico da aplicação 
    private final String timestamp;
    private final String path; // URI da requisição que causou o erro 
    private final String requestId; // Identificador único da requisição (opcional, útil para rastrear erros)
    private final List<String> details; // Lista de mensagens de erro mais detalhadas (ex: múltiplos erros de validação)
    private final Map<String, String> fieldErrors; // Erros específicos de campos (para validação de formulários)

    private ApiErrorResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.errorCode = builder.errorCode;
        this.timestamp = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.path = builder.path;
        this.requestId = builder.requestId;
        this.details = builder.details.isEmpty() ? null : Collections.unmodifiableList(new ArrayList<>(builder.details));
        this.fieldErrors = builder.fieldErrors.isEmpty() ? null : Collections.unmodifiableMap(new HashMap<>(builder.fieldErrors));
    }

    // Getters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public String getErrorCode() { return errorCode; }
    public String getTimestamp() { return timestamp; }
    public String getPath() { return path; }
    public String getRequestId() { return requestId; }
    public List<String> getDetails() { return details; }
    public Map<String, String> getFieldErrors() { return fieldErrors; }

    // --- Builder Interno ---
    public static class Builder {
        private final int status;
        private final String message;
        private String errorCode;
        private String path;
        private String requestId;
        private List<String> details = new ArrayList<>();
        private Map<String, String> fieldErrors = new HashMap<>();

        public Builder(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder details(List<String> details) {
            if (details != null) {
                this.details = new ArrayList<>(details);
            }
            return this;
        }

        public Builder addDetail(String detail) {
            if (detail != null) {
                this.details.add(detail);
            }
            return this;
        }

        public Builder fieldErrors(Map<String, String> fieldErrors) {
            if (fieldErrors != null) {
                this.fieldErrors = new HashMap<>(fieldErrors);
            }
            return this;
        }

        public Builder addFieldError(String field, String error) {
            if (field != null && error != null) {
                this.fieldErrors.put(field, error);
            }
            return this;
        }

        public ApiErrorResponse build() {
            return new ApiErrorResponse(this);
        }
    }
}