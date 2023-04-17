package com.ljselection.exercise.thirdparties;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "currencies", // configuration = CurrenciesConfiguration.class,
    url = "http://localhost:8082")
public interface CurrencyClient {

  @GetMapping("/v1/currencies")
  List<Currency> getCurrencies();

  @GetMapping("/v1/currencies/{currencyCode}")
  Currency getCurrencyByCode(@PathVariable String currencyCode);
}
