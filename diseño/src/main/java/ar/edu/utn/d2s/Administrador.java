package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;

/**
 * @author CeciliaGS
 * @version 1.0
 * @created 08-may-2016 17:22:17
 */
public class Administrador {

	private Repositorio m_Repositorio = new Repositorio();
	private Proceso proceso;
	private LinkedList<ManejadorDeError> manejadorErrores = new LinkedList<ManejadorDeError>();
	
//	// la idea es que se pongan aca los diferentes origenes de servicios externos
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++	
	/**
	 * 
	 */
	public Administrador() {
		super();
		// TODO Auto-generated constructor stub
	}	

	/**	
	 * @param m_Repositorio	
	 */
	public Administrador(Repositorio m_Repositorio) {
		super();
		this.m_Repositorio = m_Repositorio;
	}
		
	public Administrador(Repositorio m_Repositorio, Proceso proceso, LinkedList<ManejadorDeError> manejadorErrores) {
		super();
		this.m_Repositorio = m_Repositorio;
		this.proceso = proceso;
		this.manejadorErrores = manejadorErrores;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++
	
		/**
	 * 
	 * @param idPuntoInteres
	 */
	public void bajaPuntoInteres(int idPuntoInteres){
		this.getM_Repositorio().eliminarPdi(idPuntoInteres);
	}

	/**
	 * 
	 * @param textoLibre
	 */
	public List<PuntoInteres> consultaPuntoInteres(String textoLibre){
		
		return this.getM_Repositorio().consultaPdiLocal(textoLibre);
		
	}

	public void crearPuntoInteres(PuntoInteres nuevoPdi){
		this.getM_Repositorio().agregarPdi(nuevoPdi);
	}

	/**
	 * 
	 * @param idPuntoInteres
	 */
	//hay que ver bien como es la cuestion del ID, para saber si se le manda el id y pdi o no
	public void modificacionPuntoInteres(int idPuntoInteres, PuntoInteres unPdi){
	this.getM_Repositorio().modificarPdi(unPdi);	
	
	}


	public Repositorio getM_Repositorio() {
		return m_Repositorio;
	}

	@Override
	public String toString() {
		return "Administrador [m_Repositorio=" + m_Repositorio + "]";
	}
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++

	public void setM_Repositorio(Repositorio m_Repositorio) {
		this.m_Repositorio = m_Repositorio;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public LinkedList<ManejadorDeError> getManejadorErrores() {
		return manejadorErrores;
	}

	public void setManejadorErrores(LinkedList<ManejadorDeError> manejadorErrores) {
		this.manejadorErrores = manejadorErrores;
	}
	
	
}//end Administrador