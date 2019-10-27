package com.tvc.example.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.support.ListItemWriter;

public class JobCompletionNotificationListener<T> extends JobExecutionListenerSupport {
	
	private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	
	public JobCompletionNotificationListener(ListItemWriter<T> writer) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beforeJob(JobExecution jobExecution){
		super.beforeJob(jobExecution);
		
		logger.info("Job Started");
	}
	
	@Override
	public void afterJob(JobExecution jobExecution){
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("Job Completed");
		}
	}

}
