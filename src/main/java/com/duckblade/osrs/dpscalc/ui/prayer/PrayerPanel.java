package com.duckblade.osrs.dpscalc.ui.prayer;

import com.duckblade.osrs.dpscalc.model.Prayer;
import com.google.common.collect.ImmutableMap;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.PluginPanel;

public class PrayerPanel extends JPanel
{

	private final JLabel drainLabel;
	private final Map<Prayer, PrayerButton> prayerButtons;

	@Inject
	public PrayerPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));

		drainLabel = new JLabel("Total Drain: 0");
		drainLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(drainLabel);

		JPanel prayerGrid = new JPanel(new GridLayout(6, 5, 1, 1));
		prayerGrid.setMinimumSize(new Dimension(182, 219));
		prayerGrid.setPreferredSize(new Dimension(182, 219));
		prayerGrid.setMaximumSize(new Dimension(182, 219));
		add(prayerGrid);

		ImmutableMap.Builder<Prayer, PrayerButton> builder = new ImmutableMap.Builder<>();
		for (Prayer p : Prayer.values())
		{
			PrayerButton panel = new PrayerButton(p);
			panel.setCallback(this::prayerCallback);
			prayerGrid.add(panel);
			builder.put(p, panel);
		}
		prayerButtons = builder.build();
	}

	private void prayerCallback(PrayerButton invoker)
	{
		// todo prayer conflicts?
		updateDrainLabel();
	}

	private void updateDrainLabel()
	{
		drainLabel.setText("Total Drain: " + getDrain());
	}

	public List<Prayer> getSelected()
	{
		return prayerButtons.values()
				.stream()
				.filter(PrayerButton::isSelected)
				.map(PrayerButton::getPrayer)
				.collect(Collectors.toList());
	}

	public int getDrain()
	{
		return getSelected().stream()
				.mapToInt(Prayer::getDrainRate)
				.sum();
	}

	public String getSummary()
	{
		List<Prayer> enabled = getSelected();
		if (enabled.isEmpty())
			return "None";

		if (enabled.size() == 1)
			return enabled.get(0).getDisplayName();
		else
			return enabled.size() + " Selected";
	}

}
