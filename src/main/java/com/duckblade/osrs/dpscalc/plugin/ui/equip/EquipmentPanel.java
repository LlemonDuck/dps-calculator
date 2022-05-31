package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.AttackerItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.ammo.BlowpipeDartsItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.exceptions.DpsComputeException;
import com.duckblade.osrs.dpscalc.calc.exceptions.MissingInputException;
import com.duckblade.osrs.dpscalc.calc.gearbonus.BlackMaskGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.MageDemonbaneGearBonus;
import com.duckblade.osrs.dpscalc.calc.gearbonus.RevenantWeaponGearBonus;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import com.duckblade.osrs.dpscalc.calc.multihit.DharoksDptComputable;
import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import com.duckblade.osrs.dpscalc.plugin.osdata.ItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJCheckBox;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJComboBox;
import com.duckblade.osrs.dpscalc.plugin.ui.util.JTextFieldIntOnlyKeyAdapter;
import com.duckblade.osrs.dpscalc.plugin.ui.util.SelectAllFocusListener;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.runelite.api.Client;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.GameState;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import net.runelite.api.VarPlayer;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

public class EquipmentPanel extends JPanel
{

	private static final DecimalFormat STAT_LABEL_FORMAT = new DecimalFormat(": #.#");

	private final Client client;
	private final ClientThread clientThread;
	private final ItemManager rlItemManager;
	private final ItemStatsProvider itemStatsProvider;

	// todo these shouldn't be here, i need another way to determine if the fields are needed
	private final BlackMaskGearBonus blackMaskGearBonus;
	private final DharoksDptComputable dharoksDptComputable;
	private final RevenantWeaponGearBonus revenantWeaponGearBonus;
	private final BlowpipeDartsItemStatsComputable blowpipeDartsItemStatsComputable;
	private final MageDemonbaneGearBonus mageDemonbaneGearBonus;
	private final AttackerItemStatsComputable attackerItemStatsComputable;

	private final Map<EquipmentInventorySlot, EquipmentSlotPanel> slotPanels;
	private final CustomJCheckBox slayerCheck;
	private final EquipmentSlotPanel weaponSlot;
	private final CustomJCheckBox inWilderness;

	private final DartSelectPanel tbpDartSelectPanel;
	private final CustomJComboBox<AttackStyle> attackStyleSelect;
	private final CustomJComboBox<Spell> spellSelect;
	private final CustomJCheckBox markOfDarkness;

	private final JPanel dharokPanel;
	private final JTextField dharokHpField;
	private final JTextField dharokMaxHpField;

	private final JPanel totalsPanel;

	@Inject
	public EquipmentPanel(
		@Nullable Client client, @Nullable ClientThread clientThread, @Nullable ItemManager rlItemManager,
		DpsCalcConfig config, ItemStatsProvider itemStatsProvider,
		BlackMaskGearBonus blackMaskGearBonus, DharoksDptComputable dharoksDptComputable, RevenantWeaponGearBonus revenantWeaponGearBonus,
		BlowpipeDartsItemStatsComputable blowpipeDartsItemStatsComputable, MageDemonbaneGearBonus mageDemonbaneGearBonus,
		AttackerItemStatsComputable attackerItemStatsComputable
	)
	{
		this.client = client;
		this.clientThread = clientThread;
		this.rlItemManager = rlItemManager;
		this.itemStatsProvider = itemStatsProvider;

		this.blackMaskGearBonus = blackMaskGearBonus;
		this.dharoksDptComputable = dharoksDptComputable;
		this.revenantWeaponGearBonus = revenantWeaponGearBonus;
		this.blowpipeDartsItemStatsComputable = blowpipeDartsItemStatsComputable;
		this.mageDemonbaneGearBonus = mageDemonbaneGearBonus;
		this.attackerItemStatsComputable = attackerItemStatsComputable;

		this.slotPanels = new HashMap<>(EquipmentInventorySlot.values().length);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 1000));

		JButton loadFromClientButton = new JButton("Load From Client");
		loadFromClientButton.addActionListener(e -> loadFromClient());
		loadFromClientButton.setAlignmentX(CENTER_ALIGNMENT);
		add(loadFromClientButton);

		add(Box.createVerticalStrut(10));

		JPanel slotPanel = new JPanel();
		slotPanel.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		slotPanel.setLayout(new BoxLayout(slotPanel, BoxLayout.Y_AXIS));
		slotPanel.setAlignmentX(CENTER_ALIGNMENT);
		add(slotPanel);

		slayerCheck = new CustomJCheckBox("On Slayer Task");
		slayerCheck.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		slayerCheck.setValue(false);
		slayerCheck.setEditable(true);
		slayerCheck.setVisible(false);

		inWilderness = new CustomJCheckBox("In Wilderness");
		inWilderness.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		inWilderness.setValue(false);
		inWilderness.setEditable(true);
		inWilderness.setVisible(false);

		for (EquipmentInventorySlot slot : EquipmentInventorySlot.values())
		{
			EquipmentSlotPanel innerPanel = new EquipmentSlotPanel(this.rlItemManager, this.itemStatsProvider, slot, this::onEquipmentChanged);
			this.slotPanels.put(slot, innerPanel);
			slotPanel.add(innerPanel);
			slotPanel.add(Box.createVerticalStrut(5));

			if (slot == EquipmentInventorySlot.HEAD)
			{
				slotPanel.add(slayerCheck);
			}

			if (slot == EquipmentInventorySlot.WEAPON)
			{
				slotPanel.add(inWilderness);
			}
		}
		weaponSlot = slotPanels.get(EquipmentInventorySlot.WEAPON);

		add(Box.createVerticalStrut(5));

		dharokHpField = new JTextField("1", 3);
		dharokHpField.setAlignmentX(Component.CENTER_ALIGNMENT); // of component
		dharokHpField.setHorizontalAlignment(JTextField.CENTER); // of inner text
		dharokHpField.addFocusListener(new SelectAllFocusListener(dharokHpField));
		dharokHpField.addKeyListener(new JTextFieldIntOnlyKeyAdapter());

		dharokMaxHpField = new JTextField("99", 3);
		dharokMaxHpField.setAlignmentX(Component.CENTER_ALIGNMENT); // of component
		dharokMaxHpField.setHorizontalAlignment(JTextField.CENTER); // of inner text
		dharokMaxHpField.addFocusListener(new SelectAllFocusListener(dharokHpField));
		dharokMaxHpField.addKeyListener(new JTextFieldIntOnlyKeyAdapter());

		dharokPanel = new JPanel();
		dharokPanel.setMinimumSize(new Dimension(0, 70));
		dharokPanel.setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 70));
		dharokPanel.setLayout(new GridLayout(3, 2));
		dharokPanel.setVisible(false);
		add(dharokPanel);

		dharokPanel.add(new JLabel("Active HP"));
		dharokPanel.add(new JLabel("Max HP"));
		dharokPanel.add(dharokHpField);
		dharokPanel.add(dharokMaxHpField);
		dharokPanel.add(Box.createVerticalStrut(10));

		tbpDartSelectPanel = new DartSelectPanel();
		tbpDartSelectPanel.setCallback(this::onEquipmentChanged);
		tbpDartSelectPanel.setAlignmentX(CENTER_ALIGNMENT);
		tbpDartSelectPanel.setVisible(false);
		tbpDartSelectPanel.setValue(config.defaultTbpDarts());
		add(tbpDartSelectPanel);

		attackStyleSelect = new CustomJComboBox<>(WeaponCategory.UNARMED.getAttackStyles(), AttackStyle::getDisplayName, "Weapon Mode");
		attackStyleSelect.setCallback(this::onEquipmentChanged);
		attackStyleSelect.setAlignmentX(CENTER_ALIGNMENT);
		add(attackStyleSelect);

		add(Box.createVerticalStrut(10));

		spellSelect = new CustomJComboBox<>(Collections.emptyList(), Spell::getDisplayName, "Spell");
		spellSelect.setCallback(this::onEquipmentChanged);
		spellSelect.setAlignmentX(CENTER_ALIGNMENT);
		spellSelect.setVisible(false);
		add(spellSelect);

		add(Box.createVerticalStrut(10));

		markOfDarkness = new CustomJCheckBox("Mark of Darkness");
		markOfDarkness.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		markOfDarkness.setValue(false);
		markOfDarkness.setEditable(true);
		markOfDarkness.setVisible(false);
		add(markOfDarkness);

		totalsPanel = new JPanel();
		totalsPanel.setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		totalsPanel.setLayout(new BoxLayout(totalsPanel, BoxLayout.Y_AXIS));
		totalsPanel.setAlignmentX(CENTER_ALIGNMENT);
		add(totalsPanel);

		rebuildTotals();
	}

	public AttackStyle getAttackStyle()
	{
		return attackStyleSelect.getValue();
	}

	public void setAttackStyle(AttackStyle newValue)
	{
		attackStyleSelect.setValue(newValue);
		onEquipmentChanged();
	}

	public Map<EquipmentInventorySlot, ItemStats> getEquipment()
	{
		HashMap<EquipmentInventorySlot, ItemStats> resultMap = new HashMap<>();
		slotPanels.forEach((key, panel) ->
		{
			ItemStats v = panel.getValue();
			if (v != null)
			{
				resultMap.put(key, panel.getValue());
			}
		});
		return resultMap;
	}

	public void setEquipment(Map<EquipmentInventorySlot, ItemStats> newEquipment)
	{
		newEquipment.forEach((slot, item) -> slotPanels.get(slot).setValue(item));

		// don't defer this step to onEquipmentChanged, since it uses invokeLater, and a caller may set this value after setting a weapon
		ItemStats newWeapon = newEquipment.get(EquipmentInventorySlot.WEAPON);
		List<AttackStyle> styles = newWeapon == null ? WeaponCategory.UNARMED.getAttackStyles() : newWeapon.getWeaponCategory().getAttackStyles();
		attackStyleSelect.setItems(styles);

		onEquipmentChanged();
	}

	public boolean isOnSlayerTask()
	{
		return slayerCheck.getValue();
	}

	public void setOnSlayerTask(boolean newValue)
	{
		slayerCheck.setValue(newValue);
		onEquipmentChanged();
	}

	public boolean isInWilderness()
	{
		return inWilderness.getValue();
	}

	public void setInWilderness(boolean inWildy)
	{
		inWilderness.setValue(inWildy);
		onEquipmentChanged();
	}

	public boolean isUsingMarkOfDarkness()
	{
		return markOfDarkness.getValue();
	}

	public void setUsingMarkOfDarkness(boolean newValue)
	{
		markOfDarkness.setValue(newValue);
		onEquipmentChanged();
	}

	public int getActiveHp()
	{
		if (!dharokPanel.isVisible())
		{
			return 1;
		}

		String content = dharokHpField.getText();
		if (content.isEmpty())
		{
			return 1;
		}

		return Integer.parseInt(content);
	}

	public int getMaxHp()
	{
		if (!dharokPanel.isVisible())
		{
			return 99;
		}

		String content = dharokMaxHpField.getText();
		if (content.isEmpty())
		{
			return 99;
		}

		return Integer.parseInt(content);
	}

	public ItemStats getTbpDarts()
	{
		return itemStatsProvider.getById(tbpDartSelectPanel.getValue().getItemId());
	}

	public Spell getSpell()
	{
		return spellSelect.getValue();
	}

	public void loadFromClient()
	{
		// this method could maybe be refactored to not nest as much but meh 

		if (client == null || clientThread == null || client.getGameState() != GameState.LOGGED_IN)
		{
			return; // ui test, or not init yet somehow
		}

		clientThread.invokeLater(() ->
		{
			ItemContainer equipped = client.getItemContainer(InventoryID.EQUIPMENT);
			if (equipped == null)
			{
				return;
			}

			slotPanels.forEach((slot, panel) ->
			{
				Item rlItem = equipped.getItem(slot.getSlotIdx());
				if (rlItem == null)
				{
					panel.setValue(null);
					return;
				}

				int canonicalId = rlItemManager.canonicalize(rlItem.getId());
				ItemStats calcItem = itemStatsProvider.getById(canonicalId);
				panel.setValue(calcItem);
			});

			// this is also done in onEquipmentChanged, but we trigger early so we can set value
			ItemStats currentWeapon = weaponSlot.getValue();
			List<AttackStyle> modes = updateAttackStyleComboBox(currentWeapon);

			int weaponModeVarp = client.getVar(VarPlayer.ATTACK_STYLE);
			modes.stream()
				.filter(wm -> wm.getVarpValue() == weaponModeVarp)
				.findAny()
				.ifPresent(wm ->
				{
					attackStyleSelect.setValue(wm);

					if (wm.getAttackType() == AttackType.MAGIC)
					{
						List<Spell> availableSpells = updateSpellComboBox(currentWeapon, getEquipment());
						int spellVarb = client.getVarbitValue(276); // todo magic number
						availableSpells.stream()
							.filter(s -> s.getVarbValue() == spellVarb)
							.findFirst()
							.ifPresent(spellSelect::setValue);
					}
				});

			onEquipmentChanged();
		});
	}

	private List<AttackStyle> updateAttackStyleComboBox(ItemStats currentWeapon)
	{
		WeaponCategory category = currentWeapon == null ? WeaponCategory.UNARMED : currentWeapon.getWeaponCategory();
		List<AttackStyle> attackStyles = category.getAttackStyles();
		attackStyleSelect.setItems(attackStyles);
		return attackStyles;
	}

	private List<Spell> updateSpellComboBox(ItemStats currentWeapon, Map<EquipmentInventorySlot, ItemStats> equipment)
	{
		assert currentWeapon != null;

		List<Spell> allSpells = Arrays.asList(Spell.values());
		spellSelect.setItems(allSpells);
		return allSpells; // todo restrict to autocast list by weapon
	}

	public void onEquipmentChanged()
	{
		SwingUtilities.invokeLater(() ->
		{
			// not sure if i'm happy with this approach
			ComputeContext gearCheckContext = new ComputeContext();
			Map<EquipmentInventorySlot, ItemStats> equipment = getEquipment();
			gearCheckContext.put(ComputeInputs.ATTACKER_EQUIPMENT, equipment);

			ItemStats currentWeapon = equipment.get(EquipmentInventorySlot.WEAPON);
			updateAttackStyleComboBox(currentWeapon);

			AttackStyle attackStyle = getAttackStyle();
			gearCheckContext.put(ComputeInputs.ATTACK_STYLE, attackStyle);

			if (attackStyle != null)
			{
				slayerCheck.setVisible(blackMaskGearBonus.isApplicable(gearCheckContext));
				tbpDartSelectPanel.setVisible(blowpipeDartsItemStatsComputable.isApplicable(gearCheckContext));
				dharokPanel.setVisible(dharoksDptComputable.isApplicable(gearCheckContext));
				inWilderness.setVisible(revenantWeaponGearBonus.isApplicable(gearCheckContext));
			}

			if (attackStyle != null && attackStyle.getAttackType() == AttackType.MAGIC)
			{
				assert currentWeapon != null;
				List<Spell> availableSpells = updateSpellComboBox(currentWeapon, equipment);
				if (availableSpells.size() == 1)
				{
					// don't prompt for a spell if there's only one available (powered staves/salamanders)
					spellSelect.setValue(availableSpells.get(0));
					spellSelect.setVisible(false);
				}
				else
				{
					spellSelect.setVisible(true);
				}
			}
			else
			{
				spellSelect.setVisible(false);
			}

			Spell spell = getSpell();
			if (spell != null)
			{
				gearCheckContext.put(ComputeInputs.SPELL, getSpell());
				markOfDarkness.setVisible(mageDemonbaneGearBonus.isApplicable(gearCheckContext));
			}

			rebuildTotals();
			revalidate();
			repaint();
		});
	}

	private JLabel buildStatLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		return label;
	}

	private JLabel buildStatLabel(String statName, int stat)
	{
		return buildStatLabel(statName + ": " + stat);
	}

	private JLabel buildStatLabel(String statName, double stat)
	{
		return buildStatLabel(statName + STAT_LABEL_FORMAT.format(stat));
	}

	private void rebuildTotals()
	{
		totalsPanel.removeAll();

		ComputeContext ctx = new ComputeContext();
		ctx.put(ComputeInputs.ATTACKER_EQUIPMENT, getEquipment());
		ctx.put(ComputeInputs.BLOWPIPE_DARTS, getTbpDarts());
		ctx.put(ComputeInputs.ATTACK_STYLE, getAttackStyle());

		ItemStats total;
		try
		{
			total = ctx.get(attackerItemStatsComputable);
		}
		catch (DpsComputeException e)
		{
			if (e.getCause() instanceof MissingInputException)
			{
				total = ItemStats.EMPTY;
			}
			else
			{
				throw e;
			}
		}

		totalsPanel.add(Box.createHorizontalGlue());

		totalsPanel.add(buildStatLabel("Stab Accuracy", total.getAccuracyStab()));
		totalsPanel.add(buildStatLabel("Slash Accuracy", total.getAccuracySlash()));
		totalsPanel.add(buildStatLabel("Crush Accuracy", total.getAccuracyCrush()));
		totalsPanel.add(buildStatLabel("Magic Accuracy", total.getAccuracyMagic()));
		totalsPanel.add(buildStatLabel("Ranged Accuracy", total.getAccuracyRanged()));

		totalsPanel.add(Box.createVerticalStrut(10));

		totalsPanel.add(buildStatLabel("Melee Strength", total.getStrengthMelee()));
		totalsPanel.add(buildStatLabel("Ranged Strength", total.getStrengthRanged()));
		totalsPanel.add(buildStatLabel("Magic Damage Bonus", total.getStrengthMagic()));

		totalsPanel.add(Box.createVerticalStrut(10));

		totalsPanel.add(buildStatLabel("Weapon Speed", total.getSpeed()));
		totalsPanel.add(buildStatLabel("Prayer", total.getPrayer()));
	}

	public boolean isReady()
	{
		// ensure selected dart if using tbp
		ItemStats currentWeapon = weaponSlot.getValue();
		if (currentWeapon != null && currentWeapon.getItemId() == ItemID.TOXIC_BLOWPIPE && getTbpDarts() == null)
		{
			return false;
		}

		// ensure selected attack style (wearing nothing is fine)
		AttackStyle attackStyle = attackStyleSelect.getValue();
		if (attackStyle == null)
		{
			return false;
		}

		// ensure spell is selected if needed
		return attackStyle.getAttackType() != AttackType.MAGIC || spellSelect.getValue() != null;
	}

	public String getSummary()
	{
		if (!isReady())
		{
			return "Not Set";
		}

		String weaponName = weaponSlot.getValue() == null ? "Unarmed" : weaponSlot.getValue().getName();
		return attackStyleSelect.getValue().getAttackType() + " - " + weaponName;
	}
}