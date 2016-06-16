package com.zabawaba.reflector;

/**
 * Determines a true or false value for a given input. For example, a
 * PublicFieldFilter might implement Filter<Field>, and return true for any
 * field who's modifiers contain 'public'.
 * 
 * @author Steven Berlanga
 * @since 0.2.0
 */
public interface Filter<T> {
	/**
	 * Applies the filter to the given object
	 * 
	 * @param object
	 *            The object that the filter should be applied to
	 * @return true if the object passes through the filter, false otherwise.
	 */
	public boolean apply(T object);
}
