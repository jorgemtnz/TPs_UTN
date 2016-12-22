package ar.edu.utn.d2s.rest;

import static ar.edu.utn.d2s.rest.SessionTrasaction.withTransaction;
import static spark.Spark.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;

import ar.edu.utn.d2s.BancoSucursal;
import ar.edu.utn.d2s.Cgp;
import ar.edu.utn.d2s.Colectivo;
import ar.edu.utn.d2s.InformacionBusqueda;
import ar.edu.utn.d2s.LocalComercial;
import ar.edu.utn.d2s.PuntoInteres;
import ar.edu.utn.d2s.UsuarioTerminal;
import ar.edu.utn.d2s.dao.PdiRepositorio;
import ar.edu.utn.d2s.dao.UserDao;
import ar.edu.utn.d2s.main.SessionFactorySingleton;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class PdiController extends AbstractController {
	
	private static PdiRepositorio pdiRepo;
	private static UserDao usuarioDAO;
	private static SessionFactory factory = SessionFactorySingleton.instance();
	
	public PdiController() {
		super();
	}

	public static ModelAndView obtenerPdi(Request request, Response response) {
		Session session = factory.openSession();
		PuntoInteres pdi = pdiRepo.obtenerPdi(session, Integer.parseInt(request.params("id")));
		Map<String, Object> map = new HashMap<>();
		
		if(request.session().attribute("user") != null 
				|| !request.session().attribute("user").toString().isEmpty()){
			UsuarioTerminal user = request.session().attribute("user");
			map.put("pdi", pdi);
			map.put("userName",user.getNombre());	
			String viewName = pdi.getSimpleClassName().toLowerCase();		
			return new ModelAndView(map, viewName);
		}
		else
			response.redirect("/login");
		return null;
		
	}

	public static void init(PdiRepositorio pdiRepositorio,
							UserDao usuarioRepositorio) {
		pdiRepo = pdiRepositorio;
		usuarioDAO = usuarioRepositorio;
		ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();
		get("/pdi/:id", PdiController::obtenerPdi, engine);
	}

	public static ModelAndView obtenerPdis(Request request, Response res, Map<String,Object> modelB) {
		Session session = factory.openSession();
		List<PuntoInteres> pdis = new ArrayList<>();
				
		//Obtengo los pdis
		//Busqueda vacia o primera vez que carga la pagina				
		if (request.queryParams("nombre") == null || request.queryParams("nombre").isEmpty()) {
			pdis = pdiRepo.obtenerPdis(session);
			//Cargo el diccionario con los pdis
			modelB.put("pdis", pdis);
		//Busqueda con texto
		} else {
			//Inicializo tiempo de busqueda
			long tiempoDeInicioDeBusqueda;
			long tiempoDeFinalizacionDeBusqueda;				
			tiempoDeInicioDeBusqueda = System.currentTimeMillis();

			//Busco los pdis
			String textoBusqueda = request.queryParams("nombre");
			List<PuntoInteres> pdisTemp = pdiRepo.obtenerPdis(session, textoBusqueda);

			//Seteo tiempo de finalizacion de busqueda
			tiempoDeFinalizacionDeBusqueda = System.currentTimeMillis();
			long tiempoBusquedaMilisegundos = tiempoDeFinalizacionDeBusqueda - tiempoDeInicioDeBusqueda;
			
			//Inicializo informacion busqueda
			InformacionBusqueda info = new InformacionBusqueda();
			info.setCantResultados(pdisTemp.size());
			info.setNombreUsuarioTerminal(request.session().attribute("userName"));
			info.setTextoBusqueda(textoBusqueda);
			info.setTiempoBusqueda(tiempoBusquedaMilisegundos);
			info.setFecha(LocalDate.now());
			
			// Persistir la info de busqueda con sus pdis asociados, y el usuario 
			// con el observer contenedor de la informacion de busqueda
			// ***** Guarda los datos nuevos solo si el observer/accion esta activo 
			// ***** y fue notificado
			UsuarioTerminal user = (UsuarioTerminal) modelB.get("user");
			withTransaction((EntityManager em) -> {				
				pdisTemp.stream().forEach(elem -> {
					info.getPdisDevueltos().add(em.find(PuntoInteres.class, elem.getId()));
				});
				UsuarioTerminal usrPersist = em.find(UsuarioTerminal.class, user.getIdUsuario());
				usrPersist.notificarObservers(info);
				usuarioDAO.save(em, usrPersist);
				request.session().attribute("user", usrPersist);
			});
			
			//Cargo el diccionario con los pdis
			modelB.put("pdis", pdisTemp);
		}		
		return new ModelAndView(modelB, "busqueda");
	}
	public static String obtenerPdisJson() {
	
		Session session = factory.openSession();
		List<PuntoInteres> pdis = new ArrayList<>();
		pdis = pdiRepo.obtenerPdis(session);
		return dataToJson(pdis);
	}
	
	public static String obtenerPdiJson(Integer id){
		Session session = factory.openSession();
		
		PuntoInteres pdi = pdiRepo.obtenerPdi(session,id);
		String pdiName = pdi.getSimpleClassName().toLowerCase();	
		String noEncontroPDI = new String("No se encontro el id solicitado");
		
		switch(pdiName){
			case "cgp":{
				Cgp cgp = new Cgp();
				cgp = (Cgp) pdi;
				return dataToJson(cgp);
				
				}
			case "colectivo":{
				Colectivo colectivo =new Colectivo();
				colectivo = (Colectivo) pdi;
				return dataToJson(colectivo);
				
			}
			case "localcomercial":{
			    LocalComercial local = new LocalComercial();
			    local = (LocalComercial) pdi;
			    return dataToJson(local);
			}
			case "bancosucursal":{
				BancoSucursal banco = new BancoSucursal();
				banco = (BancoSucursal) pdi;
				return dataToJson(banco);
			}	
			
		}		
		return dataToJson(noEncontroPDI);
		
	}


}
