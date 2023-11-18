package de.fh.dortmund.exceptions;

public class InvalidCarDataException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidCarDataException(String errorMsg) {
		super(errorMsg);
	}

}
