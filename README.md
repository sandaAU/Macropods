
CMD PROMPT:<br>
===========<br>

1)<br>
WE START 2 EUREKA SERVERS ON PORTS: 8900 + 8901<br>
-----------------------------------------------<br>
EUREKA 1<br>
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-eureka-server2<br>
$ java -Dapp_port=8900 '-Deureka_other_server_url=http://localhost:8901/eureka' -jar target/spring-cloud-eureka-server2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-ha.yml<br>
<br>
EUREKA 2<br>
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-eureka-server2<br>
$ java -Dapp_port=8901 '-Deureka_other_server_url=http://localhost:8900/eureka' -jar target/spring-cloud-eureka-server2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-ha.yml<br>
<br>
<br>
2)<br>
WE START 2 CLIENTS 2<br>
--------------------<br>
CLIENT SECOND NO.1<br>
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-eureka-client2<br>
$ java -Dapp_port=8080 -Dzone_name=USA -jar target/spring-cloud-eureka-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml<br>
<br>
CLIENT SECOND NO.2<br>
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-eureka-client2<br>
$ java -Dapp_port=8081 -Dzone_name=EU -jar target/spring-cloud-eureka-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml<br>
<br>
3)<br>
WE START 2 CLIENTS 1<br>
--------------------<br>
CLIENT FIRST NO.1<br>
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-feign-client2<br>
$ java -Dapp_port=8082 -Dzone_name=USA -jar target/spring-cloud-feign-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml<br>
<br>
CLIENT FIRST NO.2<br>
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-feign-client2<br>
$ java -Dapp_port=8083 -Dzone_name=EU -jar target/spring-cloud-feign-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml<br>
<br>
<br>
<br>
POSTMAN:<br>
========<br>
WE CALL 6x<br>
GET<br>
http://localhost:8082/restaurant/customer/1<br>
<br>
REPONSE:<br>
[
    {
        "id": 1,
        "name": "Pandas",
        "city": "DC"
    },
    {
        "id": 3,
        "name": "Little Italy",
        "city": "DC"
    }
]<br>
<br>
AS YOU CAN SEE IS CALLED BOTH CLIENTS spring-cloud-eureka-client2 EVEN WHEN WE CALL ONLY PORT=8082 ZONE=USA spring-cloud-feign-client2<br>
SHOULD CALL ONLY ZONE=USA !<br>
<br>
BY CLIENTS 1<br>
------------<br>
java -Dapp_port=8082 -Dzone_name=USA -jar target/spring-cloud-feign-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml<br>
LOG:<br>
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA<br>
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA<br>
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA<br>
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA<br>
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA<br>
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA<br>
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA<br>
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA<br>
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA<br>
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA<br>
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA<br>
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA<br>
<br>
<br>
WE SHOULD CALL ONLY ZONE=USA !, BUT WE CALL ZONE=EU AS WELL<br>
BY CLIENTS 2<br>
------------<br>
java -Dapp_port=8080 -Dzone_name=USA -jar target/spring-cloud-eureka-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml<br>
LOG:<br>
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: USA<br>
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: USA<br>
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: USA<br>
<br>
BY CLIENTS 2<br>
------------<br>
java -Dapp_port=8081 -Dzone_name=EU -jar target/spring-cloud-eureka-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml<br>
LOG:<br>
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: EU<br>
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: EU<br>
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: EU<br>
<br>


