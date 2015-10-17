package com.currconv.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Spring Boot Application class.
 * @author gauravD
 *
 */
@SpringBootApplication
public class CurrencyConvSpringBoot  {

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		SpringApplication app = new SpringApplication(WebConfiguration.class);
		app.run(args);

	}

}