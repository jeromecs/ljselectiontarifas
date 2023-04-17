package com.ljselection.exercise.rest.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Currency;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.ljselection.exercise.domain.model.Rate;
import com.ljselection.exercise.domain.services.RateService;
import com.ljselection.exercise.exceptions.ParamNotValidException;
import com.ljselection.exercise.rest.api.RateApi;
import com.ljselection.exercise.rest.mappers.RateModelMapper;
import com.ljselection.exercise.rest.model.GetRateResponse;
import com.ljselection.exercise.rest.model.PatchRateRequest;
import com.ljselection.exercise.rest.model.PostRateRequest;
import com.ljselection.exercise.rest.model.PostRateResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class RateController implements RateApi {

  private final RateService rateService;
  private final RateModelMapper rateModelMapper;

  @Override
  public ResponseEntity<PostRateResponse> postRate(PostRateRequest body) {
    try {
      Currency.getInstance(body.getCurrencyCode());
    }
    catch (NullPointerException | IllegalArgumentException e) {
      throw new ParamNotValidException("CurrencyCode is not valid: " + body.getCurrencyCode());
    }
    Rate result = rateService.postRate(rateModelMapper.toModel(body));
    return new ResponseEntity<>(rateModelMapper.toPostDto(result), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<GetRateResponse> getRate(Integer id) {
    Rate result = rateService.getRate(id);
    return new ResponseEntity<>(rateModelMapper.toGetDto(result), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> patchRate(PatchRateRequest body) {
    rateService.patchRate(rateModelMapper.toModel(body));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> deleteRate(Integer id) {
    rateService.deleteRate(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<GetRateResponse> getRateByParams(String rateDateRaw, Integer productId,
      Integer brandId) {
    LocalDate rateDate = null;
    try {
      rateDate = LocalDate.parse(rateDateRaw);
    }
    catch (DateTimeParseException e) {
      throw new ParamNotValidException("RateDate param is not valid: " + rateDateRaw);
    }
    Rate result = rateService.getRateByParams(rateDate, productId, brandId);
    return new ResponseEntity<>(rateModelMapper.toGetDto(result), HttpStatus.OK);
  }
}
