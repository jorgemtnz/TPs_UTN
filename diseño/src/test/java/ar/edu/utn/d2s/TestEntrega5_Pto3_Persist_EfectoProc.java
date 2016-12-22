package ar.edu.utn.d2s;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.javatuples.Pair;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ar.edu.utn.d2s.ObservadoresConsulta.GenerarReportes;
import ar.edu.utn.d2s.ObservadoresConsulta.GuardarBusqueda;
import ar.edu.utn.d2s.ObservadoresConsulta.MailAdmin;
import ar.edu.utn.d2s.ObservadoresConsulta.ObservadorConsulta;

public class TestEntrega5_Pto3_Persist_EfectoProc {
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
    
private SessionFactory factory = SessionFactorySingleton.instance(); 
 	
 	private Session session;
 	
 	@Before
 	public void setUp(){
 		this.session = factory.openSession();
 	}
 	
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
		
		usuario1 = new UsuarioTerminal("Terminal1", null);
		usuario2 = new UsuarioTerminal("Terminal2", null);
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

	@Test //Proceso se ejecute correctamente
	public void testEfectoDeBajaCaminoPositivo() {
		
		repositorio.agregarPdi(linea50);
		repositorio.agregarPdi(linea51);
		repositorio.agregarPdi(linea92);
		
		withTransaction( () -> {	
			this.session.persist(repositorio);	
		} );
		Repositorio repoPersistido = this.session.find(Repositorio.class, repositorio.getId());
		assertTrue(repoPersistido.getPdis().contains(linea50));
		assertTrue(repoPersistido.getPdis().contains(linea51));
		
		procesoBaja.ejecutar();
		assertFalse(repositorio.getPdis().contains(linea50));
		assertFalse(repositorio.getPdis().contains(linea51));
		
		withTransaction( () -> {	
			this.session.update(repositorio);	
		} );
		Repositorio repoPersistido2 = this.session.find(Repositorio.class, repositorio.getId());
		assertFalse(repoPersistido2.getPdis().contains(linea50));
		assertFalse(repoPersistido2.getPdis().contains(linea51));
		


	}
	
// 
	@Test
	public void testEfectoDeBajaCaminoNegativo() {	
		repositorio.agregarPdi(linea50);
		repositorio.agregarPdi(linea51);
		repositorio.agregarPdi(linea92);
		BajaPDI bajaInexistente = new BajaPDI();
		bajaInexistente.setFechaBaja(LocalDateTime.now());
		bajaInexistente.setValorBusqueda("Un pdi inexistente");
		procesoBaja.getListaBajas().add(bajaInexistente);
		procesoBaja.ejecutar();
		
		withTransaction( () -> {	
			this.session.persist(repositorio);	
		} );
		
		Repositorio repoPersistido = this.session.find(Repositorio.class, repositorio.getId());
		
		assertTrue(repoPersistido.getPdis().contains(linea50));
		assertTrue(repoPersistido.getPdis().contains(linea51));
	}
	
 	@Test
 	public void testEfectoActualizarLocCom_Actualizacion() throws Exception {
 		
 		LocalComercial abastoShopping = new LocalComercial("Abasto Shopping", "Shopping");
 		Horario horarioAtencion = new Horario(1, 9, 30, 15, 30);
 		RangoHorario rangoHorario = new RangoHorario();
 		List<String> palabrasClaveAntes = abastoShopping.getPalabrasClave();
 		rangoHorario.agregar(horarioAtencion);
 		abastoShopping.setRango(rangoHorario);
 		repositorio.agregarPdi(abastoShopping);
 		
		this.procActualizacionLocCom.setNombreArchivo("src/main/java/ar/edu/utn/d2s/files/locales_comerciales.txt");
		this.procActualizacionLocCom.ejecutar();
  		
 		withTransaction( () -> {
 			this.session.persist(horarioAtencion);
 			this.session.persist(rangoHorario);
 			this.session.persist(abastoShopping);
 		} );
 		
 		this.session.clear();
 		
 		LocalComercial localAbastoTemp = this.session.find(LocalComercial.class, abastoShopping.getId());
 		
 		List<String> palabrasClaveDespues = localAbastoTemp.getPalabrasClave();
 		
		assertFalse(palabrasClaveDespues.equals(palabrasClaveAntes)); 		
 	}
 	


}
