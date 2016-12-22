package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ar.edu.utn.d2s.Mockup_SE_Cgp.CentroDTO;
import ar.edu.utn.d2s.Mockup_SE_Cgp.Mockup_SE_Cgp;

@Entity
public class OrigenCgpExterno extends AbstractOrigenes implements PDIExterno {
	//esta es la razon
	//http://stackoverflow.com/questions/25422004/org-hibernate-mapping-unionsubclass-cannot-be-cast-to-org-hibernate-mapping-root

	@Transient
	private ConvertidorCGP convertidorCgp = new ConvertidorCGP();
	@Transient
	private Mockup_SE_Cgp se_Cgp = new Mockup_SE_Cgp();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PuntoInteres> pdis = new LinkedList<PuntoInteres>();

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public OrigenCgpExterno() {
		super();
	}
	

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++

	public List<PuntoInteres> buscarPdis(String texto) {
		this.agregarCGPs(this.convertidorCgp.convertirACgp(this.consultarCgpSerExt(texto)));
		return pdis;
	}

	private void agregarCGPs(List<Cgp> listCgps) {
		listCgps.stream().forEach(cgp -> this.getPdis().add(cgp));
	}
	
	private List<CentroDTO> consultarCgpSerExt(String unTexto) {		
		return se_Cgp.obtenerCgps(unTexto);
	}


	@Override
	public String toString() {
		return "OrigenCgpExterno []";
	}	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++

	public ConvertidorCGP getConvertidorCgp() {
		return convertidorCgp;
	}


	public void setConvertidorCgp(ConvertidorCGP convertidorCgp) {
		this.convertidorCgp = convertidorCgp;
	}


	public Mockup_SE_Cgp getSe_Cgp() {
		return se_Cgp;
	}


	public void setSe_Cgp(Mockup_SE_Cgp se_Cgp) {
		this.se_Cgp = se_Cgp;
	}

	public List<PuntoInteres> getPdis() {
		return pdis;
	}

	public void setPdis(List<PuntoInteres> pdis) {
		this.pdis = pdis;
	}
	


}
