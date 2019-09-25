package com.tvc.example;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class EmployeeWriter implements ItemWriter<Employee>{

	@Override
	public void write(List<? extends Employee> items) throws Exception {
		for (Employee item : items) { 
        	
            System.out.println(item.getFirstName()); 
        } 
		
	}

}
