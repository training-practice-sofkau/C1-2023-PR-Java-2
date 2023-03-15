package ec.com.students.sofka.api.exceptionhandler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class StudentHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String,String>> handleException(WebExchangeBindException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(er -> {
            errors.put(er.getField(),er.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
