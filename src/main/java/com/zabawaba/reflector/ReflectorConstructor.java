package com.zabawaba.reflector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectorConstructor {

	private Object obj;
	private Constructor<?> constructor;

	public ReflectorConstructor(Object obj, Constructor<?> constructor) {
		this.obj = obj;
		this.constructor = constructor;
	}

	public Constructor<?> getConstructor() {
		return constructor;
	}

	/**
	 * Invoked the constructor with the given params
	 *
	 * @param params
	 *            The parameters to pass to the constructor
	 * @return The object the constructor is suppose to create
	 * @throws InstantiationException
	 *             if the class that declares the underlying constructor
	 *             represents an abstract class.
	 * @throws IllegalArgumentException
	 *             if the count or type of parameters given don't match the
	 *             target methods parameters
	 * @throws InvocationTargetException
	 *             if the underlying constructor throws an exception
	 *
	 */
	public Object newInstance(Object... params)
			throws InstantiationException, IllegalArgumentException, InvocationTargetException {
		try {
			return constructor.newInstance(params);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
