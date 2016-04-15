package br.ufsc.labsec.desafio;

public class HashTest {
	public static void main(String[] args) {
		Login loginManager = new Login();
		
		loginManager.setUser("Joao");
		loginManager.setPassword("12345");
		
		try {
			System.out.println(loginManager.getSaltedHash(loginManager.getPassword()));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}