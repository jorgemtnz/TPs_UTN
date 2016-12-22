package ar.edu.utn.d2s.Mockup_SE_Cgp;

import java.util.LinkedList;
import java.util.List;

public class ServicioDTO {

	private String nombreServicio;
	private List<RangoServicioDTO> rangos = new LinkedList<RangoServicioDTO>();
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public ServicioDTO(String nombreServicio, List<RangoServicioDTO> rangos) {
		super();
		this.nombreServicio = nombreServicio;
		this.rangos = rangos;
	}
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor++++++++++++++++++++++++++++++++++++++
	public String getNombreServicio() {
		return nombreServicio;
	}
	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}
	public List<RangoServicioDTO> getRangos() {
		return rangos;
	}
	public void setRangos(List<RangoServicioDTO> rangos) {
		this.rangos = rangos;
	}
	
}
