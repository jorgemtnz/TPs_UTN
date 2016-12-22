package ar.edu.utn.d2s.ObservadoresConsulta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ar.edu.utn.d2s.InformacionBusqueda;

@Entity
@Table(name = "TERMINAL_LOGGERS")
public class LoggerTerminal extends AbstractObservadorConsulta implements ObservadorConsulta {


	@Transient
	static Logger log = LogManager.getLogger(LoggerTerminal.class.getName());

	@Column(name = "path_LOGGER")
	private String path = new String();

	public LoggerTerminal() {
		super();
	}

	public void actualizar(InformacionBusqueda i) throws Exception {
		String nombreUsuarioTerminal = i.getNombreUsuarioTerminal();
		String ultimoTextoBusqueda = i.getTextoBusqueda();
		int cantidadResultados = i.getCantResultados();
		long ultimoTiempoBusquedaMilisegundos = i.getTiempoBusqueda();

		log.info("El usuario:" + nombreUsuarioTerminal + " busco: " + ultimoTextoBusqueda + ", arrojo "
				+ cantidadResultados + " resultados y tardo: " + ultimoTiempoBusquedaMilisegundos + " milisengundos");
	}



	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		LoggerTerminal.log = log;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Generar log";
	}
	

}
