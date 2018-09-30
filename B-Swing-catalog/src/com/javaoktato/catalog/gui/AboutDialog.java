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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	class AboutDialogActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			AboutDialog.this.setVisible(false);
		}
	}

	public AboutDialog(JFrame owner) {
		super(owner, "A programról...", true);
		JLabel label = new JLabel("<html><p align='center'>Kövesdán Gábor: "
				+ "Szoftverfejlesztés "
				+ "Java SE platformon<br>Második kiadás<br>Swing bónuszfejezet, példaprogram</p>");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(label, BorderLayout.CENTER);
		JButton closeButton = new JButton("Bezárás");
		closeButton.addActionListener(new AboutDialogActionListener());
		this.add(closeButton, BorderLayout.SOUTH);
		this.setMinimumSize(new Dimension(300, 300));
	}
}
