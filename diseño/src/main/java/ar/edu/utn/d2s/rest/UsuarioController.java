package ar.edu.utn.d2s.rest;

import ar.edu.utn.d2s.dao.StringUtil;
import ar.edu.utn.d2s.dao.UserDao;
import ar.edu.utn.d2s.main.SessionFactorySingleton;

import ar.edu.utn.d2s.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UsuarioController {

	private static SessionFactory factory = SessionFactorySingleton.instance();

	//Se genera el dao como variable estatica en el controller 
	private static UserDao usuarioDAO;

	public UsuarioController() {
		super();
	}
	
	//password en este contexto es el nuevo ingresado , que debo verificar con el de la DB.
	public static UsuarioTerminal obtenerUsuario(String user, String password) {		
		Session session = factory.openSession();
		StringUtil convertirPassword = new StringUtil();
		String userName = null;
		String userPass = null;		
		UsuarioTerminal usuarioTemporal = usuarioDAO.obtenerUsuario(session, user);
		if (usuarioTemporal != null) 
		{
			userName = usuarioTemporal.getNombre();
			userPass = usuarioTemporal.getPassword();
			
			if ((userName.equals(user)) &&
					(userPass.equals(convertirPassword.encryptConSalt(	password, 
																		usuarioTemporal.getSaltEncoding()))))
			{			
				return usuarioTemporal;
			}
			else {
			
				return null;
			}
		}
		else
			return null;
	}
	
	public static void init(UserDao userDAO) {
		usuarioDAO = userDAO;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++

}
