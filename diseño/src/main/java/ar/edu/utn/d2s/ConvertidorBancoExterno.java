package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class ConvertidorBancoExterno {
	
	private List<BancoSucursal> lista = new LinkedList<BancoSucursal>(); 

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public ConvertidorBancoExterno() {
		super();
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++

	public List<BancoSucursal> convertirJSONaPdi(String jsonText) {
		JsonElement jelement = new JsonParser().parse(jsonText);
		JsonArray jarray = jelement.getAsJsonArray();
		jarray.forEach(elem -> this.getLista().add(crearBanco(new BancoSucursal(elem.getAsJsonObject().get("banco").getAsString(),
				elem.getAsJsonObject().get("sucursal").toString()), elem)));
		return this.getLista();
	}

	private BancoSucursal crearBanco(BancoSucursal bancoSucursal, JsonElement elem) {
		bancoSucursal.setCoordenada(new Coordenada(new Double(elem.getAsJsonObject().get("x").getAsDouble()),
				new Double(elem.getAsJsonObject().get("y").getAsDouble())));

		bancoSucursal.setServicio(crearServicios(
				new Gson().fromJson(elem.getAsJsonObject().get("servicios"), new TypeToken<List<String>>() {
				}.getType()), new LinkedList<Servicio>()));
		return bancoSucursal;
	}

	private List<Servicio> crearServicios(List<String> servicios, LinkedList<Servicio> serviciosBanco) {
		servicios.stream().forEach(elemento -> serviciosBanco.add(agregarRango(new Servicio(elemento))));

		return serviciosBanco; 
	}

	private Servicio agregarRango(Servicio servicio) {
		servicio.setRango(agregarHorario(new RangoHorario()));;
		
		return servicio;
	}

	private RangoHorario agregarHorario(RangoHorario rangoHorario) {
		rangoHorario.agregar(new Horario(1,10,00,15,00));
		return rangoHorario; 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConvertidorBancoExterno []";
	}

	public List<BancoSucursal> getLista() {
		return lista;
	}

	public void setLista(List<BancoSucursal> lista) {
		this.lista = lista;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++

}
