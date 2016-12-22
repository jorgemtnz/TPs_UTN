package ar.edu.utn.d2s.dao;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import ar.edu.utn.d2s.PuntoInteres;
import ar.edu.utn.d2s.RangoHorario;
import ar.edu.utn.d2s.Servicio;

public class PdiRepositorio {

	public PdiRepositorio() {
		super();
	}

	public void save(EntityManager em, PuntoInteres pdi) {
		em.persist(pdi);
	}
	
	public void save(EntityManager em, RangoHorario rango) {
		em.persist(rango);
	}
	
	public void save(EntityManager em, Servicio servicio) {
		em.persist(servicio);
	}
	
	public List<PuntoInteres> obtenerPdis(Session session) {
		//http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#criteria
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<PuntoInteres> criteria = criteriaBuilder.createQuery(PuntoInteres.class);
		Root<PuntoInteres> rootEntry = criteria.from(PuntoInteres.class);
		List<PuntoInteres> pdis = session.createQuery(
											criteria.select(rootEntry)
										).getResultList();
		return pdis;
	}
	
	public List<PuntoInteres> obtenerPdis(Session session, String nombres) {
		//Cargo los valores del queryString en una lista
		String [] itemsNombres = nombres.split(",");
		List<String> listaNombres = Arrays.asList(itemsNombres);
		
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<PuntoInteres> criteria = criteriaBuilder.createQuery(PuntoInteres.class);
		Root<PuntoInteres> rootEntry = criteria.from(PuntoInteres.class);
		
		//Lista de predicados para operador LIKE
		//Tantos LIKEs como cantidad de nombres que tenga en la lista
		List<Predicate> predicates = new LinkedList<Predicate>();		
		listaNombres.stream().forEach(elem -> {
			predicates.add(criteriaBuilder.like(rootEntry.get("nombre"), "%" + elem + "%"));
		});
		
		//La lista de LIKEs son agregados como OR (where nombre1 LIKE %x% OR nombre2 LIKE %y%..., etc)
		//Convierto List<Predicate> a Predicate[]
		criteria.where(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
		
		List<PuntoInteres> pdis = session.createQuery(criteria).getResultList();
		return pdis;
	}
	
	public PuntoInteres obtenerPdi(EntityManager em, int id){
		return em.find(PuntoInteres.class, id);
	}
}
