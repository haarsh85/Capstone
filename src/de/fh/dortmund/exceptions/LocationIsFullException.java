package de.fh.dortmund.exceptions;

public class LocationIsFullException extends Exception {

	private static final long serialVersionUID = 1L;

	public LocationIsFullException(String errorMsg) {
		super(errorMsg);
	}

}
