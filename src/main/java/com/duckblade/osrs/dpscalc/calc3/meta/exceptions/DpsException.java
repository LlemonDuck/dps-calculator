package com.duckblade.osrs.dpscalc.calc3.meta.exceptions;

public class DpsException extends RuntimeException
{

	public DpsException(Exception inner)
	{
		super(inner);
	}

}
