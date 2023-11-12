package com.duckblade.osrs.dpscalc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Scratch
{

	@Value
	static class Outcome
	{
		double probability;
		List<Integer> hits;

		int dimension()
		{
			return hits.size();
		}

		Outcome zip(Outcome other)
		{
			List<Integer> newHits = new ArrayList<>(this.hits);
			newHits.addAll(other.hits);

			return new Outcome(
				this.probability * other.probability,
				newHits
			);
		}

		Pair<Outcome, Outcome> shift()
		{
			assert this.hits.size() > 1;
			return new ImmutablePair<>(
				new Outcome(
					this.probability,
					Collections.singletonList(this.hits.get(0))
				),
				new Outcome(
					1.0,
					this.hits.subList(1, this.hits.size())
				)
			);
		}

		Dist transform(Transformer t)
		{
			if (dimension() == 1)
			{
				Dist d = new Dist(1);
				for (Outcome o : t.apply(this.hits.get(0)))
				{
					d.add(new Outcome(this.probability * o.probability, o.hits));
				}
				return d;
			}

			Pair<Outcome, Outcome> shifted = shift();
			return shifted.getLeft().transform(t)
				.zip(shifted.getRight().transform(t));
		}
	}

	@Value
	static class Dist implements Iterable<Outcome>
	{
		int dimension;
		List<Outcome> outcomes = new ArrayList<>();

		void add(Outcome o)
		{
			assert o.dimension() == this.dimension;
			this.outcomes.add(o);
		}

		Dist zip(Dist other)
		{
			Dist d = new Dist(this.dimension + other.dimension);
			for (Outcome o : this)
			{
				for (Outcome p : other)
				{
					d.add(o.zip(p));
				}
			}

			return d;
		}

		Dist transform(Transformer t)
		{
			Dist d = new Dist(this.dimension);

			for (Outcome o : this)
			{
				for (Outcome p : o.transform(t))
				{
					d.add(p);
				}
			}

			return d;
		}

		static Dist linear(int max)
		{
			Dist d = new Dist(1);
			double constProb = 1.0 / (max + 1);

			for (int i = 0; i <= max; i++)
			{
				d.add(new Outcome(
					constProb,
					Collections.singletonList(i)
				));
			}

			return d;
		}

		@Nonnull
		@Override
		public Iterator<Outcome> iterator()
		{
			return outcomes.listIterator();
		}

		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Dist{ dim=");
			sb.append(dimension);
			sb.append('\n');

			for (Outcome outcome : this)
			{
				sb.append('\t');
				sb.append(outcome);
				sb.append('\n');
			}

			sb.append('}');
			return sb.toString();
		}
	}

	static abstract class Transformer
	{
		abstract Dist apply(int hit);
	}

	@RequiredArgsConstructor
	static class LinearMinTransformer extends Transformer
	{

		private final int max;

		@Override
		Dist apply(int hit)
		{
			double constProb = 1.0 / (max + 1);

			Dist d = new Dist(1);
			for (int limit = 0; limit <= max; limit++)
			{
				d.add(new Outcome(
					constProb,
					Collections.singletonList(Math.min(hit, limit))
				));
			}

			return d;
		}
	}

	public static void main(String[] args)
	{
		Dist d = Dist.linear(4).zip(Dist.linear(4));
		System.out.println(d);
		System.out.println(d.transform(new LinearMinTransformer(1)));
	}

}
