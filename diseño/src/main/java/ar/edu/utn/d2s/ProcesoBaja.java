package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.utn.d2s.exceptions.RepositorioException;

public class ProcesoBaja extends ProcesoIndividual {

	private List<BajaPDI> listaBajas;

	public ProcesoBaja(List<BajaPDI> listaBajas, Administrador admin) {
		super(admin);
		this.listaBajas = listaBajas;
	}

	public void ejecutar() {
		List<String> listaBusqueda = new LinkedList<String>();
		listaBusqueda = this.getListaBajas().stream().map(baja -> baja.getValorBusqueda()).collect(Collectors.toList());
		List<PuntoInteres> listaPDIS = new LinkedList<PuntoInteres>();

		int cantidad = listaBusqueda.size();
		int contador;
		try {
			for (contador = 0; contador < cantidad; contador++) {

				listaPDIS.addAll(this.getAdmin().consultaPuntoInteres(listaBusqueda.get(contador)));

			}

			listaPDIS.stream().forEach(pdi -> this.getAdmin().bajaPuntoInteres(pdi.getIdPuntoInteres()));

		} catch (RepositorioException e) { // Este es el camino no feliz, las
											// busquedas de listaBajas no
											// devuelven nada
			setResultado(0);
			this.getAdmin().getManejadorErrores().stream().forEachOrdered(elemt -> elemt.manejarError(this));
		}
		;

	}

	public List<BajaPDI> getListaBajas() {
		return listaBajas;
	}

	public void setListaBajas(List<BajaPDI> listaBajas) {
		this.listaBajas = listaBajas;
	}

}
