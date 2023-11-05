package com.duckblade.osrs.dpscalc.plugin.ui.state.panel;

import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelInputSet;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJComboBox;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JOptionPane;

@Singleton
public class PanelInputSetSelect extends CustomJComboBox<PanelInputSet>
{

	private final PanelStateManager manager;

	@Inject
	public PanelInputSetSelect(PanelStateManager manager)
	{
		super(manager.getInputSets(), PanelInputSet::getName, null);
		this.manager = manager;
		manager.addOnSetChangedListener(() ->
		{
			setItems(manager.getInputSets());
			setValue(manager.getCurrentSet());
		});

		setValue(manager.currentSet());
		setNullLast(true);
		setNullText("New...");

		addCallback(() ->
		{
			if (getValue() == null)
			{
				createNewSet();
			}
			else
			{
				manager.selectSet(getValue());
			}
		});
	}

	public void createNewSet()
	{
		String name = JOptionPane.showInputDialog(this, "New set name:", "Create Set", JOptionPane.QUESTION_MESSAGE);
		if (name != null)
		{
			manager.createNewSet(name);
		}
	}

}
