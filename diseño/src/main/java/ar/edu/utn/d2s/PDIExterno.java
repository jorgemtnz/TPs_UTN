package ar.edu.utn.d2s;

import java.util.List;

public interface PDIExterno {	
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++
	//
	//cuando estoy buscando un cgp: por calle o zona; un banco por nombreBanco
		public abstract List<PuntoInteres> buscarPdis (String texto);
	
}
