package com.discover.cmpp.batch.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class BatchProperties {
  @Bean
  @ConfigurationProperties(prefix = "input")
  public Map<String, String> getInputFileProperties() {
    return new HashMap<String, String>();
  }

  @Bean
  @ConfigurationProperties(prefix = "output")
  public Map<String, String> getOutputFileProperties() {
    return new HashMap<String, String>();
  }

}
