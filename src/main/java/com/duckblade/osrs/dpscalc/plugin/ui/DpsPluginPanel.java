package com.duckblade.osrs.dpscalc.plugin.ui;

import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.panel.DeleteSetButton;
import com.duckblade.osrs.dpscalc.plugin.ui.state.panel.PanelInputSetSelect;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

	private final DpsCalcPanel calcPanel;

	private static final String GITHUB_LINK = "https://github.com/LlemonDuck/dps-calculator";

	@Inject
	public DpsPluginPanel(
		DpsCalcPanel calcPanel, PanelInputSetSelect panelInputSetSelect, DeleteSetButton deleteSetButton,
		PanelStateManager manager
	)
	{
		super(false);
		this.calcPanel = calcPanel;
		manager.addOnSetChangedListener(this::showHome);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));

		JPanel headerPanel = new JPanel();
		headerPanel.setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 40));
		headerPanel.setLayout(new GridBagLayout());
		add(headerPanel);

		ImageIcon homeIcon = new ImageIcon(ImageUtil.resizeImage(ImageUtil.loadImageResource(getClass(), "icon_home.png"), 25, 25));
		JButton homeButton = new JButton(homeIcon);
		homeButton.setFocusPainted(false);
		homeButton.setPreferredSize(new Dimension(35, 35));
		homeButton.addActionListener(e -> showHome());
		headerPanel.add(homeButton, new GridBagConstraints(0, 0, 1, 1, 0, 0, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0));

		headerPanel.add(panelInputSetSelect, new GridBagConstraints(1, 0, 1, 1, 1, 1, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0));
		headerPanel.add(deleteSetButton, new GridBagConstraints(2, 0, 1, 1, 0, 0, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0));

		ImageIcon ghIcon = new ImageIcon(ImageUtil.resizeImage(ImageUtil.loadImageResource(getClass(), "gh_logo.png"), 25, 25));
		JButton linkToGh = new JButton(ghIcon);
		linkToGh.setFocusPainted(false);
		linkToGh.setPreferredSize(new Dimension(35, 35));
		linkToGh.addActionListener(e -> openGhLink());
		headerPanel.add(linkToGh, new GridBagConstraints(3, 0, 1, 1, 0, 0, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0));

		JScrollPane contentScrollPane = new JScrollPane(calcPanel);
		contentScrollPane.setBorder(BorderFactory.createEmptyBorder());
		contentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		add(contentScrollPane);
	}

	private void openGhLink()
	{
		LinkBrowser.browse(GITHUB_LINK);
	}

	private void showHome()
	{
		SwingUtilities.invokeLater(calcPanel::openMenu);
	}

}
