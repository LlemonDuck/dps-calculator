package com.duckblade.osrs.dpscalc.calc3.meta.context;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import lombok.RequiredArgsConstructor;

public class CallGraphBuilder
{

	@RequiredArgsConstructor
	private static class TreeNode
	{
		final String name;
		Object val;
		List<TreeNode> children = new ArrayList<>();

		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			sb.append("class ");
			sb.append(name);
			sb.append(" { ");
			sb.append(sanitizeObjString(val));
			sb.append(" }\n");
			for (TreeNode child : children)
			{
				sb.append(name);
				sb.append(" --|> ");
				sb.append(child.name);
				sb.append('\n');
				sb.append(child);
			}

			return sb.toString();
		}

		private static String sanitizeObjString(Object val)
		{
			String classPrefix = "";
			if (val instanceof Set)
			{
				classPrefix = "Set";
			}
			else if (val instanceof Map)
			{
				classPrefix = "Map";
			}
			else if (val instanceof List)
			{
				classPrefix = "List";
			}
			return classPrefix + Objects.toString(val)
				.replaceAll("\\{", "(")
				.replaceAll("}", ")")
				.replaceAll("\\[", "(")
				.replaceAll("]", ")")
				.replaceAll(",", ",\\\\n")
				.replaceAll("\\([.^)]{4,}\\)", "(\\\\n$1)");
		}
	}

	private TreeNode root;
	private final Deque<TreeNode> workingStack = new ArrayDeque<>();

	public void enter(String name)
	{
		TreeNode next = new TreeNode(name);
		if (!workingStack.isEmpty())
		{
			workingStack.peek().children.add(next);
		}
		else
		{
			root = next;
		}

		workingStack.push(next);
	}

	public void exit(Object val)
	{
		workingStack.pop().val = val;
	}

	public void recordPut(ContextValue<?> output, Object val)
	{
		TreeNode current = workingStack.peek();
		if (current != null && output instanceof ComputeOutput)
		{
			TreeNode child = new TreeNode(output.key());
			child.val = val;
			current.children.add(child);
		}
	}

	public String toString()
	{
		Set<String> t = new TreeSet<>(Arrays.asList(root.toString().split("\n")));
		return "classDiagram\n" + String.join("\n", t);
	}

}
