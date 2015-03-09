package com.zabawaba.reflector;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
		ReflectorMethod method = null;
		for(ReflectorMethod m:list()){
			if (m.getMethod().getName().equals(methodName)){
				method = m;
			}
		}
		if (method == null) {
			throw new NoSuchMethodException(methodName);
		}
		return method;
	}

	/**
	 * Gets all {@link Method}s for the given class and all of its superclasses
	 * 
	 * @return A {@link HashSet} containing all of the method of object the
	 *         Methods instance was instantiated with
	 */
	public HashSet<ReflectorMethod> list() {
		return list(ALL_METHODS);
	}

	/**
	 * Gets all {@link Method}s for the given class and all of its superclasses
	 * where {@link Filter#apply(Object)} returns true
	 * 
	 * @param filter
	 *            The filter that determines whether or not a method is added to
	 *            the return. If {@code null} all methods will be returned.
	 * @return A {@link HashSet} containing all of the methods of object the
	 *         Method instance was instantiated with and that meet the filtering
	 *         criteria
	 */
	public HashSet<ReflectorMethod> list(Filter<Method> filter) {
		HashSet<ReflectorMethod> methods = new HashSet<ReflectorMethod>();

		if (filter == null) {
			filter = ALL_METHODS;
		}

		Class<?> currentClass = obj.getClass();
		while (currentClass != null) {
			for (Method m : currentClass.getDeclaredMethods()) {
				m.setAccessible(true);
				if (filter.apply(m)) {
					methods.add(new ReflectorMethod(obj, m));
				}
			}
			currentClass = currentClass.getSuperclass();
		}
		return methods;
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
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 * true for any public methods
	 */
	public static Filter<Method> thatArePublic() {
		return thatHaveModifiers(Modifier.PUBLIC);
	}
	
	/**
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 * true for any protected methods
	 */
	public static Filter<Method> thatAreProtected() {
		return thatHaveModifiers(Modifier.PROTECTED);
	}
	
	/**
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 * true for any private methods
	 */
	public static Filter<Method> thatArePrivate() {
		return thatHaveModifiers(Modifier.PRIVATE);
	}
	
	/**
	 * 
	 * @param modifiers Modifiers that a method should have. See {@link Modifier}
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 * true for any method that has all of the provided modifiers
	 */
	public static Filter<Method> thatHaveModifiers(final int... modifiers) {
		return new Filter<Method>() {
			public boolean apply(Method method) {
				boolean valid = true;
				for(int modifier:modifiers){
					valid &= (method.getModifiers() & modifier) == modifier;
				}
				return valid;
			}
		};
	}
	
	/**
	 * 
	 * @param prefix The string that a method's name should start with
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 * true for any method who's name starts with the given prefix
	 */
	public static Filter<Method> thatStartWith(final String prefix) {
		return new Filter<Method>(){
			public boolean apply(Method method) {
				return method.getName().startsWith(prefix);
			}
		};
	}
	
	/**
	 * 
	 * @param suffix The string that a method's name should start with
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 * true for any method who's name ends with the given suffix
	 */
	public static Filter<Method> thatEndWith(final String suffix) {
		return new Filter<Method>(){
			public boolean apply(Method method) {
				return method.getName().endsWith(suffix);
			}
		};
	}
}
