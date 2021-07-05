package org.restful.rego.service;

import java.util.HashSet;
import java.util.Set;

import org.restful.rego.exception.LinkPersonToVehicleException;
import org.restful.rego.exception.PersonExistsException;
import org.restful.rego.exception.UnlinkPersonFromVehicleException;
import org.restful.rego.exception.VehicleExistsException;
import org.restful.rego.model.Person;
import org.restful.rego.model.RegistrationDirectory;
import org.restful.rego.model.RegoData;
import org.restful.rego.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.java.Log;


@Log
@Service
public class RegoServiceImpl implements RegoService {


	@Autowired
	@Qualifier("regoDirectory")
	private RegistrationDirectory regoDirectory;
	
	
	public Person createPerson(Person person) throws PersonExistsException {
				
		Person existinPerson = existingPerson(person.getFirstName());
		
		if(existinPerson != null) {
			throw new PersonExistsException("Person Exists");
		} else {
			regoDirectory.getPersons().add(person);
		}
		
		log.info("Person created: " + person.toString());
	
		return person;
		
	}
	
	public Vehicle createVehicle(Vehicle vehicle) throws VehicleExistsException {
				
		Vehicle vehicleExists = existingVehicle(vehicle.getRegistration());
		
		if(vehicleExists != null) {
			throw new VehicleExistsException("Vehicle Exists");
		} else {
			regoDirectory.getVehicles().add(vehicle);
		}
		
		log.info("Vehicle created " + vehicle.toString());
		
		return vehicle;
		
	}
	
	public Person linkPersonToVehicle(RegoData regoData) throws LinkPersonToVehicleException {
				
		Person existingPerson = existingPerson(regoData.getFirstName());
		
		String vehicleRego = regoData.getRegistration();
		
		Vehicle existingVehicle = existingVehicle(vehicleRego);
		
		if(existingVehicle != null) {
			log.info("Vehicle " + vehicleRego +" exits, can be linked to the person " + regoData.getFirstName());
		} else {
			throw new LinkPersonToVehicleException("Vehicle " + regoData.getRegistration() +" does not "
					+ "exit, and can not be linked to the person");
		}
		
		if(isRegoAlreadyLinked(existingPerson, vehicleRego)) {
			throw new LinkPersonToVehicleException("Vehicle " + regoData.getRegistration() +" already linked to the person " + existingPerson.toString());
		}
		
		if(existingPerson != null && existingPerson.getVehicles() == null) {
			Set<String> vehicleNames = new HashSet<String>();
			vehicleNames.add(vehicleRego);
			
			existingPerson.setVehicles(vehicleNames);
			
		} else if(existingPerson.getVehicles() != null && !existingPerson.getVehicles().contains(vehicleRego)) {
			existingPerson.getVehicles().add(vehicleRego);
			
		} else {
			throw new LinkPersonToVehicleException("Vehicle " + vehicleRego +" could "
					+ "not be linked to the person " + regoData.getFirstName());
		}
		
		log.info("Person "+ regoData.getFirstName() +" linked to the Vehicle " + vehicleRego);
		
		return existingPerson;
		
	}

	private boolean isRegoAlreadyLinked(Person existingPerson, String vehicleRego) {
		if(existingPerson != null && existingPerson.getVehicles() != null && existingPerson.getVehicles().contains(vehicleRego)) {
			return true;
		}
		return false;
		
	}

	public Person unlinkPersonFromVehicle(RegoData regoData) throws UnlinkPersonFromVehicleException {
		
		String vehicleRego = regoData.getRegistration();
				
		Vehicle existingVehicle = existingVehicle(vehicleRego);
		
		if(existingVehicle != null) {
			log.info("Vehicle " + vehicleRego +" exits, can be unlinked from the person " + regoData.getFirstName());
		} else {
			throw new UnlinkPersonFromVehicleException("Vehicle " + vehicleRego +" does not "
					+ "exit, and can not be unlinked from the person");
		}
		
		Person existingPerson = existingPerson(regoData.getFirstName());
		
		if(existingPerson != null && existingPerson.getVehicles().contains(vehicleRego)) {
			existingPerson.getVehicles().remove(vehicleRego);
			
		} else {
			throw new UnlinkPersonFromVehicleException("Vehicle " + vehicleRego +" could "
					+ "not be unlinked from the person " + regoData.getFirstName());
		}
		
		log.info("Person "+ regoData.getFirstName() +" unlinked from the Vehicle " + vehicleRego);
		
		return existingPerson;
		
	}
	
	public RegistrationDirectory getRegistrationsData() throws JsonProcessingException {		
		return regoDirectory;
	}
	
	private Vehicle existingVehicle(String vehicleRego) {
		Set<Vehicle> vehicles = regoDirectory.getVehicles();

		Vehicle vehicleExists = vehicles.stream()
				.filter(v -> v.getRegistration().equals(vehicleRego))
				.findFirst()
				.orElse(null);
		return vehicleExists;
	}

	private Person existingPerson(String firstName) {
		Set<Person> persons = regoDirectory.getPersons();
		
		Person personExists = persons.stream()
				.filter(p -> p.getFirstName().equals(firstName))
				.findFirst()
				.orElse(null);
		return personExists;
	}
	

}
