package com.ljselection.exercise.data.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "T_RATES", schema = "\"ljselectiontarifas\"")
public class RateEntity {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "BRAND_ID")
  private Integer brandId;

  @Column(name = "PRODUCT_ID")
  private Integer productId;

  @Column(name = "START_DATE")
  private LocalDateTime startDate;

  @Column(name = "END_DATE")
  private LocalDateTime endDate;

  @Column(name = "PRICE")
  private BigDecimal price;

  @Column(name = "CURRENCY_CODE")
  private String currencyCode;
}
