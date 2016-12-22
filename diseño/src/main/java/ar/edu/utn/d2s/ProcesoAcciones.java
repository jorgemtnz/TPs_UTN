package ar.edu.utn.d2s;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import ar.edu.utn.d2s.ObservadoresConsulta.ObservadorConsulta;

public class ProcesoAcciones extends ProcesoIndividual {
	// los usuarios con las nuevas acciones a asignar
	private List<Pair<UsuarioTerminal, List<ObservadorConsulta>>> usrAct;
	// los usuarios con las acciones que tenia antes de actualizar
	private List<Pair<UsuarioTerminal, List<ObservadorConsulta>>> usrDeshacer = new ArrayList<Pair<UsuarioTerminal, List<ObservadorConsulta>>>();
	private Integer activaExcepcion;

	public ProcesoAcciones(Administrador admin, Integer activaExcepcion) {
		super(admin);
		this.setActivaExcepcion(activaExcepcion);
	}

	public ProcesoAcciones(Integer resultado, Administrador admin, Integer activaExcepcion) {
		super(resultado, admin);
		this.setActivaExcepcion(activaExcepcion);

	}

	public ProcesoAcciones(Administrador admin, List<Pair<UsuarioTerminal, List<ObservadorConsulta>>> usrAct,
			List<Pair<UsuarioTerminal, List<ObservadorConsulta>>> usrDeshacer) {
		super(admin);
		this.usrAct = usrAct;
		this.usrDeshacer = usrDeshacer;
	}

	public void ejecutar() {
		//ejemplo para entender
		// usuario1 (accionA, accionB)
		// usuario2 (accionA)
		// usuario3 (accionB, accionC)
		try {
			usrAct.stream().forEach(usr -> {
				
				//se eliminan todos los observer que tenia este usuario y la lista quedara como se pide en la actualizacion
				usr.getValue0().eliminarTodosObserver();
				//se recorre la lista de acciones, que son los observers o los suscriptores
				usr.getValue1().stream().forEach(accion -> {

//					UsuarioTerminal usuario = usr.getValue0();
					List<ObservadorConsulta> lista = new ArrayList<ObservadorConsulta>(
							usr.getValue0().getSuscriptores());
        //usr.getValue0()   = obtener un usuario especifico
					Pair<UsuarioTerminal, List<ObservadorConsulta>> usrOk = new Pair<UsuarioTerminal, List<ObservadorConsulta>>(
							usr.getValue0(), lista);
					// agrego las acciones que tenia el usuario, antes de
					// agregar
					// nuevas acciones
					usrDeshacer.add(usrOk);

					try {
					
						// se agregan las nuevas acciones a un usuario
						usr.getValue0().agregarObserver(accion);
					} catch (Exception e) {
						e.printStackTrace();

					}

				});

			});// se hizo solo para simular el error o la cancelacion del
				// usuario
			if (this.getActivaExcepcion() == 1) {
				throw new Error();
			}

		} catch (Error e) {

			this.deshacer();
			this.setResultado(0);
			this.getAdmin().getManejadorErrores().stream().forEachOrdered(elemt -> elemt.manejarError(this));
		}

	}

	public void deshacer() {
		try {
			// recorre la lista de usuarios con las acciones antes de
			// actualizar, y las vuelve a settear en el estado anterior
			usrDeshacer.stream().forEach(usr -> usr.getValue0().setSuscriptores(usr.getValue1()));
		} catch (Exception e) {
			//no se pudo deshacer
			this.setResultado(1);
			throw new Error();
		}
	}

	public List<Pair<UsuarioTerminal, List<ObservadorConsulta>>> getUsrAct() {
		return usrAct;
	}

	public void setUsrAct(List<Pair<UsuarioTerminal, List<ObservadorConsulta>>> usrAct) {
		this.usrAct = usrAct;
	}

	public List<Pair<UsuarioTerminal, List<ObservadorConsulta>>> getUsrDeshacer() {
		return usrDeshacer;
	}

	public void setUsrDeshacer(List<Pair<UsuarioTerminal, List<ObservadorConsulta>>> usrDeshacer) {
		this.usrDeshacer = usrDeshacer;
	}

	public Integer getActivaExcepcion() {
		return activaExcepcion;
	}

	public void setActivaExcepcion(Integer activaExcepcion) {
		this.activaExcepcion = activaExcepcion;
	}

}
