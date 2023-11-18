package de.fh.dortmund.exceptions;

public class LocationNotAvailableException extends Exception {

	private static final long serialVersionUID = 1L;

	public LocationNotAvailableException(String errorMsg) {
		super(errorMsg);
	}

}
