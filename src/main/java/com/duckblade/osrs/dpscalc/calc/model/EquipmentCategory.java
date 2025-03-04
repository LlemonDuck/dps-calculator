package com.duckblade.osrs.dpscalc.calc.model;

import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.ACCURATE;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.AGGRESSIVE;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.AUTOCAST;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.CONTROLLED;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.DEFENSIVE;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.DEFENSIVE_AUTOCAST;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.LONGRANGE;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.RAPID;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleType.CRUSH;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleType.MAGIC;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleType.RANGED;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleType.SLASH;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleType.STAB;
import com.duckblade.osrs.dpscalc.calc.model.gson.EquipmentCategoryAdapter;
import com.google.gson.annotations.JsonAdapter;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
@JsonAdapter(EquipmentCategoryAdapter.class)
public enum EquipmentCategory
{

	NONE(
		PlayerCombatStyle.builder().name("Punch").type(CRUSH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Kick").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Block").type(CRUSH).stance(DEFENSIVE).build()
	),
	TWO_HANDED_SWORD(
		PlayerCombatStyle.builder().name("Chop").type(SLASH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Slash").type(SLASH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Smash").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Block").type(SLASH).stance(DEFENSIVE).build()
	),
	AXE(
		PlayerCombatStyle.builder().name("Chop").type(SLASH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Hack").type(SLASH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Smash").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Block").type(SLASH).stance(DEFENSIVE).build()
	),
	BANNER(
		PlayerCombatStyle.builder().name("Lunge").type(STAB).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Swipe").type(SLASH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Pound").type(CRUSH).stance(CONTROLLED).build(),
		PlayerCombatStyle.builder().name("Block").type(STAB).stance(DEFENSIVE).build()
	),
	BLADED_STAFF(
		PlayerCombatStyle.builder().name("Jab").type(STAB).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Swipe").type(SLASH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Fend").type(CRUSH).stance(DEFENSIVE).build(),
		PlayerCombatStyle.builder().name("Spell").type(MAGIC).stance(DEFENSIVE_AUTOCAST).build(),
		PlayerCombatStyle.builder().name("Spell").type(MAGIC).stance(AUTOCAST).build()
	),
	BLASTER,
	BLUDGEON(
		PlayerCombatStyle.builder().name("Pound").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Pummel").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Smash").type(CRUSH).stance(AGGRESSIVE).build()
	),
	BLUNT(
		PlayerCombatStyle.builder().name("Pound").type(CRUSH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Pummel").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Block").type(CRUSH).stance(DEFENSIVE).build()
	),
	BOW(
		PlayerCombatStyle.builder().name("Accurate").type(RANGED).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Rapid").type(RANGED).stance(RAPID).build(),
		PlayerCombatStyle.builder().name("Longrange").type(RANGED).stance(LONGRANGE).build()
	),
	BULWARK(
		PlayerCombatStyle.builder().name("Pummel").type(CRUSH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Block").type(null).stance(null).build()
	),
	CHINCHOMPA(
		PlayerCombatStyle.builder().name("Short fuse").type(RANGED).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Medium fuse").type(RANGED).stance(RAPID).build(),
		PlayerCombatStyle.builder().name("Long fuse").type(RANGED).stance(LONGRANGE).build()
	),
	CLAW(
		PlayerCombatStyle.builder().name("Chop").type(SLASH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Slash").type(SLASH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Lunge").type(STAB).stance(CONTROLLED).build(),
		PlayerCombatStyle.builder().name("Block").type(SLASH).stance(DEFENSIVE).build()
	),
	CROSSBOW(
		PlayerCombatStyle.builder().name("Accurate").type(RANGED).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Rapid").type(RANGED).stance(RAPID).build(),
		PlayerCombatStyle.builder().name("Longrange").type(RANGED).stance(LONGRANGE).build()
	),
	DAGGER,
	GUN(
		PlayerCombatStyle.builder().name("Kick").type(CRUSH).stance(AGGRESSIVE).build()
	),
	PARTISAN(
		PlayerCombatStyle.builder().name("Stab").type(STAB).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Lunge").type(STAB).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Pound").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Block").type(STAB).stance(DEFENSIVE).build()
	),
	PICKAXE(
		PlayerCombatStyle.builder().name("Spike").type(STAB).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Impale").type(STAB).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Smash").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Block").type(STAB).stance(DEFENSIVE).build()
	),
	POLEARM(
		PlayerCombatStyle.builder().name("Jab").type(STAB).stance(CONTROLLED).build(),
		PlayerCombatStyle.builder().name("Swipe").type(SLASH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Fend").type(STAB).stance(DEFENSIVE).build()
	),
	POLESTAFF(
		PlayerCombatStyle.builder().name("Bash").type(CRUSH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Pound").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Block").type(CRUSH).stance(DEFENSIVE).build()
	),
	POWERED_STAFF(
		PlayerCombatStyle.builder().name("Accurate").type(MAGIC).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Accurate").type(MAGIC).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Longrange").type(MAGIC).stance(LONGRANGE).build()
	),
	POWERED_WAND(
		PlayerCombatStyle.builder().name("Accurate").type(MAGIC).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Accurate").type(MAGIC).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Longrange").type(MAGIC).stance(LONGRANGE).build()
	),
	SALAMANDER(
		PlayerCombatStyle.builder().name("Scorch").type(SLASH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Flare").type(RANGED).stance(RAPID).build(),
		PlayerCombatStyle.builder().name("Blaze").type(MAGIC).stance(DEFENSIVE).build()
	),
	SCYTHE(
		PlayerCombatStyle.builder().name("Reap").type(SLASH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Chop").type(SLASH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Jab").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Block").type(SLASH).stance(DEFENSIVE).build()
	),
	SLASH_SWORD(
		PlayerCombatStyle.builder().name("Chop").type(SLASH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Slash").type(SLASH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Lunge").type(STAB).stance(CONTROLLED).build(),
		PlayerCombatStyle.builder().name("Block").type(SLASH).stance(DEFENSIVE).build()
	),
	SPEAR(
		PlayerCombatStyle.builder().name("Lunge").type(STAB).stance(CONTROLLED).build(),
		PlayerCombatStyle.builder().name("Swipe").type(SLASH).stance(CONTROLLED).build(),
		PlayerCombatStyle.builder().name("Pound").type(CRUSH).stance(CONTROLLED).build(),
		PlayerCombatStyle.builder().name("Block").type(STAB).stance(DEFENSIVE).build()
	),
	SPIKED(
		PlayerCombatStyle.builder().name("Pound").type(CRUSH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Pummel").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Spike").type(STAB).stance(CONTROLLED).build(),
		PlayerCombatStyle.builder().name("Block").type(CRUSH).stance(DEFENSIVE).build()
	),
	STAB_SWORD(
		PlayerCombatStyle.builder().name("Stab").type(STAB).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Lunge").type(STAB).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Slash").type(SLASH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Block").type(STAB).stance(DEFENSIVE).build()
	),
	STAFF(
		PlayerCombatStyle.builder().name("Bash").type(CRUSH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Pound").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Focus").type(CRUSH).stance(DEFENSIVE).build(),
		PlayerCombatStyle.builder().name("Spell").type(MAGIC).stance(DEFENSIVE_AUTOCAST).build(),
		PlayerCombatStyle.builder().name("Spell").type(MAGIC).stance(AUTOCAST).build()
	),
	THROWN(
		PlayerCombatStyle.builder().name("Accurate").type(RANGED).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Rapid").type(RANGED).stance(RAPID).build(),
		PlayerCombatStyle.builder().name("Longrange").type(RANGED).stance(LONGRANGE).build()
	),
	UNARMED(
		PlayerCombatStyle.builder().name("Punch").type(CRUSH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Kick").type(CRUSH).stance(AGGRESSIVE).build(),
		PlayerCombatStyle.builder().name("Block").type(CRUSH).stance(DEFENSIVE).build()
	),
	WHIP(
		PlayerCombatStyle.builder().name("Flick").type(SLASH).stance(ACCURATE).build(),
		PlayerCombatStyle.builder().name("Lash").type(SLASH).stance(CONTROLLED).build(),
		PlayerCombatStyle.builder().name("Deflect").type(SLASH).stance(DEFENSIVE).build()
	),
	;

	private final List<PlayerCombatStyle> styles;

	EquipmentCategory(PlayerCombatStyle... styles)
	{
		this.styles = Arrays.asList(styles);
	}

	public static List<PlayerCombatStyle> getCombatStylesForCategory(EquipmentCategory category)
	{
		return category.getStyles();
	}

	public static List<PlayerCombatStyle> getCombatStylesForCategory(EquipmentPiece equipmentPiece)
	{
		if (equipmentPiece == null || equipmentPiece.getCategory() == null)
		{
			return UNARMED.getStyles();
		}

		return equipmentPiece.getCategory().getStyles();
	}

}
