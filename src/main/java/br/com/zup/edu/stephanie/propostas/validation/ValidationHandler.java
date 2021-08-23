package br.com.zup.edu.stephanie.propostas.validation;

import br.com.zup.edu.stephanie.propostas.request.ErrorFormatDTO;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationHandler {

    private MessageSource messageSource;

    public ValidationHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorFormatDTO> handle(MethodArgumentNotValidException exception) {
        List<ErrorFormatDTO> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErrorFormatDTO erro = new ErrorFormatDTO(e.getField(), mensagem);
            dto.add(erro);
        });

        return dto;
    }

    @ExceptionHandler(ApiErroException.class)
    public ResponseEntity<ErrorFormatDTO> handleApiErroException(ApiErroException exception) {
        ErrorFormatDTO error = new ErrorFormatDTO(exception.getField(), exception.getReason());

        return ResponseEntity.status(exception.getHttpStatus()).body(error);
    }
}
