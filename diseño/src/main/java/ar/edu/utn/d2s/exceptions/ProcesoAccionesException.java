package ar.edu.utn.d2s.exceptions;

public class ProcesoAccionesException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProcesoAccionesException() {
		super();
		
	}

	public ProcesoAccionesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

	public ProcesoAccionesException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ProcesoAccionesException(String message) {
		super(message);
	
	}

	public ProcesoAccionesException(Throwable cause) {
		super(cause);
		
	}
	
}
