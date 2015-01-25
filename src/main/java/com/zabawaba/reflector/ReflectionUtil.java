package com.zabawaba.reflector;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A utility that provides a different interface
 * for reflection.
 * 
 * @author zabawaba
 */
public class ReflectionUtil {

	/**
	 * Gets all {@link Method}s for the given class and all of its superclasses.
	 * 
	 * @param clazz
	 * @return A {@link HashSet} containing all of the methods that the given class ( and its superclasses ) has.
	 * <br><br>
	 * All of the methods returned have had {@link Method#setAccessible(boolean)} called on them.
	 */
	public static HashSet<Method> getMethods(Class<?> clazz) {
		HashSet<Method> methods = new HashSet<Method>();
		Class<?> callingClass = clazz;
		while( callingClass != null ) {
			for(Method m : callingClass.getMethods()) {
				m.setAccessible(true);
				methods.add(m);
			}
			callingClass = callingClass.getSuperclass();
		}
		return methods;
	}
}
