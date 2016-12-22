package ar.edu.utn.d2s;

import static org.junit.Assert.*;

//import java.awt.List;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.LocalTime;

import ar.edu.utn.d2s.Mockup_SE_Banco.Mockup_SE_Banco;
import ar.edu.utn.d2s.Mockup_SE_Cgp.Mockup_SE_Cgp;
import org.junit.Before;
import org.junit.Test;

public class TestEntrega2 {
	private Colectivo linea50 = new Colectivo("50");
	private Colectivo linea51 = new Colectivo("51");
	private Colectivo linea92 = new Colectivo("92");

	private LocalComercial abastoShopping = new LocalComercial("Abasto Shopping", "Shopping");
	private LocalComercial avellanedaShopping = new LocalComercial("Avellaneda Shopping", "Shopping");
	private LocalComercial devotoShopping = new LocalComercial("Devoto Shopping", "Shopping");

	private Administrador admin = new Administrador();
	private Repositorio repositorio = new Repositorio();
	private ConvertidorCGP convertidorCGP = new ConvertidorCGP();
	private ConvertidorBancoExterno convertidorBCO = new ConvertidorBancoExterno();
	private Mockup_SE_Cgp se_Cgp = new Mockup_SE_Cgp();
	private Mockup_SE_Banco se_Bco = new Mockup_SE_Banco(); 
	private CacheSerExterno cache = new CacheSerExterno();
	private OrigenCgpExterno origenCgp = new OrigenCgpExterno();
	private OrigenBancoExterno origenBanco = new OrigenBancoExterno();

	@Before
	public void setUpBefore() throws Exception {
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
		abastoShopping.addPalabraClave("Ropa");
		abastoShopping.addPalabraClave("Cine");
		abastoShopping.setCoordenada(new Coordenada(44, 55));
		abastoShopping.setRadioCercaniaRubro(4);
		abastoShopping.setRubro("Shopping");
		abastoShopping.agregarHorario(5, new LocalTime(10, 0, 0), new LocalTime(19, 0, 0));
		avellanedaShopping.addPalabraClave("Comida");
		avellanedaShopping.addPalabraClave("Entretenimiento");
		avellanedaShopping.setCoordenada(new Coordenada(4, 88));
		avellanedaShopping.setRadioCercaniaRubro(3);
		avellanedaShopping.setRubro("Entretenimiento");
		avellanedaShopping.agregarHorario(6, new LocalTime(10, 0, 0), new LocalTime(19, 0, 0));
		devotoShopping.addPalabraClave("Bowling");
		devotoShopping.addPalabraClave("Libreria");
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
		repositorio.setCache(cache);
  
		
		// Administrador
		admin.setM_Repositorio(repositorio);
	}

	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Test Punto 1: ABMC (Alta, Baja, Modificación, Consulta) de puntos
	 * del sistema.
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */

	/*
	 * Alta pdi
	 * 
	 * Descripción: Agrego un pdi a un repositorio vacio Salida esperada: Debe
	 * agregar el pdi a la lista de pdi del repositorio
	 */
	@Test
	public void testAltaPdi() {
		repositorio = new Repositorio();
		Cgp comuna5 = this.crearCgp("Comuna 5", "5");
		repositorio.agregarPdi(comuna5);
		assertSame(comuna5, repositorio.buscarPdiPorId(comuna5.getIdPuntoInteres()));
	}

	/*
	 * Alta pdi existente
	 * 
	 * Descripción: Agrego un pdi 2 veces en el repo Salida esperada: Debe
	 * lanzar una excepcion, ya que ese pdi ya fue dado de alta
	 */
	@Test(expected = ar.edu.utn.d2s.exceptions.RepositorioException.class)
	public void testAltaPdiExistente() {
		Cgp comuna5 = this.crearCgp("Comuna 5", "5");
		repositorio.agregarPdi(comuna5);
		repositorio.agregarPdi(comuna5);
	}

	/*
	 * Baja pdi
	 * 
	 * Descripción: Agrego un pdi a un repositorio con datos, lo elimino, busco
	 * en el repo por el pdi eliminado. Salida esperada: No debe encontrar el
	 * pdi, debe lanzar una exception al no encontrarlo
	 */
	@Test(expected = ar.edu.utn.d2s.exceptions.RepositorioException.class)
	public void testBajaPdi() {
		Cgp comuna5 = this.crearCgp("Comuna 5", "5");
		repositorio.agregarPdi(comuna5);
		int idComuna5 = comuna5.getIdPuntoInteres();
		repositorio.eliminarPdi(idComuna5);
		repositorio.buscarPdiPorId(idComuna5);
	}

	/*
	 * Baja pdi inexistente
	 * 
	 * Descripción: Trato de eliminar un pdi que no exista en el reposiotrio
	 * Salida esperada: No debe encontrar el pdi (al momento de eliminarlo),
	 * debe lanzar una exception al no encontrarlo
	 */
	@Test(expected = ar.edu.utn.d2s.exceptions.RepositorioException.class)
	public void testBajaPdiInexistente() {
		Cgp comuna5 = this.crearCgp("Comuna 5", "5");
		int idComuna5 = comuna5.getIdPuntoInteres();
		repositorio.eliminarPdi(idComuna5);
	}

	/*
	 * Modificacion pdi
	 * 
	 * Descripción: Agrego un pdi A sin servicios a un repositorio con datos,
	 * creo un pdi B identico, le agrego un servicio, modificio el pdi A del
	 * repo por el pdi B, busco el pdi en el repo. Salida esperada: Debe
	 * devolver un pdi/cgp con una coleccion de servicios no vacia.
	 */
	@Test
	public void testModificarPdi() {
		Cgp comuna5 = this.crearCgp("Comuna 5", "5");
		repositorio.agregarPdi(comuna5);
		int idComuna5 = comuna5.getIdPuntoInteres();

		Cgp comuna5Bis = this.crearCgp("Comuna 5", "5");
		Servicio servicioCgp = this.crearServicioCGP("Rentas");
		comuna5Bis.addServicio(servicioCgp);

		repositorio.modificarPdi(comuna5Bis);

		assertFalse(((Cgp) repositorio.buscarPdiPorId(idComuna5)).getServicios().isEmpty());
	}

	/*
	 * Modificacion pdi inexistente
	 * 
	 * Descripción: Trato de modificar un pdi que no existe dentro del repo
	 * Salida esperada: Debe devolver una excepcion ya que no existe en el repo.
	 */
	@Test(expected = ar.edu.utn.d2s.exceptions.RepositorioException.class)
	public void testModificarPdiInexistente() {
		Cgp comuna5 = this.crearCgp("Comuna 5", "5");
		repositorio.modificarPdi(comuna5);
	}

	/*
	 * Consulta de puntos en el sistema por texto
	 * 
	 * Descripción: Se busca por un texto una lista de pdis dentro del repo
	 * Salida esperada: Una lista de pdis
	 */

	@Test
	public void testBusquedaPorTextoLinea50() {
		assertTrue(repositorio.buscarPdiPorTexto("50").contains(linea50));
	
	}

	@Test
	public void testBusquedaPorTextoLinea51() {
		assertTrue(repositorio.buscarPdiPorTexto("51").contains(linea51));

	}

	@Test
	public void testBusquedaPorTextoAbastoShopping() {
		assertTrue(repositorio.buscarPdiPorTexto("Abasto").contains(abastoShopping));

	}

	/*
	 * Consulta de puntos en el sistema por ID
	 * 
	 * Descripción: Se busca por un ID un PDI dentro del repo Salida esperada:
	 * Una PDI.
	 */

	@Test
	public void testBusquedaPorIdLinea50() {
		int idLinea50 = linea50.getIdPuntoInteres();
		assertSame(repositorio.buscarPdiPorId(idLinea50), linea50);

	}

	/*
	 * Consulta de puntos en el sistema por ID inexistente
	 * 
	 * Descripción: Se busca por un ID un PDI que no existe en el repositorio
	 * Salida esperada: Una excepcion al no encontrarlo.
	 */

	@Test(expected = ar.edu.utn.d2s.exceptions.RepositorioException.class)
	public void testBusquedaPorIdPdiInexistente() {
		Cgp comuna5 = this.crearCgp("Comuna 5", "5");
		int idComuna5 = comuna5.getIdPuntoInteres();
		repositorio.buscarPdiPorId(idComuna5);

	}

	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Test Punto 2: Implementar la búsqueda de CGP.
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */
	
	/*
	 * Consulta de cgps en el los servicios externos
	 * 
	 * Descripción: Se busca por un texto una lista de pdis en los servicios externos de CGPS
	 * Salida esperada: Una lista de pdis
	 */
	
	@Test 
	public void testBusquedaPorTextoCgpDeRentas() {
		List <PuntoInteres> ResultadoBusquedaCgp = new LinkedList<PuntoInteres>();
        repositorio.agregarOrigDatos(origenCgp); //Al repo le mando solo un origenDeCgps
        ResultadoBusquedaCgp = repositorio.busquedaPdiExtendida("Rentas"); //Busca solo en el origenDeCgp
        assertTrue(ResultadoBusquedaCgp.stream().anyMatch(cgp -> cgp.getNumero() == 1)); //Tiene a la comuna 1 el cual posee un servicio de Rentas
        
	}
	
	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Test Punto 3: Implementar la búsqueda de Bancos.
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */
	
	/*
	 * Consulta de bancos en el los servicios externos
	 * 
	 * Descripción: Se busca por un texto una lista de pdis en los servicios externos de Bancos
	 * Salida esperada: Una lista de pdis
	 */
	
	@Test 
	public void testBusquedaPorTextoBanco() {
		List <PuntoInteres> ResultadoBusquedaBancos = new LinkedList<PuntoInteres>();
        repositorio.agregarOrigDatos(origenBanco); //Al repo le mando solo un origenDeBco
        ResultadoBusquedaBancos = repositorio.busquedaPdiExtendida("Caballito"); //Busca solo en el origenDeBco
         assertTrue(ResultadoBusquedaBancos.stream().anyMatch(banco -> banco.getNombre().contains("Banco Ciudad"))); //Contiene al Banco Ciudad que esta en caballito
	} 

	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Test Punto 4: Consulta (Búsqueda) de puntos extendida: se
	 * debe lograr que cuando se realice una consulta, se busquen puntos en
	 * todos los orígenes de datos. Se debe diseñar teniendo en cuenta que
	 * nuevos orígenes de datos pueden incorporarse en el futuro.
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */
	
	/*
	 * Consulta a ambos servicios externos
	 * 
	 * Descripción: Se busca por un texto una lista de pdis en los servicios externos de Bancos
	 * Salida esperada: Una lista de pdis
	 */
	
	@Test 
	public void testBusquedaExtendida() {
		List <PuntoInteres> ResultadoBusquedaExtendida = new LinkedList<PuntoInteres>();
        repositorio.agregarOrigDatos(origenBanco); //Al repo le mando el origen de Bco y Cgp
        repositorio.agregarOrigDatos(origenCgp); 
        ResultadoBusquedaExtendida = repositorio.busquedaPdiExtendida("Retiro"); //Busca en ambos origenes
        assertTrue(ResultadoBusquedaExtendida.stream().anyMatch(pdi -> pdi.getNumero() == 1)); //Tiene a la comuna 1 el cual esta en Retiro
	}
	

	/*
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * ***** Métodos auxiliares testing
	 * *************************************************************************
	 * *****
	 * *************************************************************************
	 * *****
	 */
	private Cgp crearCgp(String nombreComuna, String nroComuna) {
		Cgp comuna = new Cgp(nombreComuna, nroComuna);
		AreaComuna areaComuna = new AreaComuna();
		areaComuna.add(new Coordenada(0, 0));
		areaComuna.add(new Coordenada(5, 0));
		areaComuna.add(new Coordenada(0, 5));
		areaComuna.add(new Coordenada(5, 5));
		comuna.setAreaComuna(areaComuna);
		int hash = comuna.hashCode();
		comuna.setIdPuntoInteres(hash);
		return comuna;
	}

	private Servicio crearServicioCGP(String nombreServicio) {
		Servicio servicioComuna1 = new Servicio();
		servicioComuna1.setNombre(nombreServicio);
		RangoHorario rangoDefault = new RangoHorario();
		rangoDefault.agregar(1, new LocalTime(9, 30, 0), new LocalTime(15, 30, 0));
		servicioComuna1.setRango(rangoDefault);
		return servicioComuna1;
	}

	@Test
	public void testConvertidorSiConvierte() {
		convertidorCGP.agregaCentroDTOaCgps(se_Cgp.obtenerCgps("funcionaaaaa"));
		assertTrue(convertidorCGP.getCgpsTemp().stream().anyMatch(unCgp -> unCgp.getNumero() == 1));

	}

	@Test
	public void testConvertidorJsonABancos() {		
		List<BancoSucursal> bancosSE = convertidorBCO.convertirJSONaPdi(se_Bco.obtenerBancos("test1", "test2"));
		assertTrue(bancosSE.size() == 6);
	}
}
