package com.zabawaba.reflector;

import com.zabawaba.reflector.classes.SampleOne;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class ConstructorsTest {

	@Test
	public void testGet() throws NoSuchMethodException {
		SampleOne sample = new SampleOne();
		ReflectorConstructor constructor = Constructors.forObj(sample).get();
		assertNotNull(constructor);
	}

	@Test(expected = NoSuchMethodException.class)
	public void testGet_MissingConstructor() throws NoSuchMethodException {
		SampleOne sample = new SampleOne();
		Constructors.forObj(sample).get(Integer.class);
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
		assertEquals(1, constructors.size());
	}
}
