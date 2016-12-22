package ar.edu.utn.d2s.Mockup_SE_Cgp;

import java.util.LinkedList;
import java.util.List;

public class Mockup_SE_Cgp {

	private List<CentroDTO> centrosDTO = new LinkedList<CentroDTO>();

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	@SuppressWarnings("static-access")
	public Mockup_SE_Cgp() {
		super();
		this.centrosDTO = this.generarCentrosDTO();
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos++++++++++++++++++++++++++++++++++++++
	public List<CentroDTO> obtenerCgps(String calleOZona) {
		return this.centrosDTO;
	}

	public ServicioDTO obtenerDTO() {
		return centrosDTO.get(0).getServicios().get(0);
	}

	/*
	 * Crea la comuna 1, 2 y 3 como el fixture de la entrega 1 y agrega la
	 * comuna 4 con 2 servicios.
	 */
	private static List<CentroDTO> generarCentrosDTO() {

		List<CentroDTO> _colCentrosDTO = new LinkedList<CentroDTO>();

		List<ServicioDTO> _colServiciosDTOComuna1;
		List<RangoServicioDTO> _colRangosDTOComuna1;
		RangoServicioDTO _rango1Comuna1;
		RangoServicioDTO _rango2Comuna1;
		RangoServicioDTO _rango3Comuna1;
		RangoServicioDTO _rango4Comuna1;
		RangoServicioDTO _rango5Comuna1;

		ServicioDTO _servicioComuna1;
		CentroDTO _centroDTOComuna1;

		/*
		 * Comuna 1
		 */
		_rango1Comuna1 = new RangoServicioDTO(1, 9, 30, 15, 30); // mi horario
		_rango2Comuna1 = new RangoServicioDTO(2, 9, 30, 15, 30);
		_rango3Comuna1 = new RangoServicioDTO(3, 9, 30, 15, 30);
		_rango4Comuna1 = new RangoServicioDTO(4, 9, 30, 15, 30);
		_rango5Comuna1 = new RangoServicioDTO(5, 9, 30, 15, 30);

		_colRangosDTOComuna1 = new LinkedList<RangoServicioDTO>(); // coleccion
																	// de
																	// horarios
		_colRangosDTOComuna1.add(_rango1Comuna1);
		_colRangosDTOComuna1.add(_rango2Comuna1);
		_colRangosDTOComuna1.add(_rango3Comuna1);
		_colRangosDTOComuna1.add(_rango4Comuna1);
		_colRangosDTOComuna1.add(_rango5Comuna1);
//al servicio le agrega el rango
		_servicioComuna1 = new ServicioDTO("Rentas", _colRangosDTOComuna1); 
		// crea la coleccion de servicios
		_colServiciosDTOComuna1 = new LinkedList<ServicioDTO>();
		// agrega el servicio a la coleccion de servicios
		_colServiciosDTOComuna1.add(_servicioComuna1);

		// crea el centro DTO que es el CGP mio y le agrega atributos y
		// coleccion servicios
		_centroDTOComuna1 = new CentroDTO(1, "Retiro, San Nicolás, Puerto Madero, San Telmo, Montserrat, Constitución",
				"José Pérez", "Av.Uruguay 740", "4370-9700", _colServiciosDTOComuna1);
		// le agrega el numero de comuna
		_centroDTOComuna1.setNumeroComuna(1);

		// agrega el DTO a la coleccion de centros DTO que es la coleccion
		// futura de CGP.
		_colCentrosDTO.add(_centroDTOComuna1);
		// +++++++++++++++++++++++++++++++++++++++++++
		// ++++++++++++variables usadas comuna 2
		// ++++++++++++++++++++++++++++++++++++++++++++
		List<ServicioDTO> _colServiciosDTOComuna2;
		List<RangoServicioDTO> _colRangosDTOComuna2;
		RangoServicioDTO _rango1Comuna2;
		RangoServicioDTO _rango2Comuna2;
		RangoServicioDTO _rango3Comuna2;
		RangoServicioDTO _rango4Comuna2;
		RangoServicioDTO _rango5Comuna2;

		ServicioDTO _servicioComuna2;
		CentroDTO _centroDTOComuna2;

		/*
		 * Comuna 2
		 */
		// hace apuntar las variables ya creadas a otros rangos de horarios
		// se agrega un horario por dia por eso es que son tantos
		_rango1Comuna2 = new RangoServicioDTO(1, 8, 0, 14, 0);
		_rango2Comuna2 = new RangoServicioDTO(2, 8, 0, 14, 0);
		_rango3Comuna2 = new RangoServicioDTO(3, 8, 0, 14, 0);
		_rango4Comuna2 = new RangoServicioDTO(4, 8, 0, 14, 0);
		_rango5Comuna2 = new RangoServicioDTO(5, 8, 0, 14, 0);
		// crea la coleccion de horarios y agrega los rangos de horarios
		_colRangosDTOComuna2 = new LinkedList<RangoServicioDTO>();
		_colRangosDTOComuna2.add(_rango1Comuna2);
		_colRangosDTOComuna2.add(_rango2Comuna2);
		_colRangosDTOComuna2.add(_rango3Comuna2);
		_colRangosDTOComuna2.add(_rango4Comuna2);
		_colRangosDTOComuna2.add(_rango5Comuna2);
		// crea el servicio y le agrega la coleccion de rangos de horarios
		_servicioComuna2 = new ServicioDTO("Infracciones", _colRangosDTOComuna2);
		// crea la coleccion de servicios
		_colServiciosDTOComuna2 = new LinkedList<ServicioDTO>();
		// le agrega un servicio a la coleccion de servicios
		_colServiciosDTOComuna2.add(_servicioComuna2);
		// crea el DTO que es el futuro CGP y le agrega atributos, incluido la
		// coleccion de servicios
		_centroDTOComuna2 = new CentroDTO(2, "Recoleta", "Armando Casas", "Pres.José.E.Uriburu 1022",
				"4823-1165/72 / 4823-1094", _colServiciosDTOComuna2);
		// agrega el centro DTO a la coleccion de centrosDTO, que sera la futura
		// coleccion de CGPs
		_colCentrosDTO.add(_centroDTOComuna2);
		// +++++++++++++++++++++++++++++++++++++++++++
		// ++++++++++++variables usadas comuna 3
		// ++++++++++++++++++++++++++++++++++++++++++++
		/*
		 * Comuna 3
		 */
		List<ServicioDTO> _colServiciosDTOComuna3;
		List<RangoServicioDTO> _colRangosDTOComuna3;
		RangoServicioDTO _rango1Comuna3;
		RangoServicioDTO _rango2Comuna3;
		RangoServicioDTO _rango3Comuna3;
		RangoServicioDTO _rango4Comuna3;
		RangoServicioDTO _rango5Comuna3;

		ServicioDTO _servicioComuna3;
		CentroDTO _centroDTOComuna3;

		_rango1Comuna3 = new RangoServicioDTO(1, 9, 0, 15, 0);
		_rango2Comuna3 = new RangoServicioDTO(2, 9, 0, 15, 0);
		_rango3Comuna3 = new RangoServicioDTO(3, 9, 0, 15, 0);
		_rango4Comuna3 = new RangoServicioDTO(4, 9, 0, 15, 0);
		_rango5Comuna3 = new RangoServicioDTO(5, 9, 0, 15, 0);

		_colRangosDTOComuna3 = new LinkedList<RangoServicioDTO>();
		_colRangosDTOComuna3.add(_rango1Comuna3);
		_colRangosDTOComuna3.add(_rango2Comuna3);
		_colRangosDTOComuna3.add(_rango3Comuna3);
		_colRangosDTOComuna3.add(_rango4Comuna3);
		_colRangosDTOComuna3.add(_rango5Comuna3);

		_servicioComuna3 = new ServicioDTO("Defensa al Consumidor", _colRangosDTOComuna3);

		_colServiciosDTOComuna3 = new LinkedList<ServicioDTO>();
		_colServiciosDTOComuna3.add(_servicioComuna3);

		_centroDTOComuna3 = new CentroDTO(3, "Balvanera, San Cristóbal", "Hector Saco", "Junín 521", "4375-0644 / 45",
				_colServiciosDTOComuna3);

		_colCentrosDTO.add(_centroDTOComuna3);
		// +++++++++++++++++++++++++++++++++++++++++++
		// ++++++++++++variables usadas comuna 4
		// ++++++++++++++++++++++++++++++++++++++++++++
		/*
		 * Comuna 4
		 */
		List<ServicioDTO> _colServiciosDTOComuna4;
		
		List<RangoServicioDTO> _colRangosDTOComuna4;
		List<RangoServicioDTO> _colRangosDTOOtroComuna4;

		RangoServicioDTO _rango1Comuna4;
		RangoServicioDTO _rango2Comuna4;
		RangoServicioDTO _rango3Comuna4;
		RangoServicioDTO _rango4Comuna4;
		RangoServicioDTO _rango5Comuna4;

		RangoServicioDTO _rango1OtroComuna4;
		RangoServicioDTO _rango2OtroComuna4;
		RangoServicioDTO _rango3OtroComuna4;
		RangoServicioDTO _rango4OtroComuna4;
		RangoServicioDTO _rango5OtroComuna4;

		ServicioDTO _servicioComuna4;
		ServicioDTO _servicioOtroComuna4;
		CentroDTO _centroDTOComuna4;

		// Servicio1
		_rango1Comuna4 = new RangoServicioDTO(1, 9, 0, 18, 0);
		_rango2Comuna4 = new RangoServicioDTO(2, 9, 0, 18, 0);
		_rango3Comuna4 = new RangoServicioDTO(3, 9, 0, 18, 0);
		_rango4Comuna4 = new RangoServicioDTO(4, 9, 0, 18, 0);
		_rango5Comuna4 = new RangoServicioDTO(5, 9, 0, 18, 0);

		_colRangosDTOComuna4 = new LinkedList<RangoServicioDTO>();
		_colRangosDTOComuna4.add(_rango1Comuna4);
		_colRangosDTOComuna4.add(_rango2Comuna4);
		_colRangosDTOComuna4.add(_rango3Comuna4);
		_colRangosDTOComuna4.add(_rango4Comuna4);
		_colRangosDTOComuna4.add(_rango5Comuna4);

		_servicioComuna4 = new ServicioDTO("Atención Ciudadana", _colRangosDTOComuna4);

		_colServiciosDTOComuna4 = new LinkedList<ServicioDTO>();
		_colServiciosDTOComuna4.add(_servicioComuna4);

		// Servicio2
		_rango1OtroComuna4 = new RangoServicioDTO(1, 9, 0, 16, 0);
		_rango2OtroComuna4 = new RangoServicioDTO(2, 9, 0, 16, 0);
		_rango3OtroComuna4 = new RangoServicioDTO(3, 9, 0, 16, 0);
		_rango4OtroComuna4 = new RangoServicioDTO(4, 9, 0, 16, 0);
		_rango5OtroComuna4 = new RangoServicioDTO(5, 9, 0, 16, 0);

		_colRangosDTOOtroComuna4 = new LinkedList<RangoServicioDTO>();
		_colRangosDTOOtroComuna4.add(_rango1OtroComuna4);
		_colRangosDTOOtroComuna4.add(_rango2OtroComuna4);
		_colRangosDTOOtroComuna4.add(_rango3OtroComuna4);
		_colRangosDTOOtroComuna4.add(_rango4OtroComuna4);
		_colRangosDTOOtroComuna4.add(_rango5OtroComuna4);

		_servicioOtroComuna4 = new ServicioDTO("Servicio Social", _colRangosDTOOtroComuna4);
		_colServiciosDTOComuna4.add(_servicioOtroComuna4);

		_centroDTOComuna4 = new CentroDTO(4, "La Boca, Barracas, Parque Patricios, Nueva Pompeya", "Daniel Barrientos",
				"Av.del.Barco.Centenera 2906", "4918-1815 / 8920 /2243 4919-9024 / 9023", _colServiciosDTOComuna4);

		_colCentrosDTO.add(_centroDTOComuna4);

		return _colCentrosDTO;
	}
	
	
}
