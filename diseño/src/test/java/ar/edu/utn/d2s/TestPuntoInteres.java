package ar.edu.utn.d2s;

import static org.junit.Assert.*;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Before; //se ejecuta antes de cada test
//import org.junit.BeforeClass; //se ejecuta una sola vez al principio
import org.junit.Test;

public class TestPuntoInteres {

	private  Cgp comuna1 = new Cgp("Comuna 1", "1");
	private  Cgp comuna2 = new Cgp("Comuna 2", "2");
	private  Cgp comuna3 = new Cgp("Comuna 3", "3");
	
	private  AreaComuna polyComuna1 = new AreaComuna();
	private  AreaComuna polyComuna2 = new AreaComuna();
	private  AreaComuna polyComuna3 = new AreaComuna();
	
	
	private RangoHorario rangoDefault = new RangoHorario();
	private  Servicio servicioComuna1 = new Servicio();
	private  Servicio servicioComuna2 = new Servicio();
	private  Servicio servicioComuna3 = new Servicio();
	
	private  Servicio servicioBancoSucursal1 = new Servicio();
	private  Servicio servicioBancoSucursal2 = new Servicio();
	private  Servicio servicioBancoSucursal3 = new Servicio();
	
	private  Colectivo linea50 = new Colectivo("50");
	private  Colectivo linea51 = new Colectivo("51");
	private  Colectivo linea92 = new Colectivo("92");
	private  LocalComercial abastoShopping = new LocalComercial("Abasto Shopping", "Shopping");
	private  LocalComercial avellanedaShopping = new LocalComercial("Avellaneda Shopping", "Shopping");
	private  LocalComercial devotoShopping = new LocalComercial("Devoto Shopping", "Shopping");
	
	private  BancoSucursal bcoCiudad = new BancoSucursal("Banco Ciudad", "Caballito");
	private  BancoSucursal bcoNacion = new BancoSucursal("Banco Nacion", "Balvanera");
	private  BancoSucursal bcoSantander = new BancoSucursal("Banco Santander", "Pza Almagro");

	private static LocalDateTime horaTest;

	@Before
	public void setUpBefore() throws Exception {

		polyComuna1.add(new Coordenada(0, 0));
		polyComuna1.add(new Coordenada(4, 0));
		polyComuna1.add(new Coordenada(0, 4));
		polyComuna1.add(new Coordenada(4, 4));
		polyComuna2.add(new Coordenada(4, 4));
		polyComuna2.add(new Coordenada(4, 6));
		polyComuna2.add(new Coordenada(6, 6));
		polyComuna2.add(new Coordenada(6, 4));
		polyComuna3.add(new Coordenada(0, 6));
		polyComuna3.add(new Coordenada(0, 12));
		polyComuna3.add(new Coordenada(2, 12));
		polyComuna3.add(new Coordenada(2, 0));
		
		rangoDefault.agregar(1,  new LocalTime(9, 30, 0), new LocalTime(15, 30, 0));
		
		servicioComuna1.setRango(rangoDefault);
		servicioComuna2.setRango(rangoDefault);
		servicioComuna3.setRango(rangoDefault);
		
		servicioComuna1.setNombre("Rentas");
		servicioComuna1.agregarHorario(2, new LocalTime(9, 30, 0), new LocalTime(15, 30, 0));
		servicioComuna2.setNombre("Infracciones");
		servicioComuna2.agregarHorario(3, new LocalTime(8, 0, 0), new LocalTime(13, 45, 0));
		servicioComuna3.setNombre("Defensa al Consumidor");
		servicioComuna3.agregarHorario(4, new LocalTime(9, 0, 0), new LocalTime(18, 0, 0));
		
		
		servicioBancoSucursal1.setRango(rangoDefault);
		servicioBancoSucursal2.setRango(rangoDefault);
		servicioBancoSucursal3.setRango(rangoDefault);
		
		servicioBancoSucursal1.setNombre("Rentas");
//		servicioBancoSucursal1.agregarHorario(2, new LocalTime(9, 30, 0), new LocalTime(15, 30, 0));
		servicioBancoSucursal2.setNombre("Infracciones");
//		servicioBancoSucursal2.agregarHorario(3, new LocalTime(8, 0, 0), new LocalTime(13, 45, 0));
		servicioBancoSucursal3.setNombre("Defensa al Consumidor");
//		servicioBancoSucursal3.agregarHorario(4, new LocalTime(9, 0, 0), new LocalTime(18, 0, 0));
		//Bancos
//		bcoCiudad;
		
		// CGPs
		comuna1.setComuna("1");
		comuna1.setBarrio("San Nicolas");
		comuna1.setCoordenada(new Coordenada(2,3));
		comuna1.setAreaComuna(polyComuna1);
		comuna1.addServicio(servicioComuna1);
		comuna2.setComuna("2");
		comuna2.setBarrio("Recoleta");
		comuna1.setCoordenada(new Coordenada(5,4));
		comuna2.setAreaComuna(polyComuna2);
		comuna2.addServicio(servicioComuna2);
		comuna3.setComuna("3");
		comuna3.setBarrio("Balvanera");
		comuna1.setCoordenada(new Coordenada(1,8));
		comuna3.setAreaComuna(polyComuna3);
		comuna3.addServicio(servicioComuna3);
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
		devotoShopping.agregarHorario(7, new LocalTime(10, 0, 0), new LocalTime(23, 0, 0));
	}

	@Test
	public void testNombreMenor60() {
		PuntoInteres Obj = new LocalComercial("unNombre", "unRubro");
		
		assertTrue(Obj.getNombre().length() <= 60);
	}

	@Test
	public void testNombreNoVacio() {
		PuntoInteres Obj = new BancoSucursal("","");
		Obj.setNombre("unNombre");
		assertFalse(Obj.getNombre().isEmpty());
	}


	// Calculo Cercania
	@Test
	public void testCalculoCercaniaComuna1() {
		assertTrue(comuna1.calculoCercania(2, 3));
	}

	@Test
	public void testCalculoCercaniaComuna2() {
		assertFalse(comuna2.calculoCercania(15, 15));
	}
	
	@Test
	public void testCalculoCercaniaComuna3() {
		assertTrue(comuna3.calculoCercania(1, 8));
	}

	@Test
	public void testCalculoCercaniaLinea50() {
		assertTrue(linea50.calculoCercania(0, 2.0001));
	}

	@Test
	public void testCalculoCercaniaLinea51() {
		assertFalse(linea51.calculoCercania(28.0003, 0));
	}

	@Test
	public void testCalculoCercaniaLinea92() {
		assertTrue(linea92.calculoCercania(4, 3.9993));
	}

	@Test
	public void testCalculoCercaniaAbastoShopping() {
		assertTrue(abastoShopping.calculoCercania(44, 55.04));
	}

	@Test
	public void testCalculoCercaniaAvellanedaShopping() {
		assertTrue(avellanedaShopping.calculoCercania(3.98, 88));
	}

	@Test
	public void testCalculoCercaniaDevotoShopping() {
		assertFalse(devotoShopping.calculoCercania(133.08, 8.03));
	}

	// Calculo Disponibilidad

	@Test
	public void testCalculoDisponibilidadLinea50() {
		horaTest = new LocalDateTime(2016, 4, 19, 1, 30);
		assertTrue(linea50.calculoDisponibilidad(new LocalDateTime(), ""));
	}

	@Test
	public void testCalculoDisponibilidadLinea51() {
		horaTest = new LocalDateTime(2016, 4, 12, 23, 30);
		assertTrue(linea51.calculoDisponibilidad(new LocalDateTime(), ""));
	}

	@Test
	public void testCalculoDisponibilidadLinea92() {
		horaTest = new LocalDateTime(2016, 4, 3, 12, 0);
		assertTrue(linea92.calculoDisponibilidad(new LocalDateTime(), ""));
	}

	@Test
	public void testCalculoDisponibilidadComuna1() {
		horaTest = new LocalDateTime(2016, 4, 19, 10, 30);
		assertTrue(comuna1.calculoDisponibilidad(horaTest, "Rentas"));
	}

	@Test
	public void testCalculoDisponibilidadComuna2() {
		horaTest = new LocalDateTime(2016, 4, 20, 10, 30);
		assertTrue(comuna2.calculoDisponibilidad(horaTest, "Infracciones"));
	}

	@Test
	public void testCalculoDisponibilidadComuna3() {
		horaTest = new LocalDateTime(2016, 4, 21, 10, 30);
		assertTrue(comuna3.calculoDisponibilidad(horaTest));
	}

	@Test
	public void testCalculoDisponibilidadBancoCiudad() {
		horaTest = new LocalDateTime(2016, 4, 19, 10, 30);
		assertTrue(comuna1.calculoDisponibilidad(horaTest));
	}
	
	@Test
	public void testNotNullServicioBancoCiudad() {
		assertNotNull(comuna1.getServicios().stream().findAny());
	}

	@Test
	public void testCalculoDisponibilidadBancoNacion() {
		horaTest = new LocalDateTime(2016, 4, 20, 13, 30);
		assertTrue(comuna2.calculoDisponibilidad(horaTest));
	}

	@Test
	public void testCalculoDisponibilidadBancoSantander() {
		horaTest = new LocalDateTime(2016, 4, 21, 15, 0);
		assertTrue(comuna3.calculoDisponibilidad(horaTest));
	}

	@Test
	public void testCalculoDisponibilidadAbastoShopping() {
		horaTest = new LocalDateTime(2016, 4, 22, 13, 0);
		assertTrue(abastoShopping.calculoDisponibilidad(horaTest, ""));
	}

	@Test
	public void testCalculoDisponibilidadAvellanedaShopping() {
		horaTest = new LocalDateTime(2016, 4, 24, 13, 0);
		assertFalse(avellanedaShopping.calculoDisponibilidad(horaTest, ""));
	}

	@Test
	public void testCalculoDisponibilidadDevotoShopping() {
		horaTest = new LocalDateTime(2016, 4, 24, 13, 0);
		assertTrue(devotoShopping.calculoDisponibilidad(horaTest, ""));
	}

	// Busqueda de Puntos

	@Test
	public void testBusquedaComuna1() {
		assertTrue(comuna1.contieneTexto("1"));
	}

	@Test
	public void testBusquedaComuna2() {
		assertTrue(comuna2.contieneTexto("Infracciones"));
	}

	@Test
	public void testBusquedaComuna3() {
		assertFalse(comuna3.contieneTexto("Circo"));
	}

	@Test
	public void testBusquedaLinea50() {
		assertTrue(linea50.contieneTexto("50"));
	}

	@Test
	public void testBusquedaLinea51() {
		assertTrue(linea51.contieneTexto("51"));
	}

	@Test
	public void testBusquedaLinea92() {
		assertTrue(linea92.contieneTexto("92"));
	}

	@Test
	public void testBusquedaAbastoShopping() {
		assertTrue(abastoShopping.contieneTexto("Ropa"));
	}

	@Test
	public void testBusquedaAvellanedaShopping() {
		assertFalse(avellanedaShopping.contieneTexto("Teatro"));
	}

	@Test
	public void testBusquedaDevotoShopping() {
		assertTrue(devotoShopping.contieneTexto("Bowling"));
	}

	@Test
	public void testBusquedaBancoCiudad() {
		assertTrue(bcoCiudad.contieneTexto("Banco"));
	}

	@Test
	public void testBusquedaBancoNacion() {
		assertTrue(bcoNacion.contieneTexto("Banco"));
	}
	
	@Test
	public void testNotNullBancoNacion() {
		assertNotNull(bcoNacion.getNombre());
	}

	@Test
	public void testBusquedaBancoSantander() {
		assertFalse(bcoSantander.contieneTexto("Circo"));
	}
	


}