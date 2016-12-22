package ar.edu.utn.d2s;

public class Reintentar implements ManejadorDeError {
	
	private Integer reintentos;
		
	
	public Reintentar() {
		super();
		
	}

	public Reintentar(Integer reintentos) {
		super();
		this.reintentos = reintentos;		
	}

	@Override
//	mientras siga dando error y falten reintentos 
	public void manejarError(ProcesoIndividual procesoIndividual) {
		for(int i= 0;(i < reintentos  );i++){
			if(procesoIndividual.getResultado()==0){
				break;
			}
			procesoIndividual.ejecutar();
		}

	}

	public Integer getReintentos() {
		return reintentos;
	}



	public void setReintentos(Integer reintentos) {
		this.reintentos = reintentos;
	}



}
