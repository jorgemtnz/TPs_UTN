package ar.edu.utn.d2s;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import ar.edu.utn.d2s.exceptions.RepositorioException;

public class ProcesoActualizarLocCom extends ProcesoIndividual {
	private String nombreArchivo;

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructor++++++++++++++++++++++++++++++++++++++
	public ProcesoActualizarLocCom(Administrador admin) {
		super(admin);
	}

	public ProcesoActualizarLocCom(Administrador admin , String nombreArchivo) {
		super(admin);
		this.nombreArchivo = nombreArchivo;
	}

	public void ejecutar() {
		try (Stream<String> stream = Files.lines(Paths.get(this.getNombreArchivo()))) {
			stream.forEach(linea -> {
				String nombre = linea.split(";")[0];
				String palabrasClave = linea.split(";")[1];
				String[] palabrasClaveList = palabrasClave.split(" ");

				try {// Si existe el Local Comercial con ese nombre, actualizo
						// las palabras claves
					LocalComercial local = (LocalComercial) this.getAdmin().getM_Repositorio()
							.buscarPdiPorNombre(nombre);
					local.setPalabrasClave(new ArrayList<String>());
					Arrays.stream(palabrasClaveList).forEach(palabra -> local.addPalabraClave(palabra));
				} // Si tira RepositorioException, no existe el Local Comercial,
					// entonces lo creo
				catch (RepositorioException e) {
					LocalComercial localNuevo = new LocalComercial(nombre, "rubro");
					Arrays.stream(palabrasClaveList).forEach(palabra -> localNuevo.addPalabraClave(palabra));
					this.getAdmin().getM_Repositorio().agregarPdi(localNuevo);
					this.getAdmin().getManejadorErrores().stream().forEachOrdered(elemt -> elemt.manejarError(this));
				}
			});
		} catch (IOException e) {
			setResultado(0);
			e.printStackTrace();
		}
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

}
