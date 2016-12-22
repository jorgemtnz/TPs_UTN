package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;
import org.javatuples.Triplet;
import org.joda.time.LocalTime;
import ar.edu.utn.d2s.Mockup_SE_Cgp.CentroDTO;
import ar.edu.utn.d2s.Mockup_SE_Cgp.RangoServicioDTO;
import ar.edu.utn.d2s.Mockup_SE_Cgp.ServicioDTO;

public class ConvertidorCGP {
	private List<Cgp> cgpsTemp = new LinkedList<Cgp>();
	private List<Servicio> serCGP = new LinkedList<Servicio>();
	private List<Triplet<Integer, LocalTime, LocalTime>> rangos = new LinkedList<Triplet<Integer, LocalTime, LocalTime>>();
	private List<Horario> horarios = new LinkedList<Horario>();
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public ConvertidorCGP() {
	super();
	}
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++
//	este metodo es al que se llama desde afuera
	public List<Cgp> convertirACgp(List<CentroDTO> dTOs) {
		this.agregaCentroDTOaCgps(dTOs);
		return cgpsTemp;
	}	

	// va a agregar uno a uno los DTO ya convertidos a CGP en la coleccion
	public void agregaCentroDTOaCgps(List<CentroDTO> dTOs) {
		dTOs.stream().forEach(elemt -> this.getCgpsTemp().add(empaqueta(elemt, new Cgp("Comuna" + elemt.getNumeroComuna()))));
	}

	// metodo que agrega los servicios recogidos al cgp
	public Cgp empaqueta(CentroDTO dto, Cgp cgp) {
			
		// paso el domicilio
		String[] domicilioCompleto = new String[255];
		domicilioCompleto = dto.getDomicilio().split(" ");
		cgp.setCallePrincipal(domicilioCompleto[0]);
		Integer numeroDireccion = new Integer(0);
		 numeroDireccion = Integer.parseInt(domicilioCompleto[1]);
		cgp.setNumeroDireccion(numeroDireccion);
		// paso el numero
		cgp.setNumero(dto.getNumeroComuna());
		// paso los servicios
		cgp.setComuna(dto.getZonasIncluidas());
		dto.getServicios().stream().forEach(elemt ->cgp.agregaServiciosTemp(empaquetaServicios(elemt, new Servicio())));
		
		return cgp;
	}

	public Servicio empaquetaServicios(ServicioDTO servDto, Servicio servCgp) {		
		
		// le paso el nombre
		servCgp.setNombre(servDto.getNombreServicio());
		servDto.getRangos().stream().forEach(elemt -> this.getRangos().add(pasameRango(elemt)));
		//le estoy poniendo los rangosHorarios del DTO a la variable local Horarios
		servDto.getRangos().stream().forEach(elemt ->servCgp.pasameHorarioAtencion(pasameHorario(elemt, new Horario())));
		return servCgp;
	}

	private Triplet<Integer, LocalTime, LocalTime> pasameRango(RangoServicioDTO rangoDto) {
		// se crea la tupla y se le pasan los valores de rangoDTO
		Triplet<Integer, LocalTime, LocalTime> rango = new Triplet<Integer, LocalTime, LocalTime>(
				rangoDto.getNumeroDia(), new LocalTime(rangoDto.getHorarioDesde(), rangoDto.getMinutosDesde(), 0),
				new LocalTime(rangoDto.getHorarioHasta(), rangoDto.getMinutosHasta(), 0));
		
		return rango;
	}
	
	private Horario pasameHorario(RangoServicioDTO rangoDto, Horario horario){
		
		horario.setNumeroDia(rangoDto.getNumeroDia());
		horario.setHorarioDesde(rangoDto.getHorarioDesde());
		horario.setHorarioHasta(rangoDto.getHorarioHasta());
		horario.setMinutosDesde(rangoDto.getMinutosDesde());
		horario.setMinutosHasta(rangoDto.getMinutosHasta());
		return horario; 
		}
	
	
	@Override
	public String toString() {
		return "ConvertidorCGP []";
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++

	public List<Cgp> getCgpsTemp() {
		return cgpsTemp;
	}

	
	public List<Servicio> getSerCGP() {
		return serCGP;
	}


	public void setSerCGP(List<Servicio> serCGP) {
		this.serCGP = serCGP;
	}


	public void setCgpsTemp(List<Cgp> cgpsTemp) {
		this.cgpsTemp = cgpsTemp;
	}


	public List<Triplet<Integer, LocalTime, LocalTime>> getRangos() {
		return rangos;
	}


	public void setRangos(List<Triplet<Integer, LocalTime, LocalTime>> rangos) {
		this.rangos = rangos;
	}
	public List<Horario> getHorarios() {
		return horarios;
	}
	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}
	
	

}
