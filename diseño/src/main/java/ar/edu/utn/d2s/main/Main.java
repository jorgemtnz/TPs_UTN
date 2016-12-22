package ar.edu.utn.d2s.main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager; 
import org.joda.time.LocalDate;
import ar.edu.utn.d2s.dao.*;
import ar.edu.utn.d2s.BancoSucursal;
import ar.edu.utn.d2s.Cgp;
import ar.edu.utn.d2s.Colectivo;
import ar.edu.utn.d2s.ConvertidorBancoExterno;
import ar.edu.utn.d2s.ConvertidorCGP;
import ar.edu.utn.d2s.Coordenada;
import ar.edu.utn.d2s.Horario;
import ar.edu.utn.d2s.UsuarioTerminal;
import ar.edu.utn.d2s.Mockup_SE_Banco.Mockup_SE_Banco;
import ar.edu.utn.d2s.Mockup_SE_Cgp.Mockup_SE_Cgp;
import ar.edu.utn.d2s.ObservadoresConsulta.GenerarReportes;
import ar.edu.utn.d2s.ObservadoresConsulta.GuardarBusqueda;
import ar.edu.utn.d2s.ObservadoresConsulta.MailAdmin;
import ar.edu.utn.d2s.LocalComercial;
import ar.edu.utn.d2s.PuntoInteres;
import ar.edu.utn.d2s.RangoHorario;
import ar.edu.utn.d2s.Servicio;
import ar.edu.utn.d2s.dao.PdiRepositorio;
import ar.edu.utn.d2s.dao.UserDao;
import ar.edu.utn.d2s.exceptions.ObserverException;
import ar.edu.utn.d2s.rest.PdiController;
import ar.edu.utn.d2s.rest.UsuarioController;
import ar.edu.utn.d2s.dao.BusquedasRepositorio;
import ar.edu.utn.d2s.rest.AccionesUsrControler;
import ar.edu.utn.d2s.rest.HistorialBusquedaController;
import ar.edu.utn.d2s.InformacionBusqueda;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import static ar.edu.utn.d2s.rest.SessionTrasaction.withTransaction;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
	private static boolean errorLogueo;

	public static void main(String[] args) {
		errorLogueo = new Boolean(false);
		staticFiles.location("/static");
		ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();

		PdiRepositorio pdiRepo = new PdiRepositorio();
		UserDao usuarioDAO = new UserDao();
		BusquedasRepositorio busquedasRepo = new BusquedasRepositorio();
		AccionesUsuarioDao accionesDao = new AccionesUsuarioDao();

		inicializarDB_CargaInicial(pdiRepo, busquedasRepo);

		// Inicializacion de Controllers
		PdiController.init(pdiRepo, usuarioDAO);
		HistorialBusquedaController.init(busquedasRepo);
		AccionesUsrControler.init(accionesDao);
		UsuarioController.init(usuarioDAO);
		

		// estos son los endpoints
		post("/login", (req, res) -> {
			req.queryParams().forEach(q -> {
				System.out.println(String.format("%s = %s", q, req.queryParams(q)));
			});

			// controlo si es un usuario valido
			String userNameReq = req.queryParams("user");
			String userPasswordReq = req.queryParams("password");
			UsuarioTerminal usuario = UsuarioController.obtenerUsuario(userNameReq, userPasswordReq);

			if (usuario != null) {
				req.session().attribute("user", usuario);
				req.session().attribute("userName", userNameReq);
				req.session().attribute("userPassword", userPasswordReq);
				errorLogueo = false;
				res.redirect("/home");
			} else {
				System.out.println("Acceso denegado error en usuario o password, redireccionado a /login");
				errorLogueo = true;
				res.redirect("/login");
			}
			return 0;
		});
		// / ---> cuando la escribo me va directo al index. que es la pagina por
		// defecto
		get("/", (req, res) -> {
			UsuarioTerminal user = req.session().attribute("user");
			if (user == null) {
				System.out.println("intento acceder a index, redireccionado a /login");
				res.redirect("/login");
			} else
				res.redirect("/home");
			return 0;
		});
		get("/login", Main::loguear, engine);
		get("/busqueda", Main::controlarLogueoB, engine);
		get("/accionesBusqueda", Main::controlarLogueoAB, engine);
		get("/historialBusqueda", Main::controlarLogueoHB, engine);
		get("/home", Main::controlarLogueoHome, engine);
		get("/logout", (req, res) -> {
			req.session().removeAttribute("user");
			req.session().removeAttribute("userName");
			req.session().removeAttribute("userPassword");
			
			System.out.println("se cierra la sesion");
			res.redirect("/login");
			return 0;
		});

		// localhost:4567/json/pdi/{:id}
		get("/json/pdi/:id", (request, response) -> {

			UsuarioTerminal user = request.session().attribute("user");
			if (user == null) {
				System.out.println("Acceso denegado, redireccionado a /login");
				response.redirect("/login");
				return null;
			} else {
				response.status(200);
				response.type("application/json");
				return PdiController.obtenerPdiJson(Integer.parseInt(request.params(":id")));
			}

		});
//		localhost:4567/json/infoBusqueda/{:id}
		get("/json/infoBusqueda/:id", (request, response) -> {

			UsuarioTerminal user = request.session().attribute("user");
			if (user == null) {
				System.out.println("Acceso denegado, redireccionado a /login");
				response.redirect("/login");
				return null;
			} else {
				response.status(200);
				response.type("application/json");
				return HistorialBusquedaController.buscarPdisBusquedaJson(Integer.parseInt(request.params(":id")));
			}

		});

		// localhost:4567/json/accionesBusqueda
		// localhost:4567/json/busqueda
		// localhost:4567/json/historialBusqueda
		get("/json/*", (request, response) -> {
			String route = new String();
			route = request.splat()[0];

			Map<String, Object> model = new HashMap<>();
			ModelAndView modelAndView = new ModelAndView(model, route);

			UsuarioTerminal user = request.session().attribute("user");
			if (user == null) {
				System.out.println("Acceso denegado, redireccionado a /login");
				response.redirect("/login");
				return modelAndView;
			}

			response.status(200);
			response.type("application/json");
			String jsonString = new String();
			switch (route) {
			case "accionesBusqueda":
				return AccionesUsrControler.obtenerAccionesJson(user);
			case "busqueda":
				return PdiController.obtenerPdisJson();
			case "historialBusqueda":
				return HistorialBusquedaController.listarBusquedaJson();

			}
			return jsonString;
		});

		// fin del main
	}

	public static ModelAndView loguear(Request req, Response res) {
		Map<String, Object> modelLogin = new HashMap<>();
		ModelAndView modelAndViewLogin = new ModelAndView(modelLogin, "login");
		String error = new String();
		error = "Usuario o password incorrectos";
		if (errorLogueo) {
			System.out.println("errorLogueo true");
			modelLogin.put("error", error);
		}
		return modelAndViewLogin;
	}

	public static ModelAndView controlarLogueoHome(Request req, Response res) {
		Map<String, Object> modelHome = new HashMap<>();
		ModelAndView modelAndViewHome = new ModelAndView(modelHome, "home");

		UsuarioTerminal user = req.session().attribute("user");
		if (user == null) {
			System.out.println("Acceso denegado en /home, redireccionado a /login");
			res.redirect("/login");
		}
		modelHome.put("user", user);
		modelHome.put("userName", req.session().attribute("userName"));
		return modelAndViewHome;
	}

	public static ModelAndView controlarLogueoAB(Request req, Response res) {
		Map<String, Object> modelAB = new HashMap<>();
		ModelAndView modelAndViewAccionesBusqueda = new ModelAndView(modelAB, "accionesBusqueda");
		UsuarioTerminal user = req.session().attribute("user");
		if (user == null) {
			System.out.println("Acceso denegado en /home, redireccionado a /login");
			res.redirect("/login");
			return modelAndViewAccionesBusqueda;
		}

		modelAB.put("user", user);
		modelAB.put("userName", req.session().attribute("userName"));
		return AccionesUsrControler.obtenerAcciones(req, res, modelAB, user);
	}

	public static ModelAndView controlarLogueoB(Request req, Response res) {
		Map<String, Object> modelB = new HashMap<>();
		UsuarioTerminal user = req.session().attribute("user");
		if (user == null) {
			System.out.println("Acceso denegado en /home, redireccionado a /login");
			res.redirect("/login");
		}
		modelB.put("user", user);
		modelB.put("userName", req.session().attribute("userName"));
		return PdiController.obtenerPdis(req, res, modelB);
	}

	public static ModelAndView controlarLogueoHB(Request req, Response res) {
		Map<String, Object> modelHB = new HashMap<>();
		UsuarioTerminal user = req.session().attribute("user");
		if (user == null) {
			System.out.println("Acceso denegado en /home, redireccionado a /login");
			res.redirect("/login");
		} else
			modelHB.put("user", user);
		modelHB.put("userName", req.session().attribute("userName"));
		return HistorialBusquedaController.listarBusqueda(req, res, modelHB);
	}

	// *****************************************************************************
	/*
	 * Inicializa la DB con datos, sólo si setea el parámetro
	 * inicializarDatos:true en el archivo de configuración del sistema
	 * system.cfg
	 */
	public static void inicializarDB_CargaInicial(PdiRepositorio pdiRepo, BusquedasRepositorio busquedasRepo) {
		if (inicializarDB()) {
		
			inicializarLocalesComerciales(pdiRepo);
			inicializarCgps(pdiRepo);
			inicializarBancos(pdiRepo);
			inicializarUsuarios(pdiRepo);
			inicializarColectivos(pdiRepo);

		}
	}

	/*
	 * Lee el parámetro inicializarDatos del archivo system.cfg y lo convierte
	 * en su correpondiente booleano
	 * 
	 * Valores posibles para el parámetro incializarDatos: Valor 1 = true Valor
	 * 2 = false
	 */
	public static boolean inicializarDB() {
		boolean inicializar = false;
		Map<String, String> config = new HashMap<String, String>();
		try (Stream<String> stream = Files.lines(Paths.get("src/main/java/ar/edu/utn/d2s/files/system.cfg"))) {
			stream.forEach(linea -> {
				String parametro = linea.split(":")[0];
				String valor = linea.split(":")[1];
				config.put(parametro, valor);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		inicializar = Boolean.parseBoolean(config.get("inicializarDatos"));
		return inicializar;
	}

	private static void inicializarColectivos(PdiRepositorio pdiRepo) {
		String nombre = "Línea 500";
		PuntoInteres bondi = new Colectivo(nombre, 500);
		PuntoInteres bondi101 = new Colectivo("Linea 101", 101);
		PuntoInteres bondi7 = new Colectivo("Linea 7", 7);
		PuntoInteres bondi47 = new Colectivo("Linea 47", 7);
		withTransaction((EntityManager em) -> {
			pdiRepo.save(em, bondi);
			pdiRepo.save(em, bondi101);
			pdiRepo.save(em, bondi7);
			pdiRepo.save(em, bondi47);
		});
	}

	private static void inicializarLocalesComerciales(PdiRepositorio pdiRepo) {
		LocalComercial abastoShopping = new LocalComercial("Abasto Shopping", "Shopping");
		Horario horarioAtencion = new Horario(4, 9, 30, 15, 30);
		RangoHorario rangoHorario = new RangoHorario();
		rangoHorario.agregar(horarioAtencion);
		abastoShopping.setRango(rangoHorario);
		abastoShopping.setCallePrincipal("Av. Corrientes");
		abastoShopping.setNumeroDireccion(3247);
		abastoShopping.setRubro("Comercio");
		withTransaction((EntityManager em) -> {
			pdiRepo.save(em, rangoHorario);
			pdiRepo.save(em, abastoShopping);
		});
	}

	private static void inicializarBancos(PdiRepositorio pdiRepo) {
		agregarBancosMock(pdiRepo);
		BancoSucursal banco = new BancoSucursal("Banco Ciudad Suc Principal", "Microcentro");

		/* ***** ******** */

		Horario horarioAtencion1 = new Horario(2, 11, 50, 12, 30);
		Horario horarioAtencion2 = new Horario(3, 14, 30, 14, 55);
		RangoHorario rangoHorario = new RangoHorario();

		rangoHorario.agregar(horarioAtencion1);
		rangoHorario.agregar(horarioAtencion2);

		Servicio servicio1 = new Servicio("Transferencias", rangoHorario);

		/* ***** ******** */

		Horario horarioAtencion3 = new Horario(4, 13, 45, 14, 45);
		Horario horarioAtencion4 = new Horario(5, 10, 25, 11, 25);
		RangoHorario rangoHorario2 = new RangoHorario();

		rangoHorario2.agregar(horarioAtencion3);
		rangoHorario2.agregar(horarioAtencion4);

		Servicio servicio2 = new Servicio("Créditos", rangoHorario2);

		/* ***** ******** */

		banco.addServicio(servicio1);
		banco.addServicio(servicio2);

		banco.setCallePrincipal("Tte. Gral Juan Domingo Peron");
		banco.setNumeroDireccion(566);

		withTransaction((EntityManager em) -> {
			pdiRepo.save(em, rangoHorario);
			pdiRepo.save(em, rangoHorario2);
			pdiRepo.save(em, servicio1);
			pdiRepo.save(em, servicio2);
			pdiRepo.save(em, banco);
		});
	}

	
	@SuppressWarnings("static-access")
	private static void inicializarUsuarios(PdiRepositorio pdiRepo) {
		StringUtil convertirPassword = new StringUtil();

		Colectivo linea5 = new Colectivo("Linea 5", 50);
		Colectivo linea51 = new Colectivo("Linea 51", 51);
		Colectivo linea2 = new Colectivo("Linea 2", 92);
		UsuarioTerminal usuarioJorge = new UsuarioTerminal("TerminalJorge");
		UsuarioTerminal usuarioWalter = new UsuarioTerminal("TerminalWalter");
		UsuarioTerminal usuarioEnzo = new UsuarioTerminal("TerminalEnzo");
		usuarioJorge.setPassword(convertirPassword.encrypt("123", usuarioJorge));
		usuarioWalter.setPassword(convertirPassword.encrypt("123", usuarioWalter));
		usuarioEnzo.setPassword(convertirPassword.encrypt("123", usuarioEnzo));
		UsuarioTerminal usuarioTest = new UsuarioTerminal("test");
		usuarioTest.setPassword(convertirPassword.encrypt("123", usuarioTest));
				
		linea5.addParada(new Coordenada(0, 2));
		linea5.addParada(new Coordenada(0, 8));
		linea5.addParada(new Coordenada(0, 10));
		linea5.addParada(new Coordenada(0, 12));
		linea5.addParada(new Coordenada(0, 14));
		linea51.addParada(new Coordenada(2, 0));
		linea51.addParada(new Coordenada(8, 0));
		linea51.addParada(new Coordenada(10, 0));
		linea51.addParada(new Coordenada(12, 0));
		linea51.addParada(new Coordenada(14, 0));
		linea2.addParada(new Coordenada(4, 0));
		linea2.addParada(new Coordenada(4, 2));
		linea2.addParada(new Coordenada(4, 4));
		linea2.addParada(new Coordenada(4, 8));
		linea2.addParada(new Coordenada(4, 10));
		
		InformacionBusqueda busqueda1 = new InformacionBusqueda();
		InformacionBusqueda busqueda2 = new InformacionBusqueda();
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);
		LocalDate someDay = today.minusDays(10);
		busqueda1.setFecha(yesterday);
		busqueda1.setCantResultados(2);
		busqueda1.setTextoBusqueda("50");
		busqueda1.setTiempoBusqueda(1);
		busqueda1.setNombreUsuarioTerminal("TerminalJorge");
		busqueda1.getPdisDevueltos().add(linea5);
		busqueda1.getPdisDevueltos().add(linea51);
		
		busqueda2.setFecha(someDay);
		busqueda2.setCantResultados(2);
		busqueda2.setTextoBusqueda("2");
		busqueda2.setTiempoBusqueda(1);
		busqueda2.setNombreUsuarioTerminal("TerminalEnzo");
		busqueda2.getPdisDevueltos().add(linea2);
				
		// Agrego acciones a usuario
		GenerarReportes generarReportes = new GenerarReportes();
		try {
			usuarioJorge.agregarObserver(generarReportes);
		} catch (ObserverException e) {
		}
		;
		GuardarBusqueda guardarBusqueda = new GuardarBusqueda();
		guardarBusqueda.actualizar(busqueda1);
		
		try {
			usuarioJorge.agregarObserver(guardarBusqueda);
		} catch (ObserverException e) {
		};
		
		GuardarBusqueda guardarBusquedaEnzo = new GuardarBusqueda();
		guardarBusquedaEnzo.actualizar(busqueda2);
		
		try {
			usuarioEnzo.agregarObserver(guardarBusquedaEnzo);
		} catch (ObserverException e) {
		};

		MailAdmin mailAdmin = new MailAdmin(23);
		try {
			usuarioTest.agregarObserver(mailAdmin);
		} catch (ObserverException e) {
		}
		;

		UserDao userDao = new UserDao();
		withTransaction((EntityManager em) -> {
			em.persist(linea5);
			em.persist(linea51);
			em.persist(linea2);
			userDao.save(em, usuarioJorge);
			userDao.save(em, usuarioWalter);
			userDao.save(em, usuarioEnzo);
			userDao.save(em, usuarioTest);
			em.persist(generarReportes);
			em.persist(guardarBusqueda);
			em.persist(guardarBusquedaEnzo);
			em.persist(mailAdmin);
		});

	}
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++carga de Cgps+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	private static void inicializarCgps(PdiRepositorio pdiRepo) {
		// Cgps del mock
		agregarCGPsMock(pdiRepo);
		// Cgp que ya habian agregado
		Cgp cgp = new Cgp("Comuna 5", "Almagro, Boedo");
		// /* ***** ******** */
		// crea el horario
		Horario horarioAtencion1 = new Horario(2, 7, 30, 19, 00);
		Horario horarioAtencion2 = new Horario(3, 7, 30, 19, 00);
		// crea el rango
		RangoHorario rangoHorario = new RangoHorario();
		// agrega el horario a la coleccion de horarios
		rangoHorario.agregar(horarioAtencion1);
		rangoHorario.agregar(horarioAtencion2);
		// crea el servicio
		Servicio servicioComuna5 = new Servicio("Registro Civil", rangoHorario);
		// /* ***** ******** */
		// // crea horarios
		Horario horarioAtencion3 = new Horario(4, 8, 30, 14, 30);
		Horario horarioAtencion4 = new Horario(5, 8, 30, 14, 30);
		// crea el rango
		RangoHorario rangoHorario2 = new RangoHorario();
		// agrega horarios al rango
		rangoHorario2.agregar(horarioAtencion3);
		rangoHorario2.agregar(horarioAtencion4);
		// crea el servicio, es otro servicio
		Servicio servicioOtroComuna5 = new Servicio("Licencias", rangoHorario2);
		// /* ***** ******** */
		// // agrega un servicio
		cgp.addServicio(servicioComuna5);
		// agrega otro servicio
		cgp.addServicio(servicioOtroComuna5);
		// agrega calle principal al CGP
		cgp.setCallePrincipal("Carlos Calvo");
		cgp.setNumeroDireccion(3307);
		
		//
		withTransaction((EntityManager em) -> {
			pdiRepo.save(em, cgp);
		});

		// ************************************************
		// agrego para prueba del mock
		// *************************************************
		// Cgps nuevos
		agregarCgpsTest(pdiRepo);

	}

	private static void agregarCgpsTest(PdiRepositorio pdiRepo) {
		// fue solo para analizar donde estaba el error. Se puede reutilizar
		// para tener mas cgps
		// CGP comuna 7
		// +++++++++++++++++++++++++++
		Cgp cgp1 = new Cgp("Comuna 6", "Caballito");
		/* ***** ******** */
		// crea el horario
		Horario horarioAtencion1CGP1 = new Horario(2, 9, 30, 17, 30);
		Horario horarioAtencion2CGP1 = new Horario(3, 9, 30, 17, 30);
		// crea el rango
		RangoHorario rangoHorarioCGP1 = new RangoHorario();
		// agrega el horario a la coleccion de horarios
		rangoHorarioCGP1.agregar(horarioAtencion1CGP1);
		rangoHorarioCGP1.agregar(horarioAtencion2CGP1);
		// crea el servicio
		Servicio servicioComuna1 = new Servicio("Tarjeta Vos", rangoHorarioCGP1);
		/* ***** ******** */
		// crea horarios
		Horario horarioAtencion3CGP1 = new Horario(4, 9, 30, 17, 30);
		Horario horarioAtencion4CGP1 = new Horario(5, 9, 30, 17, 30);
		// crea el rango
		RangoHorario rangoHorario2CGP1 = new RangoHorario();
		// agrega horarios al rango
		rangoHorario2CGP1.agregar(horarioAtencion3CGP1);
		rangoHorario2CGP1.agregar(horarioAtencion4CGP1);
		// crea el servicio, es otro servicio
		Servicio servicioOtroComuna1 = new Servicio("Eco Bici", rangoHorario2CGP1);
		/* ***** ******** */
		// agrega un servicio
		cgp1.addServicio(servicioComuna1);
		// agrega otro servicio
		cgp1.addServicio(servicioOtroComuna1);
		// agrega calle principal al CGP
		cgp1.setCallePrincipal("Patricias Argentinas");
		cgp1.setNumeroDireccion(277);

		withTransaction((EntityManager em) -> {
			pdiRepo.save(em, cgp1);
		});

		// +++++++++++++++++++++++++++++++++++++++++++++++++

		// comuna 8

		// ++++++++++++++++++++++++++++++++++++++++++++++++

		Cgp cgp2 = new Cgp("Comuna 7", "Flores, Parque Chacabuco");
		/* ***** ******** */
		// crea el horario
		Horario horarioAtencion1CGP2 = new Horario(2, 8, 00, 19, 00);
		Horario horarioAtencion2CGP2 = new Horario(3, 8, 00, 19, 00);
		// crea el rango
		RangoHorario rangoHorarioCGP2 = new RangoHorario();
		// agrega el horario a la coleccion de horarios
		rangoHorarioCGP2.agregar(horarioAtencion1CGP2);
		rangoHorarioCGP2.agregar(horarioAtencion2CGP2);
		// crea el servicio
		Servicio servicioComuna2 = new Servicio("Infracciones", rangoHorarioCGP2);
		/* ***** ******** */
		// crea horarios
		Horario horarioAtencion3CGP2 = new Horario(4, 9, 00, 16, 00);
		Horario horarioAtencion4CGP2 = new Horario(5, 9, 00, 16, 00);
		// crea el rango
		RangoHorario rangoHorario2CGP2 = new RangoHorario();
		// agrega horarios al rango
		rangoHorario2CGP2.agregar(horarioAtencion3CGP2);
		rangoHorario2CGP2.agregar(horarioAtencion4CGP2);
		// crea el servicio, es otro servicio
		Servicio servicioOtroComuna2 = new Servicio("Oficina de empleo", rangoHorario2CGP2);
		/* ***** ******** */
		// agrega un servicio
		cgp2.addServicio(servicioComuna2);
		// agrega otro servicio
		cgp2.addServicio(servicioOtroComuna2);
		// agrega calle principal al CGP
		cgp2.setCallePrincipal("Av. Rivadavia");
		cgp2.setNumeroDireccion(7202);

		withTransaction((EntityManager em) -> {
			pdiRepo.save(em, cgp2);
		});

	}

	// *********************************************************
	// +++++++ del mock ++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// crea variables para todos y me devuelve los centroDto
	private static void agregarCGPsMock(PdiRepositorio pdiRepo) {
		// creo instancia del convertidor
		ConvertidorCGP convertidorCgp = new ConvertidorCGP();
		// creo instancia del mock
		Mockup_SE_Cgp se_Cgp = new Mockup_SE_Cgp();
		List<Cgp> pdisCgp = new LinkedList<Cgp>();

		pdisCgp = (convertidorCgp.convertirACgp(se_Cgp.obtenerCgps("dameCgps")));

		pdisCgp.stream().forEach(cgp -> withTransaction((EntityManager em) -> {

			pdiRepo.save(em, cgp);
		}));

	}
	
	private static void agregarBancosMock(PdiRepositorio pdiRepo) {
		
		ConvertidorBancoExterno convertidorBanco = new ConvertidorBancoExterno();

		Mockup_SE_Banco se_Banco = new Mockup_SE_Banco();
		List<BancoSucursal> listaDeBancos = new LinkedList<BancoSucursal>();

		listaDeBancos = convertidorBanco.convertirJSONaPdi(se_Banco.obtenerBancos());

		listaDeBancos.get(0).setCallePrincipal("Av.Rivadavia");   
		listaDeBancos.get(0).setNumeroDireccion(5034);
		listaDeBancos.get(1).setCallePrincipal("Av.Corrientes");   
		listaDeBancos.get(1).setNumeroDireccion(2156);
		listaDeBancos.get(2).setCallePrincipal("Av. Corrientes");   
		listaDeBancos.get(2).setNumeroDireccion(4136);
		listaDeBancos.get(3).setCallePrincipal("Bernardo de Irigoyen");   
		listaDeBancos.get(3).setNumeroDireccion(320);
		listaDeBancos.get(4).setCallePrincipal("Av. Santa Fe");   
		listaDeBancos.get(4).setNumeroDireccion(927);
		listaDeBancos.get(5).setCallePrincipal("Av.Triunvirato");   
		listaDeBancos.get(5).setNumeroDireccion(4648);
		
		
		listaDeBancos.stream().forEach(banco -> withTransaction((EntityManager em) -> {
         
			pdiRepo.save(em, banco);
		}));

	}

	// fin de la clase
}