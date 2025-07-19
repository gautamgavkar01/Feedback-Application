package com.customerfeedback.customer.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;

@SpringBootTest
class CustomerFeedbackApplicationTests {

//	@Test
//	void contextLoads() {
//	}
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void printAdminPassword() {
	    String rawPassword = "admin123";
	    String encodedPassword = passwordEncoder.encode(rawPassword);
	    System.out.println("Encoded Admin Password: " + encodedPassword);
	}


}
