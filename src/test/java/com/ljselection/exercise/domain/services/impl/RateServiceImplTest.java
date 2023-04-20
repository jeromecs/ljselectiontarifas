package com.ljselection.exercise.domain.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ljselection.exercise.data.entities.RateEntity;
import com.ljselection.exercise.data.repositories.RateRepository;
import com.ljselection.exercise.domain.mappers.RateEntityMapper;
import com.ljselection.exercise.domain.model.Rate;
import com.ljselection.exercise.exceptions.RecordNotFoundException;
import com.ljselection.exercise.thirdparties.Currency;
import com.ljselection.exercise.thirdparties.CurrencyClient;

@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {

  @Mock
  private RateRepository rateRepository;

  @Mock
  private RateEntityMapper rateEntityMapper;

  @Mock
  private CurrencyClient currencyClient;

  @Mock
  private RateEntity rateEntity;

  @Mock
  private Currency currency;

  @Mock
  private Rate rate;

  private final Integer id = 1;
  private final String currencyCode = "EUR";
  private final LocalDate rateDate = LocalDate.now();
  private final Integer productId = 2;
  private final Integer brandId = 3;

  @InjectMocks
  private RateServiceImpl rateService;

  @Test
  void testGetRateWhenIdIsNotFound() {
    when(rateRepository.findById(id)).thenReturn(Optional.ofNullable(null));
    
    try {
      rateService.getRate(id);
      fail("This line must not be reached");
    }
    catch (RecordNotFoundException e) {
    }
  }

  @Test
  void testGetRateWhenCurrencyClienThrowsException() {
    when(rateRepository.findById(id)).thenReturn(Optional.of(rateEntity));
    when(rateEntity.getCurrencyCode()).thenReturn(currencyCode);
    when(currencyClient.getCurrencyByCode(currencyCode)).thenThrow(new RuntimeException());
    when(rateEntityMapper.toModel(eq(rateEntity), any())).thenReturn(rate);

    assertThat(rateService.getRate(id)).isNotNull();
  }

  @Test
  void testGetRateWhenCurrencyClienReturnsNull() {
    when(rateRepository.findById(id)).thenReturn(Optional.of(rateEntity));
    when(rateEntity.getCurrencyCode()).thenReturn(currencyCode);
    when(currencyClient.getCurrencyByCode(currencyCode)).thenReturn(null);
    when(rateEntityMapper.toModel(eq(rateEntity), any())).thenReturn(rate);

    assertThat(rateService.getRate(id)).isNotNull();
  }

  @Test
  void testGetRateWhenOK() {
    when(rateRepository.findById(id)).thenReturn(Optional.of(rateEntity));
    when(rateEntity.getCurrencyCode()).thenReturn(currencyCode);
    when(currencyClient.getCurrencyByCode(currencyCode)).thenReturn(currency);
    final Integer decimals = 2;
    when(currency.getDecimals()).thenReturn(decimals);
    when(rateEntityMapper.toModel(eq(rateEntity), any())).thenReturn(rate);

    assertThat(rateService.getRate(id)).isNotNull();
  }

  @Test
  void testPostRate() {
    when(rateEntityMapper.toEntity(rate)).thenReturn(rateEntity);
    when(rateRepository.save(rateEntity)).thenReturn(rateEntity);
    when(rateEntityMapper.toModel(eq(rateEntity), any())).thenReturn(rate);

    assertThat(rateService.postRate(rate));
  }

  @Test
  void testPatchRateWhenIdIsNotFound() {
    when(rate.getId()).thenReturn(id);
    when(rateRepository.findById(id)).thenReturn(Optional.ofNullable(null));

    try {
      rateService.patchRate(rate);
      fail("This line must not be reached");
    } catch (RecordNotFoundException e) {
    }
  }

  @Test
  void testPatchRateWhenOK() {
    when(rate.getId()).thenReturn(id);
    when(rateRepository.findById(id)).thenReturn(Optional.of(rateEntity));
    BigDecimal price = new BigDecimal(35);
    when(rate.getPrice()).thenReturn(price);
    
    rateService.patchRate(rate);

    verify(rateEntity).setPrice(price);
    verify(rateRepository).save(rateEntity);
  }

  @Test
  void testDeleteRateWhenIdIsNotFound() {
    when(rateRepository.findById(id)).thenReturn(Optional.ofNullable(null));

    try {
      rateService.deleteRate(id);
      fail("This line must not be reached");
    } catch (RecordNotFoundException e) {
    }
  }

  @Test
  void testDeleteRateWhenOK() {
    when(rateRepository.findById(id)).thenReturn(Optional.of(rateEntity));

    rateService.deleteRate(id);

    verify(rateRepository).delete(rateEntity);
  }

  @Test
  void testGetRateByParamsWhenRateIsNotFoundByParams() {
    when(
        rateRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            eq(productId), eq(brandId), any(), any())).thenReturn(Optional.ofNullable(null));

    try {
      rateService.getRateByParams(rateDate, productId, brandId);
      fail("This line must not be reached");
    } catch (RecordNotFoundException e) {
    }
  }

  @Test
  void testGetRateByParamsWhenCurrencyClienThrowsException() {
    when(
        rateRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            eq(productId), eq(brandId), any(), any())).thenReturn(Optional.of(rateEntity));
    when(rateEntity.getCurrencyCode()).thenReturn(currencyCode);
    when(currencyClient.getCurrencyByCode(currencyCode)).thenThrow(new RuntimeException());
    when(rateEntityMapper.toModel(eq(rateEntity), any())).thenReturn(rate);

    assertThat(rateService.getRateByParams(rateDate, productId, brandId)).isNotNull();
  }

  @Test
  void testGetRateByParamsWhenCurrencyClienReturnsNull() {
    when(
        rateRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            eq(productId), eq(brandId), any(), any())).thenReturn(Optional.of(rateEntity));
    when(rateEntity.getCurrencyCode()).thenReturn(currencyCode);
    when(currencyClient.getCurrencyByCode(currencyCode)).thenReturn(null);
    when(rateEntityMapper.toModel(eq(rateEntity), any())).thenReturn(rate);

    assertThat(rateService.getRateByParams(rateDate, productId, brandId)).isNotNull();
  }

  @Test
  void testGetRateByParamsWhenOK() {
    when(
        rateRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            eq(productId), eq(brandId), any(), any())).thenReturn(Optional.of(rateEntity));
    when(rateEntity.getCurrencyCode()).thenReturn(currencyCode);
    when(currencyClient.getCurrencyByCode(currencyCode)).thenReturn(currency);
    final Integer decimals = 2;
    when(currency.getDecimals()).thenReturn(decimals);
    when(rateEntityMapper.toModel(eq(rateEntity), any())).thenReturn(rate);

    assertThat(rateService.getRateByParams(rateDate, productId, brandId)).isNotNull();
  }
}
