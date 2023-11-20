package com.duckblade.osrs.dpscalc.calc3;

import static com.duckblade.osrs.dpscalc.calc3.Calc3TestData.kalphiteQueen;
import static com.duckblade.osrs.dpscalc.calc3.Calc3TestData.kerisPartisanBreaching;
import static com.duckblade.osrs.dpscalc.calc3.Calc3TestData.maxMelee;
import com.duckblade.osrs.dpscalc.calc3.core.Dps;
import com.duckblade.osrs.dpscalc.calc3.meta.DpsComputeModule3;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Inject;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

public class Calc3Test
{

	@Inject
	private Dps dps;

	public void start() throws Exception
	{
		ComputeContext ctx = new ComputeContext();
		maxMelee(ctx, kerisPartisanBreaching());
		kalphiteQueen(ctx);
		ctx.initDebug();

		Stopwatch sw = Stopwatch.createStarted();
		double dpsOut = ctx.get(dps);
		sw.stop();

		String mm = ctx.toGraph();
		try (FileWriter fw = new FileWriter("graph.md"))
		{
			fw.write("# Calculation took " + sw.elapsed(TimeUnit.MILLISECONDS) + "ms\n");
			fw.write("```mermaid\n");
			fw.write(mm);
			fw.write("\n```");
		}

		try (FileWriter fw = new FileWriter("dps.mmd"))
		{
			fw.write(mm);
		}

		Runtime.getRuntime().exec("powershell.exe \"mmdc -i dps.mmd -t dark -o dps.png --scale 4\"").waitFor();
	}

	public static void main(String[] args) throws Exception
	{
		Guice.createInjector(new DpsComputeModule3())
			.getInstance(Calc3Test.class)
			.start();
	}

}
