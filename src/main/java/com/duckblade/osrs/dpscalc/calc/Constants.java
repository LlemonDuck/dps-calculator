package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Constants
{

	private Constants()
	{
	}

	public static final Set<Integer> BLOWPIPE_IDS = ImmutableSet.of(
		12926, // regular
		28688 // blazing
	);

	public static final Set<Integer> AKKHA_IDS = ImmutableSet.of(
		11789, 11790, 11791, 11792, 11793, 11794, 11795, 11796
	);

	public static final Set<Integer> AKKHA_SHADOW_IDS = ImmutableSet.of(
		11797, 11798, 11799
	);

	public static final Set<Integer> BABA_IDS = ImmutableSet.of(
		11778, 11779, 11780
	);

	public static final Set<Integer> KEPHRI_SHIELDED_IDS = ImmutableSet.of(
		11719
	);
	// ^v todo these two might be swapped ^v
	public static final Set<Integer> KEPHRI_UNSHIELDED_IDS = ImmutableSet.of(
		11721
	);

	public static final Set<Integer> KEPHRI_OVERLORD_IDS = ImmutableSet.of(
		11724, 11725, 11726
	);

	public static final Set<Integer> ZEBAK_IDS = ImmutableSet.of(
		11730, 11732, 11733
	);

	public static final Set<Integer> TOA_OBELISK_IDS = ImmutableSet.of(
		11750, 11751, 11752
	);

	public static final Set<Integer> P2_WARDEN_IDS = ImmutableSet.of(
		11753, 11754, // elidinis
		11756, 11757 // tumeken
	);

	public static final Set<Integer> P3_WARDEN_IDS = ImmutableSet.of(
		11761, 11763, // elidinis
		11762, 11764 // tumeken
	);

	public static final Set<Integer> TOA_WARDEN_CORE_EJECTED_IDS = ImmutableSet.of(
		11755, // elidinis
		11758 // tumeken
	);

	/**
	 * IDs of monsters that are present in Tombs of Amascut and are affected by path level.
	 */
	public static final Set<Integer> TOMBS_OF_AMASCUT_PATH_MONSTER_IDS = ImmutableSet.<Integer>builder()
		.addAll(AKKHA_IDS)
		.addAll(AKKHA_SHADOW_IDS)
		.addAll(BABA_IDS)
		.addAll(KEPHRI_SHIELDED_IDS)
		.addAll(KEPHRI_UNSHIELDED_IDS)
		.addAll(KEPHRI_OVERLORD_IDS)
		.addAll(ZEBAK_IDS)
		.build();

	/**
	 * IDs of monsters that are present in Tombs of Amascut *
	 */
	public static final Set<Integer> TOMBS_OF_AMASCUT_MONSTER_IDS = ImmutableSet.<Integer>builder()
		.addAll(TOMBS_OF_AMASCUT_PATH_MONSTER_IDS)
		.addAll(TOA_OBELISK_IDS)
		.addAll(P2_WARDEN_IDS)
		.addAll(TOA_WARDEN_CORE_EJECTED_IDS)
		.addAll(P3_WARDEN_IDS)
		.build();

	public static final Set<Integer> VERZIK_P1_IDS = ImmutableSet.of(
		10830, 10831, 10832, // em
		8369, 8370, 8371, // norm
		10847, 10848, 10849 // hmt
	);

	public static final Set<Integer> VERZIK_IDS = ImmutableSet.<Integer>builder()
		.addAll(VERZIK_P1_IDS)
		.add(
			10833, 10834, 10835, // verzik entry mode
			8372, 8373, 8374, // verzik normal mode
			10850, 10851, 10852 // verzik hard mode
		).build();

	public static final Set<Integer> SOTETSEG_IDS = ImmutableSet.of(
		8387, 8388, // normal
		10867, 10868 // hard
	);

	/**
	 * IDs of monsters that are present in Theatre of Blood *
	 */
	public static final Set<Integer> TOB_MONSTER_IDS = ImmutableSet.<Integer>builder()
		.addAll(VERZIK_P1_IDS)
		// normal
		.add(8360, 8361, 8362, 8363, 8364, 8365) // maiden
		.add(8366, 8367) // maiden crab + blood spawn
		.add(8359) // bloat
		.add(8342, 8343, 8344, 8345, 8346, 8347, 8348, 8349, 8350, 8351, 8352, 8353) // nylos
		.add(8355, 8356, 8357) // nylo boss.
		.addAll(SOTETSEG_IDS)
		.add(8339, 8340) // xarpus
		.add(8372, 8373, 8374) // verzik
		.add(8376, 8381, 8382, 8383, 8384, 8385) // verzik web + nylos
		// hmt
		.add(10822, 10823, 10824, 10825, 10826, 10827) // maiden
		.add(10828, 10829) // maiden crab + blood spawn
		.add(10813) // bloat
		.add(10791, 10792, 10793, 10794, 10795, 10796, 10797, 10798, 10799, 10800, 10801, 10802) // nylos
		.add(10804, 10805, 10806) // nylo demi-boss
		.add(10808, 10809, 10810) // nylo boss
		// sote is above
		.add(10770, 10771, 10772) // xarpus (i think there's an extra one here because of its different p3 behaviour?)
		.add(10850, 10851, 10852) // verzik
		.add(10854, 10858, 10859, 10860, 10861, 10862) // verzik web + nylos
		.build();

	/**
	 * IDs of monsters that are present in Theatre of Blood Entry Mode.
	 * Separated from norm + hmt due to different health scaling rules. *
	 */
	public static final Set<Integer> TOB_EM_MONSTER_IDS = ImmutableSet.of(
		10814, 10815, 10816, 10817, 10818, 10819, // maiden
		10820, 10821, // maiden crab + blood spawn
		10812, // bloat
		10774, 10775, 10776,
		10777, 10778, 10779, 10780, 10781, 10782, 10783, 10784, 10785, // nylos
		10787, 10788, 10789, // nylo boss
		10864, 10865, // sote
		10767, 10768, // xarpus
		10833, 10834, 10835, // verzik
		10837, 10841, 10842, 10843, 10844, 10845 // verzik web + nylos
	);

	/**
	 * IDs of Tekton from the Chambers of Xeric.
	 * Separated due to different defence scaling rules.
	 */
	public static final Set<Integer> TEKTON_IDS = ImmutableSet.of(
		7540, 7543, // reg
		7544, 7545 // cm
	);

	/**
	 * IDs of Guardians from the Chambers of Xeric.
	 * Separated due to different health scaling rules.
	 */
	public static final Set<Integer> GUARDIAN_IDS = ImmutableSet.of(
		7569, 7571, // reg
		7570, 7572 // cm
	);

	/**
	 * IDs of the Great Olm's head from the Chambers of Xeric.
	 * Separated due to different health scaling rules.
	 */
	public static final Set<Integer> OLM_HEAD_IDS = ImmutableSet.of(
		7551, // reg
		7554 // cm
	);

	/**
	 * IDs of the Great Olm's melee hand from the Chambers of Xeric.
	 */
	public static final Set<Integer> OLM_MELEE_HAND_IDS = ImmutableSet.of(
		7552, // reg
		7555 // cm
	);

	/**
	 * IDs of the Great Olm's mage hand from the Chambers of Xeric.
	 */
	public static final Set<Integer> OLM_MAGE_HAND_IDS = ImmutableSet.of(
		7550, // reg
		7553 // cm
	);

	/**
	 * IDs of the Great Olm from the Chambers of Xeric.
	 * Separated due to different health, offence, and defence scaling rules.
	 */
	public static final Set<Integer> OLM_IDS = ImmutableSet.<Integer>builder()
		.addAll(OLM_HEAD_IDS)
		.addAll(OLM_MELEE_HAND_IDS)
		.addAll(OLM_MAGE_HAND_IDS)
		.build();

	/**
	 * IDs of Scavenger beasts from the Chambers of Xeric.
	 * Separated due to different health scaling rules.
	 */
	public static final Set<Integer> SCAVENGER_BEAST_IDS = ImmutableSet.of(
		7548, 7549
	);

	/**
	 * IDs of Vespula's Abyssal portal from the Chambers of Xeric.
	 * Separated due to different offence scaling rules.
	 */
	public static final Set<Integer> ABYSSAL_PORTAL_IDS = ImmutableSet.of(
		7533
	);

	/**
	 * IDs of Vasa's Glowing crystal from the Chambers of Xeric.
	 * Separated due to different health and defence scaling rules.
	 */
	public static final Set<Integer> GLOWING_CRYSTAL_IDS = ImmutableSet.of(
		7568
	);

	/**
	 * IDs of the Ice demon from the Chambers of Xeric.
	 */
	public static final Set<Integer> ICE_DEMON_IDS = ImmutableSet.of(
		7584, // reg
		7585 // cm
	);

	/**
	 * IDs of the Fragment of Seren.
	 */
	public static final Set<Integer> FRAGMENT_OF_SEREN_IDS = ImmutableSet.of(
		8917, 8918, 8919, 8920
	);

	public static final Set<Integer> NIGHTMARE_IDS = ImmutableSet.of(
		378, 9425, 9426, 9427, 9428, 9429, 9430, 9431, 9432, 9433, 9460, // nightmare
		377, 9423, 9416, 9417, 9418, 9419, 9420, 9421, 9422, 9424, 11153, 11154, 11155 // phosani's
	);

	/**
	 * IDs of the totems in the Nightmare / Phosani's Nightmare fight.
	 * They take double damage from magic sources.
	 *
	 * @see https://oldschool.runescape.wiki/w/Totem_(The_Nightmare)#Uncharged
	 */
	public static final Set<Integer> NIGHTMARE_TOTEM_IDS = ImmutableSet.of(
		9434, 9437, 9440, 9443,
		9435, 9438, 9441, 9444
	);

	public static final Set<Integer> NEX_IDS = ImmutableSet.of(
		11278, 11279, 11280, 11281, 11282
	);

	/**
	 * IDs of monsters that calculate their magical defence using the defence stat.
	 * https://twitter.com/JagexAsh/status/1689566945635438592
	 */
	public static final Set<Integer> USES_DEFENCE_LEVEL_FOR_MAGIC_DEFENCE_NPC_IDS = ImmutableSet.<Integer>builder()
		.addAll(ICE_DEMON_IDS)
		.addAll(VERZIK_IDS)
		.addAll(FRAGMENT_OF_SEREN_IDS)
		.add(11709, 11712) // baboon brawler
		.add(9118) // rabbit (prifddinas)
		.build();

	/**
	 * IDs of Dusk.
	 */
	public static final Set<Integer> DUSK_IDS = ImmutableSet.of(
		7851, 7854, 7855, 7882, 7883, 7886, // dusk first form
		7887, 7888, 7889 // dusk second form
	);

	public static final Set<Integer> WARRIORS_GUILD_CYCLOPES = ImmutableSet.of(
		2463, 2465, 2467, // L56
		2464, 2466, 2468, // L76
		2137, 2138, 2139, 2140, 2141, 2142 // L106
	);

	public static final Set<Integer> ZULRAH_IDS = ImmutableSet.of(
		2042, 2043, 2044
	);

	/**
	 * Monsters immune to melee damage.
	 */
	public static final Set<Integer> IMMUNE_TO_MELEE_DAMAGE_NPC_IDS = ImmutableSet.<Integer>builder()
		.add(494) // kraken
		.addAll(ABYSSAL_PORTAL_IDS)
		.add(7706) // zuk
		.add(7708) // Jal-MejJak
		.add(12214, 12215, 12219) // leviathan
		.add(7852, 7853, 7884, 7885) // dawn
		.addAll(ZULRAH_IDS)
		.build();

	public static final Set<Integer> IMMUNE_TO_NON_SALAMANDER_MELEE_DAMAGE_NPC_IDS = ImmutableSet.of(
		3169, 3170, 3171, 3172, 3173, 3174, 3175, 3176, 3177, 3178, 3179, 3180, 3181, 3182, 3183, // aviansie
		7037 // reanimated aviansie
	);

	/**
	 * Monsters immune to ranged damage.
	 */
	public static final Set<Integer> IMMUNE_TO_RANGED_DAMAGE_NPC_IDS = ImmutableSet.<Integer>builder()
		.addAll(TEKTON_IDS)
		.addAll(DUSK_IDS)
		.addAll(GLOWING_CRYSTAL_IDS)
		.addAll(WARRIORS_GUILD_CYCLOPES)
		.build();

	/**
	 * Monsters immune to magic damage.
	 */
	public static final Set<Integer> IMMUNE_TO_MAGIC_DAMAGE_NPC_IDS = ImmutableSet.<Integer>builder()
		.addAll(DUSK_IDS)
		.addAll(WARRIORS_GUILD_CYCLOPES)
		.build();

	public static final Set<Integer> PARTY_SIZE_REQUIRED_MONSTER_IDS = ImmutableSet.<Integer>builder()
		.addAll(TOMBS_OF_AMASCUT_MONSTER_IDS)
		.addAll(TOB_MONSTER_IDS)
		.addAll(TOB_EM_MONSTER_IDS)
		.build();

	public static final Set<Integer> BA_ATTACKER_MONSTERS = ImmutableSet.of(
		// fighters
		1667,
		5739,
		5740,
		5741,
		5742,
		5743,
		5744,
		5745,
		5746,
		5747,

		// rangers
		1668,
		5757,
		5758,
		5759,
		5760,
		5761,
		5762,
		5763,
		5764,
		5765
	);

	public static final Set<Integer> VARDORVIS_IDS = ImmutableSet.of(12223, 12224, 12228, 12425, 12426, 13656);

	public static final Set<Integer> TITAN_ELEMENTAL_IDS = ImmutableSet.of(
		14150, // Fire elemental (Royal Titans)
		14151 // Ice elemental (Royal Titans)
	);

	public static int ACCURACY_PRECISION = 2;
	public static int DPS_PRECISION = 3;
	public static int EXPECTED_HIT_PRECISION = 1;

	public static final Set<CombatStyleStance> AUTOCAST_STANCES = ImmutableSet.of(CombatStyleStance.AUTOCAST, CombatStyleStance.DEFENSIVE_AUTOCAST);
	public static final Set<CombatStyleStance> CAST_STANCES = ImmutableSet.<CombatStyleStance>builder()
		.addAll(AUTOCAST_STANCES)
		.add(CombatStyleStance.MANUAL_CAST)
		.build();

	/**
	 * NPCs that will always die in one hit from a player attack
	 */
	public static final Set<Integer> ONE_HIT_MONSTERS = ImmutableSet.of(
		7223, // Giant rat (Scurrius)
		8584, // Flower
		11193 // Flower (A Night at the Theatre)
	);

	public static final Set<Integer> ALWAYS_MAX_HIT_MONSTERS_MELEE = ImmutableSet.<Integer>builder()
		.add(11710, 11713) // baboon thrower
		.add(12814) // frem warband archer
		.addAll(TOA_WARDEN_CORE_EJECTED_IDS)
		.build();

	public static final Set<Integer> ALWAYS_MAX_HIT_MONSTERS_RANGED = ImmutableSet.<Integer>builder()
		.add(11711, 11714) // baboon mage
		.add(12815) // frem warband seer
		.build();

	public static final Set<Integer> ALWAYS_MAX_HIT_MONSTERS_MAGIC = ImmutableSet.<Integer>builder()
		.add(11709, 11712) // baboon brawler
		.add(12816) // frem warband berserker
		.add(14150, 14151) // Royal titans elementals
		.build();

	public static final Set<String> BONE_WEAPONS = ImmutableSet.of(
		"Bone mace",
		"Bone shortbow",
		"Bone staff"
	);

	public static final int DEFAULT_ATTACK_SPEED = 4;
	public static final double SECONDS_PER_TICK = 0.6;

	public static final int TTK_DIST_MAX_ITER_ROUNDS = 1000;
	public static final double TTK_DIST_EPSILON = 0.0001;

	public static final Map<Integer, List<String>> MONSTER_PHASES_BY_ID;

	public static final Set<Integer> TD_IDS = ImmutableSet.of(13599, 13600, 13601, 13602, 13603, 13604, 13605, 13606);
	public static final List<String> TD_PHASES = ImmutableList.of("Shielded", "Shielded (Defenceless)", "Unshielded");

	public static final Set<Integer> ARAXXOR_IDS = ImmutableSet.of(13668);
	public static final List<String> ARAXXOR_PHASES = ImmutableList.of("Standard", "Enraged");

	public static final Set<Integer> HUEYCOATL_HEAD_IDS = ImmutableSet.of(14009, 14010, 14013);
	public static final Set<Integer> HUEYCOATL_BODY_IDS = ImmutableSet.of(14017);
	public static final Set<Integer> HUEYCOATL_TAIL_IDS = ImmutableSet.of(14014);
	public static final Set<Integer> HUEYCOATL_IDS = ImmutableSet.<Integer>builder()
		.addAll(HUEYCOATL_HEAD_IDS)
		.addAll(HUEYCOATL_BODY_IDS)
		.addAll(HUEYCOATL_TAIL_IDS)
		.build();
	public static final List<String> HUEYCOATL_PHASES = ImmutableList.of("Without Pillar", "With Pillar");

	static
	{
		ImmutableMap.Builder<Integer, List<String>> b = ImmutableMap.builder();
		TD_IDS.forEach(id -> b.put(id, TD_PHASES));
		ARAXXOR_IDS.forEach(id -> b.put(id, ARAXXOR_PHASES));
		HUEYCOATL_HEAD_IDS.forEach(id -> b.put(id, HUEYCOATL_PHASES));
		HUEYCOATL_TAIL_IDS.forEach(id -> b.put(id, HUEYCOATL_PHASES));
		MONSTER_PHASES_BY_ID = b.build();
	}

	public static final Map<String, Integer> GUARDIAN_PICKAXE_BONUSES = ImmutableMap.<String, Integer>builder()
		.put("Bronze pickaxe", 1)
		.put("Iron pickaxe", 1)
		.put("Steel pickaxe", 6)
		.put("Black pickaxe", 11)
		.put("Mithril pickaxe", 21)
		.put("Adamant pickaxe", 31)
		.put("Rune pickaxe", 41)
		.put("Gilded pickaxe", 41)
		.build(); // all the rest are 61

	public static final Set<String> TWINFLAME_STAFF_SPELL_CLASSES = ImmutableSet.of(
		"Bolt",
		"Blast",
		"Wave"
	);

	public static final Set<String> PARTIALLY_IMPLEMENTED_SPECS = ImmutableSet.of(
		"Ancient godsword"
	);

	public static final Set<String> UNIMPLEMENTED_SPECS = ImmutableSet.of(
		"Abyssal tentacle",
		"Ancient mace",
		"Armadyl crossbow",
		"Barrelchest anchor",
		"Blue moon spear",
		"Bone dagger",
		"Brine sabre",
		"Darklight",
		"Dinh's bulwark",
		"Dorgeshuun crossbow",
		"Dragon 2h sword",
		"Dragon crossbow",
		"Dragon hasta",
		"Dragon spear",
		"Dragon thrownaxe",
		"Eclipse atlatl",
		"Excalibur",
		"Granite hammer",
		"Granite maul",
		"Rune claws",
		"Staff of balance",
		"Staff of light",
		"Staff of the dead",
		"Toxic staff of the dead",
		"Ursine chainmace",
		"Zamorakian hasta",
		"Zamorakian spear"
	);

	public static final Map<String, Integer> WEAPON_SPEC_COSTS = ImmutableMap.<String, Integer>builder()
		.put("Abyssal dagger", 25)
		.put("Dragon dagger", 25)
		.put("Dragon longsword", 25)
		.put("Dragon mace", 25)
		.put("Osmumten's fang", 25)
		.put("Osmumten's fang (or)", 25)
		.put("Dual macuahuitl", 25)
		.put("Scorching bow", 25)
		.put("Dragon knife", 25)
		.put("Purging staff", 25)
		.put("Dawnbringer", 30)
		.put("Dragon halberd", 30)
		.put("Crystal halberd", 30)
		.put("Burning claws", 30)
		.put("Magic longbow", 35)
		.put("Magic comp bow", 35)
		.put("Dragon sword", 40)
		.put("Elder maul", 50)
		.put("Dragon warhammer", 50)
		.put("Bandos godsword", 50)
		.put("Saradomin godsword", 50)
		.put("Accursed sceptre", 50)
		.put("Accursed sceptre (a)", 50)
		.put("Arclight", 50)
		.put("Emberlight", 50)
		.put("Tonalztics of ralos", 50)
		.put("Dragon claws", 50)
		.put("Voidwaker", 50)
		.put("Toxic blowpipe", 50)
		.put("Blazing blowpipe", 50)
		.put("Webweaver bow", 50)
		.put("Magic shortbow (i)", 50)
		.put("Ancient godsword", 50)
		.put("Armadyl godsword", 50)
		.put("Zamorak godsword", 50)
		.put("Abyssal bludgeon", 50)
		.put("Abyssal whip", 50)
		.put("Magic shortbow", 55)
		.put("Dark bow", 55)
		.put("Eldritch nightmare staff", 55)
		.put("Volatile nightmare staff", 55)
		.put("Dragon scimitar", 55)
		.put("Heavy ballista", 65)
		.put("Light ballista", 65)
		.put("Saradomin's blessed sword", 65)
		.put("Zaryte crossbow", 75)
		.put("Saradomin sword", 100)
		.put("Seercull", 100)
		.build();

}
