package ar.edu.utn.d2s;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestEntrega5_Pto1_Persist_AbmOrigDatExtBusq {
	private Repositorio repositorio;
	private Colectivo bondi;
	private List<PuntoInteres> listaDePdis = new ArrayList<PuntoInteres>();
	private Busqueda unaBusqueda = new Busqueda();
	private OrigenBancoExterno origenBancoExterno = new OrigenBancoExterno();
	private OrigenCgpExterno origenCgpExterno = new OrigenCgpExterno();
	private Cgp unCgp; 
	
private SessionFactory factory = SessionFactorySingleton.instance(); 
	
	private Session session;
	
	@Before
	public void setUp(){
		this.session = factory.openSession();
	}
	
	@Before
 	public void setUpBefore() throws Exception {
		repositorio = new Repositorio(new LinkedList<PuntoInteres>(), new CacheSerExterno());
		unCgp = crearCgp("comuna1", "1");
		unCgp.setCoordenada(new Coordenada(4,4));
		bondi = new Colectivo();
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
	public void testBusqueda() throws Exception {
		
		bondi.setNombre("105");
		bondi.setCoordenada(new Coordenada(5,3));
		listaDePdis.add(bondi);
		unaBusqueda.setClave(bondi.getNombre());
		unaBusqueda.setPdis(listaDePdis);
		unaBusqueda.setTiempoBusqueda(3);
	
		withTransaction( () -> {
			this.session.persist(bondi);	
			this.session.persist(unaBusqueda);	
		} );
		
		this.session.clear();
		
		Busqueda busqueda165 = this.session.find(Busqueda.class, unaBusqueda.getId());
		assertEquals(unaBusqueda.getClave(), busqueda165.getClave());
		assertTrue(busqueda165.getPdis().contains(bondi));
		
	}
	
	@Test
	public void testOrigenCGPExternoPersistePDI() throws Exception {
		listaDePdis.add(unCgp);
		origenCgpExterno.setPdis(listaDePdis);
		repositorio.agregarPdi(unCgp);
	    
		withTransaction( () -> {
			this.session.persist(origenCgpExterno);
			this.session.persist(repositorio);
		} );
		
		this.session.clear();
		OrigenCgpExterno origenCgpExternoNew = this.session.find(OrigenCgpExterno.class, origenCgpExterno.getId());
     	assertTrue(origenCgpExternoNew.getPdis().stream().anyMatch(elemt -> elemt.getNombre().equals("comuna1")));
	}
	
	@Test
	public void testOrigenCgpExternoBaja() throws Exception {		    
		
		withTransaction( () -> {
			this.session.persist(origenBancoExterno);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} );
		
		OrigenBancoExterno origenExternoNew = this.session.find(OrigenBancoExterno.class, origenBancoExterno.getId());
     	assertNotNull(origenExternoNew);  
		
		withTransaction( () -> {
			this.session.remove(origenBancoExterno);
		} );
		
		this.session.clear();
		OrigenBancoExterno origenBanExternoNew = this.session.find(OrigenBancoExterno.class, origenBancoExterno.getId());
     	assertNull(origenBanExternoNew);     	
     	
	}
	
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
}
