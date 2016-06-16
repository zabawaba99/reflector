package com.zabawaba.reflector;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Provides a set of methods to manipulate {@link Constructor}
 *
 * @author Steven Berlanga
 * @since 0.2.1
 */
public class Constructors {

	private static Filter<Constructor<?>> ALL_CONSTRUCTORS = new Filter<Constructor<?>>() {
		public boolean apply(Constructor<?> constructor) {
			return true;
		}
	};

	private Object obj;

	private Constructors(Object obj) {
		this.obj = obj;
	}

	/**
	 * @return The default constructor of the class.
	 *
	 * @throws NoSuchMethodException
	 *             If no default constructor is declared on the class.
	 */
	public ReflectorConstructor get() throws NoSuchMethodException {
		return get(new Class[0]);
	}

	/**
	 * Finds a constructor that matches the specified parameterTypes. Private, public and package proected
	 * constructors will be returned.
	 *
	 * @param parameterTypes the parameter types for the constructor to find.
	 * @return the constructor object that matches the specified parameterTypes
	 * @throws NoSuchMethodException
	 * 				If there is no constructor with the given parameter list.
	 */
	public ReflectorConstructor get(Class<?>... parameterTypes) throws NoSuchMethodException {
		ReflectorConstructor constructor = null;
		for (ReflectorConstructor c : list()) {
			if (Arrays.deepEquals(parameterTypes, c.getConstructor().getParameterTypes())) {
				constructor = c;
			}
		}
		if (constructor == null) {
			throw new NoSuchMethodException("<init>");
		}
		return constructor;
	}

	/**
	 * Gets all Constructors for the given class and all of its superclasses
	 *
	 * @return A {@link HashSet} containing all of the constructors of object
	 *         the Constructors instance was instantiated with
	 */
	public HashSet<ReflectorConstructor> list() {
		return list(ALL_CONSTRUCTORS);
	}

	/**
	 * Gets all Constructors for the given class and all of its superclasses
	 * where {@link Filter#apply(Object)} returns true
	 *
	 * @param filter
	 *            The filter that determines whether or not a method is added to
	 *            the return. If {@code null} all methods will be returned.
	 * @return A {@link HashSet} containing all of the constructors of object
	 *         the Constructors instance was instantiated with and that meet the
	 *         filtering criteria
	 */
	public HashSet<ReflectorConstructor> list(Filter<Constructor<?>> filter) {
		HashSet<ReflectorConstructor> constructors = new HashSet<ReflectorConstructor>();

		if (filter == null) {
			filter = ALL_CONSTRUCTORS;
		}

		for (Constructor<?> c : obj.getClass().getDeclaredConstructors()) {
			c.setAccessible(true);
			if (filter.apply(c)) {
				constructors.add(new ReflectorConstructor(obj, c));
			}
		}
		return constructors;
	}

	/**
	 * Builds a new Constructors object with the context of the object given
	 *
	 * @param obj
	 *            The object whos constructors you want reflect over
	 * @return A newly created Constructors object
	 */
	public static Constructors forObj(Object obj) {
		return new Constructors(obj);
	}
}
