/**
 * SEPR project inherited from DRTN.
 * Any changes are marked by preceding comments.
 * 
 * Executables availabe at: https://seprated.github.io/Assessment4/Executables.zip
**/
package io.github.teamfractal.exception;

import io.github.teamfractal.entity.enums.ResourceType;

public class NotEnoughResourceException extends RuntimeException {
	/**
	 * Constructs an <code>NotEnoughResourceException</code> with no
	 * detail message.
	 */
	public NotEnoughResourceException() {
		super();
	}

	public NotEnoughResourceException(ResourceType resource, int required, int actual) {
		super("Not enough resource (" + resource.toString() + "). \n" +
				"Required: " + String.valueOf(required) + ", \n" +
				"Actual  : " + String.valueOf(actual));
	}

	public NotEnoughResourceException(String details, ResourceType resource, int required, int actual) {
		super("Not enough resource (" + resource.toString() + "). \n" +
				"Required: " + String.valueOf(required) + ", \n" +
				"Actual  : " + String.valueOf(actual) + " \n" +
				"Details : " + details);
	}
}
