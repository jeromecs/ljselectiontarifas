package com.ljselection.exercise.config;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

  @NonNull
  private HttpStatus status;

  @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
  @Builder.Default
  private LocalDateTime date = LocalDateTime.now();

  @NonNull
  private String message;
}
