package ar.edu.utn.d2s;

import static org.junit.Assert.assertTrue;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestEntrega5_Pto2_Persist_RepoOrigDatExt {

	
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
 	
 	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
 	public void testRepositorioSinOrigenesNoencuentra() throws Exception {
 		
 		String nombre = "165";
 		Colectivo bondi = new Colectivo(nombre);
 		List<PuntoInteres> listaPDIs = new LinkedList();
 		listaPDIs.add(bondi);
 		
 		List<Busqueda> busquedas = new LinkedList();
		CacheSerExterno cache = new CacheSerExterno(busquedas );
 		Repositorio repositorio = new Repositorio(listaPDIs, cache);
 		
 		
 		withTransaction( () -> {
 			this.session.persist(repositorio);
 		} );
 		
 		this.session.clear();
 		Repositorio persistRepositorio = this.session.find(Repositorio.class, repositorio.getId());
 		
 		List<PuntoInteres> resultPDIs = new LinkedList(); 		
 		resultPDIs = repositorio.buscarPdiPorTexto("Retiro");
 		assertTrue(persistRepositorio.getPdis().size()==1); 	
 		assertTrue( resultPDIs.size()==0);
 		
 	}
  
	@Test
 	public void testCargaRepositorioPersistido() throws Exception {
		
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Repositorio> criteria = criteriaBuilder.createQuery(Repositorio.class);
		Root<Repositorio> rootEntry = criteria.from(Repositorio.class);
		Repositorio repositorioDeDB = session.createQuery(
											criteria.select(rootEntry)
										).getSingleResult();
		
		assertTrue(repositorioDeDB.getPdis().stream().anyMatch(elemt -> elemt.getNombre().contains("165")));
		
 		
 	}
	
	
}
