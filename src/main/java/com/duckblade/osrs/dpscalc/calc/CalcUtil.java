package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.model.CombatMode;
import com.duckblade.osrs.dpscalc.model.MeleeStyle;
import com.duckblade.osrs.dpscalc.model.Spell;

import static com.duckblade.osrs.dpscalc.calc.EquipmentRequirement.*;

public class CalcUtil
{
	
	public static boolean blackMask(CalcInput input)
	{
		if (!input.isOnSlayerTask())
			return false;

		if (input.getCombatMode() == CombatMode.MELEE)
			return BLACK_MASK_MELEE.isSatisfied(input) || BLACK_MASK_MAGE_RANGED.isSatisfied(input);
		
		return BLACK_MASK_MAGE_RANGED.isSatisfied(input);
	}
	
	// salve has 3 levels - off, on, enhanced
	public static int salveLevel(CalcInput input)
	{
		if (!input.getNpcTarget().isUndead())
			return 0;

		if (input.getCombatMode() == CombatMode.MELEE)
		{
			if (!SALVE_MELEE.isSatisfied(input))
				return 0;
		}
		else if (!SALVE_MAGE_RANGED.isSatisfied(input))
			return 0;
		
		return SALVE_ENHANCED.isSatisfied(input) ? 2 : 1;
	}
	
	// void also has 3 levels, but only for ranged/mage
	public static int voidLevel(CalcInput input)
	{
		if (input.getCombatMode() == CombatMode.MELEE)
			return VOID_MELEE.isSatisfied(input) ? 1 : 0;
		
		if (input.getCombatMode() == CombatMode.RANGED && !VOID_RANGED.isSatisfied(input))
			return 0;
		if (input.getCombatMode() == CombatMode.MAGE && !VOID_MAGE.isSatisfied(input))
			return 0;
		
		return VOID_ELITE.isSatisfied(input) ? 2 : 1;
	}
	
	public static boolean dragonHunter(CalcInput input)
	{
		if (!input.getNpcTarget().isDragon())
			return false;
		
		return DRAGON_HUNTER.isSatisfied(input);
	}
	
	public static float tbowAttModifier(CalcInput input)
	{
		int magic = Math.max(input.getNpcTarget().getMagicAccuracy(), input.getNpcTarget().getLevelMagic()); // todo cox detection?
		magic = Math.min(250, magic);
		
		float t1 = 140f;
		float t2 = ((10f * 3f * magic) / 10f - 10f) / 100f;
		float t3 = (float) Math.pow(3f * magic / 10f - 100f, 2f) / 100f;
		return (t1 + t2 - t3) / 100f; // to multiplier
	}

	public static float tbowDmgModifier(CalcInput input)
	{
		int magic = Math.max(input.getNpcTarget().getMagicAccuracy(), input.getNpcTarget().getLevelMagic()); // todo cox detection?
		magic = Math.min(250, magic);

		float t1 = 250f;
		float t2 = (10f * 3f * magic / 10f - 14f) / 100f;
		float t3 = (float) Math.pow(3f * magic / 10f - 140f, 2f) / 100f;
		return (t1 + t2 - t3) / 100f; // to multiplier
	}
	
	public static float leafyMod(CalcInput input)
	{
		if (!input.getNpcTarget().isLeafy())
			return 1f;
		
		switch (input.getCombatMode())
		{
			case MAGE:
				return input.getSpell() == Spell.MAGIC_DART ? 1f : 0f; // immune to other spells
			case MELEE:
				if (!LEAF_BLADED_MELEE.isSatisfied(input))
					return 0f;
				return LEAF_BLADED_BAXE.isSatisfied(input) ? 1.175f : 1f; // only baxe has bonus
			default:
				return LEAF_BLADED_RANGED.isSatisfied(input) ? 1f : 0f; // no bonuses, but required
		}
	}
	
	public static float crystalStrMod(CalcInput input)
	{
		if (!CRYSTAL_BOW.isSatisfied(input))
			return 1f;

		float mod = 1f;
		mod += CRYSTAL_HEAD.isSatisfied(input) ? 0.025f : 0f;
		mod += CRYSTAL_BODY.isSatisfied(input) ? 0.075f : 0f;
		mod += CRYSTAL_LEGS.isSatisfied(input) ? 0.05f  : 0f;
		return mod;
	}
	
	public static float crystalAttMod(CalcInput input)
	{
		return 2f * (crystalStrMod(input) - 1f) + 1f;
	}
	
	public static boolean obsidianArmour(CalcInput input)
	{
		return OBSIDIAN_ARMOUR.isSatisfied(input) && OBSIDIAN_WEAPON.isSatisfied(input);
	}
	
	public static boolean obsidianNecklace(CalcInput input)
	{
		return OBSIDIAN_NECKLACE.isSatisfied(input) && OBSIDIAN_WEAPON.isSatisfied(input);
	}
	
	public static float inquisitorsMod(CalcInput input)
	{
		if (input.getWeaponMode().getMeleeStyle() != MeleeStyle.CRUSH) // crush only
			return 1f;
		
		if (INQUISITOR_FULL.isSatisfied(input)) // 2.5% if all 3
			return 1.025f;
		
		float mod = 1f; // otherwise 0.5% per piece
		if (INQUISITOR_HELM.isSatisfied(input))
			mod += 0.005f;
		if (INQUISITOR_BODY.isSatisfied(input))
			mod += 0.005f;
		if (INQUISITOR_LEGS.isSatisfied(input))
			mod += 0.005f;
		
		return mod;
	}
	
	public static int demonbaneLevel(CalcInput input)
	{
		if (!input.getNpcTarget().isDemon())
			return 0;
		
		if (DEMONBANE_ARCLIGHT.isSatisfied(input))
			return 2;
		else if (DEMONBANE_DARKLIGHT.isSatisfied(input) || DEMONBANE_SILVERLIGHT.isSatisfied(input))
			// i think they're the same, but wiki isn't clear
			return 1;
		else
			return 0;
	}
	
	public static float wildyAttackBonus(CalcInput input)
	{
		if (!input.isInWilderness())
		{
			return 1f;
		}
		
		switch (input.getCombatMode())
		{
			default:
			case MELEE:
				return WILDY_MELEE.isSatisfied(input) ? 1.5f : 1f;
				
			case RANGED:
				return WILDY_RANGED.isSatisfied(input) ? 1.5f : 1f;
				
			case MAGE:
				return WILDY_MAGIC.isSatisfied(input) ? 2f : 1f;
		}
	}
	
	public static float wildyDamageBonus(CalcInput input)
	{
		if (!input.isInWilderness())
		{
			return 1f;
		}
		
		switch (input.getCombatMode())
		{
			default:
			case MELEE:
				return WILDY_MELEE.isSatisfied(input) ? 1.5f : 1f;
				
			case RANGED:
				return WILDY_RANGED.isSatisfied(input) ? 1.5f : 1f;
				
			case MAGE:
				return WILDY_MAGIC.isSatisfied(input) ? 1.25f : 1f;
		}
	}
	
}