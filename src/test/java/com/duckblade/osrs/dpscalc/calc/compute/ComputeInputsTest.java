package com.duckblade.osrs.dpscalc.calc.compute;

import com.duckblade.osrs.dpscalc.calc.exceptions.MissingInputException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComputeInputsTest
{

	@Mock
	private ComputeContext context;

	@Test
	void usesProvidedKeyAsComputeKey()
	{
		ComputeInputs<String> input = ComputeInputs.of("MOCK_Key");
		assertEquals("MOCK_Key", input.key());
	}

	@Test
	void throwsExceptionWhenNoDefault()
	{
		ComputeInputs<String> noDefaultInput = ComputeInputs.of("MOCK_NoDefault");
		assertThrows(MissingInputException.class, () -> noDefaultInput.compute(context));
	}

	@Test
	void returnsDefaultWhenSupplied()
	{
		ComputeInputs<String> defaultInput = ComputeInputs.of("MOCK_Default", "DefaultValue");
		assertEquals("DefaultValue", defaultInput.compute(context));
	}

}