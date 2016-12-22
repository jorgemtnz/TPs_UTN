package ar.edu.utn.d2s.rest;

import static spark.Spark.post;

import java.io.IOException;
import java.io.StringWriter;

import org.hibernate.Session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ar.edu.utn.d2s.SessionFactorySingleton;

public class AbstractController {

	public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }
	
	 public  void postConSession(final String path, final RouteWithSession route){
		 
		 post(path,(request, response)->{
			 Session session = SessionFactorySingleton.instance().openSession();			 
	         try{
	        	 return route.handle(request, response,session);	 
	         } finally {
	        	 session.close();
	         }
			 
			 
		 });
		 
	 }
	
}
