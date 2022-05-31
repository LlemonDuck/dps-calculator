package com.duckblade.osrs.dpscalc.graphing;

import com.google.inject.grapher.ShortNameFactory;
import java.lang.reflect.Member;

final class GraphingComputeContextNameFactory extends ShortNameFactory
{

	private final GraphingComputeContext context;

	GraphingComputeContextNameFactory(GraphingComputeContext context)
	{
		this.context = context;
	}

	@Override
	public String getMemberName(Member member)
	{
		String key = member.getDeclaringClass().getSimpleName();
		if (key.endsWith("Computable"))
		{
			key = key.substring(0, key.length() - 10);
		}

		Object raw = context.getRaw(key);
		if (raw == null)
		{
			return "null";
		}
		return raw.toString();
	}
}
