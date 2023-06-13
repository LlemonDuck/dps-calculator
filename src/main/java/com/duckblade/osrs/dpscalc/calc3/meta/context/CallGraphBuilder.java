package com.duckblade.osrs.dpscalc.calc3.meta.context;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
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
			return Objects.toString(val)
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

	public String toString()
	{
		Set<String> t = new TreeSet<>(Arrays.asList(root.toString().split("\n")));
		return "classDiagram\n" + String.join("\n", t);
	}

}
