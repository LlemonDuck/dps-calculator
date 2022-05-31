package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.calc.DpsComputeModule;
import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import com.duckblade.osrs.dpscalc.plugin.module.DpsPluginModule;
import com.duckblade.osrs.dpscalc.plugin.osdata.ItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.NpcDataProvider;
import com.duckblade.osrs.dpscalc.plugin.ui.DpsPluginPanel;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Providers;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import net.runelite.api.Client;
import net.runelite.client.RuneLite;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ContainableFrame;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.skin.SubstanceRuneLiteLookAndFeel;
import net.runelite.client.util.SwingUtil;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class DPSCalcUITest
{

	public static void main(String[] args) throws InterruptedException, InvocationTargetException
	{
		Injector testInjector = Guice.createInjector(i ->
		{
			i.bind(Client.class).toProvider(Providers.of(null));
			i.bind(ItemManager.class).toProvider(Providers.of(null));
			i.bind(ClientThread.class).toProvider(Providers.of(null));
			i.bind(OkHttpClient.class).toInstance(new OkHttpClient.Builder()
				.cache(new Cache(new File(RuneLite.CACHE_DIR, "okhttp"), 20 * 1024 * 1024))
				.build());
			i.bind(DpsCalcConfig.class).toInstance(new DpsCalcConfig()
			{
			});
			i.install(new DpsComputeModule());
			i.install(new DpsPluginModule());
		});

		SwingUtilities.invokeAndWait(() ->
		{
			// roughly copied from RuneLite's ClientUI.java init()
			SwingUtil.setupDefaults();
			SwingUtil.setTheme(new SubstanceRuneLiteLookAndFeel());
			SwingUtil.setFont(FontManager.getRunescapeFont());

			ContainableFrame frame = new ContainableFrame();
			frame.getLayeredPane().setCursor(Cursor.getDefaultCursor());

			try
			{
				testInjector.getInstance(ItemStatsProvider.class).load();
				testInjector.getInstance(NpcDataProvider.class).load();
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
			DpsPluginPanel pluginPanel = testInjector.getInstance(DpsPluginPanel.class);

//			NpcDataManager npcDataManager = new NpcDataManager();
//			ItemDataManager itemDataManager = new ItemDataManager();
//
//			CalcManager cm = new CalcManager(new MageDpsCalc(), new MeleeDpsCalc(), new RangedDpsCalc());
//			NpcStatsPanel nsp = new NpcStatsPanel(npcDataManager);
//			EquipmentPanel ep = new EquipmentPanel(null, null, null, itemDataManager);
//			SkillsPanel sp = new SkillsPanel(null, null);
//			PrayerPanel pp = new PrayerPanel(); // heh pp
//			CalcResultPanel rp = new CalcResultPanel();
//
//			DpsCalcPanel calcPanel = new DpsCalcPanel(cm, nsp, ep, sp, pp, rp);
//
//			 preloading (current is tbow max hit)
//			nsp.loadNpcStats(npcDataManager.getNpcStatsById(NpcID.BRUTAL_BLACK_DRAGON));
//			ep.setEquipment(ImmutableMap.of(
//					EquipmentInventorySlot.WEAPON, itemDataManager.getItemStatsById(ItemID.TWISTED_BOW),
//					EquipmentInventorySlot.AMMO, itemDataManager.getItemStatsById(ItemID.DRAGON_ARROW),
//					EquipmentInventorySlot.AMULET, itemDataManager.getItemStatsById(ItemID.NECKLACE_OF_ANGUISH),
//					EquipmentInventorySlot.CAPE, itemDataManager.getItemStatsById(ItemID.AVAS_ASSEMBLER),
//					EquipmentInventorySlot.HEAD, itemDataManager.getItemStatsById(ItemID.SLAYER_HELMET_I)
//			));
//			ep.setOnSlayerTask(true);
//			ep.setWeaponMode(WeaponType.BOW.getWeaponModes().get(0));
//			sp.setSkills(new ImmutableMap.Builder<Skill, Integer>()
//					.put(Skill.RANGED, 99)
//					.put(Skill.ATTACK, 99)
//					.put(Skill.STRENGTH, 99)
//					.put(Skill.DEFENCE, 99)
//					.put(Skill.MAGIC, 99)
//					.put(Skill.PRAYER, 99)
//					.build()
//			);
//			sp.setBoosts(ImmutableMap.of(Skill.RANGED, 13));
//			pp.setOffensive(Prayer.RIGOUR);

			frame.add(pluginPanel);

			frame.setSize(242, 800);
			frame.setResizable(true);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}