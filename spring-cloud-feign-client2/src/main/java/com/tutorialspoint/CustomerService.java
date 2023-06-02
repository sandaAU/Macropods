package com.tutorialspoint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerService {
	
	@GetMapping("/customer/{id}")
	public Customer getCustomerById(@PathVariable("id") Long id);
	
}