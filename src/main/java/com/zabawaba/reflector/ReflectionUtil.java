package com.zabawaba.reflector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
		Class<?> currentClass = clazz;
		while( currentClass != null ) {
			for(Method m : currentClass.getMethods()) {
				m.setAccessible(true);
				methods.add(m);
			}
			currentClass = currentClass.getSuperclass();
		}
		return methods;
	}
	
	/**
	 * Gets all {@link Field}s for the given class and all of its superclasses.
	 * 
	 * @param clazz
	 * @return A {@link HashSet} containing all of the fields that the given class ( and its superclasses ) has.
	 * <br><br>
	 * All of the fields returned have had {@link Field#setAccessible(boolean)} called on them.
	 */
	public static HashSet<Field> getFields(Class<?> clazz) {
		HashSet<Field> fields = new HashSet<Field>();
		Class<?> currentClass = clazz;
		while( currentClass != null ) {
			for(Field f : currentClass.getFields()) {
				f.setAccessible(true);
				fields.add(f);
			}
			currentClass = currentClass.getSuperclass();
		}
		return fields;
	}
}
