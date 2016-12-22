package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.joda.time.LocalDate;
import ar.edu.utn.d2s.ObservadoresConsulta.ObservadorConsulta;
import ar.edu.utn.d2s.ObservadoresConsulta.AbstractObservadorConsulta;
import ar.edu.utn.d2s.exceptions.ObserverException;

@Entity
@Table (name = "USUARIOS")
public class UsuarioTerminal  {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUsuario;
    @Column (name = "NOMBRE")
	private String nombre;
    @Column (name = "PASWORD")
    private String password;
    @Column (name = "codigoEncoding")
    private byte[] saltEncoding;
    @OneToMany(targetEntity = AbstractObservadorConsulta.class)
    
    @Column (name = "ACCIONES")
	private List<ObservadorConsulta> suscriptores;
    @OneToOne   
    @JoinColumn(name = "id_repositorio")
	private Repositorio repositorio;
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public UsuarioTerminal(String nombre, Repositorio repositorio) {
		super();
		this.nombre = nombre;
		this.suscriptores = new LinkedList<ObservadorConsulta>();
		this.repositorio = repositorio;
	}
	
	public UsuarioTerminal(String nombre) {
		super();
		this.nombre = nombre;
		this.suscriptores = new LinkedList<ObservadorConsulta>();
	}


	public UsuarioTerminal() {
	super();
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++metodos+++++++++++++++++++++++++++++++++++++++++++
	public List<PuntoInteres> buscarTexto(String texto)
	{		
		List<PuntoInteres> ultimoResultadoBusqueda;
		long tiempoDeInicioDeBusqueda;
		long tiempoDeFinalizacionDeBusqueda;
				
		tiempoDeInicioDeBusqueda = System.currentTimeMillis();
		ultimoResultadoBusqueda = this.repositorio.buscarPdiPorTexto(texto);
		tiempoDeFinalizacionDeBusqueda = System.currentTimeMillis();
		
		long ultimoTiempoBusquedaMilisegundos = tiempoDeFinalizacionDeBusqueda - tiempoDeInicioDeBusqueda;
		
		InformacionBusqueda info = new InformacionBusqueda();
		
		info.setCantResultados(ultimoResultadoBusqueda.size());
		info.setNombreUsuarioTerminal(this.getNombre());
		info.setTextoBusqueda(texto);
		info.setTiempoBusqueda(ultimoTiempoBusquedaMilisegundos);
		info.setFecha(LocalDate.now());
		info.setPdisDevueltos(ultimoResultadoBusqueda);
		notificarObservers(info);

		return ultimoResultadoBusqueda;
	}
	
	public void notificarObservers(InformacionBusqueda paramActualizar)
	{
		this.suscriptores.stream().forEach(observer -> 
										{
											try{ observer.actualizar(paramActualizar);} 
											catch(Exception e){
//												e.printStackTrace();
											}
										});
	}

	public void agregarObserver(ObservadorConsulta o) throws ObserverException
	{
		if(!this.suscriptores.contains(o))
		{
			this.suscriptores.add(o);
		}
	}

	public void eliminarObserver(ObservadorConsulta o) throws ObserverException
	{
		if(this.suscriptores.contains(o))
		{
			this.suscriptores.remove(o);
		}
	}
	
	public void eliminarTodosObserver() {
		this.getSuscriptores().removeAll(getSuscriptores());
	}
	
	public void guardarUltimaBusqueda(Busqueda resultados){

	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioTerminal other = (UsuarioTerminal) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor+++++++++++++++++++++++++++++++++++++++++++
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public List<ObservadorConsulta> getSuscriptores() {
		return suscriptores;
	}

	public void setSuscriptores(List<ObservadorConsulta> suscriptores) {
		this.suscriptores = suscriptores;
	}

	public Repositorio getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(Repositorio repositorio) {
		this.repositorio = repositorio;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getSaltEncoding() {
		return saltEncoding;
	}

	public void setSaltEncoding(byte[] salt) {
		this.saltEncoding = salt;
	}



	
	
}
