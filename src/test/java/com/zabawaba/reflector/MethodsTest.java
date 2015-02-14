package com.zabawaba.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

import org.junit.Test;

import com.zabawaba.reflector.classes.Empty;
import com.zabawaba.reflector.classes.SampleOne;

public class MethodsTest {

	@Test
	public void testGetMethod() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = Methods.getMethod(SampleOne.class, "method1");
		assertNotNull(m);

		String example = "foo";
		SampleOne sample = new SampleOne();
		sample.field1 = example;

		String response = (String) m.invoke(sample);
		assertEquals(example, response);
	}

	@Test
	public void testGetMethod_NoMethod() {
		Method m = Methods.getMethod(SampleOne.class, "missing");
		assertNull(m);
	}

	@Test
	public void testGetMethodWithParams() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = Methods.getMethod(SampleOne.class, "override", String.class, String.class);
		assertNotNull(m);

		SampleOne sample = new SampleOne();
		int response = (Integer) m.invoke(sample, "", "");
		assertEquals(2, response);
	}

	@Test
	public void testGetMethodWithParams_NoMethod() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = Methods.getMethod(SampleOne.class, "override", String.class, String.class, String.class);
		assertNull(m);
	}

	@Test
	public void testGetMethods() {
		HashSet<Method> methods = Methods.getMethods(SampleOne.class);
		HashSet<Method> parentMethods = Methods.getMethods(Object.class);

		assertEquals(17, methods.size());
		assertEquals(5, methods.size() - parentMethods.size());
	}

	@Test
	public void testGetMethods_NoMethodsOnClass() {
		HashSet<Method> methods = Methods.getMethods(Empty.class);

		// there are 12 methods in the object class
		assertEquals(12, methods.size());
	}

	@Test
	public void testGetMethodsWithFilter() {
		HashSet<Method> fields = Methods.getMethods(SampleOne.class, Methods.PUBLIC_METHODS);
		assertEquals(13, fields.size());
	}

	@Test
	public void testGetMethodsWithFilter_NullFilter() {
		HashSet<Method> fields = Methods.getMethods(SampleOne.class, null);
		assertEquals(0, fields.size());
	}
}
