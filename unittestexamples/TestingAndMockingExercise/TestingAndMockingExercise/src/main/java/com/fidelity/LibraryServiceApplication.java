package com.fidelity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.fidelity.utils.Generated;

@SpringBootApplication
public class LibraryServiceApplication {
	
	@Generated
	public static void main(String[] args) {
		SpringApplication.run(LibraryServiceApplication.class, args);
	}

	/**
	 * This method creates a Logger that can be autowired in other classes:{@code
	 *    @Autowired 
	 *    private Logger logger;
	 }*/
	@Bean
	@Scope("prototype")
	Logger createLogger(InjectionPoint ip) {
	    Class<?> classThatWantsALogger = ip.getField().getDeclaringClass();
	    return LoggerFactory.getLogger(classThatWantsALogger);
	}

}
