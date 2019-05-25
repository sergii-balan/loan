package com.balan.sergii.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={RepositoryRestMvcAutoConfiguration.class})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, RepositoryRestMvcAutoConfiguration.class})
public class LoanApp {

	public static void main(String[] args) {
        SpringApplication.run(LoanApp.class, args);
	}
}