package ar.edu.utn.d2s.ObservadoresConsulta;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractObservadorConsulta {
	 @Id @GeneratedValue(strategy = GenerationType.TABLE)
	    private Long idAbstractObservador;

	public Long getIdAbstractObservador() {
		return idAbstractObservador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAbstractObservador == null) ? 0 : idAbstractObservador.hashCode());
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
		AbstractObservadorConsulta other = (AbstractObservadorConsulta) obj;
		if (idAbstractObservador == null) {
			if (other.idAbstractObservador != null)
				return false;
		} else if (!idAbstractObservador.equals(other.idAbstractObservador))
			return false;
		return true;
	}	
	
}
