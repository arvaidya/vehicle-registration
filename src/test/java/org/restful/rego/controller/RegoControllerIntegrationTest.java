package org.restful.rego.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.restful.rego.model.Person;
import org.restful.rego.model.RegistrationDirectory;
import org.restful.rego.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RegoControllerIntegrationTest {
	
    // bind the above RANDOM_PORT
    @LocalServerPort
    private int port;
    
	@Mock
	RegistrationDirectory regoDirectory;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
	ObjectMapper om = new ObjectMapper();
	
    @Test
    public void getRegistrations() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity(
            new URL("http://localhost:" + port + "/vehicle-registration/registrationsData").toString(), String.class);
        assertEquals("{\"persons\":[],\"vehicles\":[]}", response.getBody());

    }
    
    @Test
    public void createPerson() throws Exception {
    	
    	Person testPerson = new Person();
		testPerson.setFirstName("testFirstName");
		testPerson.setLastName("testLastName");		
    	
    	HttpEntity<Person> requestEntity = new HttpEntity<>(testPerson, new HttpHeaders());

        ResponseEntity<String> response = restTemplate.postForEntity(
            new URL("http://localhost:" + port + "/vehicle-registration/person").toString(), requestEntity, String.class);
        
        
        assertEquals(om.writeValueAsString(testPerson), response.getBody());

    }
    
    @Test
    public void createVehicle() throws Exception {
    	
		Vehicle testVehicle = new Vehicle();
		testVehicle.setMake("testToyota");
		testVehicle.setModel("testCamry");
		testVehicle.setRegistration("TestRego");		
    	
    	HttpEntity<Vehicle> requestEntity = new HttpEntity<>(testVehicle, new HttpHeaders());

        ResponseEntity<String> response = restTemplate.postForEntity(
            new URL("http://localhost:" + port + "/vehicle-registration/vehicle").toString(), requestEntity, String.class);   
        
        assertEquals(om.writeValueAsString(testVehicle), response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }
	
}
