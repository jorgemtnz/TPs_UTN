package ar.edu.utn.d2s;

import static org.junit.Assert.*;

import java.util.LinkedList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.d2s.ObservadoresConsulta.GenerarReportes;
import ar.edu.utn.d2s.ObservadoresConsulta.GuardarBusqueda;



public class TestEntrega5_Pto1_Persist_AbmUsuAcciones {

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
	public void testPersisteUsuarioTerminal() throws Exception {
		
		String nombre = "usuarioJorge";
		UsuarioTerminal userJorge = new UsuarioTerminal(nombre,null);						
		withTransaction( () -> {
			this.session.persist(userJorge);			
		} );		
		UsuarioTerminal user = this.session.find(UsuarioTerminal.class, userJorge.getIdUsuario());
		assertEquals(nombre, user.getNombre());
		
	}
	
	@Test
	public void testPersisteGenerarReportes() throws Exception {
		String nombre = "usuarioCecilia";
		UsuarioTerminal userJorge = new UsuarioTerminal(nombre,null);
		GenerarReportes generarReportes = new GenerarReportes();
		userJorge.agregarObserver(generarReportes);				
		withTransaction( () -> {
			this.session.persist(userJorge);	
			this.session.persist(generarReportes);	
		} );		
		UsuarioTerminal user = this.session.find(UsuarioTerminal.class, userJorge.getIdUsuario());
		assertEquals(1, user.getSuscriptores().size());
		
	}
	
	@Test
	public void testupdateUsuarioEliminarPersistenciaGenerarReportes() throws Exception {
		
		String nombre = "usuarioDaniel";
		UsuarioTerminal userD = new UsuarioTerminal(nombre,null);
		GenerarReportes generarReportes = new GenerarReportes();
		userD.agregarObserver(generarReportes);				
		withTransaction( () -> {
			this.session.persist(userD);	
			this.session.persist(generarReportes);	
		} );		
		UsuarioTerminal user = this.session.find(UsuarioTerminal.class, userD.getIdUsuario());
		assertEquals(1, user.getSuscriptores().size());
		
		userD.eliminarObserver(generarReportes);
		withTransaction( () -> {
			this.session.update(userD);					
		} );
		
		UsuarioTerminal userDanie = this.session.find(UsuarioTerminal.class, userD.getIdUsuario());
		assertEquals(0, userDanie.getSuscriptores().size());
		
	}
	
	@Test
	public void testEliminarUsuario() throws Exception {
		
		String nombre = "usuarioJavier";
		UsuarioTerminal userJa = new UsuarioTerminal(nombre,null);						
		withTransaction( () -> {
			this.session.persist(userJa);			
		} );		
		UsuarioTerminal user = this.session.find(UsuarioTerminal.class, userJa.getIdUsuario());
		assertEquals(nombre, user.getNombre());
		
		withTransaction( () -> {
			this.session.remove(userJa);		
		} );
		
		UsuarioTerminal userNull = this.session.find(UsuarioTerminal.class, userJa.getIdUsuario());
		assertNull(userNull);
		
	}
	
	//TODO: hacer test de integracion de busqueda un texto usando las clases busqueda, usuario terminal (metodo buscarTexto)
	//observadores: generarReportes, guardarBusqueda
	@Test
	public void testPersistirBuscarTexto() throws Exception {
		Repositorio repositorio = new Repositorio(
				new LinkedList<PuntoInteres>(),
				new CacheSerExterno());
		UsuarioTerminal userD = new UsuarioTerminal("usuarioDaniel",repositorio);
		GenerarReportes generarReportes = new GenerarReportes();
		GuardarBusqueda guardarBusqueda = new GuardarBusqueda();
		userD.agregarObserver(generarReportes);
		userD.agregarObserver(guardarBusqueda);
		
		//Ejecuto la busqueda y debe guardar los datos de busqueda en el observador
		userD.buscarTexto("Rentas");
		userD.buscarTexto("Banco Santander");
		userD.buscarTexto("Almagro");		
		
		withTransaction( () -> {
			this.session.persist(repositorio);
			this.session.persist(userD);	
			this.session.persist(generarReportes);
			this.session.persist(guardarBusqueda);
		} );	
		
	}
	
}
