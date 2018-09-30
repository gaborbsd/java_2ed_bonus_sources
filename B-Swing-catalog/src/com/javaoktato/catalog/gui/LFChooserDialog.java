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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class LFChooserDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JList<String> list;
	private JFrame topLevel[];

	class LFChooserActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			LFUtil.setLF(list.getSelectedValue());
			for (JFrame frame : topLevel)
				SwingUtilities.updateComponentTreeUI(frame);
			setVisible(false);
		}
	}

	public LFChooserDialog(JFrame owner, final JFrame[] topLevel) {
		super(owner, "Kinézet kiválasztása", true);
		this.topLevel = topLevel;

		LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
		List<String> plafList = new ArrayList<>();
		for (LookAndFeelInfo li : plafs) {
			plafList.add(li.getClassName());
		}

		list = new JList<>(plafList.toArray(new String[0]));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedValue(LFUtil.getLF(), true);
		this.add(list, BorderLayout.CENTER);

		JButton button = new JButton("OK");

		button.addActionListener(new LFChooserActionListener());
		this.add(button, BorderLayout.SOUTH);
		this.setMinimumSize(new Dimension(300, 400));
	}
}
