package accessible_toilet.accessible_toilet.common.web;

import accessible_toilet.accessible_toilet.common.exception.BadRequestException;
import accessible_toilet.accessible_toilet.common.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException ex) {
        return ResponseEntity.badRequest().body(err("BAD_REQUEST", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validation(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(err("VALIDATION_ERROR", ex.getBindingResult().toString()));
    }

    private Map<String, Object> err(String code, String msg) {
        return Map.of("timestamp", Instant.now().toString(), "code", code, "message", msg);
    }
}
