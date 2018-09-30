/*
 * Copyright © 2018. Kövesdán Gábor
 * 
 * Az alábbi forráskód a "Szoftverfejlesztés Java SE platformon"
 * c. könyv második kiadásának (ISBN 978-615-00-2933-7) mellékletét
 * képezi.  A forráskódot vagy annak részeit a kiadó engedélye nélkül
 * tilos reprodukálni, adatrögzítő rendszerben tárolni, bármilyen
 * formában vagy eszközzel elektronikus úton vagy más módon közölni.
 */

package com.javaoktato.phonebook.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.javaoktato.phonebook.domain.Person;
import com.javaoktato.phonebook.domain.PhoneBook;

public class Main {

	public static void main(String[] args) {
		// Adatok inicializálása
		Person p = new Person("Gábor", "Kövesdán",
				"Magyar Tudósok krt. 2. QB224\nBudapest 1117", "+3614632713");
		Person p2 = new Person("Doktorandusz", "Dániel",
				"Magyar Tudósok krt. 2. QB224\nBudapest 1117", "+3614632713");
		PhoneBook phoneBook = new PhoneBook();
		phoneBook.addEntry(p);
		phoneBook.addEntry(p2);

		try {

			// Kell egy JAXBContext, amivel létrehozzuk a Marshallert
			JAXBContext ctx = JAXBContext.newInstance(PhoneBook.class);
			Marshaller marshaller = ctx.createMarshaller();

			// Formázzuk a kiírt adatokat
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Kiírjuk a PhoneBook osztályt fájlba és képernyőre
			File f = new File("tmp.xml");
			marshaller.marshal(phoneBook, f);
			marshaller.marshal(phoneBook, System.out);

			// Visszaolvassuk fájlból, és megnézzük ugyanazt kapjuk-e
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			PhoneBook phoneBook2 = (PhoneBook) unmarshaller.unmarshal(f);
			if (phoneBook.equals(phoneBook2))
				System.out.println("Helyesen beolvastuk a kiírt adatot.");

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
