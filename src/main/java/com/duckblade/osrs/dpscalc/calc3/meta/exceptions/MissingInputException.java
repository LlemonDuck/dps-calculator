package com.duckblade.osrs.dpscalc.calc3.meta.exceptions;

import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;

public class MissingInputException extends RuntimeException
{

	public MissingInputException(ContextValue<?> missingInput)
	{
		super(String.format("ComputeInput [%s] was not supplied and has no default value.", missingInput.key()));
	}

}
