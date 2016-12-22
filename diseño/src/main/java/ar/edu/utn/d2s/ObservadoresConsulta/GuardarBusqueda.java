package ar.edu.utn.d2s.ObservadoresConsulta;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import ar.edu.utn.d2s.InformacionBusqueda;

@Entity
@Table(name = "GUARDAR_BUSQUEDAS")
public class GuardarBusqueda  extends AbstractObservadorConsulta implements ObservadorConsulta {
	@OneToMany
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<InformacionBusqueda> listaInfo = new LinkedList<InformacionBusqueda>();;

	@Override
	public String toString() {
		return "Guardar busquedas";
	}

	public GuardarBusqueda() {
		super();

	}

	public void actualizar(InformacionBusqueda i) {
		// TODO: obtener de this.subject UltimoResultadoDeBusqueda y otros
		// parametros para
		// guardar en this.subject.logBusquedas
		// TODO: Adaptar sacar datos de parametros recibidos por actualizar
		// TODO: Adaptar guardar en el mismo objeto GuardarBusqueda
		listaInfo.add(i);

	}

	public void guardarBusqueda() {

	}

	public List<InformacionBusqueda> getListaInfo() {
		return listaInfo;
	}

	public void setListaInfo(List<InformacionBusqueda> listaInfo) {
		this.listaInfo = listaInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((listaInfo == null) ? 0 : listaInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GuardarBusqueda other = (GuardarBusqueda) obj;
		if (listaInfo == null) {
			if (other.listaInfo != null)
				return false;
		} else if (!listaInfo.equals(other.listaInfo))
			return false;
		return true;
	}

	


}
