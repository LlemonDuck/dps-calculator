package com.duckblade.osrs.dpscalc.calc.compute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ComputableTest
{

	private static class TestClass1 implements Computable<String>
	{
		@Override
		public String compute(ComputeContext context)
		{
			throw new IllegalStateException();
		}
	}

	private static class TestClass2Computable implements Computable<String>
	{
		@Override
		public String compute(ComputeContext context)
		{
			throw new IllegalStateException();
		}
	}

	@Test
	void defaultKeyIsClassName()
	{
		assertEquals("TestClass1", new TestClass1().key());
	}

	@Test
	void defaultKeyRemovesComputableSuffix()
	{
		assertEquals("TestClass2", new TestClass2Computable().key());
	}

}