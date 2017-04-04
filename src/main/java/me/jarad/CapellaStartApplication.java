package me.jarad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CapellaStartApplication {

	public static final String USE_HAZELCAST = "true";
	
	public static void main(String[] args) {
		SpringApplication.run(CapellaStartApplication.class, args);
	}
}
