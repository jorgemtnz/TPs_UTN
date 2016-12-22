package ar.edu.utn.d2s;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.uqbar.geodds.Point;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
//@Table(name = "LOCALESCOMERCIALES")
public class LocalComercial extends PuntoInteres {

	@ManyToOne
	private RangoHorario rango = new RangoHorario();
	
	@ElementCollection(targetClass=String.class)
	private List<String> palabrasClave = new ArrayList<String>();
	private String rubro;
	private double radioCercaniaRubro;
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public LocalComercial() {
		super();
	}
	
	public LocalComercial(String nombre, String rubro) {
		super(nombre);
		this.rubro = rubro;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++
	
	public void agregarHorario(Integer dia, LocalTime fechaInicio, LocalTime fechaFin){
		this.getRango().agregar(dia, fechaInicio, fechaFin);
			
	}
	


	public boolean contieneTexto(String unTexto){		
		return this.getNombre().startsWith(unTexto) ||
				palabrasClave.contains(unTexto) ||
				rubro.equals(unTexto);
	}	

	/*
	 * Determina cuando un punto de interes esta cerca de una coordenada (x, y) 
	 * que representa latitud y longitud respectivamente. 
	 * c) Para los locales comerciales, cada rubro define el radio de cercaia. 
	 * Ejemplo: para las librerias escolares cerca es estar a un radio de 5 cuadras, 
	 * para un kiosco de diarios en cambio son 2 cuadras, y as� sucesivamente. 
	 * 
	 * Retorna:
	 * 		true, si el punto de inter�s est� cercana al punto xy
	 * 		false, de lo contrario
	 */
	public boolean calculoCercania(double latitud, double longitud) {
		Point puntoXy = new Point(latitud, longitud);
		return this.coordenada.distance(puntoXy) < this.radioCercaniaRubro; //radioCercaniaRubro medida en kilometros
	}

	public boolean calculoDisponibilidad(LocalDateTime unaFecha, Object unValor) {
		return this.getRango().pertenece(unaFecha);		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getNombre().hashCode() + this.getRubro().hashCode(); ;
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
		LocalComercial other = (LocalComercial) obj;
		if (this.getNombre() != other.getNombre() 
			|| this.getRubro() != other.getRubro())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocalComercial ["+this.getNombre() +"]";
	}
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++
	public String getRubro() {
		return rubro;
	}

	public void setRubro(String rubro) {
		this.rubro = rubro;
	}
	
	public List<String> getPalabrasClave() {
		return palabrasClave;
	}

	public void setPalabrasClave(List<String> palabrasClave) {
		this.palabrasClave = palabrasClave;
	}

	public void setRango(RangoHorario rango) {
		this.rango = rango;
	}

	public void addPalabraClave(String palabra) {
		palabrasClave.add(palabra);
	}
	
	public RangoHorario getRango() {
		return rango;
	}

	/*
	 * Expresada en kilometros
	 */
	public double getradioCercaniaRubro() {
		return radioCercaniaRubro;
	}

	/*
	 * Expresada en kilometros
	 */
	public void setRadioCercaniaRubro(double radioCercaniaRubro) {
		this.radioCercaniaRubro = radioCercaniaRubro;
	}
	
}
