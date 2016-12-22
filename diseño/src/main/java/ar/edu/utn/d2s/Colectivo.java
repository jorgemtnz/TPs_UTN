package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import org.joda.time.LocalDateTime;
import org.uqbar.geodds.Point;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@Entity
public class Colectivo extends PuntoInteres {

	@ElementCollection
	@CollectionTable(name="parada", 
	joinColumns=@JoinColumn(name="idColectivo"))
	private List<Coordenada> paradas = new LinkedList<Coordenada>();

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public Colectivo(String nombre) {
		super(nombre);
		Integer x = Integer.valueOf(nombre);
		this.setNumero(x);	
	}
	
	public Colectivo(String nombre, int numero) {
		super(nombre);
		this.setNumero(numero);	
	}
	public Colectivo() {
		super();	
	}	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++

	public boolean contieneTexto(String unTexto) {
		/* TODO: lo que corresponda al punto 3 */
		//valida si unTexto es un numero, si es 114hola entonces lanza error
		int linea;
		try {
		linea = Integer.parseInt(unTexto);//convierte a un integer objeto
		} catch (Exception numberException){
			return false;
		};
		if ( this.getNumero() == linea)
			return true;
		else
			return false;
	}

	/*
	 * Determina cuando un punto de interes esta cerca de una coordenada (x, y) 
	 * que representa latitud y longitud respectivamente. 
	 * a) Para los colectivos, solo si esa coordenada esta a menos de 1 cuadra de alguna parada del recorrido.
	 * 
	 * Retorna:
	 * 		true, si una parada esta cercana al punto xy
	 * 		false, de lo contrario
	 */
	public boolean calculoCercania(double latitud, double longitud) {
		double _distanciaCercania = 0.1; //medida en kil�metros
		Point puntoXy = new Point(latitud, longitud);
		Predicate<Point> predicado = parada -> parada.distance(puntoXy) < _distanciaCercania;
		return this.paradas.stream().anyMatch(predicado);
	}

	public boolean calculoDisponibilidad(LocalDateTime unaFecha, Object unValor) {
		return true;

	}

	
	@Override
	public String toString() {
		return "Colectivo [numero=" + this.getNumero() + "]";
	}	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getNumero();
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
		Colectivo other = (Colectivo) obj;
		if (this.getNumero() != other.getNumero())
			return false;
		return true;
	}	
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++
		public List<Coordenada> getParadas() {
			return paradas;
		}

		public void setParadas(List<Coordenada> paradas) {
			this.paradas = paradas;
		}
		
		public void addParada(Coordenada parada) {
			this.paradas.add(parada);
		}
}
