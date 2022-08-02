package com.crio.starter.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config.
 */

@Configuration
public class ModelMapperConfig {

  // ModelMapper config - Bean declaration
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}