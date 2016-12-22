package ar.edu.utn.d2s.rest;

import static spark.Spark.post;
import static ar.edu.utn.d2s.rest.SessionTrasaction.withTransaction;
import static spark.Spark.delete;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ar.edu.utn.d2s.UsuarioTerminal;
import ar.edu.utn.d2s.ObservadoresConsulta.AbstractObservadorConsulta;
import ar.edu.utn.d2s.ObservadoresConsulta.GenerarReportes;
import ar.edu.utn.d2s.ObservadoresConsulta.GuardarBusqueda;
import ar.edu.utn.d2s.ObservadoresConsulta.LoggerTerminal;
import ar.edu.utn.d2s.ObservadoresConsulta.MailAdmin;
import ar.edu.utn.d2s.ObservadoresConsulta.ObservadorConsulta;
import ar.edu.utn.d2s.dao.AccionesUsuarioDao;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AccionesUsrControler extends AbstractController {
	private static AccionesUsuarioDao accionesDao;
	
	public AccionesUsrControler() {
	super();
	}
	
	public static Object agregarAccion(Request request, Response response) {
		withTransaction((EntityManager em) -> {		
			String accionText = request.queryParams("nuevaAccion");
			UsuarioTerminal usuarioTemp = request.session().attribute("user");
			UsuarioTerminal usr = em.find(UsuarioTerminal.class, usuarioTemp.getIdUsuario());
			switch (accionText) {
				case "Generar Reportes": {
					GenerarReportes generarReportes = new GenerarReportes();
					try {
						usr.agregarObserver(generarReportes);
					} catch (Exception e) {
					
						e.printStackTrace();
					}
					accionesDao.save(em, generarReportes);
					break;
				}
				case "Enviar mail a Administrador": {
					MailAdmin mailAdmin = new MailAdmin(60);
					try {
						usr.agregarObserver(mailAdmin);
					} catch (Exception e) {
					
						e.printStackTrace();
					}
					accionesDao.save(em, mailAdmin);
					break;
				}
				case "Guardar busquedas": {
					GuardarBusqueda guardarBusqueda = new GuardarBusqueda();
					try {
						usr.agregarObserver(guardarBusqueda);
					} catch (Exception e) {
					
						e.printStackTrace();
					}
					accionesDao.save(em, guardarBusqueda);
					break;
				}
				case "Generar log": {
					LoggerTerminal logTerminal = new LoggerTerminal();
					try {
						usr.agregarObserver(logTerminal);
					} catch (Exception e) {
					
						e.printStackTrace();
					}
					accionesDao.save(em, logTerminal);
					break;
				}
			}
			request.session().attribute("user", usr);
		});
		response.redirect("/accionesBusqueda");
		return null;
	}

	public static Object borrarAccion(Request request, Response response) {
		withTransaction((EntityManager em) -> {
			UsuarioTerminal usuarioTemp = request.session().attribute("user");
			UsuarioTerminal usr = em.find(UsuarioTerminal.class, usuarioTemp.getIdUsuario());
			ObservadorConsulta obs = (ObservadorConsulta) em.find(AbstractObservadorConsulta.class, Long.parseLong(request.params("id"))); 
			usr.eliminarObserver(obs);
			em.remove(obs);
			request.session().attribute("user", usr);
		});
		response.status(202);
		return "";
	}

	public static ModelAndView obtenerAcciones(Request request, Response res, Map<String, Object> modelAB,
			UsuarioTerminal sessionUsr) {
		UsuarioTerminal usuario = sessionUsr;
		List<ObservadorConsulta> accionesUsr = new ArrayList<>();
		accionesUsr = usuario.getSuscriptores();

		List<String> accionesD = new LinkedList<String>();
		accionesD.add("Generar Reportes");
		accionesD.add("Enviar mail a Administrador");
		accionesD.add("Guardar busquedas");
		accionesD.add("Generar log");

		List<String> accionesUsrD = new LinkedList<String>();
		accionesUsrD = creaListaActualizada(accionesD, accionesUsr);
		
		modelAB.put("accionesUsuarioD", accionesUsrD);
		modelAB.put("accionesUsuario", accionesUsr);
		return new ModelAndView(modelAB, "accionesBusqueda");

	}
	
	public static String obtenerAccionesJson(UsuarioTerminal sessionUsr) {
		UsuarioTerminal usuario = sessionUsr;
		List<Map<String, String>> accionesUsr = new ArrayList<Map<String, String>>();
		
		for (ObservadorConsulta obs : usuario.getSuscriptores()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("Nombre", obs.getClass().getSimpleName());
			accionesUsr.add(map);
		}
		
		return dataToJson(accionesUsr);
	}

	private static List<String> creaListaActualizada(List<String> accionesD, List<ObservadorConsulta> accionesUsr) {
		try{
		accionesUsr.stream().forEach(obs -> accionesD.remove(obs.toString()));
		} catch ( Exception e){ e.printStackTrace();}
		return accionesD;
	}

	public static void init(AccionesUsuarioDao accDao) {
		accionesDao = accDao;
		post("/accionesBusqueda", AccionesUsrControler::agregarAccion);
		delete("/accionesBusqueda/:id", AccionesUsrControler::borrarAccion);
	}


}
