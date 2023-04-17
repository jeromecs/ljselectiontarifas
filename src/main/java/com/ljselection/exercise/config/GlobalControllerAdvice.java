package com.ljselection.exercise.config;

import java.time.LocalDateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.ljselection.exercise.exceptions.ParamNotValidException;
import com.ljselection.exercise.exceptions.RecordNotFoundException;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(RecordNotFoundException.class)
  public ResponseEntity<ApiError> handleRecordNotFound(Exception ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.builder()
        .status(HttpStatus.NOT_FOUND).date(LocalDateTime.now()).message(ex.getMessage()).build());
  }

  @ExceptionHandler(ParamNotValidException.class)
  public ResponseEntity<ApiError> handleParamNotValid(Exception ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError.builder()
        .status(HttpStatus.BAD_REQUEST).date(LocalDateTime.now()).message(ex.getMessage()).build());
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return ResponseEntity.status(status).headers(headers).body(ApiError.builder().status(status)
        .date(LocalDateTime.now()).message(ex.getMessage()).build());
  }
}
