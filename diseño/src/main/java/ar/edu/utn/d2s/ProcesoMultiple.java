package ar.edu.utn.d2s;

import java.util.LinkedList;

public class ProcesoMultiple implements Proceso {
	
	private LinkedList<Proceso> procesos;
	
	public ProcesoMultiple(LinkedList<Proceso> procesos) {
		super();
		this.procesos = procesos;
	}	
	
	public ProcesoMultiple() {
		super();	
	}

	public void ejecutar() {
		this.getProcesos().stream().forEach(proceso -> proceso.ejecutar());
	}

	public LinkedList<Proceso> getProcesos() {
		return procesos;
	}

	public void setProcesos(LinkedList<Proceso> procesos) {
		this.procesos = procesos;
	}	
	
}
