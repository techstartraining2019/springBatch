package com.discover.cmpp.batch.config.parent;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Tobias Flohre
 */
@Component
public class CustomJobBuilderFactory extends JobBuilderFactory {

  @Autowired
  private JobExecutionListener[] listeners;



  public CustomJobBuilderFactory(JobRepository jobRepository, JobExecutionListener... listeners) {
    super(jobRepository);
    this.listeners = listeners;
  }

  @Override
  public JobBuilder get(String name) {
    JobBuilder jobBuilder = super.get(name);
    for (JobExecutionListener jobExecutionListener : listeners) {
      jobBuilder = jobBuilder.listener(jobExecutionListener);
    }
    return jobBuilder;
  }

}
