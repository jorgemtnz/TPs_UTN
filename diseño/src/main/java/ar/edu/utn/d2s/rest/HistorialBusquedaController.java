package ar.edu.utn.d2s.rest;

import static spark.Spark.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ar.edu.utn.d2s.main.SessionFactorySingleton;
import ar.edu.utn.d2s.InformacionBusqueda;
import ar.edu.utn.d2s.PuntoInteres;
import ar.edu.utn.d2s.UsuarioTerminal;
import ar.edu.utn.d2s.dao.BusquedasRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class HistorialBusquedaController extends AbstractController {
	private static BusquedasRepositorio busquedaRepo;
	private static SessionFactory factory = SessionFactorySingleton.instance();
	
	public HistorialBusquedaController() {
		super();
	}
	
	public static ModelAndView listarBusqueda(Request request, Response response, Map<String,Object> modelHB) {
		Session session = factory.openSession();
		List<InformacionBusqueda> historialBusquedas = new ArrayList<>();
        

		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd"); 

		boolean consultaVacia = (request.queryParams("fechaDesde") == null
				|| request.queryParams("fechaDesde").isEmpty())
				&& (request.queryParams("fechaHasta") == null || request.queryParams("fechaHasta").isEmpty())
				&& (request.queryParams("nombreBuscado") == null || request.queryParams("nombreBuscado").isEmpty());

		boolean consultaSoloUsuarios = (request.queryParams("fechaDesde") == null
				|| request.queryParams("fechaDesde").isEmpty())
				&& (request.queryParams("fechaHasta") == null || request.queryParams("fechaHasta").isEmpty())
				&& !(request.queryParams("nombreBuscado") == null || request.queryParams("nombreBuscado").isEmpty());

		boolean consultaSoloFechas = !(request.queryParams("fechaDesde") == null
				|| request.queryParams("fechaDesde").isEmpty())
				&& !(request.queryParams("fechaHasta") == null || request.queryParams("fechaHasta").isEmpty())
				&& (request.queryParams("nombreBuscado") == null || request.queryParams("nombreBuscado").isEmpty());

		boolean consultaSoloFechaHasta = (request.queryParams("fechaDesde") == null
				|| request.queryParams("fechaDesde").isEmpty())
				&& !(request.queryParams("fechaHasta") == null || request.queryParams("fechaHasta").isEmpty())
				&& (request.queryParams("nombreBuscado") == null || request.queryParams("nombreBuscado").isEmpty());

		boolean consultaSoloFechaDesde = !(request.queryParams("fechaDesde") == null
				|| request.queryParams("fechaDesde").isEmpty())
				&& (request.queryParams("fechaHasta") == null || request.queryParams("fechaHasta").isEmpty())
				&& (request.queryParams("nombreBuscado") == null || request.queryParams("nombreBuscado").isEmpty());
		
		boolean consultaFechaHastaConUsuario = (request.queryParams("fechaDesde") == null
				|| request.queryParams("fechaDesde").isEmpty())
				&& !(request.queryParams("fechaHasta") == null || request.queryParams("fechaHasta").isEmpty())
				&& !(request.queryParams("nombreBuscado") == null || request.queryParams("nombreBuscado").isEmpty());
		
		boolean consultaFechaDesdeConUsuario = !(request.queryParams("fechaDesde") == null
				|| request.queryParams("fechaDesde").isEmpty())
				&& (request.queryParams("fechaHasta") == null || request.queryParams("fechaHasta").isEmpty())
				&& !(request.queryParams("nombreBuscado") == null || request.queryParams("nombreBuscado").isEmpty());
		
		boolean consultaLlena = !(request.queryParams("fechaDesde") == null
				|| request.queryParams("fechaDesde").isEmpty())
				&& !(request.queryParams("fechaHasta") == null || request.queryParams("fechaHasta").isEmpty())
				&& !(request.queryParams("nombreBuscado") == null || request.queryParams("nombreBuscado").isEmpty());

		if (consultaVacia) {
			historialBusquedas = busquedaRepo.obtenerBusquedas(session); // trae todas
		}

		if (consultaSoloUsuarios) {
			historialBusquedas = busquedaRepo.obtenerBusquedas(session, request.queryParams("nombreBuscado"));
		}

		if (consultaSoloFechas) {
			LocalDate fechaDesde = dtf.parseLocalDate(request.queryParams("fechaDesde"));
			LocalDate fechaHasta = dtf.parseLocalDate(request.queryParams("fechaHasta"));
			
			historialBusquedas = busquedaRepo.obtenerBusquedas(session, fechaDesde, fechaHasta);
		}

		if (consultaSoloFechaHasta) {
			LocalDate fechaHasta = dtf.parseLocalDate(request.queryParams("fechaHasta"));
			
			historialBusquedas = busquedaRepo.obtenerBusquedas(session, fechaHasta);
		}
		
		if (consultaSoloFechaDesde) {
			LocalDate fechaHasta = LocalDate.now();
			LocalDate fechaDesde = dtf.parseLocalDate(request.queryParams("fechaDesde"));

			historialBusquedas = busquedaRepo.obtenerBusquedas(session, fechaDesde, fechaHasta);
		}
		
		if (consultaFechaHastaConUsuario){
			LocalDate fechaHasta = dtf.parseLocalDate(request.queryParams("fechaHasta"));
			historialBusquedas = busquedaRepo.obtenerBusquedas(session, fechaHasta, request.queryParams("nombreBuscado"));

		}
		
		if (consultaFechaDesdeConUsuario){
			LocalDate fechaHasta = LocalDate.now();
			LocalDate fechaDesde = dtf.parseLocalDate(request.queryParams("fechaDesde"));
			historialBusquedas = busquedaRepo.obtenerBusquedas(session, fechaDesde, fechaHasta, request.queryParams("nombreBuscado"));

		}
		
		if(consultaLlena){
			LocalDate fechaHasta = dtf.parseLocalDate(request.queryParams("fechaHasta"));
			LocalDate fechaDesde = dtf.parseLocalDate(request.queryParams("fechaDesde"));
			historialBusquedas = busquedaRepo.obtenerBusquedas(session, fechaDesde, fechaHasta, request.queryParams("nombreBuscado"));
		}

		modelHB.put("historialBusquedas", historialBusquedas);
		return new ModelAndView(modelHB, "historialBusqueda");
	}
	
	public static ModelAndView obtenerPdisSegunBusqueda(Request request, Response response) {
		Session session = factory.openSession();
		List<PuntoInteres> infoBusqueda = busquedaRepo.obtenerPdisXBusqueda(session, Integer.parseInt(request.params("id")));
		Map<String, Object> map = new HashMap<>();
		
		if(request.session().attribute("user") != null 
				|| !request.session().attribute("user").toString().isEmpty()){
			UsuarioTerminal user = request.session().attribute("user");
		map.put("userName",user.getNombre());
		map.put("infoBusqueda", infoBusqueda);
		return new ModelAndView(map, "infoBusqueda");
		}
		else
			response.redirect("/login");
		return null;
		
	}
	
	
	public static void init(BusquedasRepositorio pBusquedaRepo) {
		busquedaRepo = pBusquedaRepo;
		ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();
		get("/infoBusqueda/:id", HistorialBusquedaController::obtenerPdisSegunBusqueda, engine);
	}

	public static String listarBusquedaJson() {
		Session session = factory.openSession();
		List<Map<String, String>> historialBusquedas = new ArrayList<Map<String, String>>();
		
		for (InformacionBusqueda informacionBusqueda : busquedaRepo.obtenerBusquedas(session)) {
			String cantRes = ((Integer) informacionBusqueda.getCantResultados()).toString();
			String tiempo = ((Long) informacionBusqueda.getTiempoBusqueda()).toString();
			Map<String, String> map = new HashMap<String, String>();
			map.put("Fecha", informacionBusqueda.getFecha().toString("yyyy-MM-dd"));
			map.put("Usuario", informacionBusqueda.getNombreUsuarioTerminal());
			map.put("Parametros", informacionBusqueda.getTextoBusqueda());
			map.put("Pois", cantRes);
			map.put("Tiempo de busqueda", tiempo);
			historialBusquedas.add(map);
		}
		return dataToJson(historialBusquedas);
	}
	
	public static String buscarPdisBusquedaJson(Integer id) {
		Session session = factory.openSession();
		List<PuntoInteres> infoBusqueda = busquedaRepo.obtenerPdisXBusqueda(session, id);
		
		return dataToJson(infoBusqueda);
		
	}

}
