package ar.edu.utn.d2s.dao;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import ar.edu.utn.d2s.UsuarioTerminal;
//
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class StringUtil {

	private static Random random = new Random((new Date()).getTime());

	
	public StringUtil(){
		super();
	}
	/**
	 * encripta el string usando el salt
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String password, UsuarioTerminal usuario) {
		BASE64Encoder encoder = new BASE64Encoder();
		
		byte[] salt = new byte[8];		
		random.nextBytes(salt);
		usuario.setSaltEncoding(salt);
		return encoder.encode(salt) + encoder.encode(password.getBytes());
	}


	public Object encryptConSalt(String password, byte[] saltEncoding) {
		BASE64Encoder encoder = new BASE64Encoder();

		return encoder.encode(saltEncoding) + encoder.encode(password.getBytes());		
	
	}

}
