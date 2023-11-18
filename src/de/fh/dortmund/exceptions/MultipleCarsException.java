package de.fh.dortmund.exceptions;

public class MultipleCarsException extends Exception {

	private static final long serialVersionUID = 1L;

	public MultipleCarsException(String errorMsg) {
		super(errorMsg);
	}
}
