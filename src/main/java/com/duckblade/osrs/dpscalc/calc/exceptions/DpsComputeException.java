package com.duckblade.osrs.dpscalc.calc.exceptions;

public class DpsComputeException extends RuntimeException
{

	public DpsComputeException(Exception inner)
	{
		super(inner);
	}

}
