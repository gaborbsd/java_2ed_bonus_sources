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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableRowSorter;

import com.javaoktato.catalog.domain.CatalogEntry;
import com.javaoktato.catalog.domain.CatalogUtil;

public class MainWindow implements Runnable {

	private JFrame mainWindow;
	CatalogTableModel tableModel = new CatalogTableModel();
	JTable table = new JTable(tableModel);

	class CatalogActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ActionCommand cmd = ActionCommand.valueOf(e.getActionCommand());
			if (cmd == null)
				return;

			switch (cmd) {
			case ABOUT:
				AboutDialog dialog = new AboutDialog(mainWindow);
				dialog.setVisible(true);
				break;
			case DELETE_ENTRY:
				int row = table.getSelectedRow();
				tableModel.remove(row);
				break;
			case DETAILS_ENTRY:
				int rowToUpdate = table.getSelectedRow();
				CatalogEntry toUpdate = tableModel.get(rowToUpdate);
				EditDialog editDialog = new EditDialog(mainWindow, toUpdate);
				editDialog.setVisible(true);
				CatalogEntry modifiedEntry = editDialog.parseForm();
				tableModel.set(rowToUpdate, modifiedEntry);
				break;
			case EXIT:
				System.exit(0);
				break;
			case NEW_DOCUMENT:
				tableModel.clear();
				break;
			case NEW_ENTRY:
				EditDialog newDialog = new EditDialog(mainWindow);
				newDialog.setVisible(true);
				CatalogEntry newEntry = newDialog.parseForm();
				tableModel.add(newEntry);
				break;
			case OPEN_DOCUMENT:
				JFileChooser openChooser = new JFileChooser();
				int retOpen = openChooser.showOpenDialog(mainWindow);

				if (retOpen == JFileChooser.APPROVE_OPTION) {
					String openFile = openChooser.getSelectedFile()
							.getAbsolutePath();
					try {
						List<CatalogEntry> opened = CatalogUtil
								.openCatalog(openFile);
						tableModel.newCatalog(opened);
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case SAVE_DOCUMENT:
				JFileChooser saveChooser = new JFileChooser();
				int retSave = saveChooser.showSaveDialog(mainWindow);
				if (retSave == JFileChooser.APPROVE_OPTION) {
					String saveFile = saveChooser.getSelectedFile()
							.getAbsolutePath();
					try {
						CatalogUtil.saveCatalog(tableModel.getCatalog(),
								saveFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case OPTIONS_LF:
				new LFChooserDialog(mainWindow, new JFrame[] { mainWindow })
						.setVisible(true);
				SwingUtilities.updateComponentTreeUI(mainWindow);
			}
		}
	}

	@Override
	public void run() {
		// Kinézet betöltése
		LFUtil.loadLF();
		mainWindow = new JFrame("Lemezkatalógus");

		ActionListener actionListener = new CatalogActionListener();

		JMenu fileMenu = new JMenu("Fájl");
		JMenuItem fileNewMenu = new JMenuItem("Új");
		fileNewMenu.setActionCommand(ActionCommand.NEW_DOCUMENT.toString());
		fileNewMenu.addActionListener(actionListener);
		fileMenu.add(fileNewMenu);

		JMenuItem fileOpenMenu = new JMenuItem("Megnyitás...");
		fileOpenMenu.setActionCommand(ActionCommand.OPEN_DOCUMENT.toString());
		fileOpenMenu.addActionListener(actionListener);
		fileMenu.add(fileOpenMenu);

		JMenuItem fileSaveMenu = new JMenuItem("Mentés...");
		fileSaveMenu.setActionCommand(ActionCommand.SAVE_DOCUMENT.toString());
		fileSaveMenu.addActionListener(actionListener);
		fileMenu.add(fileSaveMenu);

		fileMenu.addSeparator();

		JMenuItem fileExitMenu = new JMenuItem("Kilépés");
		fileExitMenu.setActionCommand(ActionCommand.EXIT.toString());
		fileExitMenu.addActionListener(actionListener);
		fileMenu.add(fileExitMenu);

		JMenu optionsMenu = new JMenu("Beállítások");
		JMenuItem optionsLFMenu = new JMenuItem("Megjelenés...");
		optionsLFMenu.setActionCommand(ActionCommand.OPTIONS_LF.toString());
		optionsLFMenu.addActionListener(actionListener);
		optionsMenu.add(optionsLFMenu);

		JMenu helpMenu = new JMenu("Súgó");
		JMenuItem helpAboutMenu = new JMenuItem("Névjegy...");
		helpAboutMenu.setActionCommand(ActionCommand.ABOUT.toString());
		helpAboutMenu.addActionListener(actionListener);
		helpMenu.add(helpAboutMenu);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);
		mainWindow.setJMenuBar(menuBar);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setMinimumSize(new Dimension(750, 550));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSorter(new TableRowSorter<CatalogTableModel>(tableModel));
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, new GridBagConstraints(0, 0, 3, 1, 4, 80,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 10, 10));
		JButton newButton = new JButton("Új...");
		newButton.setActionCommand(ActionCommand.NEW_ENTRY.toString());
		newButton.addActionListener(actionListener);
		JButton deleteButton = new JButton("Töröl");
		deleteButton.setActionCommand(ActionCommand.DELETE_ENTRY.toString());
		deleteButton.addActionListener(actionListener);
		JButton detailsButton = new JButton("Módosít...");
		detailsButton.setActionCommand(ActionCommand.DETAILS_ENTRY.toString());
		detailsButton.addActionListener(actionListener);
		panel.add(newButton, new GridBagConstraints(0, 1, 1, 1, 1, 1,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 10, 10));
		panel.add(deleteButton, new GridBagConstraints(1, 1, 1, 1, 1, 1,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 10, 10));
		panel.add(detailsButton, new GridBagConstraints(2, 1, 1, 1, 1, 1,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 10, 10));

		mainWindow.add(panel);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setMinimumSize(new Dimension(800, 600));
		mainWindow.setVisible(true);
	}
}
