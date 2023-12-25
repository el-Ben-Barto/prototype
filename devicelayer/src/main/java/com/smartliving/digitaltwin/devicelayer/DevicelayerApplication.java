package com.smartliving.digitaltwin.devicelayer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Documentation Service Layer APIs v1.0"))
public class DevicelayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevicelayerApplication.class, args);
	}

}
