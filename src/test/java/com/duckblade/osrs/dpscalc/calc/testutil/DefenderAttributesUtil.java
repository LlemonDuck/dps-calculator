package com.duckblade.osrs.dpscalc.calc.testutil;

import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import net.runelite.api.NpcID;

public class DefenderAttributesUtil
{

	public static DefenderAttributes DEMON = DefenderAttributes.builder().isDemon(true).build();
	public static DefenderAttributes DRAGON = DefenderAttributes.builder().isDragon(true).build();
	public static DefenderAttributes KALPHITE = DefenderAttributes.builder().isKalphite(true).build();
	public static DefenderAttributes LEAFY = DefenderAttributes.builder().isLeafy(true).build();
	public static DefenderAttributes UNDEAD = DefenderAttributes.builder().isUndead(true).build();
	public static DefenderAttributes VAMPYRE1 = DefenderAttributes.builder().isVampyre1(true).build();
	public static DefenderAttributes VAMPYRE2 = DefenderAttributes.builder().isVampyre2(true).build();
	public static DefenderAttributes VAMPYRE3 = DefenderAttributes.builder().isVampyre3(true).build();

	public static DefenderAttributes MAX_MAGIC = DefenderAttributes.builder().accuracyMagic(350).build();
	public static DefenderAttributes MIN_MAGIC = DefenderAttributes.builder().accuracyMagic(0).build();

	public static DefenderAttributes ZULRAH = DefenderAttributes.builder().name("Zulrah").npcId(NpcID.ZULRAH).build();
	public static DefenderAttributes CALLISTO = DefenderAttributes.builder().name("Callisto").npcId(NpcID.CALLISTO).build();

	public static DefenderAttributes SIZE_1 = DefenderAttributes.builder().size(1).build();
	public static DefenderAttributes SIZE_2 = DefenderAttributes.builder().size(2).build();
	public static DefenderAttributes SIZE_3 = DefenderAttributes.builder().size(3).build();
	public static DefenderAttributes SIZE_4 = DefenderAttributes.builder().size(3).build();

}
