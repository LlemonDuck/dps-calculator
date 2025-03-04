package com.duckblade.osrs.dpscalc.plugin.osdata.clientdata;

import com.duckblade.osrs.dpscalc.calc.model.EquipmentCategory;
import com.duckblade.osrs.dpscalc.calc.model.EquipmentPiece;
import com.duckblade.osrs.dpscalc.calc.model.Monster;
import com.duckblade.osrs.dpscalc.calc.model.Player;
import com.duckblade.osrs.dpscalc.calc.model.PlayerBuffs;
import com.duckblade.osrs.dpscalc.calc.model.PlayerCombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.PlayerEquipment;
import com.duckblade.osrs.dpscalc.calc.model.PlayerSkills;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiDataProvider;
import static com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiDataProvider.ALL_EQUIPMENT;
import static com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiDataProvider.ALL_SPELLS;
import com.google.common.collect.ImmutableSet;
import java.util.EnumSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Client;
import net.runelite.api.EquipmentInventorySlot;
import static net.runelite.api.EquipmentInventorySlot.AMMO;
import static net.runelite.api.EquipmentInventorySlot.AMULET;
import static net.runelite.api.EquipmentInventorySlot.BODY;
import static net.runelite.api.EquipmentInventorySlot.BOOTS;
import static net.runelite.api.EquipmentInventorySlot.CAPE;
import static net.runelite.api.EquipmentInventorySlot.GLOVES;
import static net.runelite.api.EquipmentInventorySlot.HEAD;
import static net.runelite.api.EquipmentInventorySlot.LEGS;
import static net.runelite.api.EquipmentInventorySlot.RING;
import static net.runelite.api.EquipmentInventorySlot.SHIELD;
import static net.runelite.api.EquipmentInventorySlot.WEAPON;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.Skill;
import net.runelite.api.SkullIcon;
import net.runelite.api.VarPlayer;
import net.runelite.api.Varbits;
import net.runelite.client.plugins.slayer.SlayerPluginService;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RuneLiteClientDataProvider implements ClientDataProvider
{

	private static final int AUTOCAST_SPELL_VARBIT = 276;
	public static final EquipmentInventorySlot[] ALL_SLOTS = new EquipmentInventorySlot[]{
		HEAD,
		AMMO, AMULET, CAPE,
		WEAPON, BODY, SHIELD,
		LEGS,
		GLOVES, BOOTS, RING
	};

	private static final Set<Integer> FORINTHRY_SURGE_SKULLS = ImmutableSet.of(
		SkullIcon.FORINTHRY_SURGE,
		SkullIcon.FORINTHRY_SURGE_DEADMAN,
		SkullIcon.FORINTHRY_SURGE_KEYS_ONE,
		SkullIcon.FORINTHRY_SURGE_KEYS_TWO,
		SkullIcon.FORINTHRY_SURGE_KEYS_THREE,
		SkullIcon.FORINTHRY_SURGE_KEYS_FOUR,
		SkullIcon.FORINTHRY_SURGE_KEYS_FIVE
	);

	private final Client client;
	private final SlayerPluginService slayerPluginService;

	private final InteractingNpcTracker interactingNpcTracker;
	private final WikiDataProvider dataProvider;

	@Override
	public Player getPlayer()
	{
		if (!dataProvider.isLoaded())
		{
			Player.builder().build();
		}

		PlayerEquipment eq = getPlayerEquipment();
		return Player.builder()
			.style(getPlayerStyle(eq))
			.skills(getPlayerSkills())
			.boosts(getPlayerBoosts())
			.equipment(eq)
			.prayers(getPlayerPrayers())
			.buffs(getPlayerBuffs())
			.spell(getPlayerSpell())
			.build();
	}

	@Override
	public Monster getMonster()
	{
		// todo scaling
		return interactingNpcTracker.getLastInteracted();
	}

	private PlayerCombatStyle getPlayerStyle(PlayerEquipment equipment)
	{
		EquipmentPiece weapon = equipment.getWeapon();
		EquipmentCategory category = weapon != null ? weapon.getCategory() : EquipmentCategory.UNARMED;

		int asVarp = client.getVarpValue(VarPlayer.ATTACK_STYLE);
		for (PlayerCombatStyle style : category.getStyles())
		{
			if (style.getVarp() == asVarp)
			{
				return style;
			}
		}

		return category.getStyles().get(0);
	}

	private PlayerSkills getPlayerSkills()
	{
		return PlayerSkills.builder()
			.hp(client.getRealSkillLevel(Skill.HITPOINTS))
			.atk(client.getRealSkillLevel(Skill.ATTACK))
			.str(client.getRealSkillLevel(Skill.STRENGTH))
			.def(client.getRealSkillLevel(Skill.DEFENCE))
			.ranged(client.getRealSkillLevel(Skill.RANGED))
			.magic(client.getRealSkillLevel(Skill.MAGIC))
			.prayer(client.getRealSkillLevel(Skill.PRAYER))
			.mining(client.getRealSkillLevel(Skill.MINING))
			.herblore(client.getRealSkillLevel(Skill.HERBLORE))
			.build();
	}

	private PlayerSkills getPlayerBoosts()
	{
		return PlayerSkills.builder()
			.hp(client.getBoostedSkillLevel(Skill.HITPOINTS) - client.getRealSkillLevel(Skill.HITPOINTS))
			.atk(client.getBoostedSkillLevel(Skill.ATTACK) - client.getRealSkillLevel(Skill.ATTACK))
			.str(client.getBoostedSkillLevel(Skill.STRENGTH) - client.getRealSkillLevel(Skill.STRENGTH))
			.def(client.getBoostedSkillLevel(Skill.DEFENCE) - client.getRealSkillLevel(Skill.DEFENCE))
			.ranged(client.getBoostedSkillLevel(Skill.RANGED) - client.getRealSkillLevel(Skill.RANGED))
			.magic(client.getBoostedSkillLevel(Skill.MAGIC) - client.getRealSkillLevel(Skill.MAGIC))
			.prayer(client.getBoostedSkillLevel(Skill.PRAYER) - client.getRealSkillLevel(Skill.PRAYER))
			.mining(client.getBoostedSkillLevel(Skill.MINING) - client.getRealSkillLevel(Skill.MINING))
			.herblore(client.getBoostedSkillLevel(Skill.HERBLORE) - client.getRealSkillLevel(Skill.HERBLORE))
			.build();
	}

	private PlayerEquipment getPlayerEquipment()
	{
		ItemContainer eq = client.getItemContainer(InventoryID.EQUIPMENT);
		PlayerEquipment ret = PlayerEquipment.builder().build();
		if (eq == null)
		{
			return ret;
		}

		Item[] items = eq.getItems();
		for (EquipmentInventorySlot slot : ALL_SLOTS)
		{
			Item item = slot.getSlotIdx() < items.length ? items[slot.getSlotIdx()] : null;
			if (item != null)
			{
				ret.set(slot, ALL_EQUIPMENT.get(item.getId()));
			}
		}

		return ret;
	}

	public Set<Prayer> getPlayerPrayers()
	{
		Set<Prayer> prayers = EnumSet.noneOf(Prayer.class);
		for (Prayer p : Prayer.values())
		{
			if ((p == Prayer.DEADEYE && client.getVarbitValue(Varbits.PRAYER_DEADEYE_UNLOCKED) == 0)
				|| (p == Prayer.MYSTIC_VIGOUR && client.getVarbitValue(Varbits.PRAYER_MYSTIC_VIGOUR_UNLOCKED) == 0))
			{
				continue;
			}
			if (client.isPrayerActive(p.getRlPrayer()))
			{
				prayers.add(p);
			}
		}

		return prayers;
	}

	public PlayerBuffs getPlayerBuffs()
	{
		return PlayerBuffs.builder()
			.onSlayerTask(
				slayerPluginService.getTargets()
					.stream()
					.anyMatch(n -> n.getIndex() == interactingNpcTracker.getLastInteractedIndex()))
			.inWilderness(client.getVarbitValue(Varbits.PVP_SPEC_ORB) == 1)
			.forinthrySurge(FORINTHRY_SURGE_SKULLS.contains(client.getLocalPlayer().getSkullIcon()))
			.soulreaperStacks(0) // todo
			.baAttackerLevel(0) // todo
			.chinchompaDistance(4) // todo
			.kandarinDiary(client.getVarbitValue(Varbits.DIARY_KANDARIN_HARD) != 0)
			.chargeSpell(false) // todo
			.markOfDarknessSpell(false) // todo
			.usingSunfireRunes(false) // todo
			.build();
	}

	public Spell getPlayerSpell()
	{
		int spellVarb = client.getVarbitValue(AUTOCAST_SPELL_VARBIT);
		for (Spell s : ALL_SPELLS)
		{
			if (s.getVarb() == spellVarb)
			{
				return s;
			}
		}

		return null;
	}

}
