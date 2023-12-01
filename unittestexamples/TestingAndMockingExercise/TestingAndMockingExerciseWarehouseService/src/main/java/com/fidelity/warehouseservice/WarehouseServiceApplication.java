package com.fidelity.warehouseservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Spring Boot application that launches the WarehouseService 
 * which is a RESTful web service.
 * 
 * @author ROI Instructor Team
 */
// tell Spring Boot where to scan for annotated components
@SpringBootApplication(scanBasePackages = {
	"com.fidelity.integration", 
	"com.fidelity.restservices", 
	"com.fidelity.business.service"
})
// tell MyBatis where to scan for mapping interface files: either
// add @MapperScan here, or add @Mapper to all MyBatis mapping interfaces
@MapperScan(basePackages="com.fidelity.integration.mapper")  
public class WarehouseServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(WarehouseServiceApplication.class, args);
	}
}
