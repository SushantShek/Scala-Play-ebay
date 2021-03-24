package com.mp.showandtell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ShowAndTellApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowAndTellApplication.class, args);
	}

}
