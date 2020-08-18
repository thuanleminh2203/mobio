package com.venesa;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableCaching
public class GatewayMobileApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(GatewayMobileApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	}	
	
	@Bean
	public WebClient getWebclient() {
		return WebClient.create();
	}

	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
		return new Jackson2ObjectMapperBuilder().propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	}

}