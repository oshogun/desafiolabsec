package br.ufsc.labsec.desafio;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



/**
 * Class for registering and authenticating users.
 */
@ManagedBean
@SessionScoped
public class Login {
	private JSONObject users;
	
	
	private String message;

	private String user;
	private String password;
	
	
	public Login() {
		JSONParser parser = new JSONParser();
		
		try (FileReader fr = new FileReader("passwords.json")) {
			users = (JSONObject) parser.parse(fr);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the current user name
	 */
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the current password
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return a message for the user
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Verifies if the current user and password match a user in the database.
	 */
	public void login() {
		if (!users.containsKey(user)) {
			message = "No such user.";
		} else if (users.get(user).equals(password)) {
			message = "Login ok.";			
		} else {
			message = "Login fail.";			
		}
	}

	/**
	 * Registers the current user and password.
	 */
	public void register() {
		users.put(user, password);
		message = "User registered!";	
		try (FileWriter fw = new FileWriter("passwords.json")) {
			fw.write(users.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
