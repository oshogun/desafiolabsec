package br.ufsc.labsec.desafio;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.Security;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;


import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
	
	
	private static final int iterations = 200000;
	private static final int saltLen = 32;
	private static final int desiredKeyLen = 256;
	
	/**
	 *  Default constructor. Loads/initializes the password database
	 */
	public Login() {
		// Adds Bouncy Castle as a provider for security algorithms
		Security.addProvider(new BouncyCastleProvider()); 
		JSONParser parser = new JSONParser();
		
		// Parses the passwords file, if exists, or creates a new one
		try (FileReader fr = new FileReader("passwords.json")) {
			users = (JSONObject) parser.parse(fr);
		} catch (FileNotFoundException e) {			
			try {
				FileWriter fw = new FileWriter("passwords.json");
				users = new JSONObject();
				fw.write(users.toJSONString());
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param password
	 * @return A salted hash of the password, in the string format "salt$hash"
	 * @throws Exception
	 */
	public String getSaltedHash(String password) throws Exception {
		byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
		
		return Base64.toBase64String(salt)+"$"+hash(password,salt);
		
	}
	
	/**
	 * 
	 * @param password
	 * @param salt
	 * @return a hash of the given password, using the given salt
	 * @throws Exception
	 */
	public String hash(String password, byte[] salt) throws Exception {
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1", "BC");
		SecretKey key = f.generateSecret(new PBEKeySpec(
				password.toCharArray(), salt, iterations, desiredKeyLen));
		return Base64.toBase64String(key.getEncoded());
	}
	
	/**
	 * 
	 * @param password
	 * @param storedPassword
	 * @return true if given password matches with the stored one.
	 * @throws Exception
	 */
	public boolean check(String password, String storedPassword) throws Exception {
		String[] saltAndPass = storedPassword.split("\\$");
		if (saltAndPass.length != 2) {
			throw new IllegalStateException("Stored password should have the form salt$hash");
		}
		String tested = hash(password, Base64.decode(saltAndPass[0]));
		return tested.equals(saltAndPass[1]);
	
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
		try {
			if (!users.containsKey(user)) {
				message = "No such user.";
			} else if (check(password,users.get(user).toString())) {
				message = "Login ok.";			
			} else {
				message = "Login fail.";			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Registers the current user and password.
	 */
	public void register() {
		try {
			String newSaltedHash = getSaltedHash(password);
			users.put(user, newSaltedHash);
			FileWriter fw = new FileWriter("passwords.json");
			fw.write(users.toJSONString());
			message = "User registered!";	
			fw.close();
			// message = getAbsolutePathOfDBFile(); // just for test
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// just for testing purposes
	public String getAbsolutePathOfDBFile() {
		File f = new File("passwords.json");
		
		return f.getAbsolutePath(); 
	}
	
	
	
}
	

