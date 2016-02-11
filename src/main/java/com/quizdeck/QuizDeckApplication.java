package com.quizdeck;


import com.quizdeck.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class QuizDeckApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizDeckApplication.class, args);
	}

	@Bean(name = "secretKey")
	public String secretKey() {
		return "secret";
	}

	@Bean
	@Resource(name = "secretKey")
	public FilterRegistrationBean authFilter(String secretKey) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new AuthFilter(secretKey));
		registration.addUrlPatterns("/rest/secure/*");
		return registration;
	}
}
