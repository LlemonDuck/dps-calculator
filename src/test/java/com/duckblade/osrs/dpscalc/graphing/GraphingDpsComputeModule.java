package com.duckblade.osrs.dpscalc.graphing;

import com.duckblade.osrs.dpscalc.calc.DpsComputeModule;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.google.inject.grapher.NameFactory;
import com.google.inject.grapher.graphviz.PortIdFactory;
import com.google.inject.grapher.graphviz.PortIdFactoryImpl;
import java.lang.annotation.Annotation;

public class GraphingDpsComputeModule extends DpsComputeModule
{

	@Override
	protected void configure()
	{
		super.configure();
		GraphingComputeContext context = new GraphingComputeContext();
		bind(ComputeContext.class).toInstance(context);

		try
		{
			// this is a pretty janky hack but the class isn't public so whatever
			//noinspection unchecked
			Class<? extends Annotation> graphviz = (Class<? extends Annotation>) Class.forName("com.google.inject.grapher.graphviz.Graphviz");
			bind(NameFactory.class)
				.annotatedWith(graphviz)
				.toInstance(new GraphingComputeContextNameFactory(context));

			bind(PortIdFactory.class)
				.annotatedWith(graphviz)
				.to(PortIdFactoryImpl.class);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

}
