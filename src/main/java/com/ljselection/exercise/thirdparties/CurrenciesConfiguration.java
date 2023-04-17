package com.ljselection.exercise.thirdparties;

import org.springframework.context.annotation.Bean;
import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;

// @Configuration
public class CurrenciesConfiguration {
  @Bean
  public Contract feignContract() {
    return new feign.Contract.Default();
  }

  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    return new BasicAuthRequestInterceptor("", "");// user - password
  }
}
