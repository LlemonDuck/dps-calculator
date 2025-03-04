package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.dist.AttackDistribution;
import com.duckblade.osrs.dpscalc.calc.dist.HitDistribution;
import com.duckblade.osrs.dpscalc.calc.dist.Hitsplat;
import com.duckblade.osrs.dpscalc.calc.dist.WeightedHit;
import java.util.Arrays;
import lombok.Value;

public class Claws
{

	@Value
	private static class ClawRoll
	{
		double chancePerDmg;
		int low;
		int high;
	}

	private static ClawRoll generateTotals(int accRoll, int totalRolls, double acc, int max, int highOffset)
	{
		int low = max * (totalRolls - accRoll) / 4;
		int high = max + low + highOffset;
		double chancePreviousRollsFail = Math.pow(1 - acc, accRoll);
		double chanceThisRollPasses = chancePreviousRollsFail * acc;
		double chancePerDmg = chanceThisRollPasses / (high - low + 1);

		return new ClawRoll(chancePerDmg, low, high);
	}

	public static AttackDistribution dClawDist(double acc, int max)
	{
		HitDistribution.HitDistributionBuilder dist = HitDistribution.builder();

		for (int accRoll = 0; accRoll < 4; accRoll++)
		{
			ClawRoll clawRoll = generateTotals(accRoll, 4, acc, max, -1);
			for (int dmg = clawRoll.low; dmg <= clawRoll.high; dmg++)
			{
				switch (accRoll)
				{
					case 0:
						dist.hit(new WeightedHit(
							clawRoll.chancePerDmg,
							new Hitsplat(dmg / 2),
							new Hitsplat(dmg / 4),
							new Hitsplat(dmg / 8),
							new Hitsplat(dmg / 8 + 1)
						));
						break;

					case 1:
						dist.hit(new WeightedHit(
							clawRoll.chancePerDmg,
							new Hitsplat(dmg / 2),
							new Hitsplat(dmg / 4),
							new Hitsplat(dmg / 4 + 1),
							Hitsplat.INACCURATE
						));
						break;

					case 2:
						dist.hit(new WeightedHit(
							clawRoll.chancePerDmg,
							new Hitsplat(dmg / 2),
							new Hitsplat(dmg / 2 + 1),
							Hitsplat.INACCURATE,
							Hitsplat.INACCURATE
						));
						break;

					case 3:
						dist.hit(new WeightedHit(
							clawRoll.chancePerDmg,
							new Hitsplat(dmg + 1),
							Hitsplat.INACCURATE,
							Hitsplat.INACCURATE,
							Hitsplat.INACCURATE
						));
						break;
				}
			}
		}

		double chanceAllFail = Math.pow(1 - acc, 4);
		dist.hit(new WeightedHit(
			chanceAllFail * 2 / 3,
			new Hitsplat(1, false),
			new Hitsplat(1, false),
			Hitsplat.INACCURATE,
			Hitsplat.INACCURATE
		));
		dist.hit(new WeightedHit(
			chanceAllFail / 3,
			Hitsplat.INACCURATE,
			Hitsplat.INACCURATE,
			Hitsplat.INACCURATE,
			Hitsplat.INACCURATE
		));

		return new AttackDistribution(dist.build());
	}

	public static AttackDistribution burningClawSpec(double acc, int max)
	{
		HitDistribution.HitDistributionBuilder dist = HitDistribution.builder();

		for (int accRoll = 0; accRoll < 3; accRoll++)
		{
			ClawRoll clawRoll = generateTotals(accRoll, 3, acc, max, 0);
			for (int dmg = clawRoll.low; dmg <= clawRoll.high; dmg++)
			{
				switch (accRoll)
				{
					case 0:
						dist.hit(new WeightedHit(
							clawRoll.chancePerDmg,
							new Hitsplat(dmg / 2),
							new Hitsplat(dmg / 4),
							new Hitsplat(dmg / 4)
						));
						break;

					case 1:
						dist.hit(new WeightedHit(
							clawRoll.chancePerDmg,
							new Hitsplat(dmg / 2 - 1),
							new Hitsplat(dmg / 4 - 1),
							new Hitsplat(2)
						));
						break;

					case 2:
						dist.hit(new WeightedHit(
							clawRoll.chancePerDmg,
							new Hitsplat(dmg - 2),
							new Hitsplat(1),
							new Hitsplat(1)
						));
						break;
				}
			}
		}

		double chanceAllFail = Math.pow(1 - acc, 3);
		dist.hit(new WeightedHit(
			chanceAllFail / 5,
			Hitsplat.INACCURATE,
			Hitsplat.INACCURATE,
			Hitsplat.INACCURATE
		));
		dist.hit(new WeightedHit(
			chanceAllFail * 2 / 5,
			new Hitsplat(1, false),
			Hitsplat.INACCURATE,
			Hitsplat.INACCURATE
		));
		dist.hit(new WeightedHit(
			chanceAllFail * 2 / 5,
			new Hitsplat(1, false),
			new Hitsplat(1, false),
			Hitsplat.INACCURATE
		));

		return new AttackDistribution(dist.build());
	}

	private static int[][] BURN_MATRIX = new int[][]{
		{0, 0, 0},
		{0, 0, 1},
		{0, 1, 0},
		{0, 1, 1},
		{1, 0, 0},
		{1, 0, 1},
		{1, 1, 0},
		{1, 1, 1},
	};

	private static final double[] BURN_EXPECTED = new double[3];

	static
	{
		for (int accRoll = 0; accRoll < 3; accRoll++)
		{
			double acc = 0;
			for (int[] row : BURN_MATRIX)
			{
				double burnChance = 0.15 * (accRoll + 1);
				double burn1 = row[0] == 1 ? burnChance : 1 - burnChance;
				double burn2 = row[1] == 1 ? burnChance : 1 - burnChance;
				double burn3 = row[2] == 1 ? burnChance : 1 - burnChance;
				double chanceOfRow = burn1 * burn2 * burn3;

				int damage = Arrays.stream(row).map(entry -> entry * 10).sum();
				if (row[0] == 1 && row[1] == 1)
				{
					// there's a (presumed) bug here, where if the first two hitsplats apply burn,
					// then they overlap and miss 1 damage on the first tick.
					// https://discord.com/channels/177206626514632704/1098698914498101368/1285181996896620566
					damage -= 1;
				}

				acc += chanceOfRow * damage;
			}
			BURN_EXPECTED[accRoll] = acc;
		}
	}

	public static double burningClawDoT(double acc)
	{
		// 10 damage burn x3 hitsplats, 15/30/45% chance per splat dependent on which roll hits
		double accumulator = 0;

		for (int accRoll = 0; accRoll < 3; accRoll++)
		{
			double prevRollsFail = Math.pow(1 - acc, accRoll);
			double thisRollHits = prevRollsFail * acc;

			accumulator += thisRollHits * BURN_EXPECTED[accRoll];
		}

		return accumulator;
	}

}
