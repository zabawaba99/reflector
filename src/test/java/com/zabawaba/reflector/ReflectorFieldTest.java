package com.zabawaba.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import com.zabawaba.reflector.classes.SampleOne;

public class ReflectorFieldTest {
	
	private SampleOne sample;
	private String expected;
	private Field field;
	
	@Before
	public void setup() throws NoSuchFieldException, SecurityException {
		sample = new SampleOne();
		expected = "foobar";
		field = SampleOne.class.getField("field1");
	}

	@Test
	public void testGetField(){
		ReflectorField rField = new ReflectorField(sample, field);
		assertEquals(field, rField.getField());
	}
	
	@Test
	public void testGetValue() {
		sample.field1 = expected;
		
		ReflectorField rField = new ReflectorField(sample, field);
		assertEquals(expected, rField.getValue());
	}
	
	@Test
	public void testGetValue_IllegalArgument() {
		ReflectorField rField = new ReflectorField(new Object(), field);
		assertNull(rField.getValue());
	}
	
	@Test
	public void testGetValue_IllegalAccess() throws NoSuchFieldException, SecurityException {
		Field field = SampleOne.class.getDeclaredField("field3");
		ReflectorField rField = new ReflectorField(new SampleOne(), field);
		assertNull(rField.getValue());
	}
	
	@Test
	public void testValueEqual() {
		sample.field1 = expected;			
		ReflectorField rField = new ReflectorField(sample, field);
		assertEquals(true, rField.valueEquals(expected));
	}
	
	@Test
	public void testValueEqual_NullObject() {
		sample.field1 = expected;
		ReflectorField rField = new ReflectorField(sample, field);
		assertEquals(false, rField.valueEquals(null));
	}
	
	@Test
	public void testValueEqual_NullReceiver() {
		ReflectorField rField = new ReflectorField(sample, field);
		assertEquals(false, rField.valueEquals(expected));
	}
	
	@Test
	public void testValueEqual_NullObjectAndReceiver() {
		ReflectorField rField = new ReflectorField(sample, field);
		assertEquals(true, rField.valueEquals(null));
	}
}
