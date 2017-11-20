package com.testspringboot;

import com.testspringboot.service.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@ComponentScan
public class TestspringbootApplication  implements CommandLineRunner{
	@Autowired
	private KafkaSender kafkaSender;


	@Override
	public void run(String... args) throws Exception {
		kafkaSender.consume();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {

		SpringApplication.run(TestspringbootApplication.class, args);

	}

//	@RequestMapping("/hello")
//	public String sayHello()
//	{
//		return "Hello Spring Boot App";
//	}

}
