/*
 * Copyright © 2018. Kövesdán Gábor
 * 
 * Az alábbi forráskód a "Szoftverfejlesztés Java SE platformon"
 * c. könyv második kiadásának (ISBN 978-615-00-2933-7) mellékletét
 * képezi.  A forráskódot vagy annak részeit a kiadó engedélye nélkül
 * tilos reprodukálni, adatrögzítő rendszerben tárolni, bármilyen
 * formában vagy eszközzel elektronikus úton vagy más módon közölni.
 */

package com.javaoktato.catalog.domain;

import java.io.Serializable;

public class CatalogEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum EntryType {
		CD, DVD, BD;
	}

	private String author;
	private String title;
	private EntryType type;
	private int lenghtInMins;
	private String desc;

	public CatalogEntry() {
	}

	public CatalogEntry(String author, String title, EntryType type,
			int lenghtInMins, String desc) {
		this.author = author;
		this.title = title;
		this.type = type;
		this.lenghtInMins = lenghtInMins;
		this.desc = desc;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public EntryType getType() {
		return type;
	}

	public void setType(EntryType type) {
		this.type = type;
	}

	public int getLenghtInMins() {
		return lenghtInMins;
	}

	public void setLenghtInMins(int lenghtInMins) {
		this.lenghtInMins = lenghtInMins;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CatalogEntry other = (CatalogEntry) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}