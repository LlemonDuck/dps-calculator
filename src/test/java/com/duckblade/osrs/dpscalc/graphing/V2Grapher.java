package com.duckblade.osrs.dpscalc.graphing;

import com.duckblade.osrs.dpscalc.ItemStatsConstants;
import com.duckblade.osrs.dpscalc.TestNpcStats;
import com.duckblade.osrs.dpscalc.TestSkills;
import com.duckblade.osrs.dpscalc.calc.DptComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.grapher.graphviz.GraphvizGrapher;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

// customize inputs in configureInputs, then run main
// output file is graphviz format in file dps.dot
public class V2Grapher
{

	public static void configureInputs(ComputeContext context)
	{
		ItemStats weapon = ItemStatsConstants.SCYTHE;
		context.put(ComputeInputs.ATTACKER_EQUIPMENT, ItemStatsConstants.maxMelee(weapon));
		context.put(ComputeInputs.ATTACK_STYLE, weapon.getWeaponCategory().getAttackStyles().get(1));
		context.put(ComputeInputs.ATTACKER_SKILLS, TestSkills.MAXED_WITH_BOOSTS);
		context.put(ComputeInputs.ATTACKER_PRAYERS, Collections.singleton(Prayer.PIETY));

		TestNpcStats.verzikP2(context);
	}

	public static void main(String[] args) throws Exception
	{
		Injector injector = Guice.createInjector(new GraphingDpsComputeModule());
		Class<DptComputable> root = DptComputable.class;

		ComputeContext context = injector.getInstance(ComputeContext.class);
		configureInputs(context);
		context.get(injector.getInstance(root));

		StringWriter sw = new StringWriter();
		try (PrintWriter out = new PrintWriter(sw))
		{
			GraphvizGrapher grapher = injector.getInstance(GraphvizGrapher.class);
			grapher.setOut(out);
			grapher.setRankdir("TB");
			grapher.graph(injector, Collections.singleton(Key.get(root)));
		}

		String dotFile = sw.toString();
		String processedDotFile = postProcess(dotFile);
		try (FileWriter fw = new FileWriter("dps.dot"))
		{
			fw.write(processedDotFile);
		}

		Runtime.getRuntime().exec("dot -Tpng dps.dot -o dps.png").waitFor();
	}

	private static String postProcess(String dotFile)
	{
		StringWriter outSw = new StringWriter();
		PrintWriter output = new PrintWriter(outSw);

		Scanner s = new Scanner(dotFile);
		output.println(s.nextLine()); // first two lines are always meta
		output.println(s.nextLine());

		HashMap<String, String> originalNodeLine = new HashMap<>();
		Set<String> nodes = new HashSet<>();
		Set<String> dropNodes = new HashSet<>();
		Set<Pair<String, String>> edges = new HashSet<>();

		// pull existing nodes and edges
		while (s.hasNext())
		{
			String line = s.nextLine();
			String[] parts = line.split(" ");

			boolean isNode = !(parts.length > 2 && parts[1].equals("->"));
			if (isNode)
			{
				String nodeId = parts[0];
				originalNodeLine.put(nodeId, line);
				nodes.add(nodeId);
				if (line.contains("null") || line.toLowerCase().contains("multibinder") || line.contains("Set&lt;"))
				{
					dropNodes.add(nodeId);
				}
			}
			else
			{
				String start = parts[0].split(":")[0];
				String end = parts[2].split(":")[0];
				edges.add(Pair.of(start, end));
			}
		}

		// drop edges and nodes with null values
		Optional<String> toDropOpt;
		Function<String, Predicate<Pair<String, String>>> edgeContainsNode = node -> edge -> edge.getRight().equals(node) || edge.getLeft()
			.equals(node);
		while ((toDropOpt = dropNodes.stream().filter(nodes::contains).findFirst()).isPresent())
		{
			String toDrop = toDropOpt.get();

			Set<String> parents = edges.stream()
				.filter(edge -> edge.getRight().equals(toDrop))
				.map(Pair::getLeft)
				.collect(Collectors.toSet());

			Set<String> children = edges.stream()
				.filter(edge -> edge.getLeft().equals(toDrop))
				.map(Pair::getRight)
				.collect(Collectors.toSet());

			Sets.cartesianProduct(parents, children)
				.forEach(list ->
				{
					String left = list.get(0);
					String right = list.get(1);
					nodes.add(left);
					nodes.add(right);
					edges.add(Pair.of(left, right));
				});

			edges.removeIf(edgeContainsNode.apply(toDrop));
			nodes.remove(toDrop);
		}

		nodes.removeIf(node -> edges.stream().noneMatch(edgeContainsNode.apply(node)));

		nodes.forEach(node ->
			output.println(originalNodeLine.get(node)));
		edges.forEach(edge ->
			output.println(edge.getLeft() + " -> " + edge.getRight()));

		output.println("}");
		output.close();

		return outSw.toString();
	}

}
