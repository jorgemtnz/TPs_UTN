package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Jorge
 * @version 1.0
 * @created 08-may-2016 17:22:45
 */
@Entity
public class Busqueda {

	/**
	 * texto que se ingreso
	 */
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String clave = new String();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PuntoInteres> pdis = new LinkedList<PuntoInteres>();
	private Integer tiempoBusqueda = new Integer(0);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	/**
	 * 
	 */
	public Busqueda() {
		super();
	
	}

	public Busqueda(String clave, List<PuntoInteres> pdis) {
		super();
		this.clave = clave;
		this.pdis = pdis;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * definido el equal en Busqueda por el numero de clave
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Busqueda other = (Busqueda) obj;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		return true;
	}	
	
	
	@Override
	public String toString() {
		return "Busqueda [clave=" + clave + "]";
	}

	public void finalize() throws Throwable {
		
	}
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++

		public String getClave() {
			return clave;
		}

		public void setClave(String clave) {
			this.clave = clave;
		}

		public List<PuntoInteres> getPdis() {
			return pdis;
		}

		public void setPdis(List<PuntoInteres> pdis) {
			this.pdis = pdis;		
		}

		public Integer getTiempoBusqueda() {
			return tiempoBusqueda;
		}

		public void setTiempoBusqueda(Integer tiempoBusqueda) {
			this.tiempoBusqueda = tiempoBusqueda;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		
		
	
}//end Busqueda