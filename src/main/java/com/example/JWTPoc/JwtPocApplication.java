package com.example.JWTPoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.poc.*")
public class JwtPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtPocApplication.class, args);
	}

}
