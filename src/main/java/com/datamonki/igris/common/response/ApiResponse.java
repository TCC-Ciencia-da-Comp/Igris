package com.datamonki.igris.common.response; // Ou seu pacote desejado

import java.time.OffsetDateTime; // Opcional: para omitir campos nulos
import java.time.ZoneOffset; // Opcional: para definir a ordem dos campos no JSON
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Classe genérica para respostas de API bem-sucedidas, unificando respostas
 * para um único objeto ou listas.
 * O campo 'data' pode ser de qualquer tipo T, incluindo List<E>.
 * Esta classe é imutável e usa o padrão Builder para construção.
 * Tipicamente usada com códigos de status HTTP 2xx.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Opcional: Não serializa campos nulos
@JsonPropertyOrder({ "status", "message", "timestamp", "requestId", "processingTimeMs", "data", "metadata", "links" }) // Ordem sugerida
public class ApiResponse<T> {

    private final int status; // Código de status HTTP (ex: 200, 201)
    private final String message; // Mensagem descritiva da resposta
    private final T data; // Os dados da resposta (payload). Pode ser null ou uma Lista.
    private final String timestamp; // Timestamp da resposta no formato ISO 8601 UTC
    private final Map<String, Object> metadata; // Metadados adicionais (ex: paginação, contagem)
    private final Map<String, String> links; // Links relacionados (HATEOAS simplificado)
    private final String requestId; // Identificador único da requisição (opcional)
    private final Long processingTimeMs; // Tempo de processamento em milissegundos (opcional)

    private ApiResponse(Builder<T> builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.data = builder.data;
        this.timestamp = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.metadata = builder.metadata.isEmpty() ? null : Collections.unmodifiableMap(new HashMap<>(builder.metadata));
        this.links = builder.links.isEmpty() ? null : Collections.unmodifiableMap(new HashMap<>(builder.links));
        this.requestId = builder.requestId;
        this.processingTimeMs = builder.processingTimeMs;
    }

    // Getters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public String getTimestamp() { return timestamp; }
    public Map<String, Object> getMetadata() { return metadata; }
    public Map<String, String> getLinks() { return links; }
    public String getRequestId() { return requestId; }
    public Long getProcessingTimeMs() { return processingTimeMs; }

    // --- Builder Interno ---
    public static class Builder<T> {
        private final int status;
        private final String message;
        private T data;
        private Map<String, Object> metadata = new HashMap<>();
        private Map<String, String> links = new HashMap<>();
        private String requestId;
        private Long processingTimeMs;

        public Builder(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public Builder<T> data(T data) {
            this.data = data;
            if (data instanceof List) {
                this.addMetadata("count", ((List<?>) data).size());
            }
            return this;
        }

        public Builder<T> addMetadata(String key, Object value) {
            if (value != null) { // Evitar adicionar metadados nulos se não desejado
                this.metadata.put(key, value);
            }
            return this;
        }

        public Builder<T> metadata(Map<String, Object> metadata) {
            if (metadata != null) {
                this.metadata = metadata;
            }
            return this;
        }

        public Builder<T> addLink(String rel, String href) {
            if (href != null) { // Evitar adicionar links nulos
                this.links.put(rel, href);
            }
            return this;
        }

        public Builder<T> links(Map<String, String> links) {
            if (links != null) {
                this.links = links;
            }
            return this;
        }

        public Builder<T> requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder<T> processingTimeMs(long processingTimeMs) {
            this.processingTimeMs = processingTimeMs;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(this);
        }
    }

    // --- Métodos Fábrica Estáticos para conveniência ---

    /**
     * Cria uma resposta de sucesso (HTTP 200) com dados e mensagem.
     */
    public static <E> ApiResponse<E> success(String message, E data) {
        return new ApiResponse.Builder<E>(200, message)
                .data(data)
                .build();
    }

    /**
     * Cria uma resposta de sucesso (HTTP 200) com dados, mensagem e metadados.
     */
    public static <E> ApiResponse<E> success(String message, E data, Map<String, Object> metadata) {
        return new ApiResponse.Builder<E>(200, message)
                .data(data)
                .metadata(metadata)
                .build();
    }
    
    /**
     * Cria uma resposta de sucesso (HTTP 200) para uma lista de dados, com mensagem.
     * A contagem de itens é automaticamente adicionada aos metadados.
     */
    public static <E> ApiResponse<List<E>> successForList(String message, List<E> dataList) {
        return new ApiResponse.Builder<List<E>>(200, message)
                .data(dataList) // O builder já adiciona 'count' ao metadata
                .build();
    }

    /**
     * Cria uma resposta de sucesso (HTTP 200) apenas com mensagem (sem corpo de dados).
     */
    public static ApiResponse<Void> success(String message) {
        return new ApiResponse.Builder<Void>(200, message)
                .build();
    }

    /**
     * Cria uma resposta de criação bem-sucedida (HTTP 201) com dados e mensagem.
     */
    public static <E> ApiResponse<E> created(String message, E data) {
        return new ApiResponse.Builder<E>(201, message)
                .data(data)
                .build();
    }

    /**
     * Cria uma resposta de aceitação (HTTP 202) apenas com mensagem.
     * Útil para operações assíncronas.
     */
    public static ApiResponse<Void> accepted(String message) {
        return new ApiResponse.Builder<Void>(202, message)
                .build();
    }

    /**
     * Cria uma resposta de "Sem Conteúdo" (HTTP 204) apenas com mensagem.
     * O corpo da resposta geralmente é vazio para 204, mas a mensagem pode ser útil internamente
     * ou se o cliente puder lidar com isso.
     * Spring geralmente remove o corpo para respostas 204.
     */
    public static ApiResponse<Void> noContent(String message) {
        return new ApiResponse.Builder<Void>(204, message)
                .build();
    }
}