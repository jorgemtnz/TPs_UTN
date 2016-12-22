package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "INFORMACION_BUSQUEDAS")
public class InformacionBusqueda {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	 @Column (name = "NOMBRE_USUARIO_TERMINAL")
	private String nombreUsuarioTerminal;
	 @Column (name = "TEXTO_BUSQUEDA")
	private String textoBusqueda;
	 @Column (name = "FECHA")
	 @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate fecha;
	 @Column (name = "CANT_RESULTADOS")
	private int cantResultados;
	 @Column (name = "TIEMPO")
	private long tiempoBusqueda;

	 @ManyToMany
	private List<PuntoInteres> pdisDevueltos;
	
	public InformacionBusqueda() {
		super();
		pdisDevueltos = new LinkedList<PuntoInteres>();
		
	}

	public List<PuntoInteres> getPdisDevueltos() {
		return pdisDevueltos;
	}

	public void setPdisDevueltos(List<PuntoInteres> pdisDevueltos) {
		this.pdisDevueltos = pdisDevueltos;
	}

	public String getNombreUsuarioTerminal() {
		return nombreUsuarioTerminal;
	}

	public void setNombreUsuarioTerminal(String nombreUsuarioTerminal) {
		this.nombreUsuarioTerminal = nombreUsuarioTerminal;
	}

	public String getTextoBusqueda() {
		return textoBusqueda;
	}

	public void setTextoBusqueda(String textoBusqueda) {
		this.textoBusqueda = textoBusqueda;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getCantResultados() {
		return cantResultados;
	}

	public void setCantResultados(int cantResultados) {
		this.cantResultados = cantResultados;
	}

	public long getTiempoBusqueda() {
		return tiempoBusqueda;
	}

	public void setTiempoBusqueda(long tiempoBusqueda) {
		this.tiempoBusqueda = tiempoBusqueda;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nombreUsuarioTerminal == null) ? 0 : nombreUsuarioTerminal.hashCode());
		result = prime * result + ((textoBusqueda == null) ? 0 : textoBusqueda.hashCode());
		result = prime * result + (int) (tiempoBusqueda ^ (tiempoBusqueda >>> 32));
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
		InformacionBusqueda other = (InformacionBusqueda) obj;
		if (id != other.id)
			return false;
		if (nombreUsuarioTerminal == null) {
			if (other.nombreUsuarioTerminal != null)
				return false;
		} else if (!nombreUsuarioTerminal.equals(other.nombreUsuarioTerminal))
			return false;
		if (textoBusqueda == null) {
			if (other.textoBusqueda != null)
				return false;
		} else if (!textoBusqueda.equals(other.textoBusqueda))
			return false;
		if (tiempoBusqueda != other.tiempoBusqueda)
			return false;
		return true;
	}
	
	

}
