package ar.edu.utn.d2s.rest;



import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.hibernate.Session;
import ar.edu.utn.d2s.main.SessionFactorySingleton;


@FunctionalInterface
public interface SessionTrasaction {

	void run( EntityManager entityManager) throws Exception;
	
	static void withTransaction( SessionTrasaction command){
		Session session = SessionFactorySingleton.instance().openSession();
		EntityTransaction tx = session.beginTransaction();
		
		try {
			command.run(session);	
		} catch (Exception ex){
			ex.printStackTrace();
			tx.rollback();
		}
		tx.commit();		
		
	}
	
}
