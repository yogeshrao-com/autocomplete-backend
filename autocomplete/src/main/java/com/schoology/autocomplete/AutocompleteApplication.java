package com.schoology.autocomplete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan({"com.autocomplete.controller", "com.autocomplete.config", "com.autocomplete.service, com.autocomplete.model"})
@SpringBootApplication
public class AutocompleteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutocompleteApplication.class, args);
	}

}
