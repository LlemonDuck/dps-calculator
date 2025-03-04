package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import static com.duckblade.osrs.dpscalc.calc.Constants.CAST_STANCES;
import com.duckblade.osrs.dpscalc.calc.model.PlayerCombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import static com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiDataProvider.ALL_SPELLS;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateVisibleComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJComboBox;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SpellSelectPanel extends StateBoundJComboBox<Spell> implements StateVisibleComponent
{

	@Inject
	public SpellSelectPanel(PanelStateManager manager)
	{
		super(
			ALL_SPELLS.stream()
				.sorted(Comparator.comparing(Spell::getName))
				.collect(Collectors.toList()),
			Spell::getName,
			"Spell",
			manager,
			(ps, v) -> ps.getPlayer().setSpell(v),
			ps -> ps.getPlayer().getSpell()
		);

		setAlignmentX(CENTER_ALIGNMENT);
		setVisible(false);
		addBottomPadding(10);
	}

	@Override
	public void updateVisibility()
	{
		PlayerCombatStyle style = getState().getPlayer().getStyle();
		if (style == null)
		{
			return;
		}

		boolean visible = CAST_STANCES.contains(style.getStance());
		setVisible(visible);
		if (!visible)
		{
			setValue(null);
		}
	}
}
