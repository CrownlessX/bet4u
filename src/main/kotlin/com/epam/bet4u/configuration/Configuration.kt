package com.epam.bet4u.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

/** Provides configuration required for application. */
@Configuration
class Configuration {

  /** Creates bean for [RestClient.Builder]. */
  @Bean
  fun restClientBuilder() = RestClient.builder()
}