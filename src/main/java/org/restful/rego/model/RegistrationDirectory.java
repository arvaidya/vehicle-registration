package org.restful.rego.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class RegistrationDirectory {

	private Set<Person> persons;
	private Set<Vehicle> vehicles;

	public RegistrationDirectory(HashSet<Person> persons, HashSet<Vehicle> vehicles) {
		this.persons = persons;
		this.vehicles = vehicles;

	}

}
