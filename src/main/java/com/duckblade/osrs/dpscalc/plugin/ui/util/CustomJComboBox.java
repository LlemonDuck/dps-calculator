package com.duckblade.osrs.dpscalc.plugin.ui.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.runelite.client.ui.PluginPanel;

public class CustomJComboBox<T> extends JPanel
{

	private static final int HEIGHT_WITH_TITLE = 40;
	private static final int HEIGHT_WITHOUT_TITLE = 25;

	private final JLabel titleLabel;
	private final JComboBox<String> comboBox;

	private List<T> items;
	private final Function<T, String> displayMapper;
	private boolean allowNull = true;
	private boolean nullLast = false;
	private String nullText = "";

	private boolean callbackEnabled = true; // disables callback when programmatically setting combobox value

	private final List<Runnable> callbacks = new ArrayList<>();

	public CustomJComboBox(List<T> items, Function<T, String> displayMapper, String title)
	{
		this.displayMapper = displayMapper;
		setMinimumSize(new Dimension(0, title != null ? HEIGHT_WITH_TITLE : HEIGHT_WITHOUT_TITLE));
		setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, title != null ? HEIGHT_WITH_TITLE : HEIGHT_WITHOUT_TITLE));
		setLayout(new BorderLayout());

		titleLabel = new JLabel();
		titleLabel.setVisible(false);
		setTitle(title);

		comboBox = new JComboBox<>();
		comboBox.setPrototypeDisplayValue("");
		comboBox.addActionListener(e ->
		{
			if (callbackEnabled)
			{
				invokeCallbacks();
			}
		});
		this.items = items;
		updateInternalComboBox();
		add(comboBox, BorderLayout.CENTER);
	}

	public void setTitle(String title)
	{
		SwingUtilities.invokeLater(() ->
		{
			boolean alreadyVisible = titleLabel.isVisible();
			titleLabel.setText(title);
			titleLabel.setVisible(title != null);
			setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, title != null ? HEIGHT_WITH_TITLE : HEIGHT_WITHOUT_TITLE));

			if (!alreadyVisible && titleLabel.isVisible())
			{
				add(titleLabel, BorderLayout.NORTH);
			}
			else if (alreadyVisible && !titleLabel.isVisible())
			{
				remove(titleLabel);
			}
		});
	}

	public void setItems(List<T> newItems)
	{
		if (newItems != items)
		{
			items = newItems;
			updateInternalComboBox();
		}
	}

	public void setAllowNull(boolean newValue)
	{
		if (allowNull != newValue)
		{
			allowNull = newValue;
			if (items.isEmpty())
			{
				throw new IllegalStateException("Must provide non-empty items before disabling null");
			}
			updateInternalComboBox();
		}
	}

	public void setNullLast(boolean newValue)
	{
		if (nullLast != newValue)
		{
			nullLast = newValue;
			updateInternalComboBox();
		}
	}

	public void setNullText(String newValue)
	{
		newValue = newValue == null ? "" : newValue;
		if (!newValue.equals(nullText))
		{
			nullText = newValue;
			updateInternalComboBox();
		}
	}

	private void updateInternalComboBox()
	{
		callbackEnabled = false;
		comboBox.removeAllItems();
		if (allowNull && !nullLast)
		{
			comboBox.addItem(nullText);
		}

		items.stream()
			.map(displayMapper)
			.forEach(comboBox::addItem);

		if (allowNull && nullLast)
		{
			comboBox.addItem(nullText);
		}
		callbackEnabled = true;
	}

	public T getValue()
	{
		if (!allowNull)
		{
			return items.get(comboBox.getSelectedIndex());
		}

		int nullIx = nullLast ? items.size() : 0;
		if (comboBox.getSelectedIndex() == nullIx)
		{
			return null;
		}

		int nullOffset = nullLast ? 0 : 1;
		return items.get(comboBox.getSelectedIndex() - nullOffset);
	}

	public void setValue(T newValue)
	{
		callbackEnabled = false;
		if (newValue == null)
		{
			if (!allowNull)
			{
				callbackEnabled = true;
				throw new IllegalArgumentException(newValue + " does not exist in items");
			}

			int nullIx = nullLast ? items.size() : 0;
			comboBox.setSelectedIndex(nullIx);
		}
		else
		{
			int nullOffset = (allowNull && !nullLast) ? 1 : 0;
			comboBox.setSelectedIndex(items.indexOf(newValue) + nullOffset);
		}
		callbackEnabled = true;
	}

	public void addCallback(Runnable r)
	{
		callbacks.add(r);
	}

	protected void invokeCallbacks()
	{
		callbacks.forEach(Runnable::run);
	}

	public void enableAutocomplete()
	{
		AutoCompletion.enable(comboBox);
	}

}
