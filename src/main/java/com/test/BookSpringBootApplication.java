	package com.test;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BookSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookSpringBootApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder encode() {
		return NoOpPasswordEncoder.getInstance();
	}	

}
