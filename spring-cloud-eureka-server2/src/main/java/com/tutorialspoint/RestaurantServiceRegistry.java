package com.tutorialspoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RestaurantServiceRegistry {
	
	public static void main(String[] args) {
		SpringApplication.run(RestaurantServiceRegistry.class, args);
	}
	
}