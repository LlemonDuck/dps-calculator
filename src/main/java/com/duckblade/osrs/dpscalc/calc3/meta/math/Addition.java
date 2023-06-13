package com.duckblade.osrs.dpscalc.calc3.meta.math;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Addition implements Operation
{

	private final int addend;

	@Override
	public int apply(int previous)
	{
		return previous + addend;
	}

	@Override
	public String toString()
	{
		String sgn = addend == 0 ? "" : addend > 0 ? "+" : "-";
		return sgn + addend;
	}

}
