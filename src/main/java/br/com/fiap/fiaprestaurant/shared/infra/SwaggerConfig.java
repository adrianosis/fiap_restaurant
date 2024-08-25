package br.com.fiap.fiaprestaurant.shared.infra;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("API Restaurant - FIAP")
                        .description("Spring Boot API for restaurant.")
                        .version("v1")
                        .contact(new Contact()
                                .name("FIAP - Pós Tech")
                                .url("https://on.fiap.com.br/")));
    }
}
