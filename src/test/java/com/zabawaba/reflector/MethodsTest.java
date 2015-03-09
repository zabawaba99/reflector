package com.zabawaba.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import org.junit.Test;

import com.zabawaba.reflector.classes.SampleOne;

public class MethodsTest {

	@Test
	public void testGet() throws NoSuchMethodException {
		SampleOne sample = new SampleOne();
		ReflectorMethod method = Methods.forObj(sample).get("method1");
		assertNotNull(method);
	}

	@Test(expected = NoSuchMethodException.class)
	public void testGet_MissingMethod() throws NoSuchMethodException {
		SampleOne sample = new SampleOne();
		Methods.forObj(sample).get("i_don't_exist");
		fail("should have through exception");
	}

	@Test
	public void testForObj() {
		SampleOne sample = new SampleOne();
		Methods m = Methods.forObj(sample);
		assertNotNull(m);
	}

	@Test
	public void testList() {
		HashSet<ReflectorMethod> methods = Methods.forObj(new SampleOne()).list();
		assertEquals(19, methods.size());
	}

	@Test
	public void testListWithFilter() {
		HashSet<ReflectorMethod> methods = Methods.forObj(new SampleOne()).list(Methods.PUBLIC_METHODS);
		assertEquals(14, methods.size());
	}

	@Test
	public void testListWithFilter_NullFilter() {
		HashSet<ReflectorMethod> methods = Methods.forObj(new SampleOne()).list(null);
		assertEquals(19, methods.size());
	}

	@Test
	public void testPublicMethodFilter() throws NoSuchMethodException, SecurityException {
		Method method = SampleOne.class.getMethod("method1");
		Filter<Method> f = Methods.thatArePublic();
		assertTrue(f.apply(method));
	}

	@Test
	public void testProtecedMethodFilter() throws NoSuchMethodException, SecurityException {
		Method method = SampleOne.class.getDeclaredMethod("notPublic");
		Filter<Method> f = Methods.thatAreProtected();
		assertTrue(f.apply(method));
	}

	@Test
	public void testPrivateMethodFilter() throws NoSuchMethodException, SecurityException {
		Method method = SampleOne.class.getDeclaredMethod("methodPrivate");
		Filter<Method> f = Methods.thatArePrivate();
		assertTrue(f.apply(method));
	}

	@Test
	public void testModifierMethodFilter() throws NoSuchMethodException, SecurityException {
		Method validMethod = SampleOne.class.getDeclaredMethod("notPublic");
		Method invalidMethod = SampleOne.class.getDeclaredMethod("method1");
		Filter<Method> f = Methods.thatHaveModifiers(Modifier.STATIC, Modifier.PROTECTED);
		assertTrue(f.apply(validMethod));
		assertFalse(f.apply(invalidMethod));
	}

	@Test
	public void testMethodPrefixFilter() throws NoSuchMethodException, SecurityException {
		Method validMethod = SampleOne.class.getDeclaredMethod("method2");
		Method invalidMethod = SampleOne.class.getDeclaredMethod("method1");
		Filter<Method> f = Methods.thatStartWith("method2");
		assertTrue(f.apply(validMethod));
		assertFalse(f.apply(invalidMethod));
	}

	@Test
	public void testMethodSuffixFilter() throws NoSuchMethodException, SecurityException {
		Method validMethod = SampleOne.class.getDeclaredMethod("method2");
		Method invalidMethod = SampleOne.class.getDeclaredMethod("method1");
		Filter<Method> f = Methods.thatEndWith("2");
		assertTrue(f.apply(validMethod));
		assertFalse(f.apply(invalidMethod));
	}
}
