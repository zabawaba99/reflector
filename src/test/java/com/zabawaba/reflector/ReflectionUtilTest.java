package com.zabawaba.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

import org.junit.Test;

import com.zabawaba.reflector.classes.Empty;
import com.zabawaba.reflector.classes.SampleOne;

public class ReflectionUtilTest {

	@Test
	public void testGetMethod() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = ReflectionUtil.getMethod(SampleOne.class, "method1");
		assertNotNull(m);

		String example = "foo";
		SampleOne sample = new SampleOne();
		sample.field1 = example;

		String response = (String) m.invoke(sample);
		assertEquals(example, response);
	}

	@Test
	public void testGetMethod_NoMethod() {
		Method m = ReflectionUtil.getMethod(SampleOne.class, "missing");
		assertNull(m);
	}

	@Test
	public void testGetMethodWithParams() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = ReflectionUtil.getMethod(SampleOne.class, "override", String.class, String.class);
		assertNotNull(m);

		SampleOne sample = new SampleOne();
		int response = (Integer) m.invoke(sample, "", "");
		assertEquals(2, response);
	}

	@Test
	public void testGetMethodWithParams_NoMethod() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = ReflectionUtil.getMethod(SampleOne.class, "override", String.class, String.class, String.class);
		assertNull(m);
	}

	@Test
	public void testGetMethods() {
		HashSet<Method> methods = ReflectionUtil.getMethods(SampleOne.class);
		HashSet<Method> parentMethods = ReflectionUtil.getMethods(Object.class);

		assertEquals(16, methods.size());
		assertEquals(4, methods.size() - parentMethods.size());
	}

	@Test
	public void testGetMethods_NoMethodsOnClass() {
		HashSet<Method> methods = ReflectionUtil.getMethods(Empty.class);

		// there are 12 methods in the object class
		assertEquals(12, methods.size());
	}

	@Test
	public void testGetField() throws IllegalArgumentException, IllegalAccessException {
		Field field = ReflectionUtil.getField(SampleOne.class, "field1");
		assertNotNull(field);

		String example = "foo";
		SampleOne sample = new SampleOne();
		sample.field1 = example;

		String value = (String) field.get(sample);
		assertEquals(example, value);
	}

	@Test
	public void testGetFields() {
		HashSet<Field> fields = ReflectionUtil.getFields(SampleOne.class);
		assertEquals(3, fields.size());
	}

	@Test
	public void testGetFields_NoFieldsOnClass() {
		HashSet<Field> fields = ReflectionUtil.getFields(Empty.class);

		// there are 0 fields in the object class
		assertEquals(0, fields.size());
	}

	@Test
	public void testGetFieldValue() {
		String expected = "foo";
		SampleOne sample = new SampleOne();
		sample.field1 = expected;
		Object value = ReflectionUtil.getFieldValue(sample, "field1");
		assertEquals(expected, value);
	}

	@Test
	public void testGetFieldValue_NullObject() {
		Object value = ReflectionUtil.getFieldValue(null, "foo");
		assertNull(value);
	}

	@Test
	public void testGetFieldValue_MissingField() {
		SampleOne sample = new SampleOne();
		Object value = ReflectionUtil.getFieldValue(sample, "missing");
		assertNull(value);
	}

	/*
	 * TODO: figure out why this test messes up cobertura reporting
	 * 
	 * @Test
	 * public void testGetFieldValue_SecurityManager() {
	 *	SampleOne sample = new SampleOne();
	 *	System.err.close();
	 *	System.setSecurityManager(new SecurityManager());
	 *	Object value = ReflectionUtil.getFieldValue(sample, "field3");
	 *	assertNull(value);
	 *}
	 */
}
