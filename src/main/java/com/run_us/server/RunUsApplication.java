package com.run_us.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RunUsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunUsApplication.class, args);
	}

}
