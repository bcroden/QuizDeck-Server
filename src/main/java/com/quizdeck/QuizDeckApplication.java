package com.quizdeck;


import com.quizdeck.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@SpringBootApplication
public class QuizDeckApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizDeckApplication.class, args);
	}

	@Bean(name = "secretKey")
	public String secretKey() {
		return System.getProperty("SECRET_KEY", "default_key");
	}

	@Bean
	@Resource(name = "secretKey")
	public FilterRegistrationBean authFilter(String secretKey) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new AuthFilter(secretKey));
		registration.addUrlPatterns("/rest/secure/*");
		return registration;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedOrigins("*");
			}
		};
	}
}
