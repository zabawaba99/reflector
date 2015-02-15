package com.zabawaba.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.Test;

import com.zabawaba.reflector.classes.Empty;
import com.zabawaba.reflector.classes.SampleOne;

public class FieldsTest {
	
	@Test
	public void testGet() throws NoSuchFieldException {
		SampleOne sample = new SampleOne();
		ReflectorField field = Fields.forObj(sample).get("field1");
		assertNotNull(field);
	}
	
	@Test(expected=NoSuchFieldException.class)
	public void testGet_MissingField() throws NoSuchFieldException {
		SampleOne sample = new SampleOne();
		Fields.forObj(sample).get("i_don't_exist");
		fail("should have through exception");
	}
	
	@Test
	public void testForObj() {
		SampleOne sample = new SampleOne();
		Fields f = Fields.forObj(sample);
		assertNotNull(f);
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
		HashSet<ReflectorField> fields = Fields.forObj(new SampleOne()).list();
		assertEquals(3, fields.size());
	}

	@Test
	public void testGetFields_NoFieldsOnClass() {
		HashSet<ReflectorField> fields = Fields.forObj(new Empty()).list();

		// there are 0 fields in the object class
		assertEquals(0, fields.size());
	}

	@Test
	public void testGetFieldsWithFilter() {
		HashSet<ReflectorField> fields = Fields.forObj(new SampleOne()).list(Fields.PUBLIC_FIELDS);
		assertEquals(2, fields.size());
	}

	@Test
	public void testGetFieldsWithFilter_NullFilter() {
		HashSet<ReflectorField> fields = Fields.forObj(new SampleOne()).list(null);
		assertEquals(3, fields.size());
	}
}
