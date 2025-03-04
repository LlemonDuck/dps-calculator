package com.duckblade.osrs.dpscalc.calc.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AmmoApplicability
{

	INCLUDED,
	ALLOWED,
	INVALID,
	;

	public static final Set<Integer> BOW_T1 = new HashSet<>(Arrays.asList(
		882, 883, 5616, 5622, 598, 942, // Bronze arrow + variants
		884, 885, 5617, 5623, 2532, 2533, // Iron arrow + variants
		22227, 22228, 22229, 22230 // barb assault
	));

	public static final Set<Integer> BOW_T5 = Stream.concat(
		BOW_T1.stream(),
		Stream.of(886, 887, 5618, 5624, 2534, 2535) // Steel arrow + variants
	).collect(Collectors.toSet());

	public static final Set<Integer> BOW_T20 = Stream.concat(
		BOW_T5.stream(),
		Stream.of(888, 889, 5619, 5625, 2536, 2537) // Mithril arrow + variants
	).collect(Collectors.toSet());

	public static final Set<Integer> BOW_T30 = Stream.concat(
		BOW_T20.stream(),
		Stream.of(890, 891, 5620, 5626, 2538, 2539) // Adamant arrow + variants
	).collect(Collectors.toSet());

	public static final Set<Integer> BOW_T40 = Stream.concat(
		BOW_T30.stream(),
		Stream.of(892, 893, 5621, 5627, 78, 2540, 2541) // Rune arrow + variants, ice arrows
	).collect(Collectors.toSet());

	public static final Set<Integer> BOW_T50 = Stream.concat(
		BOW_T40.stream(),
		Stream.of(21326, 21332, 21334, 21336, 4160, 21328, 21330) // Amethyst arrow + variants, broad arrows
	).collect(Collectors.toSet());

	public static final Set<Integer> BOW_T60 = Stream.concat(
		BOW_T50.stream(),
		Stream.of(11212, 11227, 11228, 11229, 11217, 11222) // Dragon arrow + variants
	).collect(Collectors.toSet());

	public static final Set<Integer> CB_T1 = ImmutableSet.of(
		877, 878, 6061, 6062, 879, 9236 // Bronze bolts + variants, opal bolts + (e)
	);

	public static final Set<Integer> CB_T16 = ImmutableSet.<Integer>builder()
		.addAll(CB_T1)
		.add(9139, 9286, 9293, 9300, 9335, 9237) // Blurite bolts + variants, jade bolts + (e)
		.build();

	public static final Set<Integer> CB_T26 = ImmutableSet.<Integer>builder()
		.addAll(CB_T16)
		.add(9140, 9287, 9294, 9301, 880, 9238, 9145, 9292, 9299, 9306) // Iron bolts + variants, pearl bolts + (e), silver bolts
		.build();

	public static final Set<Integer> CB_T31 = ImmutableSet.<Integer>builder()
		.addAll(CB_T26)
		.add(9141, 9288, 9295, 9302, 9336, 9239) // Steel bolts + variants, topaz bolts + (e)
		.build();

	public static final Set<Integer> CB_T36 = ImmutableSet.<Integer>builder()
		.addAll(CB_T31)
		.add(9142, 9289, 9296, 9303, 9337, 9240, 9338, 9241) // Mithril bolts + variants, sapphire/emerald bolts + (e)
		.build();

	public static final Set<Integer> CB_T46 = ImmutableSet.<Integer>builder()
		.addAll(CB_T36)
		.add(9143, 9290, 9297, 9304, 9339, 9242, 9340, 9243) // Runite bolts + variants, broad bolts, amethyst broad bolts, dragonstone/onyx bolts + (e)
		.build();

	public static final Set<Integer> CB_T61 = ImmutableSet.<Integer>builder()
		.addAll(CB_T46)
		.add(9144, 9291, 9298, 9305, 11875, 21316, 9341, 9244, 9342, 9245) // Runite bolts + variants, broad bolts, amethyst broad bolts, dragonstone/onyx bolts + (e)
		.build();

	public static final Set<Integer> CB_T64 = ImmutableSet.<Integer>builder()
		.addAll(CB_T61)
		.add(21905, 21924, 21926, 21928, 21955, 21932, 21957, 21934, 21959, 21936, 21961, 21938, 21963, 21940, 21965, 21942, 21967, 21944, 21969, 21946, 21971, 21948, 21973, 21950) // Runite bolts + variants, broad bolts, amethyst broad bolts, dragonstone/onyx bolts + (e)
		.build();

	public static final Set<Integer> JAVELINS = ImmutableSet.of(
		825, 831, 5642, 5648, // Bronze javelin + variants
		826, 832, 5643, 5649, // Iron javelin + variants
		827, 833, 5644, 5650, // Steel javelin + variants
		828, 834, 5645, 5651, // Mithril javelin + variants
		829, 835, 5646, 5652, // Adamant javelin + variants
		830, 836, 5647, 5653, // Rune javelin + variants
		21318, 21320, 21322, 21324, // Amethyst javelin + variants
		19484, 19486, 19488, 19490 // Dragon javelin + variants
	);

	public static final Map<Integer, Set<Integer>> WEAPON_AMMO = ImmutableMap.<Integer, Set<Integer>>builder()
		.put(11708, BOW_T1) // Cursed goblin bow
		.put(23357, BOW_T1) // Rain bow
		.put(9705, ImmutableSet.of(9706)) // Training bow
		.put(841, BOW_T1) // Shortbow
		.put(839, BOW_T1) // Longbow
		.put(843, BOW_T5) // Oak shortbow
		.put(845, BOW_T5) // Oak longbow
		.put(4236, BOW_T5) // Signed oak bow
		.put(849, BOW_T20) // Willow shortbow
		.put(847, BOW_T20) // Willow longbow
		.put(10280, BOW_T20) // Willow comp bow
		.put(853, BOW_T30) // Maple shortbow
		.put(851, BOW_T30) // Maple longbow
		.put(2883, ImmutableSet.of(2866)) // Ogre bow
		.put(4827, ImmutableSet.of(2866, 4773, 4778, 4783, 4788, 4793, 4798, 4803)) // Comp ogre bow
		.put(857, BOW_T40) // Yew shortbow
		.put(855, BOW_T40) // Yew longbow
		.put(10282, BOW_T40) // Yew comp bow
		.put(28794, BOW_T50) // Bone shortbow
		.put(6724, BOW_T50) // Seercull
		.put(861, BOW_T50) // Magic shortbow
		.put(12788, BOW_T50) // Magic shortbow (i)
		.put(859, BOW_T50) // Magic longbow
		.put(10284, BOW_T50) // Magic comp bow
		.put(11235, BOW_T60) // Dark bow
		.put(27853, BOW_T60) // Dark bow (bh)
		.put(12424, BOW_T60) // 3rd age bow
		.put(27610, BOW_T60) // Venator bow
		.put(27612, BOW_T60) // Venator bow (uncharged)
		.put(20997, BOW_T60) // Twisted bow
		.put(29591, BOW_T60) // Scorching bow
		.put(837, CB_T1) // Crossbow
		.put(767, CB_T1) // Phoenix crossbow
		.put(9174, CB_T1) // Bronze crossbow
		.put(9176, CB_T16) // Blurite crossbow
		.put(9177, CB_T26) // Iron crossbow
		.put(9179, CB_T31) // Steel crossbow
		.put(9181, CB_T36) // Mithril crossbow
		.put(9183, CB_T46) // Adamant crossbow
		.put(9185, CB_T61) // Rune crossbow
		.put(21902, CB_T64) // Dragon crossbow
		.put(19478, JAVELINS) // Light ballista
		.put(19481, JAVELINS) // Heavy ballista
		.put(8880, ImmutableSet.<Integer>builder().addAll(CB_T16).add(9140, 9287, 9294, 9301, 8882).build()) // Dorgeshuun crossbow
		.put(10156, ImmutableSet.of(10158, 10159)) // Hunters' crossbow
		.put(4734, ImmutableSet.of(4740)) // Karil's crossbow (undmg)
		.put(21012, CB_T64) // Dragon hunter crossbow
		.put(11785, CB_T64) // Armadyl crossbow
		.put(26374, CB_T64) // Zaryte crossbow
		.put(12924, Collections.emptySet()) // Toxic blowpipe (empty)
		.put(12926, Collections.emptySet()) // Toxic blowpipe (charged)
		.put(22547, Collections.emptySet()) // Craw's bow (empty)
		.put(22550, Collections.emptySet()) // Craw's bow (charged)
		.put(23983, Collections.emptySet()) // Crystal bow (empty)
		.put(23985, Collections.emptySet()) // Crystal bow (inactive)
		.put(24123, Collections.emptySet()) // Crystal bow (new)
		.put(27652, Collections.emptySet()) // Webweaver bow (empty)
		.put(27655, Collections.emptySet()) // Webweaver bow (charged)
		.put(25862, Collections.emptySet()) // Bow of faerdhinen (empty)
		.put(25865, Collections.emptySet()) // Bow of faerdhinen (charged)
		.put(10149, ImmutableSet.of(10142)) // Swamp lizard, Guam tar
		.put(10146, ImmutableSet.of(10143)) // Orange salamander, Marrentill tar
		.put(10147, ImmutableSet.of(10144)) // Red salamander, Tarromin tar
		.put(10148, ImmutableSet.of(10145)) // Black salamander, Harralander tar
		.put(28834, ImmutableSet.of(28837)) // Tecu salamander, Irit tar
		.put(28869, ImmutableSet.of(28872, 28878)) // Hunters' sunlight crossbow
		.put(29000, ImmutableSet.of(28991)) // Eclipse atlatl
		.build();

	public static AmmoApplicability ammoApplicability(Integer weaponId, Integer ammoId)
	{
		Set<Integer> validAmmo = WEAPON_AMMO.get(weaponId);

		// The weapon does not use ammo
		if (validAmmo == null || validAmmo.isEmpty())
		{
			return ALLOWED;
		}

		// weapon requires ammo, and we have one that matches the list
		if (validAmmo.contains(ammoId))
		{
			return INCLUDED;
		}

		return INVALID;
	}

}
