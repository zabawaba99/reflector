package com.zabawaba.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.Test;

import com.zabawaba.reflector.classes.Empty;
import com.zabawaba.reflector.classes.SampleOne;

public class MethodsTest {

	@Test
	public void testGet() throws NoSuchMethodException {
		SampleOne sample = new SampleOne();
		ReflectorMethod method = Methods.forObj(sample).get("method1");
		assertNotNull(method);
	}
	
	@Test(expected=NoSuchMethodException.class)
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
		assertEquals(18, methods.size());
	}

	@Test
	public void testListWithFilter() {
		HashSet<ReflectorMethod> methods = Methods.forObj(new SampleOne()).list(Methods.PUBLIC_METHODS);
		assertEquals(14, methods.size());
	}

	@Test
	public void testListWithFilter_NullFilter() {
		HashSet<ReflectorMethod> methods = Methods.forObj(new SampleOne()).list(null);
		assertEquals(18, methods.size());
	}
}
