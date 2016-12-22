package ar.edu.utn.d2s.dao;

import java.math.BigInteger;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.joda.time.LocalDate;

import ar.edu.utn.d2s.InformacionBusqueda;
import ar.edu.utn.d2s.PuntoInteres;
import ar.edu.utn.d2s.ObservadoresConsulta.GuardarBusqueda;

public class BusquedasRepositorio {

	private final String stringJoin =
			"SELECT i.id, i.CANT_RESULTADOS, i.FECHA, i.NOMBRE_USUARIO_TERMINAL, i.TEXTO_BUSQUEDA, i.TIEMPO "
			+ "FROM GUARDAR_BUSQUEDAS AS g "
			+ "INNER JOIN GUARDAR_BUSQUEDAS_INFORMACION_BUSQUEDAS gi "
				+ "ON gi.GuardarBusqueda_idAbstractObservador =  g.idAbstractObservador "
			+ "INNER JOIN INFORMACION_BUSQUEDAS i "
				+ "ON i.id = gi.listaInfo_id ";
	
	//Sin campos, busqueda por default
	public List<InformacionBusqueda> obtenerBusquedas(Session session) {
//		//http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#criteria
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<InformacionBusqueda> criteria = criteriaBuilder.createQuery(InformacionBusqueda.class);
		Root<GuardarBusqueda> rootEntry = criteria.from(GuardarBusqueda.class);
		CriteriaQuery<InformacionBusqueda> all = criteria.select(rootEntry.get("listaInfo"));	
		List<InformacionBusqueda> resultList = session.createQuery(all).getResultList();
		return resultList;
	}
	
	//listado completo (se llenan todos los parametros, osea usuario, y las 2 fechas)
	public List<InformacionBusqueda> obtenerBusquedas(Session session, LocalDate fechaDesde, LocalDate fechaHasta, String nombreBuscado) {
		String sqlString = stringJoin 
							+ "WHERE (i.FECHA BETWEEN '" + fechaDesde.toString() + "' AND '" + fechaHasta.toString() + "' )"
							+ "AND	(i.NOMBRE_USUARIO_TERMINAL LIKE '%" + nombreBuscado + "%')";
		return this.getResultQuery(session, sqlString);
	}
	
	//listado sin usuario (solo fechas)
	public List<InformacionBusqueda> obtenerBusquedas(Session session, LocalDate fechaDesde, LocalDate fechaHasta) {
		String sqlString = stringJoin 
							+ "WHERE (i.FECHA BETWEEN '" + fechaDesde.toString() + "' AND '" + fechaHasta.toString() + "')";
		return this.getResultQuery(session, sqlString);		
	}
	
	//listado solo fecha hasta, sin user
	public List<InformacionBusqueda> obtenerBusquedas(Session session, LocalDate fechaHasta) {
		String sqlString = stringJoin
							+ "WHERE (i.FECHA <= '" + fechaHasta.toString() + "' )";
		return this.getResultQuery(session, sqlString);	
	}
	
	//listado solo fecha hasta, con user
	public List<InformacionBusqueda> obtenerBusquedas(Session session, LocalDate fechaHasta, String nombreBuscado) {
		String sqlString = stringJoin
							+ "WHERE (i.FECHA <= '" + fechaHasta.toString() + "' )"
							+ "AND (i.NOMBRE_USUARIO_TERMINAL LIKE '%" + nombreBuscado + "%')";
		return this.getResultQuery(session, sqlString);
	}		
		
	//listado sin fechas, solo user
	public List<InformacionBusqueda> obtenerBusquedas(Session session, String nombreBuscado) {
		String sqlString = stringJoin
							+ "WHERE (i.NOMBRE_USUARIO_TERMINAL LIKE '%" + nombreBuscado + "%')";
		return this.getResultQuery(session, sqlString);	
	}
	
	public List<PuntoInteres> obtenerPdisXBusqueda(EntityManager em, int id){ //obtiene una busqueda sola segun el id
		InformacionBusqueda unaBusqueda =  em.find(InformacionBusqueda.class, id);
		return (unaBusqueda.getPdisDevueltos());
	}
	
	public void save(EntityManager em, InformacionBusqueda informacion) {
		em.persist(informacion);
	}
	
	private List<InformacionBusqueda> getResultQuery(Session session, String sqlString)
	{
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = session.createNativeQuery(sqlString).getResultList();
		List<InformacionBusqueda> infoList = new LinkedList<InformacionBusqueda>();
		resultList.stream().forEach(elem -> {
			InformacionBusqueda info = new InformacionBusqueda();
			info.setId((Integer)elem[0]);
			info.setCantResultados((Integer)elem[1]);
			info.setFecha(new LocalDate((Date)elem[2]));
			info.setNombreUsuarioTerminal((String)elem[3]);
			info.setTextoBusqueda((String)elem[4]);
			info.setTiempoBusqueda(((BigInteger)elem[5]).longValue());
			infoList.add(info);
		});		
		return infoList;
	}
}
