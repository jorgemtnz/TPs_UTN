package ar.edu.utn.d2s.ObservadoresConsulta;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import ar.edu.utn.d2s.InformacionBusqueda;
import org.joda.time.LocalDate;
import org.hibernate.annotations.Cascade;
import org.javatuples.*;

@Entity
@Table(name = "GENERAR_REPORTES")
public class GenerarReportes extends AbstractObservadorConsulta implements ObservadorConsulta {
	@OneToMany
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<InformacionBusqueda> listaInfo = new LinkedList<InformacionBusqueda>();
	@Column(name = "FECHA_REPORTE")
	private LocalDate fechaDeReporte = new LocalDate();
	@Transient
	private Map<String, Long> counts;
	
	public GenerarReportes() {
		super();
		listaInfo = new LinkedList<InformacionBusqueda>();
	}

	public void actualizar(InformacionBusqueda i) {
		// TODO: generar reportes con los datos de this.subject.logBusquedas
		// TODO: Adaptar sacar datos de parametros recibidos por actualizar
		// TODO: Adaptar guardar en el mismo objeto GenerarReporte
		listaInfo.add(i);
	}

	public List<Pair<LocalDate, Integer>> obtenerCantidadDeBusquedasPorFecha(LocalDate fechaInicio,
			LocalDate fechaFin) {
		List<Pair<LocalDate, Integer>> listaReportes = new LinkedList<Pair<LocalDate, Integer>>();

		for (fechaDeReporte = fechaInicio; fechaDeReporte
				.isBefore(fechaFin); fechaDeReporte = fechaDeReporte.plusDays(1)) {

			int cantidadDeBusquedas = listaInfo.stream().filter(info -> info.getFecha().equals(fechaDeReporte))
					.collect(Collectors.toList()).size();
			Pair<LocalDate, Integer> reporte = new Pair<LocalDate, Integer>(fechaDeReporte, cantidadDeBusquedas);
			listaReportes.add(reporte);
		}
		return listaReportes; 

	}

	public Map<String, Long> obtenerCantidadDeResultadosPorBusqueda(String usuario) {
		counts = listaInfo.stream()
				.collect(Collectors.groupingBy(InformacionBusqueda::getTextoBusqueda, Collectors.counting()));
		return counts;

	}

	public Map<String, Long> obtenerCantidadDeResultadosTotales() {
		counts = listaInfo.stream()
				.collect(Collectors.groupingBy(InformacionBusqueda::getNombreUsuarioTerminal, Collectors.counting()));
		return counts;
	}

	public List<InformacionBusqueda> getListInfo() {
		return listaInfo;
	}

	public void setListInfo(List<InformacionBusqueda> listInfo) {
		this.listaInfo = listInfo;
	}



	public List<InformacionBusqueda> getListaInfo() {
		return listaInfo;
	}

	public void setListaInfo(List<InformacionBusqueda> listaInfo) {
		this.listaInfo = listaInfo;
	}

	public LocalDate getFechaDeReporte() {
		return fechaDeReporte;
	}

	public void setFechaDeReporte(LocalDate fechaDeReporte) {
		this.fechaDeReporte = fechaDeReporte;
	}

	public Map<String, Long> getCounts() {
		return counts;
	}

	public void setCounts(Map<String, Long> counts) {
		this.counts = counts;
	}

	@Override
	public String toString() {
		return "Generar Reportes";
	}


}
