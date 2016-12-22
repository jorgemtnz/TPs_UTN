package ar.edu.utn.d2s.rest;



import javax.persistence.EntityManager;

import spark.Request;
import spark.Response;

@FunctionalInterface
public interface RouteWithSession {

	Object handle(Request request, Response response, EntityManager entityManager) throws Exception;
	
}
