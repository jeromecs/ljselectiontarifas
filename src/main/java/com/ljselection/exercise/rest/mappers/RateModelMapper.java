package com.ljselection.exercise.rest.mappers;

import java.util.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import com.ljselection.exercise.domain.model.Rate;
import com.ljselection.exercise.rest.model.GetRateResponse;
import com.ljselection.exercise.rest.model.PatchRateRequest;
import com.ljselection.exercise.rest.model.PostRateRequest;
import com.ljselection.exercise.rest.model.PostRateResponse;

@Component
@Mapper(componentModel = "spring", imports = Currency.class)
public interface RateModelMapper {

  @Mapping(target = "currencySymbol",
      expression = "java(Currency.getInstance(rate.getCurrencyCode()).getSymbol())")
  GetRateResponse toGetDto(final Rate rate);

  PostRateResponse toPostDto(final Rate rate);

  Rate toModel(PostRateRequest request);

  Rate toModel(PatchRateRequest request);
}
