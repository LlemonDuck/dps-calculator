package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.model.ItemStats;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.runelite.api.ItemID.ADAMANT_DART;
import static net.runelite.api.ItemID.AMETHYST_DART;
import static net.runelite.api.ItemID.BLACK_DART;
import static net.runelite.api.ItemID.BRONZE_DART;
import static net.runelite.api.ItemID.DRAGON_DART;
import static net.runelite.api.ItemID.IRON_DART;
import static net.runelite.api.ItemID.MITHRIL_DART;
import static net.runelite.api.ItemID.RUNE_DART;
import static net.runelite.api.ItemID.STEEL_DART;

@Singleton
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ItemDataManager
{

	private static final Gson GSON = new Gson();

	@Getter
	private boolean loaded;

	private Map<Integer, ItemStats> itemStatsMap;
	private List<ItemStats> darts;

	public void init(Reader reader)
	{
		itemStatsMap = GSON.fromJson(reader, new TypeToken<HashMap<Integer, ItemStats>>()
		{
		}.getType());

		itemStatsMap.forEach((id, stats) -> stats.setItemId(id));

		int[] DART_IDS = {DRAGON_DART, AMETHYST_DART, RUNE_DART, ADAMANT_DART, MITHRIL_DART, BLACK_DART, STEEL_DART, IRON_DART, BRONZE_DART};
		darts = IntStream.of(DART_IDS)
			.mapToObj(itemStatsMap::get)
			.collect(Collectors.toList());
	}

	public List<ItemStats> getAllDarts()
	{
		return Collections.unmodifiableList(darts);
	}

	public List<ItemStats> getBySlot(final int targetSlot)
	{
		return itemStatsMap.values()
			.stream()
			.filter(is -> is.getSlot() == targetSlot)
			.sorted(Comparator.comparing(ItemStats::getName))
			.distinct()
			.collect(Collectors.toList());
	}

	public ItemStats getItemStatsById(int npcId)
	{
		return itemStatsMap.get(npcId);
	}

}