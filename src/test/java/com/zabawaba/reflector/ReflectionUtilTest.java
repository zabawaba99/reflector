package com.zabawaba.reflector;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.HashSet;

import org.junit.Test;

import com.zabawaba.reflector.classes.Empty;
import com.zabawaba.reflector.classes.Methods;

public class ReflectionUtilTest {

	@Test
	public void testGetMethods_NoMethodsOnClass() {
		HashSet<Method> methods = ReflectionUtil.getMethods(Empty.class);
		
		// there are 9 default methods on the object class
		assertEquals(9, methods.size());
	}
	
	@Test
	public void testGetMethods_MethodsOnClass(){
		HashSet<Method> methods = ReflectionUtil.getMethods(Methods.class);
		HashSet<Method> parentMethods = ReflectionUtil.getMethods(Object.class);
		
		assertEquals(11, methods.size());
		assertEquals(2, methods.size() - parentMethods.size());
	}

}
