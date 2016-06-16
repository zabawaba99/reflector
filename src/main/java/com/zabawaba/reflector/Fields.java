package com.zabawaba.reflector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

/**
 * Provides a set methods to access and manipulate {@link Field}s
 * 
 * @author Steven Berlanga
 * @since 0.2.0
 */
public class Fields {
	private static Filter<Field> ALL_FIELDS = new Filter<Field>() {
		public boolean apply(Field field) {
			return true;
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

	/**
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 *         true for any public fields
	 */
	public static Filter<Field> thatArePublic() {
		return thatHaveModifiers(Modifier.PUBLIC);
	}

	/**
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 *         true for any protected fields
	 */
	public static Filter<Field> thatAreProtected() {
		return thatHaveModifiers(Modifier.PROTECTED);
	}

	/**
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 *         true for any private fields
	 */
	public static Filter<Field> thatArePrivate() {
		return thatHaveModifiers(Modifier.PRIVATE);
	}

	/**
	 * 
	 * @param modifiers
	 *            Modifiers that a field should have. See {@link Modifier}
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 *         true for any field that has all of the provided modifiers
	 */
	public static Filter<Field> thatHaveModifiers(final int... modifiers) {
		return new Filter<Field>() {
			public boolean apply(Field field) {
				boolean valid = true;
				for (int modifier : modifiers) {
					valid &= (field.getModifiers() & modifier) == modifier;
				}
				return valid;
			}
		};
	}

	/**
	 * 
	 * @param prefix
	 *            The string that a field's name should start with
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 *         true for any field who's name starts with the given prefix
	 */
	public static Filter<Field> thatStartWith(final String prefix) {
		return new Filter<Field>() {
			public boolean apply(Field field) {
				return field.getName().startsWith(prefix);
			}
		};
	}

	/**
	 * 
	 * @param suffix
	 *            The string that a field's name should start with
	 * @return a {@link Filter} where {@link Filter#apply(Object)} will return
	 *         true for any field who's name ends with the given suffix
	 */
	public static Filter<Field> thatEndWith(final String suffix) {
		return new Filter<Field>() {
			public boolean apply(Field field) {
				return field.getName().endsWith(suffix);
			}
		};
	}
}
