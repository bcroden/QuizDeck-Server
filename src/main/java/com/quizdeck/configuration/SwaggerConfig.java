package com.quizdeck.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Generates REST API Documentation using Swagger 2
 *
 * Created by Brandon on 2/13/2016.
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry
                        .addViewController("/")
                        .setViewName("redirect:/swagger-ui.html");
            }
        };
    }

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("QuizDeck")
                .apiInfo(new ApiInfoBuilder()
                        .title("QuizDeck Server")
                        .description("The REST Web Service for QuizDeck")
                        .license("MIT")
                        .licenseUrl("https://github.com/bcroden/QuizDeck-Server/blob/master/LICENSE")
                        .build())
                .select()
                .paths(regex("/rest.*"))
                .build();
    }
}
