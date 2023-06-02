package com.tutorialspoint;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantCustomerInstancesController {
	
	static HashMap<Long, Customer> mockCustomerData = new HashMap<>();
	static {
		mockCustomerData.put(1L, new Customer(1, "Jane", "DC"));
		mockCustomerData.put(2L, new Customer(2, "John", "SFO"));
		mockCustomerData.put(3L, new Customer(3, "Kate", "NY"));
	}
	
	@Value("${eureka.instance.metadataMap.zone}")
    private String zone;
	
	@Autowired
	private DiscoveryClient eurekaConsumer;
	
	
	@GetMapping("/customer/{id}") // WAS: @RequestMapping
	public Customer getCustomerInfo(@PathVariable("id") Long id) {
		System.out.println("RestaurantCustomerInstancesController::getCustomerInfo , id: " + id + ", zone: " + zone);
		return mockCustomerData.get(id);
	}
	
	// Return names of all services
	@GetMapping("/customer-service-instances") // WAS: @RequestMapping
	public List<String> serviceInstancesByApplicationName() {
		System.out.println("RestaurantCustomerInstancesController::serviceInstancesByApplicationName , zone: " + zone);
		return this.eurekaConsumer.getServices();
	}
	
	// Return one service depend on service name
	@GetMapping("/customer-service-instances/{applicationName}") // WAS: @RequestMapping
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		System.out.println("RestaurantCustomerInstancesController::serviceInstancesByApplicationName , applicationName: " + applicationName + ", zone: " + zone);
		return this.eurekaConsumer.getInstances(applicationName);
	}
	
	// Return all services
	@GetMapping("/customer-service-instances/all") // WAS: @RequestMapping
	public List<List<ServiceInstance>> serviceInstancesAll() {
		System.out.println("RestaurantCustomerInstancesController::serviceInstancesAll , zone: " + zone);
		return this.eurekaConsumer.getServices().stream().map(serviceName -> this.eurekaConsumer.getInstances(serviceName)).collect(Collectors.toList());
	}

}
