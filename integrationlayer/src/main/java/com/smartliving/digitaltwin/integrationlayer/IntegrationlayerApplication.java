package com.smartliving.digitaltwin.integrationlayer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Documentation Integration Layer APIs v1.0"))
public class IntegrationlayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrationlayerApplication.class, args);
	}

}
