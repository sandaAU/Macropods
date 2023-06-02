
CMD PROMPT:
===========

1)
WE START 2 EUREKA SERVERS ON PORTS: 8900 + 8901
-----------------------------------------------
EUREKA 1
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-eureka-server2
$ java -Dapp_port=8900 '-Deureka_other_server_url=http://localhost:8901/eureka' -jar target/spring-cloud-eureka-server2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-ha.yml

EUREKA 2
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-eureka-server2
$ java -Dapp_port=8901 '-Deureka_other_server_url=http://localhost:8900/eureka' -jar target/spring-cloud-eureka-server2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-ha.yml


2)
WE START 2 CLIENTS 2
--------------------
CLIENT SECOND NO.1
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-eureka-client2
$ java -Dapp_port=8080 -Dzone_name=USA -jar target/spring-cloud-eureka-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml

CLIENT SECOND NO.2
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-eureka-client2
$ java -Dapp_port=8081 -Dzone_name=EU -jar target/spring-cloud-eureka-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml

3)
WE START 2 CLIENTS 1
--------------------
CLIENT FIRST NO.1
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-feign-client2
$ java -Dapp_port=8082 -Dzone_name=USA -jar target/spring-cloud-feign-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml

CLIENT FIRST NO.2
sandaau@DESKTOP-77LBPFE MINGW64 /f/17.7.21-13.55/PROJECTS/EclipseSpring_6.6.22-23.16/SpringBoot-SpringCloud-projetcs_tutorialspoint.com_Olga-Sharma_1.6.23-23.35/spring-cloud-feign-client2
$ java -Dapp_port=8083 -Dzone_name=EU -jar target/spring-cloud-feign-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml



POSTMAN:
========
WE CALL 6x
GET
http://localhost:8082/restaurant/customer/1

REPONSE:
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
]

AS YOU CAN SEE IS CALLED BOTH CLIENTS spring-cloud-eureka-client2 EVEN WHEN WE CALL ONLY PORT=8082 ZONE=USA spring-cloud-feign-client2
SHOULD CALL ONLY ZONE=USA !

BY CLIENTS 1
------------
java -Dapp_port=8082 -Dzone_name=USA -jar target/spring-cloud-feign-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml
LOG:
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA
RestaurantController::getRestaurantForCustomer , id: 1, zone: USA
RestaurantController::getRestaurantForCustomer , customerCity: DC, zone: USA


WE SHOULD CALL ONLY ZONE=USA !, BUT WE CALL ZONE=EU AS WELL
BY CLIENTS 2
------------
java -Dapp_port=8080 -Dzone_name=USA -jar target/spring-cloud-eureka-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml
LOG:
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: USA
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: USA
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: USA

BY CLIENTS 2
------------
java -Dapp_port=8081 -Dzone_name=EU -jar target/spring-cloud-eureka-client2-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:application-za.yml
LOG
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: EU
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: EU
RestaurantCustomerInstancesController::getCustomerInfo , id: 1, zone: EU


