package com.duckblade.osrs.dpscalc.ui.util;

import java.awt.BorderLayout;
import java.util.List;
import java.util.function.Function;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Setter;

public class CustomJComboBox<T> extends JPanel
{

	private final JLabel titleLabel;
	private final JComboBox<String> comboBox;

	private List<T> items;
	private final Function<T, String> displayMapper;

	private boolean callbackEnabled = true; // disables callback when programmatically setting combobox value

	@Setter
	private Runnable callback;

	public CustomJComboBox(List<T> items, Function<T, String> displayMapper, String title)
	{
		this.displayMapper = displayMapper;
		setLayout(new BorderLayout());

		titleLabel = new JLabel();
		titleLabel.setVisible(false);
		setTitle(title);

		comboBox = new JComboBox<>();
		comboBox.setPrototypeDisplayValue("");
		comboBox.addActionListener(e ->
		{
			if (callbackEnabled && callback != null)
				callback.run();
		});
		setItems(items);
		add(comboBox, BorderLayout.CENTER);
	}

	public void setTitle(String title)
	{
		boolean alreadyVisible = titleLabel.isVisible();
		titleLabel.setText(title);
		titleLabel.setVisible(title != null);

		if (!alreadyVisible && titleLabel.isVisible())
			add(titleLabel, BorderLayout.NORTH);
		else if (alreadyVisible && !titleLabel.isVisible())
			remove(titleLabel);
	}

	public void setItems(List<T> newItems)
	{
		if (newItems == items)
			return;
		
		callbackEnabled = false;
		items = newItems;
		comboBox.removeAllItems();
		comboBox.addItem("");

		items.stream()
				.map(displayMapper)
				.forEach(comboBox::addItem);
		callbackEnabled = true;
	}

	public T getValue()
	{
		// empty string is 0
		if (comboBox.getSelectedIndex() <= 0)
			return null;

		return items.get(comboBox.getSelectedIndex() - 1);
	}

	public void setValue(T newValue)
	{
		callbackEnabled = false;
		// -1 return value of item not found maps to selecting index 0 which is none. Useful!
		comboBox.setSelectedIndex(items.indexOf(newValue) + 1);
		callbackEnabled = true;
	}

	public void enableAutocomplete()
	{
		AutoCompletion.enable(comboBox);
	}

}
