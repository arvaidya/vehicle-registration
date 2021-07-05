package org.restful.rego.service;

import org.restful.rego.exception.LinkPersonToVehicleException;
import org.restful.rego.exception.PersonExistsException;
import org.restful.rego.exception.UnlinkPersonFromVehicleException;
import org.restful.rego.exception.VehicleExistsException;
import org.restful.rego.model.Person;
import org.restful.rego.model.RegistrationDirectory;
import org.restful.rego.model.RegoData;
import org.restful.rego.model.Vehicle;

import com.fasterxml.jackson.core.JsonProcessingException;



public interface RegoService {
	
	
	public Person createPerson(Person person) throws PersonExistsException;
	
	public Vehicle createVehicle(Vehicle vehicle) throws VehicleExistsException;
	
	public Person linkPersonToVehicle(RegoData regoData) throws LinkPersonToVehicleException;

	public Person unlinkPersonFromVehicle(RegoData regoData) throws UnlinkPersonFromVehicleException;
	
	public RegistrationDirectory getRegistrationsData() throws JsonProcessingException;

}

