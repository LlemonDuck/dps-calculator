package com.duckblade.osrs.dpscalc.plugin.ui.state.panel;

import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import net.runelite.client.util.ImageUtil;

@Singleton
public class DuplicateSetButton extends JButton
{

    private static final ImageIcon DELETE_ICON =
            new ImageIcon(ImageUtil.resizeImage(ImageUtil.loadImageResource(DeleteSetButton.class, "icon_copy.png"), 20, 20));

    @Inject
    public DuplicateSetButton(PanelStateManager manager)
    {
        super(DELETE_ICON);
        setFocusPainted(false);
        addActionListener(e ->
        {
            String name = JOptionPane.showInputDialog(
                    this,
                    "New set name:",
                    "Duplicate \"" + manager.currentSet().getName() + "\"",
                    JOptionPane.QUESTION_MESSAGE);
            if (name != null)
            {
                manager.duplicateSet(manager.currentSet(), name);
            }
        });
    }
}
