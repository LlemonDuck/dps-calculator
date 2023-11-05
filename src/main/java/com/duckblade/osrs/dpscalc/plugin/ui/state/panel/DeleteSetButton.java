package com.duckblade.osrs.dpscalc.plugin.ui.state.panel;

import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import net.runelite.client.util.ImageUtil;

@Singleton
public class DeleteSetButton extends JButton
{

	private static final ImageIcon DELETE_ICON =
		new ImageIcon(ImageUtil.resizeImage(ImageUtil.loadImageResource(DeleteSetButton.class, "icon_delete.png"), 20, 20));

	@Inject
	public DeleteSetButton(PanelStateManager manager)
	{
		super(DELETE_ICON);
		setFocusPainted(false);
		addActionListener(e ->
		{
			int confirmResult = JOptionPane.showConfirmDialog(this, "Delete the current set?", "Delete Set", JOptionPane.YES_NO_OPTION);
			if (confirmResult == JOptionPane.YES_OPTION)
			{
				manager.deleteSet(manager.currentSet());
			}
		});
	}
}
