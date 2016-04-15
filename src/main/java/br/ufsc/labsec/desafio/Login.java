package br.ufsc.labsec.desafio;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.KeySpec;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
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
	
	public Login() {
		Security.addProvider(new BouncyCastleProvider());
		JSONParser parser = new JSONParser();
		
		try (FileReader fr = new FileReader("passwords.json")) {
			users = (JSONObject) parser.parse(fr);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	public String getSaltedHash(String password) throws Exception{
		byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
		
		return Base64.toBase64String(salt)+"$"+hash(password,salt);
		
	}
	
	public String hash(String password, byte[] salt) throws Exception {
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1", "BC");
		SecretKey key = f.generateSecret(new PBEKeySpec(
				password.toCharArray(), salt, iterations, desiredKeyLen));
		return Base64.toBase64String(key.getEncoded());
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
	

