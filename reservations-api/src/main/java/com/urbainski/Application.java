package com.urbainski;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableConfigurationProperties
@EnableWebFlux
@EnableMongoAuditing
@OpenAPIDefinition(
		info = @Info(
				title = "${info.app.name}",
				description = "${info.app.description}",
				version = "${info.app.version}"))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}