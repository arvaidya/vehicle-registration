package org.restful.rego.exception;

public class PersonExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3958522244050525831L;

	public PersonExistsException(String errorMessage) {
		super(errorMessage);
	}

}
