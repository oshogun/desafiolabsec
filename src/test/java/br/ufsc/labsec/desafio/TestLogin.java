package br.ufsc.labsec.desafio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Test;

/**
 * Test class for br.ufsc.labsec.desafio.Login.
 */
public class TestLogin {
	
	@Test
	public void testSanity() throws Exception { 
		Login login = new Login();
		login.setUser("Teste");
		login.setPassword("senhamuitosegura");
		login.register();
		
		assertEquals(login.getUser(), "Teste");
		assertEquals(login.getPassword(),"senhamuitosegura");
		assertThat(login.getUser(), not("NaoTeste"));
		assertThat(login.getPassword(),not("senhanaotaosegura"));
		assertTrue(login.check("senhamuitosegura", login.getSaltedHash(login.getPassword())));
		assertFalse(login.check("senhanaotaosegura", login.getSaltedHash(login.getPassword())));
		
	
		
	}
}
