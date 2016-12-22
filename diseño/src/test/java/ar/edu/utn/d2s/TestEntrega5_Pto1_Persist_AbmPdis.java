 package ar.edu.utn.d2s;
 
 import static org.junit.Assert.*;
 
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.Transaction;
 import org.junit.After;
 import org.junit.Before;
 import org.junit.Test;
 
 
 
 public class TestEntrega5_Pto1_Persist_AbmPdis {
 
 private SessionFactory factory = SessionFactorySingleton.instance(); 
 	
 	private Session session;
 	
 	@Before
 	public void setUp(){
 		this.session = factory.openSession();
 	}
 	
 	@After
 	public void tearDown(){
 		this.session.close();
 	}
 	
 	public void withTransaction( Runnable command){
 		Transaction transaction = this.session.beginTransaction();
 		command.run();
 		transaction.commit(); 
 	}
 	
 	@Test
 	public void testColectivo() throws Exception {
 		
 		String nombre = "165"; 		
 		Colectivo bondi = new Colectivo(nombre);
 		
 		withTransaction( () -> {
 			this.session.persist(bondi);
 		} );
 		
		this.session.clear();
 		
 		Colectivo bondi165 = this.session.find(Colectivo.class, bondi.getId());
 		assertEquals(nombre, bondi165.getNombre());
 		
 	}

 	@Test
 	public void testColectivoPdi() throws Exception {
 		
 		String nombre = "500"; 		
 		PuntoInteres bondi = new Colectivo(nombre);
 		
 		withTransaction( () -> {
 			this.session.persist(bondi);
 		} );
 		
		this.session.clear();
 		
 		PuntoInteres bondi165 = this.session.find(PuntoInteres.class, bondi.getId());
 		assertEquals(nombre, bondi165.getNombre());
 		
 	}
 	
 	@Test
 	public void testLocalComercialRangoHorario_Persist() throws Exception {

 		LocalComercial floresShopping = new LocalComercial("Flores Shopping", "Shopping");
 		Horario horarioAtencion = new Horario(4, 9, 30, 15, 30);
 		RangoHorario rangoHorario = new RangoHorario();
 		
 		rangoHorario.agregar(horarioAtencion);
 		floresShopping.setRango(rangoHorario);
  		
 		withTransaction( () -> {
 			this.session.persist(rangoHorario);
 			this.session.persist(floresShopping);
 		} );
 		
 		this.session.clear();
 		
 		LocalComercial localTemp = this.session.find(LocalComercial.class, floresShopping.getId());
 		assertEquals(1, localTemp.getRango().getHorariosAtencion().size());
 		
 	}
 	
 	@Test
 	public void testLocalComercial_Modifica_AgregaHorario() throws Exception {

 		LocalComercial caballitoShopping = new LocalComercial("Caballito Shopping", "Shopping");
 		Horario horarioAtencion = new Horario(2, 10, 30, 15, 30);
 		RangoHorario rangoHorario = new RangoHorario();
 		
 		rangoHorario.agregar(horarioAtencion);
 		caballitoShopping.setRango(rangoHorario);
  		
 		withTransaction( () -> {
 			this.session.persist(rangoHorario);
 			this.session.persist(caballitoShopping);
 		} );
 		
 		this.session.clear();
 		
 		Horario horarioAtencion2 = new Horario(3, 11, 30, 15, 30);
 		caballitoShopping.getRango().agregar(horarioAtencion2);
 		
 		withTransaction( () -> {
 			this.session.update(rangoHorario);
			this.session.update(caballitoShopping);					
		} );
 		
 		LocalComercial localTemp2 = this.session.find(LocalComercial.class, caballitoShopping.getId());
 		assertEquals(2, localTemp2.getRango().getHorariosAtencion().size());
 		
 	}
 	
 	@Test
 	public void testLocalComercial_Elimina() throws Exception {
	this.session.clear();
 		LocalComercial abastoShopping = new LocalComercial("Abasto Shopping", "Shopping");
 		Horario horarioAtencion = new Horario(5, 9, 30, 15, 30);
 		RangoHorario rangoHorario1 = new RangoHorario();
 		
 		rangoHorario1.agregar(horarioAtencion);
 		abastoShopping.setRango(rangoHorario1);
  		
 		withTransaction( () -> {
			this.session.persist(rangoHorario1);
 			this.session.persist(abastoShopping);
 		} );
 		
 		this.session.clear();
 		
 		 		
 		withTransaction( () -> { 	
 			this.session.remove(rangoHorario1);
 			this.session.remove(abastoShopping);					
		} );
 		
 		LocalComercial localAbastoTemp2 = this.session.find(LocalComercial.class, abastoShopping.getId());
 		assertNull(localAbastoTemp2);
		
 		
 	}

 	@Test
 	public void testPersistEliminaColectivo() throws Exception {
 		
 		String nombre = "15";
 		Colectivo bondi_15 = new Colectivo(nombre);
 		
 		withTransaction( () -> {
 			this.session.persist(bondi_15);	
 		} );		
 		
 		Colectivo bondi15 = this.session.find(Colectivo.class, bondi_15.getId());
 		
 		assertEquals(nombre, bondi15.getNombre());
 		
 		withTransaction( () -> {
 			this.session.remove(bondi_15);	
 		} );
 		
 		Colectivo bondi15A = this.session.find(Colectivo.class, bondi_15.getId());
 		assertNull(bondi15A);
 		
 		this.session.clear();
 		
 	}
 	
 	@Test
	public void testPersistModificaColectivo() throws Exception {
 		
 		String nombre = "25";
 		Colectivo bondi_25 = new Colectivo(nombre);
 		
 		withTransaction( () -> {
 			this.session.persist(bondi_25);	
 		} );		

 		
 		Colectivo bondi15 = this.session.find(Colectivo.class, bondi_25.getId());
 		
 		assertEquals(nombre, bondi15.getNombre());
 		
 		bondi_25.setNombre("25A");
 		
 		withTransaction( () -> {
 			this.session.update(bondi_25);	
 		} );
 		
 		Colectivo bondi25A = this.session.find(Colectivo.class, bondi_25.getId());
 		assertEquals("25A", bondi25A.getNombre());
 		
 		this.session.clear();
 		
 	}
 	
 	@Test
 	public void testCgpServicioRangoHorario_Persist() throws Exception {

 		Cgp cgp = new Cgp("Comuna 1", "Retiro, San Nicolás, Puerto Madero, San Telmo, Montserrat, Constitución");
 		
 		/* ***** ******** */
 		
 		Horario horarioAtencion1 = new Horario(1, 10, 30, 16, 30);
 		Horario horarioAtencion2 = new Horario(2, 10, 30, 16, 30);
 		RangoHorario rangoHorario = new RangoHorario();
 		
 		rangoHorario.agregar(horarioAtencion1);
 		rangoHorario.agregar(horarioAtencion2);
 		
 		Servicio servicioComuna1 = new Servicio("Rentas", rangoHorario);
 		
 		/* ***** ******** */
 		
 		Horario horarioAtencion3 = new Horario(4, 12, 00, 17, 00);
 		Horario horarioAtencion4 = new Horario(5, 12, 00, 17, 00);
 		RangoHorario rangoHorario2 = new RangoHorario();
 		
 		rangoHorario2.agregar(horarioAtencion3);
 		rangoHorario2.agregar(horarioAtencion4);
 		
 		Servicio servicioComuna2 = new Servicio("Infracciones", rangoHorario2);
 		
 		/* ***** ******** */
 		
 		cgp.addServicio(servicioComuna1);
 		cgp.addServicio(servicioComuna2);
 		
  		
 		withTransaction( () -> {
 			this.session.persist(rangoHorario);
 			this.session.persist(rangoHorario2);
 			this.session.persist(servicioComuna1);
 			this.session.persist(servicioComuna2);
 			this.session.persist(cgp);
 		} );
 		
 		this.session.clear();
 		
 		Cgp localTemp = this.session.find(Cgp.class, cgp.getId());
 		assertEquals(2, localTemp.getServicios().size());
 		
 	}
 	
	@Test
 	public void testBancoSucursalServicioRangoHorario_Persist() throws Exception {

 		BancoSucursal banco = new BancoSucursal("Banco Ciudad Caballito", "Caballito Suc. 1");
 		
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
 		
  		
 		withTransaction( () -> {
 			this.session.persist(rangoHorario);
 			this.session.persist(rangoHorario2);
 			this.session.persist(servicio1);
 			this.session.persist(servicio2);
 			this.session.persist(banco);
 		} );
 		
 		this.session.clear();
 		
 		BancoSucursal localTemp = this.session.find(BancoSucursal.class, banco.getId());
 		assertEquals(2, localTemp.getServicio().size());
 		
 	}
 	
 }