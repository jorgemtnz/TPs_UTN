package ar.edu.utn.d2s;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestPersistirObjetosIniciales {

	private SessionFactory factory = SessionFactorySingleton.instance();

	private Session session;
	private static Repositorio repositorio;
	private static List<PuntoInteres> listaDePdis = new ArrayList<PuntoInteres>();

	private static UsuarioTerminal usuarioJorge;
	private static UsuarioTerminal usuarioWalter;
	private static UsuarioTerminal usuarioEnzo;

	private static Colectivo linea50 = new Colectivo("50");
	private static Colectivo linea51 = new Colectivo("51");
	private static Colectivo linea92 = new Colectivo("92");

	@Before
	public void setUp() {
		this.session = factory.openSession();

		repositorio = new Repositorio(listaDePdis, new CacheSerExterno());

		usuarioJorge = new UsuarioTerminal("TerminalJorge", repositorio);
		usuarioWalter = new UsuarioTerminal("TerminalWalter", repositorio);
		usuarioEnzo = new UsuarioTerminal("TerminalEnzo", repositorio);

		usuarioJorge.setPassword("1234567");
		usuarioWalter.setPassword("1234567");
		usuarioEnzo.setPassword("1234567");

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

		repositorio.agregarPdi(linea50);
		repositorio.agregarPdi(linea51);
		repositorio.agregarPdi(linea92);

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
	public void testPersistirObjetosInicialesLogin() throws Exception {
		// 1.-se corrio el test y dio verde
		// 2- se apago y reinicio la pc y se verifico que los datos fueron persistidos
		// correctamente.
		withTransaction(() -> {
			session.persist(repositorio);
			session.persist(usuarioJorge);
			session.persist(usuarioWalter);
			session.persist(usuarioEnzo);
		});

		Repositorio persistRepositorio = this.session.find(Repositorio.class, repositorio.getId());
		assertTrue(persistRepositorio.getPdis().size() > 1);

		UsuarioTerminal userJorge = this.session.find(UsuarioTerminal.class, usuarioJorge.getIdUsuario());
		assertTrue(userJorge.getPassword() == "1234567");
		assertTrue(userJorge.getNombre() == "TerminalJorge");
	}

}
