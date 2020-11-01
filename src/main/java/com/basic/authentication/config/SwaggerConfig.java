package com.basic.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${app.version}")
    public String version;

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(getSecuritySchemes())
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.basic.authentication"))
                .paths(PathSelectors.any())
                .build();
    }

    private List<SecurityScheme> getSecuritySchemes() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new BasicAuth("basicAuth"));
        return securitySchemes;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("USERS - Basic Authentication")
                .description("Aplicação padrão para criação de usuários autenticados com Basic Authentication")
                .version(version)
                .build();
    }

}