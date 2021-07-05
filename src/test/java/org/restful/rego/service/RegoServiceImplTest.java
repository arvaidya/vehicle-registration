package org.restful.rego.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.restful.rego.exception.LinkPersonToVehicleException;
import org.restful.rego.exception.PersonExistsException;
import org.restful.rego.exception.VehicleExistsException;
import org.restful.rego.model.Person;
import org.restful.rego.model.RegistrationDirectory;
import org.restful.rego.model.RegoData;
import org.restful.rego.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.fasterxml.jackson.core.JsonProcessingException;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class RegoServiceImplTest {
	
	@Mock
	RegistrationDirectory regoDirectory;

	@Autowired
	private RegoServiceImpl regoService;

	
	@Test
	void testRegistrationsData() throws JsonProcessingException {
		assertEquals(new RegistrationDirectory(new HashSet<>(), new HashSet<>()), regoService.getRegistrationsData());
	}
	
	
	@Test
	void testCreatePerson() throws PersonExistsException, JsonProcessingException {
		
		Person testPerson = new Person();
		testPerson.setFirstName("testFirstName");
		testPerson.setLastName("testLastName");
		
		RegistrationDirectory regoDirectory = new RegistrationDirectory(new HashSet<Person>(), new HashSet<Vehicle>());
		regoDirectory.getPersons().add(testPerson);
  
		assertEquals(testPerson, regoService.createPerson(testPerson));

		//trying to create same person
		assertThrows(PersonExistsException.class, () -> {
			regoService.createPerson(testPerson);
		});
		
		assertEquals(regoDirectory, 
				regoService.getRegistrationsData());
	}
	
	@Test
	void testCreateVehicle() throws VehicleExistsException, JsonProcessingException {
		
		Vehicle testVehicle = new Vehicle();
		testVehicle.setMake("testToyota");
		testVehicle.setModel("testCamry");
		testVehicle.setRegistration("TestRego");
		
		RegistrationDirectory regoDirectory = new RegistrationDirectory(new HashSet<Person>(), new HashSet<Vehicle>());
		regoDirectory.getVehicles().add(testVehicle);
  
		assertEquals(testVehicle, regoService.createVehicle(testVehicle));

		//trying to create same vehicle
		assertThrows(VehicleExistsException.class, () -> {
			regoService.createVehicle(testVehicle);
		});
		
		assertEquals(regoDirectory, regoService.getRegistrationsData());
	}
	
	
	@Test
	void testLinkPersonToVehicle() throws PersonExistsException, VehicleExistsException, LinkPersonToVehicleException {
		
		Person testPerson = new Person();
		testPerson.setFirstName("testFirstName");
		testPerson.setLastName("testLastName");
		regoService.createPerson(testPerson);
		
		Vehicle testVehicle = new Vehicle();
		testVehicle.setMake("testToyota");
		testVehicle.setModel("testCamry");
		testVehicle.setRegistration("TestRego");		
		regoService.createVehicle(testVehicle);

		
		RegoData regoData = new RegoData();
		regoData.setFirstName("testFirstName");
		regoData.setLastName("testLastName");
		regoData.setRegistration("TestRego");
		
		Person personUpdated = regoService.linkPersonToVehicle(regoData);
  
		assertEquals(testPerson, personUpdated);
		
	}

	@Test
	void testUnlinkPersonToVehicle() throws PersonExistsException, VehicleExistsException, LinkPersonToVehicleException {
		
		Person testPerson = new Person();
		testPerson.setFirstName("testFirstName");
		testPerson.setLastName("testLastName");
		regoService.createPerson(testPerson);
		
		Vehicle testVehicle = new Vehicle();
		testVehicle.setMake("testToyota");
		testVehicle.setModel("testCamry");
		testVehicle.setRegistration("TestRego");		
		regoService.createVehicle(testVehicle);

		
		RegoData regoData = new RegoData();
		regoData.setFirstName("testFirstName");
		regoData.setLastName("testLastName");
		regoData.setRegistration("TestRego");
  
		assertEquals(testPerson, regoService.linkPersonToVehicle(regoData));
		
	}
	
	
}
