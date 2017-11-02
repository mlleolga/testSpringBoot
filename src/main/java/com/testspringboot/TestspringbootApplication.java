package com.testspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestspringbootApplication.class, args);
	}

	@RequestMapping("/hello")
	public String sayHello()
	{
		return "Hello Spring Boot App";
	}

}
