package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ar.edu.utn.d2s.exceptions.CacheSerExternoException;
import ar.edu.utn.d2s.exceptions.RepositorioException;

@Entity
public class Repositorio {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PuntoInteres> pdis = new LinkedList<PuntoInteres>();	
	@Transient
	List<PuntoInteres> pdisResultSE = new LinkedList<PuntoInteres>();
	@Transient
	private CacheSerExterno cache;
	
	 @OneToMany(targetEntity = AbstractOrigenes.class)
	 @Column (name = "ORIGENES")

	private List<PDIExterno> otrosOrigDatos = new LinkedList<PDIExterno>();
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public Repositorio() {
		super();
	}


	public Repositorio(List<PuntoInteres> pdis, CacheSerExterno cache) {
		super();
		this.pdis = pdis;
		this.cache = cache;
	}


	public Repositorio(List<PuntoInteres> pdis, CacheSerExterno cache, List<PDIExterno> pdisExternos) {
		super();
		this.pdis = pdis;
		this.cache = cache;
		this.otrosOrigDatos = pdisExternos;
	}
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++

	/**
	 * Agrega un PDI en el repositorio, si ya existe el PDI lanza una excepcion
	 * 
	 * @param pdi
	 * @throws RepositorioException 
	 */
	public void agregarPdi(PuntoInteres unPdi) throws RepositorioException {
		if (pdis.stream().anyMatch(pdi -> pdi.equals(unPdi))) {
			throw new RepositorioException("El PDI" + unPdi.getIdPuntoInteres() + "ya existe en el repositorio");
		} else {
			unPdi.setIdPuntoInteres(unPdi.hashCode());
			pdis.add(unPdi);
		}
	}

	/**
	 * Agrega una lista de PDI en el repositorio, si algun PDI ya existe continua con el proximo
	 * 
	 * @param pdi
	 * @throws RepositorioException 
	 */
	public void agregaPdis(List<PuntoInteres> pdis){
		pdis.stream().forEach(elemt -> { 
				try{ this.agregarPdi(elemt);} 
				catch (RepositorioException e){ }
			});
	}
	
	/**
	 * Elimina un PDI del repositorio, si no existe lanza una excepcion
	 * @param idPdi
	 */
	public void eliminarPdi(int idPdi) throws RepositorioException {
		if (pdis.stream().anyMatch(pdi -> pdi.getIdPuntoInteres() == idPdi)) {
			pdis.remove(this.buscarPdiPorId(idPdi));
		} else {
			throw new RepositorioException("El PDI" + idPdi + "no existe en el repositorio");
		}
	}		
	
	public void modificarPdi(PuntoInteres unPdi) throws RepositorioException {
		try {
			this.eliminarPdi(unPdi.getIdPuntoInteres());
		} catch (RepositorioException e) {
			throw new RepositorioException(e);
		}
		try {
			this.agregarPdi(unPdi);
		} catch (RepositorioException e) {
			throw new RepositorioException(e);
		}
	}		
	/**
	 * Busca en el repositorio un PDI por ID, sino existe lanza una excepcion.
	 * @param idPdi
	 */
	public PuntoInteres buscarPdiPorId(int idPdi) throws RepositorioException {
		List<PuntoInteres> pdisTemp = new LinkedList<PuntoInteres>();
		
		if (pdis.stream().anyMatch(pdi -> pdi.getIdPuntoInteres() == idPdi)) {
			pdisTemp.addAll(pdis.stream().filter(pdi -> pdi.getIdPuntoInteres() == idPdi).collect(Collectors.toList()));
			return pdisTemp.get(0);
		} else
			throw new RepositorioException("El PDI con la id " + idPdi + "no existe");
	}
	
	/**
	 * Busca en el repositorio un PDI por nombre, sino existe lanza una excepcion.
	 * @param nombre
	 */
	public PuntoInteres buscarPdiPorNombre(String nombre) throws RepositorioException {
		List<PuntoInteres> pdisTemp = new LinkedList<PuntoInteres>();
		
		if (pdis.stream().anyMatch(pdi -> pdi.getNombre().equals(nombre))) {
			pdisTemp.addAll(pdis.stream().filter(pdi -> pdi.getNombre().equals(nombre)).collect(Collectors.toList()));
			return pdisTemp.get(0);
		} else
			throw new RepositorioException("El PDI con el nombre " + nombre + "no existe");
	}
	
	/**
	 * Se buscan PDIs en el repositorio por un texto libre
	 * @param textoLibre
	 */
	public List<PuntoInteres> consultaPdiLocal(String textoLibre) throws RepositorioException {

		if (pdis.stream().anyMatch(pdi -> pdi.contieneTexto(textoLibre))) {
			return pdis.stream().filter(pdi -> pdi.contieneTexto(textoLibre)).collect(Collectors.toList());
		} else
			throw new RepositorioException("La palabra" + textoLibre + "no arrojo resultados en el repositorio");
	}	

	/**
	 * Busca PDIs por texto. Retorna los PDIs obtenidos en el repositorio + los PDIs de los serv Externos.
	 * Para las consulta del servicio externo, consulta en la cache si ya se realizo la busqueda. 
	 * Si existe en la cache, se retorna estos PDIs. Caso contrario se busca en los servicios Externos, 
	 * y se agrega la busqueda en la cache para futuras consultas. Ademas se agregan los PDIs en el repositorio.
	 * @param texto
	 */
	public List<PuntoInteres> buscarPdiPorTexto(String texto) {
		List<PuntoInteres> pdisTemporal = new LinkedList<PuntoInteres>();
		List<PuntoInteres> pdisTemporalExt = new LinkedList<PuntoInteres>();		
		try {
			pdisTemporal = this.consultaPdiLocal(texto);	
		} catch (RepositorioException e) {
//			No hay acción a tomar en este caso
		};
		try{
			pdisTemporalExt = this.cache.buscarPdi(texto);
		} catch (CacheSerExternoException e){
			pdisTemporalExt = this.busquedaPdiExtendida(texto);
			this.cache.agregarBusqueda(pdisTemporalExt, texto);
		}
		this.agregaPdis(pdisTemporalExt);
		pdisTemporal.addAll(pdisTemporalExt);
		return pdisTemporal.stream().distinct().collect(Collectors.toList());
	}
	
	/**
	 * Busca en los servicios externos por un texto
	 * 
	 * @param texto
	 */
	public List<PuntoInteres> busquedaPdiExtendida(String texto) {	
		otrosOrigDatos.stream().forEach(orig -> pdisResultSE.addAll(orig.buscarPdis(texto)));
		return pdisResultSE.stream().distinct().collect(Collectors.toList());
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++

	public List<PuntoInteres> getPdis() {
		return pdis;
	}


	public void setPdis(List<PuntoInteres> pdis) {
		this.pdis = pdis;
	}


	public CacheSerExterno getCache() {
		return cache;
	}


	public List<PDIExterno> getPdisExternos() {
		return otrosOrigDatos;
	}
	

	public void setCache(CacheSerExterno cache) {
		this.cache = cache;
	}
	

	public void agregarOrigDatos(PDIExterno origenDatos) {
		this.getPdisExternos().add(origenDatos);
	}

	public List<PDIExterno> getOtrosOrigDatos() {
		return otrosOrigDatos;
	}

	public void setOtrosOrigDatos(List<PDIExterno> otrosOrigDatos) {
		this.otrosOrigDatos = otrosOrigDatos;
	}

	public List<PuntoInteres> getPdisResultFE() {
		return pdisResultSE;
	}

	public void setPdisResultRE(List<PuntoInteres> pdisTemp) {
		this.pdisResultSE = pdisTemp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<PuntoInteres> getPdisResultSE() {
		return pdisResultSE;
	}

	public void setPdisResultSE(List<PuntoInteres> pdisResultSE) {
		this.pdisResultSE = pdisResultSE;
	}
	
	
}// end Repositorio
