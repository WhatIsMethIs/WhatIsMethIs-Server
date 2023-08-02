package com.WhatIsMethIs.config;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;

@Configuration
public class Swagger3Config {

    @Bean
    public OpenAPI OpenAPI(){

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-ACCESS-TOKEN");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("TOKEN");
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth",securityScheme))
                .info(apiInfo())
                .security(Arrays.asList(securityRequirement));
    }

    private Info apiInfo() {
        return new Info()
                .title("WhatIsMethis API")
                .description("WhatIsMethis api 명세서")
                .version("v0.0.1");
    }

}
