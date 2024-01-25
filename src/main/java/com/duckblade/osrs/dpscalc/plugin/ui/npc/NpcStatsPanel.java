package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProviderThreadProxy;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState.MutableDefenderAttributes;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState.MutableDefensiveBonuses;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJCheckBox;
import com.duckblade.osrs.dpscalc.plugin.ui.util.LoadFromClientButton;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.Getter;

@Singleton
public class NpcStatsPanel extends JPanel implements StateBoundComponent
{

	@Getter
	private final PanelStateManager manager;
	private final ClientDataProviderThreadProxy clientDataProviderThreadProxy;

	private final CustomJCheckBox manualEntry;
	private final NpcSelectPanel npcSelectPanel;
	private final RaidPartySizePanel raidPartySizePanel;
	private final NpcSkillsPanel npcSkillsPanel;
	private final NpcBonusesPanel npcBonusesPanel;
	private final NpcAttributesPanel npcAttributesPanel;
	private final ToaInvocationLevelPanel toaInvocationLevelPanel;
	private final ToaPathLevelPanel toaPathLevelPanel;

	@Inject
	public NpcStatsPanel(
		PanelStateManager manager, ClientDataProviderThreadProxy clientDataProviderThreadProxy,
		NpcSelectPanel npcSelectPanel, RaidPartySizePanel raidPartySizePanel, NpcSkillsPanel npcSkillsPanel,
		NpcBonusesPanel npcBonusesPanel, NpcAttributesPanel npcAttributesPanel, ToaInvocationLevelPanel toaInvocationLevelPanel, ToaPathLevelPanel toaPathLevelPanel
	)
	{
		this.manager = manager;
		this.raidPartySizePanel = raidPartySizePanel;
		this.toaInvocationLevelPanel = toaInvocationLevelPanel;
		this.toaPathLevelPanel = toaPathLevelPanel;
		this.clientDataProviderThreadProxy = clientDataProviderThreadProxy;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(new LoadFromClientButton(this::loadFromClient));

		manualEntry = new CustomJCheckBox("Manual Entry Mode");
		manualEntry.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
		manualEntry.addCallback(() -> setManualMode(manualEntry.getValue()));
		add(manualEntry);
		add(Box.createVerticalStrut(5));

		this.npcSelectPanel = npcSelectPanel;
		npcSelectPanel.addCallback(this::fromState);
		add(npcSelectPanel);

		raidPartySizePanel.addCallback(this::fromState);
		add(raidPartySizePanel);

		toaInvocationLevelPanel.addCallback(this::fromState);
		add(toaInvocationLevelPanel);

		toaPathLevelPanel.addCallback(this::fromState);
		add(toaPathLevelPanel);

		this.npcSkillsPanel = npcSkillsPanel;
		add(npcSkillsPanel);

		this.npcBonusesPanel = npcBonusesPanel;
		add(npcBonusesPanel);

		this.npcAttributesPanel = npcAttributesPanel;
		add(npcAttributesPanel);
	}

	public void setManualMode(boolean manualMode)
	{
		if (!manualMode)
		{
			npcSelectPanel.setValue(null);
		}
		npcSelectPanel.setVisible(!manualMode);

		npcSkillsPanel.setEditable(manualMode);
		npcBonusesPanel.setEditable(manualMode);
		npcAttributesPanel.setEditable(manualMode);
	}

	public void loadFromClient()
	{
		clientDataProviderThreadProxy.tryAcquire(clientDataProvider ->
		{
			Skills s = clientDataProvider.getNpcTargetSkills();
			getState().setDefenderSkills(s != null ? s.getTotals() : Skills.EMPTY.getTotals());
			getState().setDefenderBonuses(MutableDefensiveBonuses.fromImmutable(clientDataProvider.getNpcTargetBonuses()));
			getState().setDefenderAttributes(MutableDefenderAttributes.fromImmutable(clientDataProvider.getNpcTargetAttributes()));
			SwingUtilities.invokeLater(this::fromState);
		});
	}

	@Override
	public void toState()
	{
		raidPartySizePanel.toState();
		toaInvocationLevelPanel.toState();
		toaPathLevelPanel.toState();
		if (!manualEntry.getValue())
		{
			npcSelectPanel.toState();

			npcSkillsPanel.fromScaled();
			return;
		}
		npcSkillsPanel.toState();
		npcBonusesPanel.toState();
		npcAttributesPanel.toState();
	}

	@Override
	public void fromState()
	{
		raidPartySizePanel.fromState();
		raidPartySizePanel.updateVisibility();
		toaInvocationLevelPanel.fromState();
		toaInvocationLevelPanel.updateVisibility();
		toaPathLevelPanel.fromState();
		toaPathLevelPanel.updateVisibility();
		if (!manualEntry.getValue())
		{
			npcSelectPanel.fromState();

			npcSkillsPanel.fromScaled();
			npcBonusesPanel.fromState();
			npcAttributesPanel.fromState();
			return;
		}
		npcSkillsPanel.fromState();
		npcBonusesPanel.fromState();
		npcAttributesPanel.fromState();
	}

	public boolean isReady()
	{
		return manualEntry.getValue() || npcSelectPanel.getValue() != null;
	}

	public String getSummary()
	{
		if (!isReady())
		{
			return "Not Set";
		}

		if (manualEntry.getValue())
		{
			return "Entered Manually";
		}

		return getState().getDefenderAttributes().getName();
	}

}
