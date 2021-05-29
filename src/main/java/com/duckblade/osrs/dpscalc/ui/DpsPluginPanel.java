package com.duckblade.osrs.dpscalc.ui;

import com.duckblade.osrs.dpscalc.model.NpcStats;
import com.duckblade.osrs.dpscalc.ui.util.ResizingCardLayout;
import com.google.inject.Injector;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;

@Singleton
public class DpsPluginPanel extends PluginPanel
{
	private static final int INPUT_SET_COUNT = 5;

	private final JPanel inputCardPanel;
	private final CardLayout inputCardLayout;

	private final JComboBox<String> inputSelect;
	private final List<DpsCalcPanel> inputPanels;

	private static final String GITHUB_LINK = "https://github.com/LlemonDuck/dps-calculator";

	// used by plugin
	@Inject
	public DpsPluginPanel(Injector injector)
	{
		this(IntStream.range(0, INPUT_SET_COUNT).mapToObj(i -> injector.getInstance(DpsCalcPanel.class)).collect(Collectors.toList()));
	}

	private DpsPluginPanel(List<DpsCalcPanel> calcPanels)
	{
		super(false);

		this.inputPanels = calcPanels;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));

		inputCardPanel = new JPanel();
		inputCardLayout = new ResizingCardLayout();
		inputCardPanel.setLayout(inputCardLayout);
		calcPanels.forEach(cp -> inputCardPanel.add(cp, cp.getPanelId()));

		JPanel headerPanel = new JPanel();
		headerPanel.setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 40));
		headerPanel.setLayout(new BorderLayout());
		add(headerPanel);

		JButton homeButton = new JButton("Home");
		homeButton.setPreferredSize(new Dimension(60, 30));
		homeButton.setForeground(Color.white);
		homeButton.addActionListener(e -> showHome());
		headerPanel.add(homeButton, BorderLayout.WEST);

		inputSelect = new JComboBox<>(new Vector<>(INPUT_SET_COUNT));
		calcPanels.forEach(cp -> inputSelect.addItem("Set " + cp.getPanelId()));
		inputSelect.setForeground(Color.white);
		inputSelect.addActionListener(e -> showHome());
		headerPanel.add(inputSelect, BorderLayout.CENTER);

		ImageIcon ghIcon = new ImageIcon(ImageUtil.resizeImage(ImageUtil.loadImageResource(getClass(), "gh_logo.png"), 25, 25));
		JButton linkToGh = new JButton(ghIcon);
		linkToGh.setPreferredSize(new Dimension(60, 30));
		linkToGh.addActionListener(e -> openGhLink());
		headerPanel.add(linkToGh, BorderLayout.EAST);

		JScrollPane contentScrollPane = new JScrollPane(inputCardPanel);
		contentScrollPane.setBorder(BorderFactory.createEmptyBorder());
		contentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(contentScrollPane);
	}

	private void openGhLink()
	{
		LinkBrowser.browse(GITHUB_LINK);
	}

	private DpsCalcPanel currentPanel()
	{
		int ix = inputSelect.getSelectedIndex();
		if (ix == -1)
			return inputPanels.get(0);

		return inputPanels.get(ix);
	}

	private void showHome()
	{
		SwingUtilities.invokeLater(() ->
		{
			DpsCalcPanel currentPanel = currentPanel();
			inputCardLayout.show(inputCardPanel, currentPanel.getPanelId());
			currentPanel.openMenu();
		});
	}

	public void openNpcStats(NpcStats preload)
	{
		DpsCalcPanel currentPanel = currentPanel();
		currentPanel.openNpcStats(preload);
	}

}
