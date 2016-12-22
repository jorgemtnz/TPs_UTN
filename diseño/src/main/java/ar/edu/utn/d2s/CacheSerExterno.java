package ar.edu.utn.d2s;


import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import ar.edu.utn.d2s.exceptions.CacheSerExternoException;

public class CacheSerExterno {

	private List<Busqueda> busquedas = new LinkedList<Busqueda>();

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	
	public CacheSerExterno() {
		super();	
	}

	public CacheSerExterno(List<Busqueda> busquedas) {
		super();
		this.busquedas = busquedas;
	}
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++

	
	public void agregarBusqueda(List<PuntoInteres> pdis, String clave) {
		Busqueda busqueda = new Busqueda(clave, pdis);
		this.busquedas.add(busqueda);
		}

	public List<PuntoInteres> buscarPdi(String clave) throws CacheSerExternoException {
		Busqueda busqueda = new Busqueda();

		if (this.getBusquedas().stream().anyMatch(busq -> busq.getClave() == clave)) {

			busqueda = (this.getBusquedas().stream().filter(busq -> busq.getClave() == clave)
					.collect(Collectors.toList())).get(0);

			return busqueda.getPdis();
		}
		
		else  throw new CacheSerExternoException ("La palabra" + clave + "no arrojo resultados en la cache");
		}


	@Override
	public String toString(){
		return "CacheSerExterno [busquedas=" + busquedas + "]";
	}
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++


	public List<Busqueda> getBusquedas() {
		return busquedas;
	}


	public void setBusquedas(List<Busqueda> busquedas) {
		this.busquedas = busquedas;
	}


	
}// end Cache