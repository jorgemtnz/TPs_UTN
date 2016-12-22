package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.javatuples.*;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

@Entity
@Table(name = "RANGOHORARIOS")
public class RangoHorario {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id_rangoHorario;

	@Transient
	private List<Triplet<Integer, LocalTime, LocalTime>> horarios = new LinkedList<Triplet<Integer, LocalTime, LocalTime>>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	
	private List<Horario> horariosAtencion = new LinkedList<Horario>();
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructor+++++++++++++++++++++++++++++++++++++++++++

	public RangoHorario() {
		super();
	}


	public RangoHorario(List<Triplet<Integer, LocalTime, LocalTime>> horarios) {
		super();
		this.horarios = horarios;
	}
	
	public RangoHorario(List<Triplet<Integer, LocalTime, LocalTime>> horarios, List<Horario> horariosAtencion) {
		super();
		this.horariosAtencion = horariosAtencion;
		this.horarios = horarios;
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++++++++++++metodos+++++++++++++++++++++++++++++

	public void agregar(Integer dia, LocalTime fechaInicio, LocalTime fechaFin) {
		Triplet<Integer, LocalTime, LocalTime> unHorario = new Triplet<Integer, LocalTime, LocalTime>(
				Integer.valueOf(dia), fechaInicio, fechaFin);
		this.getHorarios().add(unHorario);
	}
	
	public void agregar(Horario horarioAtencion) {
		this.getHorariosAtencion().add(horarioAtencion);
	}

	public boolean pertenece(LocalDateTime unaFecha) {
		return this.getHorarios().stream()
				.anyMatch(unHorario -> unHorario.getValue0().equals(unaFecha.getDayOfWeek())
						&& unHorario.getValue1().isBefore(unaFecha.toLocalTime())
						&& unHorario.getValue2().isAfter(unaFecha.toLocalTime()));
	}


	@Override
	public String toString() {
		return "RangoHorario [horarios=" + horarios + "; horariosAtencion=" + horariosAtencion + "]";
	}
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accessor+++++++++++++++++++++++++++++++++++++++++++

	@Transient
	public void setHorarios(List<Triplet<Integer, LocalTime, LocalTime>> horarios) {
		this.horarios = horarios;
	}
	
	@Transient
	public List<Triplet<Integer, LocalTime, LocalTime>> getHorarios() {
		return horarios;
	}

	public List<Horario> getHorariosAtencion() {
		return horariosAtencion;
	}

	public void setHorariosAtencion(List<Horario> horariosAtencion) {
		this.horariosAtencion = horariosAtencion;
	}


}
