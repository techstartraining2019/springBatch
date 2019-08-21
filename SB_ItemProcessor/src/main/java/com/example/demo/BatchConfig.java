package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
 

 
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
 
    @Value("/input/inputData.csv")
    private Resource inputResource;
 
	/*
	 * @Bean public Job readCSVFilesJob() { return jobBuilderFactory
	 * .get("readCSVFilesJob") .incrementer(new RunIdIncrementer()) .start(step1())
	 * .build(); }
	 * 
	 * @Bean public Step step1() { return stepBuilderFactory .get("step1")
	 * .<Employee, Employee>chunk(1) .reader(reader()) .processor(processor())
	 * .writer(writer()) .build(); }
	 */
 
    @Bean
    @StepScope
    public ItemProcessor<Employee, Employee> processor() {
        return new ValidationProcessor();
    }
    
    
    @Bean
    @StepScope
    public FlatFileItemReader<Employee> flatFileItemReader() {
        FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
        
        reader.setResource(inputResource);
       // reader.setResource(new ClassPathResource("people.csv"));
        reader.setLinesToSkip(1);
 
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("firstName", "lastName");
 
        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);
 
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(tokenizer);
        reader.setLineMapper(lineMapper);
 
        return reader;
    }
 
 
	/*
	 * @Bean public FlatFileItemReader<Employee> reader() {
	 * FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<Employee>();
	 * itemReader.setLineMapper(lineMapper()); itemReader.setLinesToSkip(1);
	 * itemReader.setResource(inputResource); return itemReader; }
	 */
 
	/*
	 * @Bean public LineMapper<Employee> lineMapper() { DefaultLineMapper<Employee>
	 * lineMapper = new DefaultLineMapper<Employee>(); DelimitedLineTokenizer
	 * lineTokenizer = new DelimitedLineTokenizer(); lineTokenizer.setNames(new
	 * String[] { "id", "firstName", "lastName" });
	 * lineTokenizer.setIncludedFields(new int[] { 0, 1, 2 });
	 * BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new
	 * BeanWrapperFieldSetMapper<Employee>();
	 * fieldSetMapper.setTargetType(Employee.class);
	 * lineMapper.setLineTokenizer(lineTokenizer);
	 * lineMapper.setFieldSetMapper(fieldSetMapper); return lineMapper; }
	 */
 
    @Bean
    @StepScope
    public FlatFileItemWriter<Employee> flatFileItemWriter() {
     FlatFileItemWriter<Employee> flatFileItemWriter = new FlatFileItemWriter<>();
     flatFileItemWriter.setShouldDeleteIfExists(true);
     flatFileItemWriter.setResource(new FileSystemResource("src/main/resources/modified_emp.txt"));
     flatFileItemWriter.setLineAggregator((person) -> {
     return person.getFirstName() + ":" + person.getLastName();
     });
     return flatFileItemWriter;
    }
}