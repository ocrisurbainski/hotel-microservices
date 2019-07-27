package com.urbainski.reservasapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.urbainski.reservasapi.config.ReservasApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(ReservasApiProperty.class)
public class ReservasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservasApiApplication.class, args);
	}

}