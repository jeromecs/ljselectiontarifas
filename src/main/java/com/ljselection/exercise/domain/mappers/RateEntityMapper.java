package com.ljselection.exercise.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import com.ljselection.exercise.data.entities.RateEntity;
import com.ljselection.exercise.domain.model.Rate;

@Component
@Mapper(componentModel = "spring")
public interface RateEntityMapper {

  @Mapping(target = "price", expression = "java(entity.getPrice().setScale(currencyScale))")
  Rate toModel(RateEntity entity, Integer currencyScale);

  RateEntity toEntity(Rate rate);
}
