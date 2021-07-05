package org.restful.rego;

import java.util.HashSet;

import org.restful.rego.model.Person;
import org.restful.rego.model.RegistrationDirectory;
import org.restful.rego.model.Vehicle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VehicleRegistrationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleRegistrationProjectApplication.class, args);
	}
	
	@Bean(name = "regoDirectory")
	public RegistrationDirectory regoDirectory() {
		return new RegistrationDirectory(new HashSet<Person>(), new HashSet<Vehicle>());
 
	}

}
