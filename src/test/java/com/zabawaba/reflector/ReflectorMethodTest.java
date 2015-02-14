package com.zabawaba.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import com.zabawaba.reflector.classes.SampleOne;

public class ReflectorMethodTest {
	
	private SampleOne sample;
	private String expected;
	private Method method;
	
	@Before
	public void setup() throws NoSuchMethodException, SecurityException {
		sample = new SampleOne();
		expected = "foobar";
		method = SampleOne.class.getMethod("method1");
	}

	@Test
	public void testGetMethod(){
		ReflectorMethod rMethod = new ReflectorMethod(sample, method);
		assertEquals(method, rMethod.getMethod());
	}
	
	@Test
	public void testCall() throws InvocationTargetException, IllegalAccessException {
		sample.field1 = expected;
		
		ReflectorMethod rMethod = new ReflectorMethod(sample, method);
		assertEquals(expected, rMethod.call());
	}
	
	@Test
	public void testCall_IllegalAccess() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, SecurityException {
		sample.field1 = expected;
		method = SampleOne.class.getDeclaredMethod("notPublic");
		ReflectorMethod rMethod = new ReflectorMethod(sample, method);
		assertEquals(null, rMethod.call());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCall_IllegalArgument() throws InvocationTargetException, IllegalAccessException {
		sample.field1 = expected;
		
		new ReflectorMethod(sample, method).call("foo");
		fail("should have failed");
	}
	
	@Test(expected=InvocationTargetException.class)
	public void testCall_InvocationTarget() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, SecurityException {
		sample.field1 = expected;
		method = SampleOne.class.getMethod("blowup");
		new ReflectorMethod(sample, method).call();
		fail("should have failed");
	}
}
