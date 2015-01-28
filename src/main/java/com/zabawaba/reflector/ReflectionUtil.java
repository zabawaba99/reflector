package com.zabawaba.reflector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A utility that provides a different interface for reflection.
 * 
 * @author zabawaba
 */
public class ReflectionUtil {

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
		HashSet<Method> methods = new HashSet<Method>();
		Class<?> currentClass = clazz;
		while (currentClass != null) {
			for (Method m : currentClass.getDeclaredMethods()) {
				m.setAccessible(true);
				methods.add(m);
			}
			currentClass = currentClass.getSuperclass();
		}
		return methods;
	}

	/**
	 * Get a field with the specified name in the given class
	 * 
	 * @param clazz
	 *            Class to fetch field from
	 * @param name
	 *            Name of the field to get
	 * @return A field that matches the provided name, {@code null} if there is
	 *         no field with said name.
	 */
	public static Field getField(Class<?> clazz, String name) {
		for (Field field : getFields(clazz)) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * Gets all {@link Field}s for the given class and all of its superclasses.
	 * 
	 * @param clazz
	 *            Class to fetch fields from
	 * @return A {@link HashSet} containing all of the fields that the given
	 *         class ( and its superclasses ) has. <br>
	 * <br>
	 *         All of the fields returned have had
	 *         {@link Field#setAccessible(boolean)} called on them.
	 */
	public static HashSet<Field> getFields(Class<?> clazz) {
		HashSet<Field> fields = new HashSet<Field>();
		Class<?> currentClass = clazz;
		while (currentClass != null) {
			try {
				for (Field f : currentClass.getDeclaredFields()) {
					try {
						f.setAccessible(true);
						fields.add(f);
					} catch (SecurityException e) {
						// TODO: error handling
					}
				}
			} catch (SecurityException e) {
				// TODO: error handling
			}
			currentClass = currentClass.getSuperclass();
		}
		return fields;
	}

	/**
	 * Find a field with the given name in the given object and returns fields
	 * value
	 * 
	 * @param <T>
	 *            Type of the requested field's value
	 * @param obj
	 *            Object to find the field in
	 * @param name
	 *            Name of the field to look for
	 * @return The value of the field with the given name. <br>
	 *         {@code null} will be returned if:
	 *         <ul>
	 *         <li>The given object is null</li>
	 *         <li>The requested field does not exist in the object</li>
	 *         <li>The requested field is not Public and there is a
	 *         {@link SecurityManager} in place/</li>
	 *         <li>The value of the found field is not the same type as T</li>
	 *         </ul>
	 */
	public static Object getFieldValue(Object obj, String name) {
		if (obj == null) {
			return null;
		}
		Field field = getField(obj.getClass(), name);
		if (field != null) {
			try {
				return field.get(obj);
			} catch (IllegalArgumentException e) {
				/*
				 * object is not an instance of the class that was used to fetch
				 * the field.
				 * 
				 * this should never happen since obj.getClass() was the class
				 * used to fetch the field
				 */
				// TODO: error handling
			} catch (IllegalAccessException e) {
				// should not happen since the field object is manually
				// being set to accessible
				// TODO: error handling
			}
		}
		return null;
	}
}
