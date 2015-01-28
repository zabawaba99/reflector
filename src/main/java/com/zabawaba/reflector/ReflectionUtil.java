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
	
	/**
	 * Find a field with the given name in the given object and returns
	 * fields value
	 * 
	 * @param <T> Type of the requested field's value
	 * @param obj Object to find the field in
	 * @param name Name of the field to look for
	 * @return The value of the field with the given name. 
	 * <br>
	 * {@code null} will be returned if:
	 * <ul>
	 * <li>The given object is null</li>
	 * <li>The requested field does not exist in the object</li>
	 * <li>The requested field is not Public and there is a {@link SecurityManager} in place/</li>
	 * <li>The value of the found field is not the same type as T</li>
	 * </ul>
	 */
	public static Object getFieldValue(Object obj, String name) {
		if (obj == null) {
			return null;
		}
				
		try {
			Field field = obj.getClass().getField(name);
			field.setAccessible(true);
			return field.get(obj); 
		} catch (NoSuchFieldException e) {
			// field doesn't exist in object
			e.printStackTrace();
		} catch (SecurityException e) {
			// there is a security manager in place that 
			// doesn't allow for access to private fields
			e.printStackTrace();
		} catch (ClassCastException e) {
			// field is not of type T
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			/* 
			 * object is not an instance of the class that was used to fetch the field.
			 * 
			 * this should never happen since obj.getClass() was the class used to 
			 * fetch the field
			 */
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// should not happen since the field object is manually
			// being set to accessible
			e.printStackTrace();
		}
		return null;
	}
}
