package org.restful.rego.exception;

public class VehicleExistsException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6877916122599579522L;

	public VehicleExistsException(String errorMessage) {
		super(errorMessage);
	}

}
