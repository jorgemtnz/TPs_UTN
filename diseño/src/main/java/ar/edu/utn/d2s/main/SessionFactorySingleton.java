package ar.edu.utn.d2s.main;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactorySingleton {

	private static SessionFactory instance;
	public static SessionFactory instance(){
		if(instance == null){
			createSessionFactory();
		}
		return instance;
	}
	
	
	
	private static void createSessionFactory() {
		//https://docs.jboss.org/hibernate/orm/5.0/quickstart/html/#hibernate-gsg-tutorial-basic-config

		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configura desde hibernate.cfg.xml
				.build();
		try {
			instance =  new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			StandardServiceRegistryBuilder.destroy( registry );
			throw e;
		}
	    
	}
	
}
