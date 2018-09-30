/*
 * Copyright © 2018. Kövesdán Gábor
 * 
 * Az alábbi forráskód a "Szoftverfejlesztés Java SE platformon"
 * c. könyv második kiadásának (ISBN 978-615-00-2933-7) mellékletét
 * képezi.  A forráskódot vagy annak részeit a kiadó engedélye nélkül
 * tilos reprodukálni, adatrögzítő rendszerben tárolni, bármilyen
 * formában vagy eszközzel elektronikus úton vagy más módon közölni.
 */

package com.javaoktato.swingdemo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class HelloSwing extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	public class ExitActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	public void run() {
		// ablak címe
		setTitle("Helló, Swing!");

		// c�mke hozz�ad�sa
		JLabel label = new JLabel("Helló, Swing!");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(label, BorderLayout.CENTER);

		// gomb hozzáadása
		JButton button = new JButton("Bezár");
		// gomb eseménykezelője
		button.addActionListener(new ExitActionListener());
		this.add(button, BorderLayout.SOUTH);

		// minimális ablakméret
		this.setMinimumSize(new Dimension(400, 300));
		// jobb felső x-re bezárás
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// megjelenítés
		this.setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new HelloSwing());
	}
}
