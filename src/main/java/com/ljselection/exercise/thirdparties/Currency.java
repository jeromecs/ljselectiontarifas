package com.ljselection.exercise.thirdparties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Currency {

  @JsonProperty("id")
  private String symbol;

  @JsonProperty("code")
  private String code;

  @JsonProperty("decimals")
  private Integer decimals;
}
