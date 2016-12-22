package ar.edu.utn.d2s;

import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUIHistorialBusqueda {

	private SessionFactory factory = SessionFactorySingleton.instance();

	private Session session;
	private static InformacionBusqueda busqueda1;
	private static InformacionBusqueda busqueda2;
	private Colectivo linea50;
	private Colectivo linea50RamalB;
	private Colectivo linea92;
	
	@Before
	public void setUp() {
		this.session = factory.openSession();
		busqueda1 = new InformacionBusqueda();
		busqueda2 = new InformacionBusqueda();
		linea50 = new Colectivo("50");
		linea50RamalB = new Colectivo("50");
		linea92 = new Colectivo("92");
		
		busqueda1.setFecha(LocalDate.now());
		busqueda1.setCantResultados(2);
		busqueda1.setTextoBusqueda("50");
		busqueda1.setTiempoBusqueda(1);
		busqueda1.setNombreUsuarioTerminal("TerminalEnzo");
		busqueda1.getPdisDevueltos().add(linea50);
		busqueda1.getPdisDevueltos().add(linea50RamalB);
		
		
		
		busqueda2.setFecha(LocalDate.now());
		busqueda2.setCantResultados(1);
		busqueda2.setTextoBusqueda("92");
		busqueda2.setTiempoBusqueda(1);
		busqueda2.setNombreUsuarioTerminal("TerminalJorge");
		busqueda2.getPdisDevueltos().add(linea92);
		
	}
	
	@After
	public void tearDown() {
		this.session.close();
	}

	public void withTransaction(Runnable command) {
		Transaction transaction = this.session.beginTransaction();
		command.run();
		transaction.commit();
	}
	
	@Test
	public void testPersistirBusquedas() throws Exception {
		withTransaction(() -> {
			session.persist(busqueda1);
			session.persist(busqueda2);
			session.persist(linea50);
			session.persist(linea50RamalB);
			session.persist(linea92);
		});

		InformacionBusqueda busqueda1Persistida = this.session.find(InformacionBusqueda.class, busqueda1.getId());
		assertTrue(busqueda1Persistida.getNombreUsuarioTerminal() == "TerminalEnzo");
	}
		
}
