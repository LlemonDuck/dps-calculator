package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.calc.CalcManager;
import com.duckblade.osrs.dpscalc.calc.MageDpsCalc;
import com.duckblade.osrs.dpscalc.calc.MeleeDpsCalc;
import com.duckblade.osrs.dpscalc.calc.RangedDpsCalc;
import com.duckblade.osrs.dpscalc.model.Prayer;
import com.duckblade.osrs.dpscalc.model.WeaponType;
import com.duckblade.osrs.dpscalc.ui.DpsCalculatorPanel;
import com.duckblade.osrs.dpscalc.ui.equip.EquipmentPanel;
import com.duckblade.osrs.dpscalc.ui.prayer.PrayerPanel;
import com.duckblade.osrs.dpscalc.ui.npc.NpcStatsPanel;
import com.duckblade.osrs.dpscalc.ui.skills.SkillsPanel;
import com.google.common.collect.ImmutableMap;
import java.awt.Cursor;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import net.runelite.api.NpcID;
import net.runelite.api.Skill;
import net.runelite.client.ui.ContainableFrame;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.skin.SubstanceRuneLiteLookAndFeel;
import net.runelite.client.util.SwingUtil;

public class DPSCalcUITest
{

	public static void main(String[] args) throws InterruptedException, InvocationTargetException
	{
		SwingUtilities.invokeAndWait(() ->
		{
			// roughly copied from RuneLite's ClientUI.java init()
			SwingUtil.setupDefaults();
			SwingUtil.setTheme(new SubstanceRuneLiteLookAndFeel());
			SwingUtil.setFont(FontManager.getRunescapeFont());

			ContainableFrame frame = new ContainableFrame();
			frame.getLayeredPane().setCursor(Cursor.getDefaultCursor());

			NpcDataManager npcDataManager = new NpcDataManager();
			ItemDataManager itemDataManager = new ItemDataManager();

			CalcManager cm = new CalcManager(new MageDpsCalc(), new MeleeDpsCalc(), new RangedDpsCalc());
			NpcStatsPanel nsp = new NpcStatsPanel(npcDataManager);
			EquipmentPanel ep = new EquipmentPanel(null, null, itemDataManager);
			SkillsPanel sp = new SkillsPanel(null);
			PrayerPanel pp = new PrayerPanel(); // heh

			// preloading (current is tbow max hit)
			nsp.loadNpcStats(npcDataManager.getNpcStatsById(NpcID.BRUTAL_BLACK_DRAGON));
			ep.setEquipment(ImmutableMap.of(
					EquipmentInventorySlot.WEAPON, itemDataManager.getItemStatsById(ItemID.TWISTED_BOW),
					EquipmentInventorySlot.AMMO, itemDataManager.getItemStatsById(ItemID.DRAGON_ARROW),
					EquipmentInventorySlot.AMULET, itemDataManager.getItemStatsById(ItemID.NECKLACE_OF_ANGUISH),
					EquipmentInventorySlot.CAPE, itemDataManager.getItemStatsById(ItemID.AVAS_ASSEMBLER),
					EquipmentInventorySlot.HEAD, itemDataManager.getItemStatsById(ItemID.SLAYER_HELMET_I)
			));
			ep.setOnSlayerTask(true);
			ep.setWeaponMode(WeaponType.BOW.getWeaponModes().get(0));
			sp.setSkills(new ImmutableMap.Builder<Skill, Integer>()
					.put(Skill.RANGED, 99)
					.put(Skill.ATTACK, 99)
					.put(Skill.STRENGTH, 99)
					.put(Skill.DEFENCE, 99)
					.put(Skill.MAGIC, 99)
					.put(Skill.PRAYER, 99)
					.build()
			);
			sp.setBoosts(ImmutableMap.of(Skill.RANGED, 13));
			pp.setOffensive(Prayer.RIGOUR);

			frame.add(new DpsCalculatorPanel(cm, nsp, ep, sp, pp));

			frame.setSize(242, 800);
			frame.setResizable(true);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}
