package ar.edu.utn.d2s.exceptions;

public class ObserverException extends Exception {

	private static final long serialVersionUID = 1L;

	public ObserverException() {
		super();
		
	}

	public ObserverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public ObserverException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public ObserverException(String message) {
		super(message);
		
	}

	public ObserverException(Throwable cause) {
		super(cause);
	
	}
}