package com.zabawaba.reflector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Wraps {@link Method} and provides some utility methods
 * 
 * @author zabawaba99
 */
public class ReflectorMethod {
	private Object obj;
	private Method method;
	
	ReflectorMethod(Object obj, Method method) {
		this.obj = obj;
		this.method = method;
	}
	
	/**
	 * Gets the {@link Method} that is being wrapped
	 * 
	 * @return The {@link Method} that is being wrapped
	 */
	public Method getMethod() {
		return method;
	}
	
	/**
	 * Executes the underlying Method with the provided params
	 * 
	 * @param params The parameters to execute the method with
	 * @return The object that the underlying method returns
	 * @throws InvocationTargetException if the underlying method throws an exception
	 * @throws IllegalArgumentException if the count or type of parameters given don't match the target methods parameters
	 */
	public Object call(Object... params) throws InvocationTargetException, IllegalArgumentException {
		try {
			return method.invoke(obj, params);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
