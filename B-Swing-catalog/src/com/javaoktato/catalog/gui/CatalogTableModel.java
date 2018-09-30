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

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.javaoktato.catalog.domain.CatalogEntry;

public class CatalogTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private List<CatalogEntry> list;
	private String[] colNames = { "Cím", "Előadá", "Típus", "Hossz (perc)",
			"Leírás" };

	public CatalogTableModel() {
		this.list = new ArrayList<>();
	}

	public CatalogTableModel(List<CatalogEntry> list) {
		this.list = list;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int entryNo, int propNo) {
		CatalogEntry e = list.get(entryNo);
		switch (propNo) {
		case 0:
			return e.getTitle();
		case 1:
			return e.getAuthor();
		case 2:
			return e.getType().toString();
		case 3:
			return Integer.toString(e.getLenghtInMins());
		case 4:
			return e.getDesc();
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public void remove(int idx) {
		list.remove(idx);
		fireTableRowsDeleted(idx, idx);
	}

	public CatalogEntry get(int idx) {
		return list.get(idx);
	}

	public void set(int idx, CatalogEntry entry) {
		list.set(idx, entry);
		fireTableRowsUpdated(idx, idx);
	}

	public void clear() {
		list.clear();
		fireTableDataChanged();
	}

	public void add(CatalogEntry entry) {
		list.add(entry);
		fireTableRowsInserted(list.size() - 2, list.size() - 2);
	}

	public void newCatalog(List<CatalogEntry> list) {
		this.list = list;
		fireTableDataChanged();
	}

	public List<CatalogEntry> getCatalog() {
		return list;
	}
}
