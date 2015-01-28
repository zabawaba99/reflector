package com.zabawaba.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;

import org.junit.Test;

import com.zabawaba.reflector.classes.Empty;
import com.zabawaba.reflector.classes.SampleOne;

public class ReflectionUtilTest {

	@Test
	public void testGetMethods_NoMethodsOnClass() {
		HashSet<Method> methods = ReflectionUtil.getMethods(Empty.class);

		// there are 9 methods in the object class
		assertEquals(9, methods.size());
	}

	@Test
	public void testGetMethods_MethodsOnClass() {
		HashSet<Method> methods = ReflectionUtil.getMethods(SampleOne.class);
		HashSet<Method> parentMethods = ReflectionUtil.getMethods(Object.class);

		assertEquals(11, methods.size());
		assertEquals(2, methods.size() - parentMethods.size());
	}

	@Test
	public void testGetFields_NoFieldsOnClass() {
		HashSet<Field> fields = ReflectionUtil.getFields(Empty.class);

		// there are 0 fields in the object class
		assertEquals(0, fields.size());
	}
	
	@Test
	public void testGetFields_FieldsOnClass() {
		HashSet<Field> fields = ReflectionUtil.getFields(SampleOne.class);
		assertEquals(2, fields.size());
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
	
	@Test
	public void testGetFieldValue_SecurityManager() {
		SecurityManager m = new SecurityManager();
		System.setSecurityManager(m);
		SampleOne sample = new SampleOne();
		Object value = ReflectionUtil.getFieldValue(sample, "field3");
		assertNull(value);
	}
}
