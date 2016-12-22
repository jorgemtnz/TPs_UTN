package ar.edu.utn.d2s.Mockup_SE_Cgp;

import java.util.LinkedList;
import java.util.List;

public class CentroDTO {

	private int numeroComuna;
	private String zonasIncluidas;
	private String nombreDirector;
	private String domicilio;
	private String telefono;
	private List<ServicioDTO> servicios = new LinkedList<ServicioDTO>();
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public CentroDTO(int numeroComuna, String zonasIncluidas, String nombreDirector, String domicilio, String telefono,
			List<ServicioDTO> servicios) {
		super();
		this.numeroComuna = numeroComuna;
		this.zonasIncluidas = zonasIncluidas;
		this.nombreDirector = nombreDirector;
		this.domicilio = domicilio;
		this.telefono = telefono;
		this.servicios = servicios;
	}
	
	public CentroDTO() {		
		super();
		// TODO Auto-generated constructor stub
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor++++++++++++++++++++++++++++++++++++++
	public int getNumeroComuna() {
		return numeroComuna;
	}
	public void setNumeroComuna(int numeroComuna) {
		this.numeroComuna = numeroComuna;
	}
	public String getZonasIncluidas() {
		return zonasIncluidas;
	}
	public void setZonasIncluidas(String zonasIncluidas) {
		this.zonasIncluidas = zonasIncluidas;
	}
	public String getNombreDirector() {
		return nombreDirector;
	}
	public void setNombreDirector(String nombreDirector) {
		this.nombreDirector = nombreDirector;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public List<ServicioDTO> getServicios() {
		return servicios;
	}
	public void setServicios(List<ServicioDTO> servicios) {
		this.servicios = servicios;
	}
	
	
}
