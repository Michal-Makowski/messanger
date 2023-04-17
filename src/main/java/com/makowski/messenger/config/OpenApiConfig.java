package com.makowski.messenger.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI openAPI(){
        OpenAPI customOpenApi = new OpenAPI()
            .info(new Info()
            .title("Messenger API")
            .description("Java REST API for messanger App")
            .version("v1.0"));
        customOpenApi
            .path("/login", new PathItem()
            .post(new Operation()
                .tags(List.of("Authentication"))
                .summary("Login")
                .description("Login with username and password.")
                .responses(new ApiResponses()
                .addApiResponse("200", new ApiResponse().description("User authenticated"))
                .addApiResponse("404", new ApiResponse().description("Username dont exist in our database"))
                .addApiResponse("201", new ApiResponse().description("Wrong password"))
                )));
            return customOpenApi;
    }
}
