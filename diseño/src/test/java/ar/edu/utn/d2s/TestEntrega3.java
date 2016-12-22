package ar.edu.utn.d2s;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.javatuples.Pair;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import ar.edu.utn.d2s.ObservadoresConsulta.GenerarReportes;
import ar.edu.utn.d2s.ObservadoresConsulta.GuardarBusqueda;
import ar.edu.utn.d2s.ObservadoresConsulta.LoggerTerminal;
import ar.edu.utn.d2s.ObservadoresConsulta.MailAdmin;
import ar.edu.utn.d2s.exceptions.ObserverException;

public class TestEntrega3 {
	
	private MyMailSender mailSender;
	private UsuarioTerminal usuarioTerminal;
	List<PuntoInteres> pdisTemp;
	private OrigenCgpExterno origenCgp;
	private OrigenBancoExterno origenBanco;
	private Repositorio repositorio;
	private MailAdmin mailAdmin;	

	@Mock()
	InformacionBusqueda busqueda;
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Before
	public void setUpBefore() throws Exception {
		mailSender = new MyMailSender();
		repositorio = new Repositorio(
				new LinkedList<PuntoInteres>(),
				new CacheSerExterno());
		origenCgp = new OrigenCgpExterno();
		origenBanco = new OrigenBancoExterno();
		repositorio.agregarOrigDatos(origenBanco);
        repositorio.agregarOrigDatos(origenCgp); 
		usuarioTerminal = new UsuarioTerminal(
								"UsuarioTerminalPrueba", repositorio);
		mailAdmin = new MailAdmin(20);
		busqueda= new InformacionBusqueda();
		
	}
	
	/*
	 * Mail Admin 
	 * se va a realizar un busqueda y evaluar el tiempo que se ha demorado y se mandara un mail o no
	 * 
	 */
	
	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Test Mail Admin
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */
	@Test    // prueba unitaria
	public void testAdminMailSeMandaMail(){
//		tiempo para mandar mail es 20
		busqueda.setTiempoBusqueda(40*1000);
		mailAdmin.actualizar(busqueda);
		assertTrue(mailAdmin.getMailEnviado());		
	}
	
	@Test   // prueba unitaria
	public void testAdminMailNoSeMandaMail(){
//		tiempo para mandar mail es 20
		busqueda.setTiempoBusqueda(10*1000);
		mailAdmin.actualizar(busqueda);
		assertFalse(mailAdmin.getMailEnviado());		
	}
	
	@Test     // prueba integral
	public void testAdminMailNoMandaMail(){
//		tiempo para mandar mail es 20
		try {
			this.usuarioTerminal.agregarObserver(mailAdmin);
		} catch (ObserverException e) {
		
			e.printStackTrace();
		}
		//tiempo estimado de 5 a 10 		
		this.usuarioTerminal.buscarTexto("Balvanera");
		assertFalse(mailAdmin.getMailEnviado());
	}
	
	@Test      // prueba integral
	public void testAdminMailSiMandaMail(){
//		tiempo para mandar mail es 1
		mailAdmin.setMailAdminSegundosDemora(0);
		try {
			this.usuarioTerminal.agregarObserver(mailAdmin);
		} catch (ObserverException e) {
		
			e.printStackTrace();
		}
		//tiempo estimado de 5 a 10 
		this.usuarioTerminal.buscarTexto("Balvanera");
		assertTrue(mailAdmin.getMailEnviado());		
	}
	
	
	/*
	 * Log: Generar un log que muestre: el nombre buscado, 
	 * la cantidad de resultados devuelto, tiempo que tardó
	 * en ejecutar dicha consulta. 
	 * Se debe utilizar algún framework de loggin como ​ Log4J​  
	 * para Java o Log4net​  para .Net.
	 * 
	 */
	
	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Test Habilitar Log
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */

	@Test
	public void testLog() throws IOException {
		//Agrego accion de logger. Generara log4j. 
		LoggerTerminal Logger = new LoggerTerminal();
		try {
			this.usuarioTerminal.agregarObserver(Logger);
		} catch (ObserverException e) {
		
			e.printStackTrace();
		}
		
		File archivoLog = new File("log/Busquedas.log");
		archivoLog.createNewFile();
		
		long longitud = archivoLog.length();
		
		//Ejecuto la busqueda, debe generar datos el archivo Busquedas.log y aumentar la longitud del archivo
		this.usuarioTerminal.buscarTexto("Balvanera");
		
		assertTrue(archivoLog.length() > 0);
	}
	
	/*
	 * GuardarBusqueda: El usuario terminal puede configurar si se almacenan 
	 * los resultados de las búsquedas. Se quiere conocer qué es lo que se buscó 
	 * (frase buscada), la cantidad de resultados devueltos por el Sistema y 
	 * el tiempo que demoró dicha consulta. 
	 * 
	 */
	
	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Test GuardarBusqueda
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */
	
	@Test
	public void testGuardarBusqueda() {
		//Agrego accion de guardarBusqueda. 
		GuardarBusqueda observerBusqueda = new GuardarBusqueda();
		try {
			this.usuarioTerminal.agregarObserver(observerBusqueda);
		} catch (ObserverException e) {
			
			e.printStackTrace();
		}
		
		//Ejecuto la busqueda y debe guardar los datos de busqueda en el observador
		this.usuarioTerminal.buscarTexto("Banco Ciudad");
		
		//Evaluo que se guarda el resultado de busqueda
		assertTrue(observerBusqueda.getListaInfo().get(0).getTextoBusqueda().equals("Banco Ciudad"));
	}
	
	/*
	 * Reporte 1: Obtener un reporte que permita totalizar las
	 * cantidad de búsquedas por Fecha por terminal.
	 */
	
	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Test Reporte cantidad de busquedas por fecha
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */
	
	@Test
	public void testReporteBusquedasFecha() {
		//Agrego accion de guardarBusqueda. 
		GenerarReportes observerReportes = new GenerarReportes();
		try {
			this.usuarioTerminal.agregarObserver(observerReportes);
		} catch (ObserverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Ejecuto la busqueda y debe guardar los datos de busqueda en el observador
		this.usuarioTerminal.buscarTexto("Rentas");
		this.usuarioTerminal.buscarTexto("Banco Santander");
		this.usuarioTerminal.buscarTexto("Almagro");		
		
		List<Pair<LocalDate, Integer>> listaReportesEsperados = new LinkedList<Pair<LocalDate, Integer>>();
		
		LocalDate hoy = LocalDate.now();
		LocalDate maniana = hoy.plusDays(1);
		
		Pair<LocalDate,Integer> reporteEsperado = new Pair<LocalDate,Integer>(hoy, 3);

		listaReportesEsperados.add(reporteEsperado);
					
		//Evaluo que el resultado que devuelve sea el correcto
		List<Pair<LocalDate, Integer>> listaReportesDevueltos= observerReportes.obtenerCantidadDeBusquedasPorFecha(hoy, maniana);
		assertTrue(listaReportesEsperados.equals(listaReportesDevueltos));
	}
	
	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Test Deshabilitar Log
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */
//TODO: testLogDeshabilitar
	@Test
	public void testLogDeshabilitar() throws IOException {
		//Agrego accion de logger. Generara log4j. 
		LoggerTerminal Logger = new LoggerTerminal();
		try {
			this.usuarioTerminal.agregarObserver(Logger);
		} catch (ObserverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Lo borro
		try {
			this.usuarioTerminal.eliminarObserver(Logger);
		} catch (ObserverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File archivoLog = new File("log/Busquedas.log");
		archivoLog.createNewFile();
		
		long longitud = archivoLog.length();
		
		//Ejecuto la busqueda, la longitud del archivo log no debe aumentar
		this.usuarioTerminal.buscarTexto("Balvanera");
		
		assertTrue(archivoLog.length() == longitud);
	}
	
	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Test GuardarBusqueda Deshabilitar
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */
	
	@Test
	public void testGuardarBusquedaDeshabilitar() {
		//Agrego accion de guardarBusqueda. 
		GuardarBusqueda observerBusqueda = new GuardarBusqueda();
		try {
			this.usuarioTerminal.agregarObserver(observerBusqueda);
		} catch (ObserverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.usuarioTerminal.eliminarObserver(observerBusqueda);
		} catch (ObserverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Ejecuto la busqueda y debe guardar los datos de busqueda en el observador
		this.usuarioTerminal.buscarTexto("Banco Ciudad");
		
		//Evaluo que se guarda el resultado de busqueda
		assertTrue(observerBusqueda.getListaInfo().size()==0);
	}
	
	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Test Reporte cantidad de busquedas por fecha Deshabilitar
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */
	
	@Test
	public void testReporteBusquedasFechaDeshabilitar() {
		//Agrego accion de guardarBusqueda. 
		GenerarReportes observerReportes = new GenerarReportes();
		try {
			this.usuarioTerminal.agregarObserver(observerReportes);
		} catch (ObserverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Lo borro
		try {
			this.usuarioTerminal.eliminarObserver(observerReportes);
		} catch (ObserverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Ejecuto la busqueda y NO debe guardar los datos de busqueda en el observador
		this.usuarioTerminal.buscarTexto("Rentas");
		this.usuarioTerminal.buscarTexto("Banco Santander");
		this.usuarioTerminal.buscarTexto("Almagro");		
		
		LocalDate hoy = LocalDate.now();
		LocalDate maniana = hoy.plusDays(1);
		
		List<Pair<LocalDate, Integer>> listaReportesEsperados = new LinkedList<Pair<LocalDate, Integer>>();
		Pair<LocalDate,Integer> reporteEsperado = new Pair<LocalDate,Integer>(hoy, 0); //Se espera 0 ya que no guardo ninguna busqueda
		listaReportesEsperados.add(reporteEsperado);
		
		//Evaluo que el resultado sea el esperado
		List<Pair<LocalDate, Integer>> listaReportesDevueltos= observerReportes.obtenerCantidadDeBusquedasPorFecha(hoy, maniana);
	
		assertTrue(listaReportesEsperados.equals(listaReportesDevueltos));
	}
}
