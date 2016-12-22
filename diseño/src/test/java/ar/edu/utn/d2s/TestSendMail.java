package ar.edu.utn.d2s;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestSendMail {

	private MyMailSender mailSender;

	@Before
	public void setUp() {
		mailSender = new MyMailSender();	
	
	}

	@Test
	public void sendMail() throws Exception {
		String subject = "Test1";
		  String body = "Test Message1";
		  assertTrue( mailSender.sendMail("test.dest@nutpan.com", "test.src@nutpan.com", subject, body));
	}
	
	

}
