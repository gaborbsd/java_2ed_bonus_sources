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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class CatalogUtil {

	public static void saveCatalog(List<CatalogEntry> catalog, String fileName)
			throws FileNotFoundException, IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(fileName));) {
			oos.writeObject(catalog);
		}
	}

	public static List<CatalogEntry> openCatalog(String fileName)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				fileName));) {
			@SuppressWarnings("unchecked")
			List<CatalogEntry> catalog = (List<CatalogEntry>) ois.readObject();
			return catalog;
		}
	}
}
