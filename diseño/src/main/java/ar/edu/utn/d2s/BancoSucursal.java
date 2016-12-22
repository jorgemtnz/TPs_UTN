package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;
import org.joda.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class BancoSucursal extends PuntoInteres {
	
	private String sucursal = new String();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Servicio> servicioss= new LinkedList<Servicio>();

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++

	public BancoSucursal(String nombre, String sucursal) {
		super(nombre);
		this.sucursal = sucursal;	
	}
	
	public BancoSucursal() {
		super();	
	}	
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++

public void addServicio(Servicio servicio) {
		
		this.servicioss.add(servicio);
	}
	
	public boolean contieneTexto(String unTexto){
		/* TODO: lo que corresponda al punto 3 */
		return  this.getNombre().startsWith(unTexto);
	}

	public boolean calculoDisponibilidad(LocalDateTime unaFecha, Object unValor) {
		return servicioss.stream().anyMatch(unServicio -> unServicio.getNombre().equalsIgnoreCase((String) unValor) && unServicio.estaAbierto(unaFecha));
	}	

	/*
	 * Determina cuando un punto de interés está cerca de una coordenada (x, y) 
	 * que representa latitud y longitud respectivamente.
	 * Aplica para Banco: en general, un punto de interés se considera cercano si se encuentra a una distancia 
	 * menor a 5 cuadras
	 */
	public boolean calculoCercania(double latitud, double longitud) {
		Coordenada puntoXy = new Coordenada(latitud, longitud);
		return this.coordenada.distance(puntoXy) < this.RADIO_CERCANIA; //RADIO_CERCANIA genérica para PDI, medida en kilómetros
	}

	
	public String toString() {		
		return "[Banco sucursal=" + this.getNombre() + " " + this.getSucursal() + "]";
	}	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getNombre().hashCode() + this.sucursal.hashCode(); ;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BancoSucursal other = (BancoSucursal) obj;
		if (this.getNombre() != other.getNombre() 
			|| this.sucursal != other.sucursal)
			return false;
		return true;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor++++++++++++++++++++++++++++++++


	public List<Servicio> getServicio() {
		return servicioss;
	}

	public void setServicio(List<Servicio> servicio) {
		this.servicioss = servicio;
	}

	public Coordenada getCoordenada() {
		return coordenada;
	}
	
	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}
	
	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}	

}
