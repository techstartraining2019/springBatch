package com.discover.cmpp.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.discover.cmpp.batch.ValidationProcessor;
import com.discover.cmpp.batch.config.parent.CommonJobConfigurationForInheritance;
import com.discover.cmpp.batch.domain.Partner;
import com.discover.cmpp.batch.exception.UnknownGenderException;
import com.discover.cmpp.batch.listener.LogSkipListener;


/**
 * @author Tobias Flohre
 */
@Configuration
@EnableBatchProcessing
public class InheritedConfigurationJobConfiguration extends CommonJobConfigurationForInheritance {

  @Bean
  public Job inheritedConfigurationJob() {
    return customJobBuilders().get("inheritedConfigurationJob").start(step()).build();
  }

  @Bean
  public Step step() {
    return customStepBuilders().get("step").faultTolerant().skipLimit(10)
        .skip(UnknownGenderException.class).listener(logSkipListener()).build();
  }

  @Override
  @Bean
  public ItemProcessor<Partner, Partner> processor() {
    return new ValidationProcessor();
  }

  @Override
  @Bean
  public CompletionPolicy completionPolicy() {
    return new SimpleCompletionPolicy(3);
  }

  @Bean
  public LogSkipListener logSkipListener() {
    return new LogSkipListener();
  }
}
