package br.com.fiap.fiaprestaurant.shared.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ValidationErrorResponse extends MessageError {
    private Map<String, String> fieldErrors;
}
