package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.joda.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Cgp extends PuntoInteres {

	private String comuna = new String();
	// Servicio tiene nombre y RangoHorario(tupla, tres valores)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Servicio> servicios = new LinkedList<Servicio>();
	@Embedded
	private AreaComuna areaComuna = new AreaComuna();

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public Cgp(String nombre, String comuna) {
		super(nombre);
		this.comuna = comuna;
	}

	public Cgp(String nombre) {
		super(nombre);
	}
	
	public Cgp( ) {
		super();
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++

	public void addServicio(Servicio servicio) {
		this.servicios.add(servicio);
	}

	public boolean contieneTexto(String unTexto) {
		Predicate<Servicio> predicado = servicio -> servicio.cumpleCondicion(unTexto);
		return this.servicios.stream().anyMatch(predicado) || this.getComuna().equals(unTexto);
	}

	/*
	 * Determina cuando un punto de interes esta cerca de una coordenada (x, y)
	 * que representa latitud y longitud respectivamente. b) Los CGP tambien
	 * cumplen la condicion de cercania, si la coordenada esta dentro de la zona
	 * delimitada por la comuna.
	 * 
	 * Retorna: true, si una coordenada est� dentro de la comuna false, de lo
	 * contrario
	 */
	public boolean calculoCercania(double latitud, double longitud) {
		Coordenada puntoXy = new Coordenada(latitud, longitud);
		return this.getAreaComuna().isInside(puntoXy);
	}

	public boolean calculoDisponibilidad(LocalDateTime unaFecha, Object unValor) {
		return servicios.stream().anyMatch(unServicio -> unServicio.getNombre().equalsIgnoreCase((String) unValor)
				&& unServicio.estaAbierto(unaFecha));

	}

	public boolean calculoDisponibilidad(LocalDateTime unaFecha) {
		return servicios.stream().anyMatch(unServicio -> unServicio.estaAbierto(unaFecha));

	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getNombre().hashCode() + this.comuna.hashCode();
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
		Cgp other = (Cgp) obj;
		if (this.getNombre() != other.getNombre() || comuna != other.comuna)
			return false;
		return true;
	}

	public void agregaServiciosTemp(Servicio servicio) {
		this.getServicios().add(servicio);

	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++

	public String getComuna() {
		return comuna;
	}

	public void setComuna(String comuna) {
		this.comuna = comuna;
	}

	public AreaComuna getAreaComuna() {
		return areaComuna;
	}

	public void setAreaComuna(AreaComuna areaComuna) {
		this.areaComuna = areaComuna;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}

}
