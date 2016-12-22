package ar.edu.utn.d2s.exceptions;

public class RepositorioException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RepositorioException() {
		super();
	
	}

	public RepositorioException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

	public RepositorioException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public RepositorioException(String message) {
		super(message);
		
	}

	public RepositorioException(Throwable cause) {
		super(cause);
	
	}	
	
}
