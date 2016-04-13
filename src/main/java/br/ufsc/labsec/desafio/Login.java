package br.ufsc.labsec.desafio;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Class for registering and authenticating users.
 */
@ManagedBean
@SessionScoped
public class Login {
	private static Map<String, String> users = new HashMap<String, String>();

	private String message;

	private String user;
	private String password;

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
	}
}
