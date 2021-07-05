package org.restful.rego.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.restful.rego.model.Person;
import org.restful.rego.model.RegistrationDirectory;
import org.restful.rego.model.RegoData;
import org.restful.rego.model.Vehicle;
import org.restful.rego.service.RegoService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RegoControllerTest {
    
	@Mock
	RegistrationDirectory regoDirectory;
	
	@Mock
	RegoService regoService;
    
//    @Autowired
//    private TestRestTemplate restTemplate;
    
    @InjectMocks
    RegoController regoController;
    
	ObjectMapper om = new ObjectMapper();
	
	@BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
	
//    @Test
//    public void getRegistrations() throws Exception {
//
//        ResponseEntity<String> response = restTemplate.getForEntity(
//            new URL("http://localhost:" + port + "/vehicle-registration/registrationsData").toString(), String.class);
//        assertEquals("{\"persons\":[],\"vehicles\":[]}", response.getBody());
//
//    }
    
    @Test
    public void createPerson() throws Exception {
    	
    	Person testPerson = new Person();
		testPerson.setFirstName("testFirstName");
		testPerson.setLastName("testLastName");		
    	
        when(regoService.createPerson(testPerson)).thenReturn(testPerson);
        
        ResponseEntity<Person> responseEntity = regoController.createPerson(testPerson); 
        
        assertEquals(testPerson, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }
    
    @Test
    public void createVehicle() throws Exception {
    	
		Vehicle testVehicle = new Vehicle();
		testVehicle.setMake("testToyota");
		testVehicle.setModel("testCamry");
		testVehicle.setRegistration("TestRego");		
    	
		when(regoService.createVehicle(testVehicle)).thenReturn(testVehicle);
        
        ResponseEntity<Vehicle> responseEntity = regoController.createVehicle(testVehicle); 
        
        assertEquals(testVehicle, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }
    
    @Test
    public void testLinkPersonToVehicle() throws Exception {
    	
		RegoData testRegoData = new RegoData();
		testRegoData.setFirstName("testFirstName");
		testRegoData.setLastName("testLastName");	
		testRegoData.setRegistration("TestRego");	
		
		Set<String> vehicles = new HashSet<String>();
		vehicles.add("TestRego");
		
		Person testPerson = new Person();
		testPerson.setFirstName("testFirstName");
		testPerson.setLastName("testLastName");	
		testPerson.setVehicles(vehicles);
    	
		when(regoService.linkPersonToVehicle(testRegoData)).thenReturn(testPerson);
        
        ResponseEntity<Person> responseEntity = regoController.linkPersonToVehicle(testRegoData); 
        
        assertEquals(testPerson, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
    
    @Test
    public void testUnlinkPersonToVehicle() throws Exception {
    	
		RegoData testRegoData = new RegoData();
		testRegoData.setFirstName("testFirstName");
		testRegoData.setLastName("testLastName");	
		testRegoData.setRegistration("TestRego");	
		
		Set<String> vehicles = new HashSet<String>();
		vehicles.add("TestRego");
		
		Person testPerson = new Person();
		testPerson.setFirstName("testFirstName");
		testPerson.setLastName("testLastName");	
		testPerson.setVehicles(vehicles);
    	
		when(regoService.unlinkPersonFromVehicle(testRegoData)).thenReturn(testPerson);
        
        ResponseEntity<Person> responseEntity = regoController.unlinkPersonToVehicle(testRegoData); 
        
        assertEquals(testPerson, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
	
}
