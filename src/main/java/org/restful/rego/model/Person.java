package org.restful.rego.model;

import java.util.Set;

import lombok.Data;

@Data
public class Person {

	private String firstName;
	private String lastName;
	private Set<String> vehicles;
	
}
