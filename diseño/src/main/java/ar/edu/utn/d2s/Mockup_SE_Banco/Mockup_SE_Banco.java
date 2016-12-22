package ar.edu.utn.d2s.Mockup_SE_Banco;

import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;

public class Mockup_SE_Banco {

	private List<BancoDTO> bancos = new LinkedList<BancoDTO>();

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public Mockup_SE_Banco() {
		super();
		this.bancos = this.generarBancos();
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos++++++++++++++++++++++++++++++++++++++
	public String obtenerBancos(String nombreBanco, String nombreServicio)
	{
		Gson json = new Gson();
		return json.toJson(this.bancos);
	}
	
	public String obtenerBancos(){
		return this.obtenerBancos("","");
	}
	
	/*
	 * Se crean los 3 bancos del fixture de la entrega 1, y se agregan
	 * 3 bancos más.
	 */
	private List<BancoDTO> generarBancos() {
		
		List<BancoDTO> _colBancosDTO  = new LinkedList<BancoDTO>();
		
		List<String> _serviciosCaballito;
		List<String> _serviciosBalvanera;
		List<String> _serviciosAlmagro;
		List<String> _serviciosMonserrat;
		List<String> _serviciosRetiro;
		List<String> _serviciosUrquiza;

		
		BancoDTO _bancoDTOCaballito;
		BancoDTO _bancoDTOBalvanera;
		BancoDTO _bancoDTOAlmagro;
		BancoDTO _bancoDTOMonserrat;
		BancoDTO _bancoDTORetiro;
		BancoDTO _bancoDTOUrquiza;
		/*
		 * Banco Ciudad Caballito
		 */
		_serviciosCaballito = new LinkedList<String>();
		_serviciosCaballito.add("cobro cheques");
		_serviciosCaballito.add("depósitos");
		_serviciosCaballito.add("extracciones");
		_serviciosCaballito.add("transferencias");
		_serviciosCaballito.add("créditos");
		
		_bancoDTOCaballito = new BancoDTO("Banco Ciudad Caballito", 1, 2, "Caballito", "Javier Loeschbor", _serviciosCaballito);
		
		_colBancosDTO.add(_bancoDTOCaballito);
		
		/*
		 * Banco Nacion Balvanera usa una lista de strings para los servicios
		 */
		_serviciosBalvanera = new LinkedList<String>(); 
		_serviciosBalvanera.add("depósitos");
		_serviciosBalvanera.add("extracciones");
		_serviciosBalvanera.add("transferencias");
		_serviciosBalvanera.add("seguros");
		
		_bancoDTOBalvanera = new BancoDTO("Banco Nación Balvanera", 3, 4, "Balvanera", "Fabián Fantaguzzi", _serviciosBalvanera);
		
		_colBancosDTO.add(_bancoDTOBalvanera);
		
		/*
		 * Banco Santander Plaza Almagro
		 */
		_serviciosAlmagro = new LinkedList<String>();
		_serviciosAlmagro.add("depósitos");
		_serviciosAlmagro.add("extracciones");
		_serviciosAlmagro.add("préstamos");
		_serviciosAlmagro.add("inversiones");
		
		_bancoDTOAlmagro = new BancoDTO("Banco Santander Plaza Almagro", 5, 6, "Plaza Almagro", "Guido Montal", _serviciosAlmagro);
		
		_colBancosDTO.add(_bancoDTOAlmagro);
		
		/*
		 * Banco Ciudad Monserrat
		 */
		_serviciosMonserrat = new LinkedList<String>();
		_serviciosMonserrat.add("cobro cheques");
		_serviciosMonserrat.add("depósitos");
		_serviciosMonserrat.add("extracciones");
		_serviciosMonserrat.add("transferencias");
		
		_bancoDTOMonserrat = new BancoDTO("Banco Ciudad Monserrat", 7, 8, "Monserrat", "Pedro Picapiedras", _serviciosMonserrat);
		
		_colBancosDTO.add(_bancoDTOMonserrat);
		
		/*
		 * Banco Nacion Retiro
		 */
		_serviciosRetiro = new LinkedList<String>();
		_serviciosRetiro.add("depósitos");
		_serviciosRetiro.add("extracciones");
		_serviciosRetiro.add("transferencias");
		
		_bancoDTORetiro = new BancoDTO("Banco Nación Retiro", 9, 10, "Retiro", "Juan Beltrán", _serviciosRetiro);
		
		_colBancosDTO.add(_bancoDTORetiro);
		
		/*
		 * Banco Santander Urquiza
		 */
		_serviciosUrquiza = new LinkedList<String>();
		_serviciosUrquiza.add("depósitos");
		_serviciosUrquiza.add("extracciones");
		_serviciosUrquiza.add("préstamos");
		
		_bancoDTOUrquiza = new BancoDTO("Banco Santander Urquiza", 11, 12, "Urquiza", "Ernesto Rumi", _serviciosUrquiza);
		
		_colBancosDTO.add(_bancoDTOUrquiza);
		
		return _colBancosDTO;
	}

}
