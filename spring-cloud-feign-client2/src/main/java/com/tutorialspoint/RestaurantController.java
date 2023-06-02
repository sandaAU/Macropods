package com.tutorialspoint;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RestaurantController {
	
	@Autowired
	CustomerService customerService;
	
	@Value("${eureka.instance.metadataMap.zone:no-zone}")
    private String zone;
	
	static HashMap<Long, Restaurant> mockRestaurantData = new HashMap<>();
	
	static {
		mockRestaurantData.put(1L, new Restaurant(1, "Pandas", "DC"));
		mockRestaurantData.put(2L, new Restaurant(2, "Indies", "SFO"));
		mockRestaurantData.put(3L, new Restaurant(3, "Little Italy", "DC"));
	}

	/**
	 * 
	 * HOW WE CALL TWO CLIENTS WHERE 1) CLIENT 1 - feign-client , 2) CLIENT 2 - eureka-client
	 * =============================
	 * 
	 * CLIENT 2
	 *  /spring-cloud-eureka-client
	 *  RestaurantCustomerInstancesController - @RestController
	 *  @RequestMapping("/customer/{id}")
	 *  	public Customer getCustomerInfo(@PathVariable("id") Long id) {
	 *  
	 *  ^   |
	 *  |   |
	 *  |   V
	 *  
	 *  CLIENT 1
	 *  /spring-cloud-feign-client
	 *  CustomerService - @FeignClient(name = "customer-service")
	 *  @RequestMapping("/customer/{id}")
	 *  	public Customer getCustomerById(@PathVariable("id") Long id);
	 *  
	 *  ^   |
	 *  |   |
	 *  |   V
	 *  
	 *  CLIENT 1
	 *  /spring-cloud-feign-client
	 *  RestaurantController - @RestController
	 *  @RequestMapping("/restaurant/customer/{id}")
	 *  	public List<Restaurant> getRestaurantForCustomer(@PathVariable("id") Long id)
	 *  
	 *  
	 *  ^   |
	 *  |   |
	 *  |   V
	 *  
	 *  POSTMAN CALL AS:
	 *  GET
	 *  URL http://localhost:8082/restaurant/customer/1
	 *  
	 *  RESPONSE TO POSTMAN:
	 *  [
	 *      {
	 *          "id": 1,
	 *          "name": "Pandas",
	 *          "city": "DC"
	 *      },
	 *      {
	 *          "id": 3,
	 *          "name": "Little Italy",
	 *          "city": "DC"
	 *      }
	 *  ]
	 * 
	 * 
	 */
	@GetMapping("/restaurant/customer/{id}") // WAS: @RequestMapping
	public List<Restaurant> getRestaurantForCustomer(@PathVariable("id") Long id) {
		
		System.out.println("RestaurantController::getRestaurantForCustomer , id: " + id + ", zone: " + zone);
		String customerCity = customerService.getCustomerById(id).getCity();
		System.out.println("RestaurantController::getRestaurantForCustomer , customerCity: " + customerCity + ", zone: " + zone);
		
		return mockRestaurantData.entrySet().stream().filter(entry -> entry.getValue().getCity().equals(customerCity))
				.map(entry -> entry.getValue()).collect(Collectors.toList());
	}
	
}
