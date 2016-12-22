package ar.edu.utn.d2s;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

@Entity
public class Servicio {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String nombre = new String();
	@ManyToOne(cascade = CascadeType.ALL)
	private RangoHorario rango = new RangoHorario();	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++	
	public Servicio() {
		super();	
	}
	

	public Servicio(String nombre, RangoHorario rango) {
		super();
		this.nombre = nombre;
		this.rango = rango;
	}	
	
	public Servicio(String nombre) {
		super();
		this.nombre = nombre;
	}	

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++metodos+++++++++++++++++++++++++++++++++++
	
	public boolean cumpleCondicion(String unTexto){
		return this.nombre.startsWith(unTexto);
	}
	
	public void agregarHorario(int dia, LocalTime fechaInicio, LocalTime fechaFin){
		Integer objDia = new Integer(dia);
		this.getRango().agregar(objDia, fechaInicio, fechaFin);			
	}
		
	public boolean estaAbierto (LocalDateTime unaFecha){
		return this.getRango().pertenece(unaFecha);
	}
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public RangoHorario getRango() {
		return rango;
	}
	
	public void setRango(RangoHorario unRango){
		 this.rango =unRango;
	}

	public void pasameHorarioAtencion(Horario horarioAtencion) {
		this.getRango().getHorariosAtencion().add(horarioAtencion);
		
	}	
	
	
}
