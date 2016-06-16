package com.zabawaba.reflector;

import com.zabawaba.reflector.classes.SampleOne;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReflectorConstructorTest {

	private SampleOne sample;
	private String expected;
	private Constructor<SampleOne> constructor;

	@Before
	public void setup() throws NoSuchMethodException, SecurityException {
		sample = new SampleOne();
		expected = "foobar";
		constructor = SampleOne.class.getConstructor();
	}

	@Test
	public void testGetConstructor() {
		ReflectorConstructor rConstructor = new ReflectorConstructor(sample, constructor);
		assertEquals(constructor, rConstructor.getConstructor());
	}

	@Test
	public void testNewInstance() throws InstantiationException, IllegalArgumentException, InvocationTargetException {
		ReflectorConstructor rConstructor = new ReflectorConstructor(sample, constructor);
		assertNotNull(expected, rConstructor.newInstance());
	}
}
