package com.zabawaba.reflector.classes;

public class SampleOne {
	public String field1;
	protected volatile int field2;
	@SuppressWarnings("unused")
	private volatile float field3;

	public String method1(){return field1;}
	public void method2(){}
	public int override(String param){return 1;}
	public int override(String param, String param2){ return 2;}
	protected static void notPublic(){}
	@SuppressWarnings("unused")
	private void methodPrivate(){}
	public void blowup(){throw new RuntimeException("BOOM!");}
}
