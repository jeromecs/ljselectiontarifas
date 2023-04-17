package com.ljselection.exercise.domain.services.impl;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import com.ljselection.exercise.data.entities.RateEntity;
import com.ljselection.exercise.data.repositories.RateRepository;
import com.ljselection.exercise.domain.mappers.RateEntityMapper;
import com.ljselection.exercise.domain.model.Rate;
import com.ljselection.exercise.domain.services.RateService;
import com.ljselection.exercise.exceptions.RecordNotFoundException;
import com.ljselection.exercise.thirdparties.Currency;
import com.ljselection.exercise.thirdparties.CurrencyClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

  private final RateRepository rateRepository;
  private final RateEntityMapper rateEntityMapper;
  private final CurrencyClient currencyClient;

  @Override
  public Rate getRate(Integer id) {
    RateEntity entity =
        rateRepository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException("Rate " + id + " not found"));
    Currency currency = null;
    try {
      currency = currencyClient.getCurrencyByCode(entity.getCurrencyCode());
    } catch (Exception e) {
      e.printStackTrace();
      // TODO Loggear error del servicio
    }
    return rateEntityMapper.toModel(entity, currency != null ? currency.getDecimals() : 0);
  }

  @Override
  public Rate postRate(Rate rate) {
    RateEntity entity = rateRepository.save(rateEntityMapper.toEntity(rate));
    return rateEntityMapper.toModel(entity, 0);
  }

  @Override
  public void patchRate(Rate rate) {
    RateEntity entity = rateRepository.findById(rate.getId())
        .orElseThrow(() -> new RecordNotFoundException("Rate " + rate.getId() + " not found"));
    entity.setPrice(rate.getPrice());
    rateRepository.save(entity);
  }

  @Override
  public void deleteRate(Integer id) {
    // POSIBLE MEJORA ES BUSCAR Y GUARDAR LA TARIFA EN UN HISTORICO
    RateEntity entity = rateRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException("Rate " + id + " not found"));
    rateRepository.delete(entity);
  }

  @Override
  public Rate getRateByParams(LocalDate rateDate, Integer productId, Integer brandId) {
    RateEntity entity =
        rateRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            productId, brandId, rateDate.atStartOfDay(), rateDate.atStartOfDay())
            .orElseThrow(() -> new RecordNotFoundException("Rate not found"));
    Currency currency = null;
    try {
      currency = currencyClient.getCurrencyByCode(entity.getCurrencyCode());
    } catch (Exception e) {
      e.printStackTrace();
      // TODO Loggear error del servicio
    }
    return rateEntityMapper.toModel(entity, currency != null ? currency.getDecimals() : 0);
  }
}
