package com.tvc.example.ex;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
@EnableBatchProcessing
public abstract class AbstractFileLoader<T> {

  @Autowired
  JobBuilderFactory jobs;

  @Autowired
  ApplicationContext applicationContext;


  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  ResourcePatternResolver resourcePatternResolver;

  public abstract Job loaderJob(JobExecutionListener listener);

  public abstract FieldSetMapper<T> getFieldSetMapper();

  public abstract String getFilesPath();

  public abstract String[] getColumnNames();

  public abstract int getChunkSize();

  public abstract int getThrottleLimit();



  public final Job createJob(JobExecutionListener listener) {
    return jobs.get(this.getClass().getSimpleName()).incrementer(new RunIdIncrementer())
        .listener(listener).start(step1()).build();
  }

  @JobScope
  Step step1() {
    return stepBuilderFactory.get("step1").chunk(10).reader(null).writer(null).build();
  }

  /*
   * public final Job createJob(Step s1, JobExecutionListener listener) { return
   * jobs.get(this.getClass().getSimpleName()).incrementer(new RunIdIncrementer())
   * .listener(listener).start(s1).build(); }
   */


  /*
   * @Bean(name = "accountJob") public Job createJob(Reader) {
   * 
   * Step step = stepBuilderFactory.get("step-1") .chunk(1) .reader(new Reader(resource))
   * .processor(processor) .writer(writer) .build();
   * 
   * Job job = jobBuilderFactory.get("accounting-job") .incrementer(new RunIdIncrementer())
   * .listener(this) .start(step) .build();
   * 
   * return job; }
   */
  // "#{jobParameters[outputFileName]}"

  /*
   * @Bean Job job() { return jobs.get("myJob").start(step1()).build(); }
   * 
   * @Bean
   * 
   * @JobScope Step step1() { return
   * stepBuilderFactory.get("step1").chunk(10).reader(null).writer(null).build(); }
   */



  @Bean
  @StepScope
  @Value("#{stepExecutionContext['fileName']}")
  public FlatFileItemReader<T> reader(String file) {
    FlatFileItemReader<T> reader = new FlatFileItemReader<T>();
    String path = file.substring(file.indexOf(":") + 1, file.length());
    FileSystemResource resource = new FileSystemResource(path);
    reader.setResource(resource);
    DefaultLineMapper<T> lineMapper = new DefaultLineMapper<T>();
    lineMapper.setFieldSetMapper(getFieldSetMapper());
    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
    tokenizer.setNames(getColumnNames());
    lineMapper.setLineTokenizer(tokenizer);
    reader.setLineMapper(lineMapper);
    reader.setLinesToSkip(1);
    return reader;
  }

  @Bean
  public ItemProcessor<T, T> processor() {
    return null;
  }

  @Bean
  @JobScope
  public ListItemWriter<T> writer() {
    ListItemWriter<T> writer = new ListItemWriter<T>();
    return writer;
  }
}
