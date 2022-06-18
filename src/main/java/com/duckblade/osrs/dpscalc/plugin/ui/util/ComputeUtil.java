package com.duckblade.osrs.dpscalc.plugin.ui.util;

import com.duckblade.osrs.dpscalc.calc.exceptions.DpsComputeException;
import com.duckblade.osrs.dpscalc.calc.exceptions.MissingInputException;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class ComputeUtil
{

	public void computeSilent(Runnable compute)
	{
		try
		{
			compute.run();
		}
		catch (DpsComputeException e)
		{
			if (!(e.getCause() instanceof MissingInputException))
			{
				throw e;
			}
		}
	}

	public <T> T tryCompute(Supplier<T> compute)
	{
		try
		{
			return compute.get();
		}
		catch (DpsComputeException e)
		{
			if (!(e.getCause() instanceof MissingInputException))
			{
				throw e;
			}
			return null;
		}
	}

}
