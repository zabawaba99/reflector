package com.zabawaba.reflector;

import java.lang.reflect.Field;

/**
 * Wraps {@link Field} and provides some utility methods
 * 
 * @author zabawaba99
 */
public class ReflectorField {
	private Object obj;
	private Field field;

	ReflectorField(Object obj, Field field) {
		this.obj = obj;
		this.field = field;
	}

	/**
	 * Gets the {@link Field} that is being wrapped
	 * 
	 * @return The {@link Field} that is being wrapped
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Gets the field's value
	 * 
	 * @return The Object value of the field. Returns {@code null} if the field
	 *         is null or there was an exception getting the field
	 */
	public Object getValue() {
		try {
			return field.get(this.obj);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Checks if the given object is equal to the value of the field
	 * 
	 * @param obj
	 *            The object to compare to
	 * @return true if {@link #getValue()} is equal to the provided object,
	 *         false otherwise
	 */
	public boolean valueEquals(Object obj) {
		if (obj != null) {
			// if object is not null, check if equal
			return obj.equals(this.obj);
		}
		// else they both can be null
		return obj == null && this.obj == null;
	}
}
