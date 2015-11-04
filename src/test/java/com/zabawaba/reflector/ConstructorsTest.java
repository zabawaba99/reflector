package com.zabawaba.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.Test;

import com.zabawaba.reflector.classes.SampleOne;

public class ConstructorsTest {

	@Test
	public void testGet() throws NoSuchMethodException {
		SampleOne sample = new SampleOne();
		ReflectorConstructor constructor = Constructors.forObj(sample).get("SampleOne");
		assertNotNull(constructor);
	}

	@Test(expected = NoSuchMethodException.class)
	public void testGet_MissingConstructor() throws NoSuchMethodException {
		SampleOne sample = new SampleOne();
		Constructors.forObj(sample).get("i_don't_exist");
		fail("should have through exception");
	}

	@Test
	public void testForObj() {
		SampleOne sample = new SampleOne();
		Constructors c = Constructors.forObj(sample);
		assertNotNull(c);
	}

	@Test
	public void testList() {
		HashSet<ReflectorConstructor> constructors = Constructors.forObj(new SampleOne()).list();
		assertEquals(2, constructors.size());
	}
}
