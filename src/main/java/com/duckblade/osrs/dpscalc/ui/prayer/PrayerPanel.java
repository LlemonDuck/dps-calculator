package com.duckblade.osrs.dpscalc.ui.prayer;

import com.duckblade.osrs.dpscalc.model.Prayer;
import com.duckblade.osrs.dpscalc.ui.util.CustomJCheckBox;
import com.duckblade.osrs.dpscalc.ui.util.CustomJComboBox;
import com.google.common.collect.ImmutableMap;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.PluginPanel;

public class PrayerPanel extends JPanel
{

	private final CustomJComboBox<Prayer> offensePrayerSelect;
	private final Map<Prayer, CustomJCheckBox> otherPrayers;

	@Inject
	public PrayerPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		
		offensePrayerSelect = new CustomJComboBox<>(Prayer.OFFENSE, Prayer::getDisplayName, "Offensive Prayer");
		offensePrayerSelect.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 25, 40));
		offensePrayerSelect.setAlignmentX(LEFT_ALIGNMENT);
		add(offensePrayerSelect);

		add(Box.createVerticalStrut(10));
		
		add(new JLabel("Other Prayers"));
		add(Box.createVerticalStrut(5));
		
		ImmutableMap.Builder<Prayer, CustomJCheckBox> builder = new ImmutableMap.Builder<>();
		Prayer.UTILITY.forEach(prayer ->
		{
			CustomJCheckBox check = new CustomJCheckBox(prayer.getDisplayName());
			check.setAlignmentX(LEFT_ALIGNMENT);
			
			add(check);
			add(Box.createVerticalStrut(5));
			builder.put(prayer, check);
		});
		otherPrayers = builder.build();
	}

	public Prayer getOffensive()
	{
		return offensePrayerSelect.getValue();
	}

	public void setOffensive(Prayer newValue)
	{
		offensePrayerSelect.setValue(newValue);
	}

	private List<Prayer> getOthers()
	{
		return otherPrayers.entrySet()
				.stream()
				.filter(e -> e.getValue().getValue())
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	public int getDrain()
	{
		Prayer oPrayer = offensePrayerSelect.getValue();
		int oDrain = oPrayer == null ? 0 : oPrayer.getDrainRate();
		return oDrain +
				getOthers().stream()
						.mapToInt(Prayer::getDrainRate)
						.sum();
	}

	public String getSummary()
	{
		Prayer offense = getOffensive();
		List<Prayer> enabledOthers = getOthers();
		if (offense == null && enabledOthers.isEmpty())
			return "None";
	
		boolean multipleOther = enabledOthers.size() != 1;
		if (offense == null)
		{
			if (!multipleOther)
				return enabledOthers.get(0).getDisplayName();
			return enabledOthers.size() + " prayers";
		}
		else if (enabledOthers.isEmpty())
			return offense.getDisplayName();
		else
			return offense.getDisplayName() + " + " + enabledOthers.size() + " other" + (multipleOther ? "s" : "");
	}

}
