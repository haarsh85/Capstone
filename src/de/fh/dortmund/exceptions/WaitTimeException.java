package de.fh.dortmund.exceptions;

public class WaitTimeException extends Exception {

	private static final long serialVersionUID = 1L;

	public WaitTimeException(String errorMsg) {
		super(errorMsg);
	}

}
