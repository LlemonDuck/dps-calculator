package com.duckblade.osrs.dpscalc.calc.compute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComputeOutputTest
{

	@Mock
	private ComputeContext context;

	@Test
	void usesProvidedKeyAsComputeKey()
	{
		ComputeOutput<String> output = ComputeOutput.of("MOCK_Key");
		assertEquals("MOCK_Key", output.key());
	}

	@Test
	void returnsNullWhenComputed()
	{
		ComputeOutput<String> missingOutput = ComputeOutput.of("MOCK_Missing");
		assertNull(missingOutput.compute(context));
	}

}