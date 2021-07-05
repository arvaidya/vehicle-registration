package org.restful.rego.controller;

import org.restful.rego.exception.LinkPersonToVehicleException;
import org.restful.rego.exception.PersonExistsException;
import org.restful.rego.exception.UnlinkPersonFromVehicleException;
import org.restful.rego.exception.VehicleExistsException;
import org.restful.rego.model.Person;
import org.restful.rego.model.RegistrationDirectory;
import org.restful.rego.model.RegoData;
import org.restful.rego.model.Vehicle;
import org.restful.rego.service.RegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class RegoController {
	
	@Autowired
	RegoService regoService;
	
	@PostMapping("/person")
	public ResponseEntity<Person> createPerson(@RequestBody Person person) throws PersonExistsException {
		
		if(!StringUtils.hasLength(person.getFirstName()) || !StringUtils.hasLength(person.getLastName())) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		Person createdPerson = regoService.createPerson(person);
		
		return new ResponseEntity<Person>(createdPerson, HttpStatus.CREATED);
	}

	@PostMapping("/vehicle")
	public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) throws VehicleExistsException {
		
		if(!StringUtils.hasLength(vehicle.getMake()) || !StringUtils.hasLength(vehicle.getModel()) 
				|| !StringUtils.hasLength(vehicle.getRegistration())) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		Vehicle createdVehicle = regoService.createVehicle(vehicle);
		
		return new ResponseEntity<Vehicle>(createdVehicle, HttpStatus.CREATED);
	}
	
	@PatchMapping("/link")
	public ResponseEntity<Person> linkPersonToVehicle(@RequestBody RegoData regoData) throws LinkPersonToVehicleException {
		
		Person personToVehicle = regoService.linkPersonToVehicle(regoData);
		
		return new ResponseEntity<Person>(personToVehicle, HttpStatus.OK);
	}
	
	@PatchMapping("/unlink")
	public ResponseEntity<Person> unlinkPersonToVehicle(@RequestBody RegoData regoData) throws UnlinkPersonFromVehicleException {
		
		Person unlinkedPerson = regoService.unlinkPersonFromVehicle(regoData);
		
		return new ResponseEntity<Person>(unlinkedPerson, HttpStatus.OK);
	}
	
	@GetMapping("/registrationsData")
	public ResponseEntity<RegistrationDirectory> registrationsData() throws JsonProcessingException {
		
		RegistrationDirectory registrationData = regoService.getRegistrationsData();
		
		return new ResponseEntity<RegistrationDirectory>(registrationData, HttpStatus.OK);
	}
}

