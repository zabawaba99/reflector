package com.zabawaba.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

	@Test
	public void testList() {
		HashSet<ReflectorField> fields = Fields.forObj(new SampleOne()).list();
		assertEquals(3, fields.size());
	}

	@Test
	public void testList_NoFieldsOnClass() {
		HashSet<ReflectorField> fields = Fields.forObj(new Empty()).list();

		// there are 0 fields in the object class
		assertEquals(0, fields.size());
	}

	@Test
	public void testListWithFilter() {
		HashSet<ReflectorField> fields = Fields.forObj(new SampleOne()).list(Fields.thatArePublic());
		assertEquals(1, fields.size());
	}

	@Test
	public void tesListWithFilter_NullFilter() {
		HashSet<ReflectorField> fields = Fields.forObj(new SampleOne()).list(null);
		assertEquals(3, fields.size());
	}
	
	@Test
	public void testPublicFieldFilter() throws NoSuchFieldException, SecurityException {
		Field field = SampleOne.class.getField("field1");
		Filter<Field> f = Fields.thatArePublic();
		assertTrue(f.apply(field));	
	}
	
	@Test
	public void testProtecedFieldFilter() throws NoSuchFieldException, SecurityException {
		Field field = SampleOne.class.getDeclaredField("field2");
		Filter<Field> f = Fields.thatAreProtected();
		assertTrue(f.apply(field));	
	}
	
	@Test
	public void testPrivateFieldFilter() throws NoSuchFieldException, SecurityException {
		Field field = SampleOne.class.getDeclaredField("field3");
		Filter<Field> f = Fields.thatArePrivate();
		assertTrue(f.apply(field));	
	}
	
	@Test
	public void testModifierFieldFilter() throws NoSuchFieldException, SecurityException {
		Field validField = SampleOne.class.getDeclaredField("field2");
		Field invalidField = SampleOne.class.getDeclaredField("field3");
		Filter<Field> f = Fields.thatHaveModifiers(Modifier.VOLATILE, Modifier.PROTECTED);
		assertTrue(f.apply(validField));
		assertFalse(f.apply(invalidField));
	}
	
	@Test
	public void testFieldPrefixFilter() throws NoSuchFieldException, SecurityException {
		Field validField = SampleOne.class.getDeclaredField("field3");
		Field invalidField = SampleOne.class.getDeclaredField("field1");
		Filter<Field> f = Fields.thatStartWith("field3");
		assertTrue(f.apply(validField));	
		assertFalse(f.apply(invalidField));
	}
	
	@Test
	public void testFieldSuffixFilter() throws NoSuchFieldException, SecurityException {
		Field validField = SampleOne.class.getDeclaredField("field3");
		Field invalidField= SampleOne.class.getDeclaredField("field1");
		Filter<Field> f = Fields.thatEndWith("3");
		assertTrue(f.apply(validField));	
		assertFalse(f.apply(invalidField));
	}
}
