package com.zabawaba.reflector;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A utility that provides a different interface for reflection.
 * 
 * @author zabawaba
 */
public class Methods {

	/**
	 * Predefined {@link Filter} where {@link Filter#apply(Object)} will return
	 * true when given a method who's {@link Modifier}s contain
	 * {@link Modifier#PUBLIC}
	 */
	public static Filter<Method> PUBLIC_METHODS = new Filter<Method>() {
		public boolean apply(Method method) {
			return (method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC;
		}
	};

	
	private static Filter<Method> ALL_METHODS = new Filter<Method>() {
		public boolean apply(Method method) {
			return true;
		}
	};
	
	private Object obj;
	private Methods(Object obj) {
		this.obj = obj;
	}

	/**
	 * Get a method that has the given name
	 * 
	 * @param methodName
	 *            The name of the method to look for
	 * @return The method that has the given name
	 * 
	 * @throws NoSuchMethodException
	 *             If no method exists with the provided name
	 */
	public ReflectorMethod get(String methodName) throws NoSuchMethodException {
		Method method = getMethod(obj.getClass(), methodName);
		if (method == null) {
			throw new NoSuchMethodException(methodName);
		}
		return new ReflectorMethod(obj, method);
	}

	/**
	 * Builds a new Methods object with context of the object you want to Reflect
	 * over
	 * 
	 * @param obj
	 *            The object whos methods you want to Reflect over
	 * @return A newly created Methods object
	 */
	public static Methods forObj(Object obj) {
		return new Methods(obj);
	}

	/**
	 * Get a method with the given name in the given class. If the method you
	 * are looking for is overloaded, you may not get the method you expect. If
	 * you are looking for a particular overloaded method, you should use
	 * {@link #getMethod(Class, String, Class...)}
	 * 
	 * @param clazz
	 *            Class to fetch method from
	 * @param name
	 *            The name of the method to return
	 * @return A method that matches the provided name, {@code null} if there is
	 *         no method with said name.
	 */
	public static Method getMethod(Class<?> clazz, String name) {
		for (Method m : getMethods(clazz)) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}

	/**
	 * Get a method with the specified name in the given class.
	 * 
	 * @param clazz
	 *            Class to fetch method from
	 * @param name
	 *            The name of the method to return
	 * @param params
	 *            The params of the method to return
	 * @return A method that matches the provided name, {@code null} if there is
	 *         no method with said name.
	 */
	public static Method getMethod(Class<?> clazz, String name, Class<?>... params) {
		for (Method m : getMethods(clazz)) {
			if (m.getName().equals(name) && Arrays.equals(m.getParameterTypes(), params)) {
				return m;
			}
		}
		return null;
	}

	/**
	 * Gets all {@link Method}s for the given class and all of its superclasses.
	 * 
	 * @param clazz
	 *            Class to fetch methods from
	 * @return A {@link HashSet} containing all of the methods that the given
	 *         class ( and its superclasses ) has. <br>
	 * <br>
	 *         All of the methods returned have had
	 *         {@link Method#setAccessible(boolean)} called on them.
	 */
	public static HashSet<Method> getMethods(Class<?> clazz) {
		return getMethods(clazz, ALL_METHODS);
	}

	/**
	 * Gets all {@link Method}s for the given class and all of its superclasses
	 * where {@link Filter#apply(Object)} returns true
	 * 
	 * @param clazz
	 *            Class to fetch methods from
	 * @param filter
	 *            The filter that determines whether or not a method is added to
	 *            the return
	 * @return A {@link HashSet} containing all of the methods that the given
	 *         class ( and its superclasses ) has that meet the filtering
	 *         criteria. <br>
	 * <br>
	 *         All of the methods returned have had
	 *         {@link Method#setAccessible(boolean)} called on them.
	 */
	public static HashSet<Method> getMethods(Class<?> clazz, Filter<Method> filter) {
		HashSet<Method> methods = new HashSet<Method>();
		if (filter == null) {
			return methods;
		}

		Class<?> currentClass = clazz;
		while (currentClass != null) {
			for (Method m : currentClass.getDeclaredMethods()) {
				m.setAccessible(true);
				if (filter.apply(m)) {
					methods.add(m);
				}
			}
			currentClass = currentClass.getSuperclass();
		}
		return methods;
	}
}
