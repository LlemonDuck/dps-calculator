package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.plugin.ui.skills.StatCategory;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState.MutableDefensiveBonuses;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundStatBox;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.JPanel;
import lombok.Getter;

@Singleton
public class NpcBonusesPanel extends JPanel implements StateBoundComponent
{

	private static ObjIntConsumer<PanelState> writer(ObjIntConsumer<MutableDefensiveBonuses> inner)
	{
		return (state, value) -> inner.accept(state.getDefenderBonuses(), value);
	}

	private static ToIntFunction<PanelState> reader(ToIntFunction<MutableDefensiveBonuses> inner)
	{
		return state -> inner.applyAsInt(state.getDefenderBonuses());
	}

	@Getter
	private final PanelStateManager manager;
	private final List<StateBoundStatBox> statBoxes = new ArrayList<>(5);

	@Inject
	public NpcBonusesPanel(PanelStateManager manager)
	{
		this.manager = manager;

		statBoxes.add(new StateBoundStatBox(manager, "dstab", "Stab", false, writer(MutableDefensiveBonuses::setDefenseStab), reader(MutableDefensiveBonuses::getDefenseStab)));
		statBoxes.add(new StateBoundStatBox(manager, "dslash", "Slash", false, writer(MutableDefensiveBonuses::setDefenseSlash), reader(MutableDefensiveBonuses::getDefenseSlash)));
		statBoxes.add(new StateBoundStatBox(manager, "dcrush", "Crush", false, writer(MutableDefensiveBonuses::setDefenseCrush), reader(MutableDefensiveBonuses::getDefenseCrush)));
		statBoxes.add(new StateBoundStatBox(manager, "dmagic", "Magic", false, writer(MutableDefensiveBonuses::setDefenseMagic), reader(MutableDefensiveBonuses::getDefenseMagic)));
		statBoxes.add(new StateBoundStatBox(manager, "drange", "Ranged", false, writer(MutableDefensiveBonuses::setDefenseRanged), reader(MutableDefensiveBonuses::getDefenseRanged)));
		add(new StatCategory("Defensive Bonuses", statBoxes));

		add(Box.createVerticalStrut(5));
		setMaximumSize(new Dimension(200, 134));
	}

	@Override
	public void toState()
	{
		statBoxes.forEach(StateBoundStatBox::toState);
	}

	@Override
	public void fromState()
	{
		statBoxes.forEach(StateBoundStatBox::fromState);
	}

	public void setEditable(boolean editable)
	{
		statBoxes.forEach(sbsb -> sbsb.setEditable(editable));
	}
}
