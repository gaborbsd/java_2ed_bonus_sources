/*
 * Copyright © 2018. Kövesdán Gábor
 * 
 * Az alábbi forráskód a "Szoftverfejlesztés Java SE platformon"
 * c. könyv második kiadásának (ISBN 978-615-00-2933-7) mellékletét
 * képezi.  A forráskódot vagy annak részeit a kiadó engedélye nélkül
 * tilos reprodukálni, adatrögzítő rendszerben tárolni, bármilyen
 * formában vagy eszközzel elektronikus úton vagy más módon közölni.
 */

package com.javaoktato.catalog.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.javaoktato.catalog.domain.CatalogEntry;
import com.javaoktato.catalog.domain.CatalogEntry.EntryType;

public class EditDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField titleField = new JTextField();
	private JTextField authorField = new JTextField();
	private JSpinner minSpinner = new JSpinner();
	private JTextArea descArea = new JTextArea(10, getWidth());
	private JLabel errLabel = new JLabel();
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton CDRadio = new JRadioButton("CD");
	private JRadioButton DVDRadio = new JRadioButton("DVD");
	private JRadioButton BDRadio = new JRadioButton("BD");

	class EditDialogActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean validates = validateData();
			if (validates)
				setVisible(false);
		}
	}

	{
		setLayout(new GridBagLayout());

		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(0, 2));
		dataPanel.setBorder(BorderFactory.createTitledBorder("Adatok:"));
		dataPanel.add(new JLabel("Cím:"));
		dataPanel.add(titleField);
		dataPanel.add(new JLabel("Szerző:"));
		dataPanel.add(authorField);
		dataPanel.add(new JLabel("Hossz (perc):"));
		minSpinner.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
		minSpinner.setEditor(new JSpinner.NumberEditor(minSpinner));
		dataPanel.add(minSpinner);

		JPanel typePanel = new JPanel();
		typePanel.setLayout(new GridLayout(0, 1));
		typePanel.setBorder(BorderFactory.createTitledBorder("Típus:"));
		CDRadio.setActionCommand("CD");
		buttonGroup.add(CDRadio);
		typePanel.add(CDRadio);
		DVDRadio.setActionCommand("DVD");
		buttonGroup.add(DVDRadio);
		typePanel.add(DVDRadio);
		BDRadio.setActionCommand("BD");
		buttonGroup.add(BDRadio);
		typePanel.add(BDRadio);

		JPanel descPanel = new JPanel();
		descPanel.setLayout(new CardLayout());
		descPanel.setBorder(BorderFactory.createTitledBorder("Leírás:"));
		descPanel.add(new JScrollPane(descArea));	

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new EditDialogActionListener());
		errLabel.setMinimumSize(new Dimension(50, 10));
		errLabel.setForeground(new Color(255, 0, 0));

		this.add(dataPanel, new GridBagConstraints(0, 0, 1, 1, 8, 4,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		this.add(typePanel, new GridBagConstraints(1, 0, 1, 1, 2, 4,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		this.add(descPanel, new GridBagConstraints(0, 1, 2, 2, 8, 8,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		this.add(errLabel, new GridBagConstraints(0, 3, 1, 2, 1, 1,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		this.add(okButton, new GridBagConstraints(1, 3, 1, 1, 1, 1,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		setMinimumSize(new Dimension(600, 400));
	}

	public EditDialog(JFrame owner) {
		super(owner, true);
	}

	public EditDialog(JFrame owner, CatalogEntry entry) {
		super(owner, true);
		titleField.setText(entry.getTitle());
		authorField.setText(entry.getAuthor());
		minSpinner.setValue(entry.getLenghtInMins());
		switch (entry.getType()) {
		case BD:
			buttonGroup.setSelected(BDRadio.getModel(), true);
			break;
		case CD:
			buttonGroup.setSelected(CDRadio.getModel(), true);
			break;
		case DVD:
			buttonGroup.setSelected(DVDRadio.getModel(), true);
			break;
		}
		descArea.setText(entry.getDesc());
	}

	private boolean validateData() {
		String msg = null;

		if (titleField.getText().equals(""))
			msg = "Adja meg a címet!";
		else if (authorField.getText().equals(""))
			msg = "Adja meg az előadót!";
		else if (buttonGroup.getSelection() == null)
			msg = "Adja meg a lemez típusát!";

		if (msg != null) {
			errLabel.setText(msg);
			return false;
		}
		return true;
	}

	public CatalogEntry parseForm() {
		CatalogEntry entry = new CatalogEntry();
		entry.setTitle(titleField.getText());
		entry.setAuthor(authorField.getText());
		entry.setLenghtInMins((Integer) minSpinner.getValue());
		entry.setType(EntryType.valueOf(buttonGroup.getSelection()
				.getActionCommand()));
		entry.setDesc(descArea.getText());
		return entry;
	}
}
