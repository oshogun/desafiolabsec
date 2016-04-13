package br.ufsc.labsec.desafio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for br.ufsc.labsec.desafio.Login.
 */
public class TestLogin {
	
	@Test
	public void testSanity() {
		Login login = new Login();
		assertTrue(true);
		assertEquals(login.getUser(), "");
	}
}
