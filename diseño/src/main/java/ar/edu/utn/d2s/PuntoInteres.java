package ar.edu.utn.d2s;

import org.joda.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class PuntoInteres {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String nombre;
	private String barrio;
	private int codigoPostal;
	private String dpto;
	@Embedded
	protected Coordenada coordenada = new Coordenada();
	private int numero;
	private String pais;
	private int piso;
	private String provincia;
	private int unidad;
	private int IdPuntoInteres;
	protected final double RADIO_CERCANIA = 0.5;
	private String callePrincipal;
    private int numeroDireccion;
    
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructor+++++++++++++++++++++++++++++++++++++++++++
	public PuntoInteres(String nombre) {
		super();
		this.nombre = nombre;		
	}

	public PuntoInteres() {
		super();
	}
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++


	abstract public boolean contieneTexto(String unTexto);
	
	abstract public boolean calculoCercania(double latitud, double longitud);

	abstract public boolean calculoDisponibilidad(LocalDateTime unaFecha, Object unValor);
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accessor+++++++++++++++++++++++++++++++++++++++++++
		public int getNumeroDireccion() {
			return numeroDireccion;
		}

		public void setNumeroDireccion(int numeroDireccion) {
			this.numeroDireccion = numeroDireccion;
		}

		public String getCallePrincipal() {
			return callePrincipal;
		}

		public void setCallePrincipal(String callePrincipal) {
			this.callePrincipal = callePrincipal;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getBarrio() {
			return barrio;
		}

		public void setBarrio(String barrio) {
			this.barrio = barrio;
		}

		public int getCodigoPostal() {
			return codigoPostal;
		}

		public void setCodigoPostal(int codigoPostal) {
			this.codigoPostal = codigoPostal;
		}

		public String getDpto() {
			return dpto;
		}

		public void setDpto(String dpto) {
			this.dpto = dpto;
		}

		public Coordenada getCoordenada() {
			return coordenada;
		}

		public void setCoordenada(Coordenada coordenada) {
			this.coordenada = coordenada;
		}

		public int getNumero() {
			return numero;
		}

		public void setNumero(int numero) {
			this.numero = numero;
		}

		public String getPais() {
			return pais;
		}

		public void setPais(String pais) {
			this.pais = pais;
		}

		public int getPiso() {
			return piso;
		}

		public void setPiso(int piso) {
			this.piso = piso;
		}

		public String getProvincia() {
			return provincia;
		}

		public void setProvincia(String provincia) {
			this.provincia = provincia;
		}

		public int getUnidad() {
			return unidad;
		}

		public void setUnidad(int unidad) {
			this.unidad = unidad;
		}
	
	
		public double getRADIO_CERCANIA() {
			return RADIO_CERCANIA;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getIdPuntoInteres() {
			return IdPuntoInteres;
		}

		public void setIdPuntoInteres(int idPuntoInteres) {
			IdPuntoInteres = idPuntoInteres;
		}	
				
		public String getDireccion()
		{
			String direccion = null;
			direccion = (this.callePrincipal == null || this.callePrincipal.isEmpty()) ? "" : this.callePrincipal + " ";
			direccion += (this.numeroDireccion == 0) ? "" : this.numeroDireccion + " ";
			direccion += (this.piso == 0) ? "" : this.piso + " ";
			direccion += (this.dpto == null || this.dpto.isEmpty()) ? "" : this.dpto;
			return direccion;
		}
		
		public String getSimpleClassName()
		{
			String fullClassName = this.getClass().toString();
			String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
			return simpleClassName;	
		}
		
}
