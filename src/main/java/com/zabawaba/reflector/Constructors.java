package com.zabawaba.reflector;

import java.lang.reflect.Constructor;
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

	public Constructors(Object obj) {
		this.obj = obj;
	}

	/**
	 * Get a constructor that has the given name
	 *
	 * @param constructorName
	 *            The name of the constructor to look for
	 * @return The constructor that has the given name
	 *
	 * @throws NoSuchMethodException
	 *             If no constructor exists with the provided name
	 */
	public ReflectorConstructor get(String constructorName) throws NoSuchMethodException {
		ReflectorConstructor constructor = null;
		for (ReflectorConstructor c : list()) {
			String className = c.getConstructor().getDeclaringClass().getSimpleName();
			if (className.equals(constructorName)) {
				constructor = c;
			}
		}
		if (constructor == null) {
			throw new NoSuchMethodException(constructorName);
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

		Class<?> currentClass = obj.getClass();
		while (currentClass != null) {
			for (Constructor<?> c : currentClass.getDeclaredConstructors()) {
				c.setAccessible(true);
				if (filter.apply(c)) {
					constructors.add(new ReflectorConstructor(obj, c));
				}
			}
			currentClass = currentClass.getSuperclass();
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
