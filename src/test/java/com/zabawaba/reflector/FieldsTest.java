package com.zabawaba.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.junit.Test;

import com.zabawaba.reflector.classes.Empty;
import com.zabawaba.reflector.classes.SampleOne;

public class FieldsTest {
	@Test
	public void testGetField() throws IllegalArgumentException, IllegalAccessException {
		Field field = Fields.getField(SampleOne.class, "field1");
		assertNotNull(field);

		String example = "foo";
		SampleOne sample = new SampleOne();
		sample.field1 = example;

		String value = (String) field.get(sample);
		assertEquals(example, value);
	}

	@Test
	public void testGetFieldValue() {
		String expected = "foo";
		SampleOne sample = new SampleOne();
		sample.field1 = expected;
		Object value = Fields.getFieldValue(sample, "field1");
		assertEquals(expected, value);
	}

	@Test
	public void testGetFieldValue_NullObject() {
		Object value = Fields.getFieldValue(null, "foo");
		assertNull(value);
	}

	@Test
	public void testGetFieldValue_MissingField() {
		SampleOne sample = new SampleOne();
		Object value = Fields.getFieldValue(sample, "missing");
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

	@Test
	public void testGetFields() {
		HashSet<Field> fields = Fields.getFields(SampleOne.class);
		assertEquals(3, fields.size());
	}

	@Test
	public void testGetFields_NoFieldsOnClass() {
		HashSet<Field> fields = Fields.getFields(Empty.class);

		// there are 0 fields in the object class
		assertEquals(0, fields.size());
	}

	@Test
	public void testGetFieldsWithFilter() {
		HashSet<Field> fields = Fields.getFields(SampleOne.class, Fields.PUBLIC_FIELDS);
		assertEquals(2, fields.size());
	}

	@Test
	public void testGetFieldsWithFilter_NullFilter() {
		HashSet<Field> fields = Fields.getFields(SampleOne.class, null);
		assertEquals(0, fields.size());
	}
}
