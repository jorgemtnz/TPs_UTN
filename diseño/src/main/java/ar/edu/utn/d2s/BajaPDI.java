package ar.edu.utn.d2s;

import org.joda.time.LocalDateTime;

public class BajaPDI {
	
	private String valorBusqueda = new String();
	private LocalDateTime fechaBaja = new LocalDateTime();
	
	public String getValorBusqueda() {
		return valorBusqueda;
	}
	
	public LocalDateTime getFechaBaja() {
		return fechaBaja;
	}
	
	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}
	public void setFechaBaja(LocalDateTime fechaBaja) {
		this.fechaBaja = fechaBaja;
	}


}
