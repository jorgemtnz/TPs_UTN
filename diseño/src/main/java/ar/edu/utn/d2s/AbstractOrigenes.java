package ar.edu.utn.d2s;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractOrigenes {
	
	 @Id @GeneratedValue(strategy = GenerationType.TABLE)
	    private Long idAbstractOrigenes;	
	 
		public long getId() {
		return idAbstractOrigenes;
	}

	public void setId(long id) {
		this.idAbstractOrigenes = id;
	}
	 

}
