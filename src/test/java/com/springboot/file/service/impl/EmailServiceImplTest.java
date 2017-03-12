/**
 * 
 */
package com.springboot.file.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Unit test class for EmailService
 * @author Prasad Paravatha
 */
public class EmailServiceImplTest {

	@InjectMocks
	EmailServiceImpl emailService;
	
	@Mock
	private JavaMailSender javaMailService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);	
	}

	@Test
	public void test() throws Exception {
		emailService.sendEmail("Test");
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
}
