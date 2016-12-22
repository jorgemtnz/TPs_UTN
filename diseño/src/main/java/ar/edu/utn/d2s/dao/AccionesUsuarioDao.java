package ar.edu.utn.d2s.dao;

import javax.persistence.EntityManager;

import ar.edu.utn.d2s.ObservadoresConsulta.ObservadorConsulta;

public class AccionesUsuarioDao {

	public void save(EntityManager em, ObservadorConsulta observer) {
		em.persist(observer);
	}
}
