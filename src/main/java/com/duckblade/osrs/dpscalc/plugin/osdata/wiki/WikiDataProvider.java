package com.duckblade.osrs.dpscalc.plugin.osdata.wiki;

import com.duckblade.osrs.dpscalc.calc.model.EquipmentPiece;
import com.duckblade.osrs.dpscalc.calc.model.Monster;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import static com.duckblade.osrs.dpscalc.plugin.osdata.wiki.SlotMapping.SLOT_NAMES;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.client.eventbus.EventBus;

@RequiredArgsConstructor
public abstract class WikiDataProvider
{

	public static final Map<Integer, EquipmentPiece> ALL_EQUIPMENT = new HashMap<>();
	public static final Map<EquipmentInventorySlot, List<EquipmentPiece>> EQUIPMENT_BY_SLOT = new HashMap<>();
	public static final Map<Integer, Integer> EQUIPMENT_ALIASES = new HashMap<>();

	public static final Map<Integer, Monster> ALL_MONSTERS = new HashMap<>();

	public static final List<Spell> ALL_SPELLS = new ArrayList<>();

	private static final int EQUIPMENT_LOADED = 1;
	private static final int ALIASES_LOADED = 1 << 1;
	private static final int MONSTERS_LOADED = 1 << 2;
	private int loaded = EQUIPMENT_LOADED | ALIASES_LOADED | MONSTERS_LOADED;

	protected abstract void loadEquipment(Consumer<List<EquipmentPiece>> callback);

	protected abstract void loadEquipmentAliases(Consumer<Map<Integer, Integer>> callback);

	protected abstract void loadMonsters(Consumer<List<Monster>> callback);

	protected abstract ExecutorService getExecutor();

	protected abstract EventBus getEventBus();

	public void loadEquipmentAsync()
	{
		getExecutor().submit(() -> loadEquipment(newEquipmentData ->
		{
			if (newEquipmentData != null)
			{
				ALL_EQUIPMENT.clear();
				newEquipmentData
					.forEach(e -> ALL_EQUIPMENT.put(e.getId(), e));

				EQUIPMENT_BY_SLOT.clear();
				SLOT_NAMES.values()
					.forEach(s -> EQUIPMENT_BY_SLOT.put(s, new ArrayList<>()));
				ALL_EQUIPMENT.values()
					.forEach(ep ->
					{
						EquipmentInventorySlot slot = SLOT_NAMES.getOrDefault(ep.getSlot(), null);
						if (slot != null)
						{
							EQUIPMENT_BY_SLOT.get(slot).add(ep);
						}
					});

				loaded &= ~EQUIPMENT_LOADED;
				checkLoaded();
			}
		}));
	}

	public void loadEquipmentAliasesAsync()
	{
		getExecutor().submit(() -> loadEquipmentAliases(newAliasData ->
		{
			if (newAliasData != null)
			{
				EQUIPMENT_ALIASES.clear();
				EQUIPMENT_ALIASES.putAll(newAliasData);

				loaded &= ~ALIASES_LOADED;
				checkLoaded();
			}
		}));
	}

	public void loadMonstersAsync()
	{
		getExecutor().submit(() -> loadMonsters(newMonsterData ->
		{
			if (newMonsterData != null)
			{
				ALL_MONSTERS.clear();
				newMonsterData
					.forEach(m -> ALL_MONSTERS.put(m.getId(), m));

				loaded &= ~MONSTERS_LOADED;
				checkLoaded();
			}
		}));
	}

	public void loadAllAsync()
	{
		loadEquipmentAsync();
		loadEquipmentAliasesAsync();
		loadMonstersAsync();
	}

	public boolean isLoaded()
	{
		return loaded == 0;
	}

	public void checkLoaded()
	{
		if (isLoaded())
		{
			getEventBus().post(new DpsDataLoaded());
		}
	}

}
