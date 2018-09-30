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

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LFUtil {
	private static String PROPFILE = "ui.properties";
	private static String LF = "LookAndFeel";

	private static String lf = null;

	private static Properties props;

	public static String getLF() {
		if (lf == null) {
			props = new Properties();
			try {
				props.load(new FileReader(PROPFILE));
			} catch (IOException e) {
				lf = UIManager.getLookAndFeel().getClass().getCanonicalName();
				return lf;
			}
			lf = props.getProperty(LF);
			if (lf == null)
				lf = UIManager.getLookAndFeel().getClass().getCanonicalName();
		}
		return lf;
	}

	public static void loadLF() {
		getLF();
		if (lf != null)
			try {
				UIManager.setLookAndFeel(lf);
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
	}

	public static void setLF(String lf) {
		if (!lf.equals(LFUtil.lf)) {
			LFUtil.lf = lf;
			if (props == null) {
				props = new Properties();
			}
			props.setProperty(LF, lf);
			try {
				props.store(new FileWriter(PROPFILE), "GUI settings");
				UIManager.setLookAndFeel(lf);
			} catch (IOException | ClassNotFoundException
					| InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
}
