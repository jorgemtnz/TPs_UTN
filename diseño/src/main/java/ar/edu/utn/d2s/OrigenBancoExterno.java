package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;	
import javax.persistence.Transient;

import ar.edu.utn.d2s.Mockup_SE_Banco.Mockup_SE_Banco;

@Entity
public class OrigenBancoExterno extends AbstractOrigenes implements PDIExterno {
	

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PuntoInteres> pdis = new LinkedList<PuntoInteres>();
	@Transient
     private ConvertidorBancoExterno convertidorBanco = new ConvertidorBancoExterno();
	@Transient
	private Mockup_SE_Banco se_Bco = new Mockup_SE_Banco();
	@Transient
	private List<String> serviciosPosible = new LinkedList <String>();


	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++	
	public OrigenBancoExterno() {	
		super();
	}	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++
	
	//Busca en el servicio externo por nombre y por cada servicio definido  
	
	public List<PuntoInteres> buscarPdis (String nombreBanco){
		pdis.addAll(this.convertidorBanco.convertirJSONaPdi(this.consultarBancoSerExt(se_Bco, nombreBanco)));
		return pdis;
	}


	public String consultarBancoSerExt(Mockup_SE_Banco se_Bco, String nombreBanco) {		
		return this.se_Bco.obtenerBancos(nombreBanco, "Constant");		
	}
	

	@Override
	public String toString() {
		return "OrigenBancoExterno []";
	}
	
	public List<PuntoInteres> buscarPdi (String texto){
		return null;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++

	public ConvertidorBancoExterno getConvertidorBanco() {
		return convertidorBanco;
	}

	public void setConvertidorBanco(ConvertidorBancoExterno convertidorBanco) {
		this.convertidorBanco = convertidorBanco;
	}

	public Mockup_SE_Banco getSe_Bco() {
		return se_Bco;
	}

	public void setSe_Bco(Mockup_SE_Banco se_Bco) {
		this.se_Bco = se_Bco;
	}

	public List<String> getServiciosBco() {
		return serviciosPosible;
	}

	public void setServiciosBco(List<String> serviciosBco) {
		this.serviciosPosible = serviciosBco;
	}
	
	public List<PuntoInteres> getPdis() {
		return pdis;
	}

	public void setPdis(List<PuntoInteres> pdis) {
		this.pdis = pdis;
	}
	

}
