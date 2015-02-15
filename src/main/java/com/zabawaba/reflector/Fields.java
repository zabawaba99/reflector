package com.zabawaba.reflector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

/**
 * Provides a set methods to access and manipulate {@link Field}s
 * 
 * @author zabawaba
 */
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
		ReflectorField field = null;
		for (ReflectorField f : list()) {
			if (f.getField().getName().equals(fieldName)) {
				field = f;
				break;
			}
		}
		if (field == null) {
			throw new NoSuchFieldException(fieldName);
		}
		return field;
	}

	/**
	 * Gets all {@link Field}s for the given class and all of its superclasses
	 * 
	 * @return A {@link HashSet} containing all of the fields of object the
	 *         Fields instance was instantiated with
	 */
	public HashSet<ReflectorField> list() {
		return list(ALL_FIELDS);
	}

	/**
	 * Gets all {@link Field}s for the given class and all of its superclasses
	 * where {@link Filter#apply(Object)} returns true
	 * 
	 * @param filter
	 *            The filter that determines whether or not a field is added to
	 *            the return. If {@code null} all fields will be returned.
	 * @return A {@link HashSet} containing all of the fields of object the
	 *         Fields instance was instantiated with and that meet the filtering
	 *         criteria
	 */
	public HashSet<ReflectorField> list(Filter<Field> filter) {
		HashSet<ReflectorField> fields = new HashSet<ReflectorField>();

		if (filter == null) {
			filter = ALL_FIELDS;
		}

		Class<?> currentClass = obj.getClass();
		while (currentClass != null) {
			for (Field f : currentClass.getDeclaredFields()) {
				// set accessible to that field values can be accessed
				f.setAccessible(true);

				// apply filter
				if (filter.apply(f)) {
					fields.add(new ReflectorField(obj, f));
				}
			}
			currentClass = currentClass.getSuperclass();
		}
		return fields;
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
}
