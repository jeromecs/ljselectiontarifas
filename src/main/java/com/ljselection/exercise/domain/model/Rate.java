package com.ljselection.exercise.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Rate {
  private Integer id;
  private Integer brandId;
  private Integer productId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private BigDecimal price;
  private String currencyCode;
}
