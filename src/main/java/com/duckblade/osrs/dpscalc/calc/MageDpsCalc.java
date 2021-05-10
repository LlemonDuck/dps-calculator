package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.model.CombatFocus;
import com.duckblade.osrs.dpscalc.model.NpcStats;
import com.duckblade.osrs.dpscalc.model.Spell;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.inject.Singleton;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;

import static com.duckblade.osrs.dpscalc.calc.CalcUtil.*;
import static com.duckblade.osrs.dpscalc.model.Spell.*;

@Singleton
// https://oldschool.runescape.wiki/w/Damage_per_second/Magic
public class MageDpsCalc extends AbstractCalc
{

	private static final Map<Spell, Function<CalcInput, Integer>> MAX_HIT_BONUS_PROVIDERS = new HashMap<>();

	static
	{
		MAX_HIT_BONUS_PROVIDERS.put(WIND_BOLT, i -> EquipmentRequirement.MAGE_CHAOS_GAUNTLETS.isSatisfied(i) ? 3 : 0);
		MAX_HIT_BONUS_PROVIDERS.put(WATER_BOLT, i -> EquipmentRequirement.MAGE_CHAOS_GAUNTLETS.isSatisfied(i) ? 3 : 0);
		MAX_HIT_BONUS_PROVIDERS.put(EARTH_BOLT, i -> EquipmentRequirement.MAGE_CHAOS_GAUNTLETS.isSatisfied(i) ? 3 : 0);
		MAX_HIT_BONUS_PROVIDERS.put(FIRE_BOLT, i -> EquipmentRequirement.MAGE_CHAOS_GAUNTLETS.isSatisfied(i) ? 3 : 0);
		MAX_HIT_BONUS_PROVIDERS.put(CLAWS_OF_GUTHIX, i -> i.isUsingCharge() ? 10 : 0);
		MAX_HIT_BONUS_PROVIDERS.put(SARADOMIN_STRIKE, i -> i.isUsingCharge() ? 10 : 0);
		MAX_HIT_BONUS_PROVIDERS.put(FLAMES_OF_ZAMORAK, i -> i.isUsingCharge() ? 10 : 0);
		MAX_HIT_BONUS_PROVIDERS.put(SEAS, i -> (magicLevel(i) - 75) / 3);
		MAX_HIT_BONUS_PROVIDERS.put(SWAMP, i -> (magicLevel(i) - 75) / 3);
		MAX_HIT_BONUS_PROVIDERS.put(SANGUINESTI, i -> (magicLevel(i) - 75) / 3);
		MAX_HIT_BONUS_PROVIDERS.put(MAGIC_DART, i ->
		{
			int weaponId = i.getPlayerEquipment().get(EquipmentInventorySlot.WEAPON).getItemId();
			boolean enhanced = weaponId == ItemID.SLAYERS_STAFF_E;
			if (enhanced)
				return (int) (magicLevel(i) * (1f / 10f));
			else
				return (int) (magicLevel(i) * (1f / 6f)) + 3;
		});
		MAX_HIT_BONUS_PROVIDERS.put(SALAMANDER_BLAZE, i ->
		{
			int weaponId = i.getPlayerEquipment().get(EquipmentInventorySlot.WEAPON).getItemId();
			int salamanderBase = 56;
			if (weaponId == ItemID.ORANGE_SALAMANDER)
				salamanderBase = 59;
			else if (weaponId == ItemID.RED_SALAMANDER)
				salamanderBase = 77;
			else if (weaponId == ItemID.BLACK_SALAMANDER)
				salamanderBase = 92;
			
			return (int) (magicLevel(i) * (64f + salamanderBase) / 640f);
		});
	}

	private static int magicLevel(CalcInput input)
	{
		return input.getPlayerSkills().get(Skill.MAGIC) + input.getPlayerBoosts().get(Skill.MAGIC);
	}
	
	public int effectiveLevel(CalcInput input)
	{
		int effLvl = magicLevel(input);

		effLvl = (int) (effLvl * input.getOffensivePrayer().getAttackMod());

		// tridents
		if (input.getWeaponMode().getCombatFocus() == CombatFocus.ACCURATE)
			effLvl += 3;
		else if (input.getWeaponMode().getCombatFocus() == CombatFocus.LONGRANGE)
			effLvl += 1;

		if (voidLevel(input) != 0)
			effLvl = (int) (effLvl * 1.145);
		
		return effLvl;
	}

	public int attackRoll(CalcInput input)
	{
		int equipmentAccuracy = input.getEquipmentStats().getAccuracyMagic();
		int attRoll = effectiveLevel(input) * (equipmentAccuracy + 64);

		if (salveLevel(input) == 2)
			attRoll = (int) (attRoll * (6f / 5f));
		else if (salveLevel(input) == 1 || blackMask(input))
			attRoll = (int) (attRoll * 1.15f);
		
		return attRoll;
	}

	public int defenseRoll(CalcInput input)
	{
		NpcStats target = input.getNpcTarget();
		return (target.getLevelMagic() + 9) * (target.getDefenseMagic() + 64);
	}

	public int maxHit(CalcInput input)
	{
		Spell spell = input.getSpell();
		int maxHit = spell.getBaseMaxHit();

		Function<CalcInput, Integer> bonusProvider = MAX_HIT_BONUS_PROVIDERS.get(spell);
		if (bonusProvider != null)
			maxHit += bonusProvider.apply(input);
		
		float magDmgBonus = input.getEquipmentStats().getStrengthMagic() / 100f + 1f;
		maxHit = (int) (maxHit * magDmgBonus);
		
		if (salveLevel(input) == 2)
			maxHit = (int) (maxHit * (6f / 5f));
		else if (salveLevel(input) == 1 || blackMask(input))
			maxHit = (int) (maxHit * 1.15f);
		
		if (EquipmentRequirement.FIRE_TOME.isSatisfied(input) && spell.getDisplayName().startsWith("Fire"))
			maxHit = (int) (maxHit * 1.5f);
		
		maxHit *= leafyMod(input);

		NpcStats target = input.getNpcTarget();
		if (target.getName() != null && target.getName().contains("Zulrah"))
			maxHit = Math.min(maxHit, 50);

		return maxHit;
	}

}
