package com.ljselection.exercise.domain.services;

import java.time.LocalDate;
import com.ljselection.exercise.domain.model.Rate;

public interface RateService {

  Rate getRate(Integer id);

  Rate postRate(Rate rate);

  void patchRate(Rate rate);

  void deleteRate(Integer id);

  Rate getRateByParams(LocalDate rateDate, Integer productId, Integer brandId);
}
