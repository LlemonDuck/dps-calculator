package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.plugin.ui.skills.StatCategory;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState.MutableDefenderAttributes;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJCheckBox;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundStatBox;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import lombok.Getter;

@Singleton
public class NpcAttributesPanel extends JPanel implements StateBoundComponent
{

	private static ObjIntConsumer<PanelState> writer(ObjIntConsumer<MutableDefenderAttributes> inner)
	{
		return (state, value) -> inner.accept(state.getDefenderAttributes(), value);
	}

	private static ToIntFunction<PanelState> reader(ToIntFunction<MutableDefenderAttributes> inner)
	{
		return state -> inner.applyAsInt(state.getDefenderAttributes());
	}

	private static BiConsumer<PanelState, Boolean> writer(BiConsumer<MutableDefenderAttributes, Boolean> inner)
	{
		return (state, value) -> inner.accept(state.getDefenderAttributes(), value);
	}

	private static Predicate<PanelState> reader(Predicate<MutableDefenderAttributes> inner)
	{
		return state -> inner.test(state.getDefenderAttributes());
	}

	@Getter
	private final PanelStateManager manager;
	private final List<StateBoundStatBox> numericalAttrBoxes = new ArrayList<>(5);
	private final List<StateBoundJCheckBox> booleanAttrBoxes = new ArrayList<>(6);

	@Inject
	public NpcAttributesPanel(PanelStateManager manager)
	{
		this.manager = manager;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(200, 300));
		setMaximumSize(new Dimension(200, 300));

		numericalAttrBoxes.add(new StateBoundStatBox(manager, "id", "NPC ID", false, writer(MutableDefenderAttributes::setNpcId), reader(MutableDefenderAttributes::getNpcId)));
		numericalAttrBoxes.add(new StateBoundStatBox(manager, "amagic", "Magic Acc.", false, writer(MutableDefenderAttributes::setAccuracyMagic), reader(MutableDefenderAttributes::getAccuracyMagic)));
		numericalAttrBoxes.add(new StateBoundStatBox(manager, "size", "Size", false, writer(MutableDefenderAttributes::setSize), reader(MutableDefenderAttributes::getSize)));
		add(new StatCategory("Defensive Bonuses", numericalAttrBoxes));
		add(Box.createVerticalStrut(5));

		JPanel booleanAttrPanel = new JPanel();
		booleanAttrPanel.setLayout(new GridLayout(4, 2));
		add(booleanAttrPanel);

		booleanAttrBoxes.add(new StateBoundJCheckBox("Demon", manager, writer(MutableDefenderAttributes::setDemon), reader(MutableDefenderAttributes::isDemon)));
		booleanAttrBoxes.add(new StateBoundJCheckBox("Dragon", manager, writer(MutableDefenderAttributes::setDragon), reader(MutableDefenderAttributes::isDragon)));
		booleanAttrBoxes.add(new StateBoundJCheckBox("Kalphite", manager, writer(MutableDefenderAttributes::setKalphite), reader(MutableDefenderAttributes::isKalphite)));
		booleanAttrBoxes.add(new StateBoundJCheckBox("Leafy", manager, writer(MutableDefenderAttributes::setLeafy), reader(MutableDefenderAttributes::isLeafy)));
		booleanAttrBoxes.add(new StateBoundJCheckBox("Undead", manager, writer(MutableDefenderAttributes::setUndead), reader(MutableDefenderAttributes::isUndead)));
		booleanAttrBoxes.add(new StateBoundJCheckBox("T1 Vampyre", manager, writer(MutableDefenderAttributes::setVampyre1), reader(MutableDefenderAttributes::isVampyre1)));
		booleanAttrBoxes.add(new StateBoundJCheckBox("T2 Vampyre", manager, writer(MutableDefenderAttributes::setVampyre2), reader(MutableDefenderAttributes::isVampyre2)));
		booleanAttrBoxes.add(new StateBoundJCheckBox("T3 Vampyre", manager, writer(MutableDefenderAttributes::setVampyre3), reader(MutableDefenderAttributes::isVampyre3)));
		booleanAttrBoxes.forEach(sbsb -> sbsb.setEditable(false));
		booleanAttrBoxes.forEach(booleanAttrPanel::add);
	}

	@Override
	public void toState()
	{
		numericalAttrBoxes.forEach(StateBoundStatBox::toState);
	}

	@Override
	public void fromState()
	{
		setPreferredSize(new Dimension(200, 300));
		setMaximumSize(new Dimension(200, 300));
		numericalAttrBoxes.forEach(StateBoundStatBox::fromState);
	}

	public void setEditable(boolean editable)
	{
		numericalAttrBoxes.forEach(sbsb -> sbsb.setEditable(editable));
		booleanAttrBoxes.forEach(sbsb -> sbsb.setEditable(editable));
	}
}
