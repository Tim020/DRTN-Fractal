/**
 * SEPR project inherited from DRTN.
 * Any changes are marked by preceding comments.
 * 
 * Executables availabe at: https://seprated.github.io/Assessment4/Executables.zip
**/
package io.github.teamfractal.entity.enums;

import io.github.teamfractal.util.StringUtil;

/**
 * The possible Resource Type in the game.
 */
public enum ResourceType {
	ENERGY,
	ORE,
	FOOD,
	ROBOTICON,
	CUSTOMISATION,
	Unknown;

	public boolean isValidResource() {
		return this == ENERGY || this == ORE || this == FOOD || this == ROBOTICON;
	}

	@Override
	public String toString() {
		switch(this) {
			case ENERGY:
			case ORE:
			case FOOD:
			case ROBOTICON:
			case CUSTOMISATION:
				return StringUtil.Capitalise(super.toString().toLowerCase());

			case Unknown:
				return "<Unknown>";
		}

		return "<Unknown type: " + super.toString() + ">";
	}
}


