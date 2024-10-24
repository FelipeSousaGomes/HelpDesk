package br.com.felipe.userserverapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI(
            @Value("${spring.data.mongodb.springdoc.openapi.title}") final String title,
            @Value("${spring.data.mongodb.springdoc.openapi.description}") final String description,
            @Value("${spring.data.mongodb.springdoc.openapi.version}") final String version
    ){
        return new OpenAPI()
                .info(
                        new Info()
                                .title(title)
                                .description(description)
                                .version(version)
                );
    }


}
