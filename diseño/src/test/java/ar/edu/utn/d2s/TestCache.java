package ar.edu.utn.d2s;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import ar.edu.utn.d2s.exceptions.CacheSerExternoException;
import org.junit.Before; //se ejecuta antes de cada test

public class TestCache {
	private CacheSerExterno cache = new CacheSerExterno();
	private Colectivo linea50 = new Colectivo("50");
	private Colectivo linea92 = new Colectivo("92");
	private List<PuntoInteres> listaPDI = new LinkedList<PuntoInteres>();
	private Busqueda busqueda = new Busqueda("linea", listaPDI);
	private List<Busqueda> listBusqueda = new LinkedList<Busqueda>();

	@Before
	public void setUpBefore() throws Exception {

		linea50.setNumero(50);
		linea50.addParada(new Coordenada(0, 2));
		linea50.addParada(new Coordenada(0, 8));
		linea50.addParada(new Coordenada(0, 10));
		linea50.addParada(new Coordenada(0, 12));
		linea50.addParada(new Coordenada(0, 14));
		linea92.setNumero(92);
		linea92.addParada(new Coordenada(4, 0));
		linea92.addParada(new Coordenada(4, 2));
		linea92.addParada(new Coordenada(4, 4));
		linea92.addParada(new Coordenada(4, 8));
		linea92.addParada(new Coordenada(4, 10));

		listaPDI.add(linea50);
		listaPDI.add(linea92);

		listBusqueda.add(busqueda);

		cache.setBusquedas(listBusqueda);

	}

	@Test
	public void testCacheBusqueda() {
		
		assertTrue(cache.getBusquedas().isEmpty() == false);
	}

	@Test
	public void testCacheBusquedaLinea() throws Exception {
		assertTrue(cache.buscarPdi("linea").isEmpty() == false);
	}

	@Test
	public void testCacheBusquedaLinea2() throws Exception {
		List<PuntoInteres> lista = new LinkedList<PuntoInteres>();
		lista = cache.buscarPdi("linea");
		assertTrue(lista.stream().anyMatch(pdi -> pdi.getNumero() == 50));
	}


	@Test(expected = CacheSerExternoException.class)
	public void testCacheBuscarExternoVacio() throws Exception {
		List<PuntoInteres> lista = new LinkedList<PuntoInteres>();
		lista.addAll(cache.buscarPdi("Banco"));
		assertTrue(lista.isEmpty() == false);
	}

}
