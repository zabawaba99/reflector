package com.zabawaba.reflector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

public class Fields {
	private static Filter<Field> ALL_FIELDS = new Filter<Field>() {
		public boolean apply(Field field) {
			return true;
		}
	};

	/**
	 * Predefined {@link Filter} where {@link Filter#apply(Object)} will return
	 * true when given a field who's {@link Modifier}s contain
	 * {@link Modifier#PUBLIC}
	 */
	public static Filter<Field> PUBLIC_FIELDS = new Filter<Field>() {
		public boolean apply(Field field) {
			return (field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC;
		}
	};

	private Object obj;

	private Fields(Object obj) {
		this.obj = obj;
	}

	/**
	 * Get a field that has the given name
	 * 
	 * @param fieldName
	 *            The name of the field to look for
	 * @return The field that has the given name
	 * 
	 * @throws NoSuchFieldException
	 *             If no field exists with the provided name
	 */
	public ReflectorField get(String fieldName) throws NoSuchFieldException {
		Field field = getField(obj.getClass(), fieldName);
		if (field == null) {
			throw new NoSuchFieldException(fieldName);
		}
		return new ReflectorField(obj, field);
	}

	/**
	 * Builds a new Fields object with context of the object you want to Reflect
	 * over
	 * 
	 * @param obj
	 *            The object whos fields you want to Reflect over
	 * @return A newly created Fields object
	 */
	public static Fields forObj(Object obj) {
		return new Fields(obj);
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
		return getFields(clazz, ALL_FIELDS);
	}

	/**
	 * Gets all {@link Field}s for the given class and all of its superclasses
	 * where {@link Filter#apply(Object)} returns true
	 * 
	 * @param clazz
	 *            Class to fetch fields from
	 * @param filter
	 *            The filter that determines whether or not a field is added to
	 *            the return
	 * @return A {@link HashSet} containing all of the fields that the given
	 *         class ( and its superclasses ) has that meet the filtering
	 *         criteria. <br>
	 * <br>
	 *         All of the fields returned have had
	 *         {@link Field#setAccessible(boolean)} called on them.
	 */
	public static HashSet<Field> getFields(Class<?> clazz, Filter<Field> filter) {
		HashSet<Field> fields = new HashSet<Field>();
		if (filter == null) {
			return fields;
		}

		Class<?> currentClass = clazz;
		while (currentClass != null) {
			for (Field f : currentClass.getDeclaredFields()) {
				f.setAccessible(true);
				if (filter.apply(f)) {
					fields.add(f);
				}
			}
			currentClass = currentClass.getSuperclass();
		}
		return fields;
	}
}
