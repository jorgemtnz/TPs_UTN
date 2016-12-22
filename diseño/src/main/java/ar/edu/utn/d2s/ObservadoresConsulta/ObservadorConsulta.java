package ar.edu.utn.d2s.ObservadoresConsulta;

import ar.edu.utn.d2s.InformacionBusqueda;


public interface ObservadorConsulta {
				
	//Debe ser llamado por el sujeto
	abstract public void actualizar(InformacionBusqueda i) throws Exception;
	
}
