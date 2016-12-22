package ar.edu.utn.d2s.exceptions;

public class UnaExceptionMuyClaraYPuntual extends Exception {

	/**
	 * Una Exception propia :)
	 */
	private static final long serialVersionUID = 1L;

	public UnaExceptionMuyClaraYPuntual() {
		super();
		
	}

	public UnaExceptionMuyClaraYPuntual(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public UnaExceptionMuyClaraYPuntual(String message, Throwable cause) {
		super(message, cause);
	
	}

	public UnaExceptionMuyClaraYPuntual(String message) {
		super(message);
	
	}

	public UnaExceptionMuyClaraYPuntual(Throwable cause) {
		super(cause);
	
	}
}