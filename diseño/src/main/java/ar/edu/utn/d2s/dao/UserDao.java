package ar.edu.utn.d2s.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import ar.edu.utn.d2s.Repositorio;
import ar.edu.utn.d2s.UsuarioTerminal;

public class UserDao {
		
	public UserDao()
	{
		super();
	}
	
	public void save(EntityManager em, UsuarioTerminal usuario) {
		em.persist(usuario);
	}

	public void update(EntityManager em, UsuarioTerminal usuario) {

		em.persist(usuario);
	}
	
	public void save(EntityManager em, Repositorio repositorio) {
		em.persist(repositorio);
	}

	 public UsuarioTerminal obtenerUsuario(Session session, String nombre) {
		 UsuarioTerminal usuario;
		 System.out.println("obtener usuario");
	 CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
	 CriteriaQuery<UsuarioTerminal> criteria = criteriaBuilder.createQuery(UsuarioTerminal.class);	
	 Root<UsuarioTerminal> root = criteria.from(UsuarioTerminal.class);
	 criteria.where(criteriaBuilder.equal(root.get("nombre"), nombre));
	 System.out.println("se hace createQuery");
	 
	try{
	  usuario = session.createQuery(criteria).getSingleResult();
	}catch(NoResultException e){ 
	
		 return null;
	 }
	return usuario;
	 }

}
