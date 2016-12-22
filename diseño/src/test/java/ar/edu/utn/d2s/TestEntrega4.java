package ar.edu.utn.d2s;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.javatuples.Pair;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import ar.edu.utn.d2s.ObservadoresConsulta.GenerarReportes;
import ar.edu.utn.d2s.ObservadoresConsulta.GuardarBusqueda;
import ar.edu.utn.d2s.ObservadoresConsulta.MailAdmin;
import ar.edu.utn.d2s.ObservadoresConsulta.ObservadorConsulta;


public class TestEntrega4 {

	private Repositorio repositorio;
	private Administrador admin;
	List<PuntoInteres> pdis;
	private ProcesoBaja procesoBaja;
	private ProcesoActualizarLocCom procActualizacionLocCom;
	private ProcesoAcciones procAcciones;
	private ProcesoMultiple procMulti;
	private Colectivo linea50 = new Colectivo("50");
	private Colectivo linea51 = new Colectivo("51");
	private Colectivo linea92 = new Colectivo("92");

	private LocalComercial abastoShopping = new LocalComercial("Abasto Shopping", "Shopping");
	private LocalComercial avellanedaShopping = new LocalComercial("Avellaneda Shopping", "Shopping");
	private LocalComercial devotoShopping = new LocalComercial("Devoto Shopping", "Shopping");
	private UsuarioTerminal usuario1;
	private UsuarioTerminal usuario2;
	private UsuarioTerminal usuario3;
	private GenerarReportes generarReportes;
	private GuardarBusqueda guardarBusqueda;
	private int sizeUsr1Antes;
	private int sizeUsr2Antes;
	private List<Pair<UsuarioTerminal, List<ObservadorConsulta>>> usrActualizar = new ArrayList<Pair<UsuarioTerminal, List<ObservadorConsulta>>>();
   private Reintentar reintentar;
    private MailAdmin mailAdmin;
    private MyMailSender myMailSender;
    private LinkedList<Proceso> procesos; 
    
	@Before
	public void setUpBefore() throws Exception {
		repositorio = new Repositorio(new LinkedList<PuntoInteres>(), new CacheSerExterno());
		admin = new Administrador(repositorio);
		reintentar = new Reintentar(0);
		mailAdmin = new MailAdmin(60);
		myMailSender = new MyMailSender();
	
		
		BajaPDI linea50Baja = new BajaPDI();
		BajaPDI linea51Baja = new BajaPDI();
		linea50Baja.setFechaBaja(LocalDateTime.now());
		linea50Baja.setValorBusqueda("50");
		linea51Baja.setFechaBaja(LocalDateTime.now());
		linea51Baja.setValorBusqueda("51");
		List<BajaPDI> listaBajas = new LinkedList<BajaPDI>();
		listaBajas.add(linea50Baja);
		listaBajas.add(linea51Baja);
		
		admin.getManejadorErrores().add(reintentar);
		admin.getManejadorErrores().add(mailAdmin);
		
		procesoBaja = new ProcesoBaja(listaBajas, admin);
		procActualizacionLocCom = new ProcesoActualizarLocCom(admin);
		procActualizacionLocCom.setAdmin(admin);
		procAcciones = new ProcesoAcciones(admin, 0);
		procMulti = new ProcesoMultiple();
		procesos = new LinkedList<Proceso>();
				
		procesos.add(procesoBaja);
		procesos.add(procActualizacionLocCom);
		procesos.add(procAcciones);
		procMulti.setProcesos(procesos);
		
		usuario1 = new UsuarioTerminal("Terminal1", repositorio);
		usuario2 = new UsuarioTerminal("Terminal2", repositorio);
		usuario3 = new UsuarioTerminal("Terminal3", repositorio);

		generarReportes = new GenerarReportes();
		guardarBusqueda = new GuardarBusqueda();		
		
		// Colectivos
		linea50.setNumero(50);
		linea50.addParada(new Coordenada(0, 2));
		linea50.addParada(new Coordenada(0, 8));
		linea50.addParada(new Coordenada(0, 10));
		linea50.addParada(new Coordenada(0, 12));
		linea50.addParada(new Coordenada(0, 14));
		linea51.setNumero(51);
		linea51.addParada(new Coordenada(2, 0));
		linea51.addParada(new Coordenada(8, 0));
		linea51.addParada(new Coordenada(10, 0));
		linea51.addParada(new Coordenada(12, 0));
		linea51.addParada(new Coordenada(14, 0));
		linea92.setNumero(92);
		linea92.addParada(new Coordenada(4, 0));
		linea92.addParada(new Coordenada(4, 2));
		linea92.addParada(new Coordenada(4, 4));
		linea92.addParada(new Coordenada(4, 8));
		linea92.addParada(new Coordenada(4, 10));

		// Locales
		abastoShopping.addPalabraClave("Palabra1");
		abastoShopping.addPalabraClave("Palabra2");
		abastoShopping.setCoordenada(new Coordenada(44, 55));
		abastoShopping.setRadioCercaniaRubro(4);
		abastoShopping.setRubro("Shopping");
		abastoShopping.agregarHorario(5, new LocalTime(10, 0, 0), new LocalTime(19, 0, 0));
		avellanedaShopping.addPalabraClave("Palabra1");
		avellanedaShopping.addPalabraClave("Palabra2");
		avellanedaShopping.setCoordenada(new Coordenada(4, 88));
		avellanedaShopping.setRadioCercaniaRubro(3);
		avellanedaShopping.setRubro("Entretenimiento");
		avellanedaShopping.agregarHorario(6, new LocalTime(10, 0, 0), new LocalTime(19, 0, 0));
		devotoShopping.addPalabraClave("Palabra1");
		devotoShopping.addPalabraClave("Palabra2");
		devotoShopping.setCoordenada(new Coordenada(33, 8));
		devotoShopping.setRadioCercaniaRubro(10);
		devotoShopping.setRubro("Shopping");
		devotoShopping.agregarHorario(7, new LocalTime(10, 0, 0), new LocalTime(23, 0, 0));

		// Repositorio
		repositorio.agregarPdi(linea50);
		repositorio.agregarPdi(linea51);
		repositorio.agregarPdi(linea92);
		repositorio.agregarPdi(abastoShopping);
		repositorio.agregarPdi(avellanedaShopping);
		repositorio.agregarPdi(devotoShopping);
		
		//Acciiones por usuario		
	}

	@Test
	public void testProcesoBajaCaminoPositivo() {		
		procesoBaja.ejecutar();
		assertFalse(repositorio.getPdis().contains(linea50));
		assertFalse(repositorio.getPdis().contains(linea51));
	}
	
	@Test
	public void testProcesoBajaCaminoNegativo() {		
		BajaPDI bajaInexistente = new BajaPDI();
		bajaInexistente.setFechaBaja(LocalDateTime.now());
		bajaInexistente.setValorBusqueda("Un pdi inexistente");
		procesoBaja.getListaBajas().add(bajaInexistente);
		procesoBaja.ejecutar();
		assertTrue(repositorio.getPdis().contains(linea50));
		assertTrue(repositorio.getPdis().contains(linea51));
	}
	
	
	@Test
	public void testProcesoActualizarLocCom_Actualizacion()
	{
		LocalComercial local = (LocalComercial) repositorio.buscarPdiPorNombre("Abasto Shopping");
		List<String> palabrasClaveAntes = local.getPalabrasClave();
		//Se actualizan los locales
		this.procActualizacionLocCom.setNombreArchivo("src/main/java/ar/edu/utn/d2s/files/locales_comerciales.txt");
		this.procActualizacionLocCom.ejecutar();
		List<String> palabrasClaveDespues = local.getPalabrasClave();
		//Se evalua haya cambiado las palabras clave de un local existente
		assertFalse(palabrasClaveDespues.equals(palabrasClaveAntes));
	}
	
	@Test
	public void testProcesoActualizarLocCom_Alta()
	{
		int sizePdisAntes = admin.getM_Repositorio().getPdis().size();		
		//Se agregan 3 locales nuevos
		this.procActualizacionLocCom.setNombreArchivo("src/main/java/ar/edu/utn/d2s/files/locales_comerciales.txt");
		this.procActualizacionLocCom.ejecutar();
		int sizePdisDespues = admin.getM_Repositorio().getPdis().size();
		//Se evalua se haya incrementado el size de los pdis del repo
		assertTrue(sizePdisDespues>sizePdisAntes);
	}
	
	@Test
	public void testProcesoAccionesOk()
	{
		List<ObservadorConsulta> listaAcciones = new ArrayList<ObservadorConsulta>();
			
		sizeUsr1Antes = usuario1.getSuscriptores().size();
		sizeUsr2Antes = usuario2.getSuscriptores().size();
		usuario3.getSuscriptores().size();		
		
		listaAcciones.add(generarReportes);
		usrActualizar.add(new Pair<UsuarioTerminal, List<ObservadorConsulta>>(usuario1,listaAcciones));			//Lista de usuarios con las acciones de cada uno
		usrActualizar.add(new Pair<UsuarioTerminal, List<ObservadorConsulta>>(usuario2,listaAcciones));			//Lista de usuarios con las acciones de cada uno

		procAcciones.setUsrAct(usrActualizar);		
		procAcciones.ejecutar();

		int sizeUsr1Despues = usuario1.getSuscriptores().size();
		int sizeUsr2Despues = usuario2.getSuscriptores().size();

		assertTrue(sizeUsr1Antes < sizeUsr1Despues && sizeUsr2Antes < sizeUsr2Despues);
	} 	

	@Test
	public void testProcesoAccionesConError()
	{
		List<ObservadorConsulta> listaAcciones = new ArrayList<ObservadorConsulta>();
			
		sizeUsr1Antes = usuario1.getSuscriptores().size();
		sizeUsr2Antes = usuario2.getSuscriptores().size();
		usuario3.getSuscriptores().size();
		
		listaAcciones.add(generarReportes);
		usrActualizar.add(new Pair<UsuarioTerminal, List<ObservadorConsulta>>(usuario1,listaAcciones));			//Lista de usuarios con las acciones de cada uno
	
		usrActualizar.add(new Pair<UsuarioTerminal, List<ObservadorConsulta>>(usuario2,listaAcciones));			//Lista de usuarios con las acciones de cada uno

		procAcciones.setUsrAct(usrActualizar);
		procAcciones.setActivaExcepcion(1);
		procAcciones.ejecutar();		
		
		int sizeUsr1Despues = usuario1.getSuscriptores().size();
		int sizeUsr2Despues = usuario2.getSuscriptores().size();

		assertTrue( sizeUsr2Antes == sizeUsr2Despues);
	}
	
	@Test
	public void testCaminoPositivoMultiproceso()
	{   procAcciones.setActivaExcepcion(0);
		LocalComercial local = (LocalComercial) repositorio.buscarPdiPorNombre("Abasto Shopping");
		List<String> palabrasClaveAntes = local.getPalabrasClave();
		//Se actualizan los locales
		this.procActualizacionLocCom.setNombreArchivo("src/main/java/ar/edu/utn/d2s/files/locales_comerciales.txt");
		int sizePdisAntes = admin.getM_Repositorio().getPdis().size();		
		//Se agregan 3 locales nuevos
		this.procActualizacionLocCom.setNombreArchivo("src/main/java/ar/edu/utn/d2s/files/locales_comerciales.txt");
		this.procActualizacionLocCom.ejecutar();
		int sizePdisDespues = admin.getM_Repositorio().getPdis().size();		
		List<ObservadorConsulta> listaAcciones = new ArrayList<ObservadorConsulta>();	
		usuario1.eliminarTodosObserver();
		usuario2.eliminarTodosObserver();
		sizeUsr1Antes = usuario1.getSuscriptores().size();
		sizeUsr2Antes = usuario2.getSuscriptores().size();
		
		listaAcciones.add(generarReportes);
		listaAcciones.add(guardarBusqueda);
		listaAcciones.add(mailAdmin);
		usrActualizar.add(new Pair<UsuarioTerminal, List<ObservadorConsulta>>(usuario1,listaAcciones));			//Lista de usuarios con las acciones de cada uno
		usrActualizar.add(new Pair<UsuarioTerminal, List<ObservadorConsulta>>(usuario2,listaAcciones));			//Lista de usuarios con las acciones de cada uno
		procAcciones.setUsrAct(usrActualizar);		
		
		procMulti.ejecutar();
	
		List<String> palabrasClaveDespues = local.getPalabrasClave();
		int sizeUsr1Despues = usuario1.getSuscriptores().size();
		int sizeUsr2Despues = usuario2.getSuscriptores().size();

		assertTrue(sizeUsr1Antes < sizeUsr1Despues && sizeUsr2Antes < sizeUsr2Despues);		
		//Se evalua se haya incrementado el size de los pdis del repo		
		assertTrue(sizePdisDespues>sizePdisAntes);
		//Se evalua haya cambiado las palabras clave de un local existente
		assertFalse(palabrasClaveDespues.equals(palabrasClaveAntes));
		assertFalse(repositorio.getPdis().contains(linea50));
		assertFalse(repositorio.getPdis().contains(linea51));			
	}
	@Test
	public void testCaminoNegativoMultiproceso()
	{ reintentar.setReintentos(2);
	//se activa la generacion de un error para simular. Esto ocurrira solamente en el proceso acciones
	procAcciones.setActivaExcepcion(1);
	
	LocalComercial local = (LocalComercial) repositorio.buscarPdiPorNombre("Abasto Shopping");
	List<String> palabrasClaveAntes = local.getPalabrasClave();
	//Se actualizan los locales
	this.procActualizacionLocCom.setNombreArchivo("src/main/java/ar/edu/utn/d2s/files/locales_comerciales.txt");
	int sizePdisAntes = admin.getM_Repositorio().getPdis().size();		
	//Se agregan 3 locales nuevos
	this.procActualizacionLocCom.setNombreArchivo("src/main/java/ar/edu/utn/d2s/files/locales_comerciales.txt");
	this.procActualizacionLocCom.ejecutar();
	int sizePdisDespues = admin.getM_Repositorio().getPdis().size();		
	List<ObservadorConsulta> listaAcciones = new ArrayList<ObservadorConsulta>();	
	usuario1.eliminarTodosObserver();
	usuario2.eliminarTodosObserver();
	sizeUsr1Antes = usuario1.getSuscriptores().size();
	sizeUsr2Antes = usuario2.getSuscriptores().size();
	
	listaAcciones.add(generarReportes);
	listaAcciones.add(guardarBusqueda);
	listaAcciones.add(mailAdmin);
	usrActualizar.add(new Pair<UsuarioTerminal, List<ObservadorConsulta>>(usuario1,listaAcciones));			//Lista de usuarios con las acciones de cada uno
	usrActualizar.add(new Pair<UsuarioTerminal, List<ObservadorConsulta>>(usuario2,listaAcciones));			//Lista de usuarios con las acciones de cada uno
	procAcciones.setUsrAct(usrActualizar);		
	
	procMulti.ejecutar();	
	
	
	List<String> palabrasClaveDespues = local.getPalabrasClave();
	int sizeUsr1Despues = usuario1.getSuscriptores().size();
	int sizeUsr2Despues = usuario2.getSuscriptores().size();

	assertTrue(sizeUsr1Antes < sizeUsr1Despues && sizeUsr2Antes < sizeUsr2Despues);		
	//Se evalua se haya incrementado el size de los pdis del repo		
	assertTrue(sizePdisDespues>sizePdisAntes);
	//Se evalua haya cambiado las palabras clave de un local existente
	assertFalse(palabrasClaveDespues.equals(palabrasClaveAntes));
	assertFalse(repositorio.getPdis().contains(linea50));
	assertFalse(repositorio.getPdis().contains(linea51));
	// se verifica que se mando el mail
	assertTrue(mailAdmin.getMailEnviado());	
	
	}

}
