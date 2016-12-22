package ar.edu.utn.d2s;

public abstract class ProcesoIndividual implements Proceso {
	
	private Integer resultado;	
	private Administrador admin;
	
	public ProcesoIndividual(Administrador admin) {
		super();
		this.admin = admin;
		this.setResultado(1);		
	}	
	
	public ProcesoIndividual(Integer resultado, Administrador admin) {
		super();
		this.resultado = resultado;
		this.admin = admin;
	}



	public abstract void ejecutar();

	public Integer getResultado() {
		return resultado;
	}

	public void setResultado(Integer resultado) {
		this.resultado = resultado;
	}

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}
	
	

}
