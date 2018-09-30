/*
 * Copyright © 2018. Kövesdán Gábor
 * 
 * Az alábbi forráskód a "Szoftverfejlesztés Java SE platformon"
 * c. könyv második kiadásának (ISBN 978-615-00-2933-7) mellékletét
 * képezi.  A forráskódot vagy annak részeit a kiadó engedélye nélkül
 * tilos reprodukálni, adatrögzítő rendszerben tárolni, bármilyen
 * formában vagy eszközzel elektronikus úton vagy más módon közölni.
 */

package com.javaoktato.rental.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Set;

import com.javaoktato.rental.domain.Flat;
import com.javaoktato.rental.server.RentalAgencyService;


public class Client {
	private static final String SERVICE_NAME = "RentalService";

	public static void main(String args[]) {
		try {
			Registry registry = LocateRegistry.getRegistry(args[0]);
			RentalAgencyService service = (RentalAgencyService) registry
					.lookup(SERVICE_NAME);
			Set<Flat> results = service.search(60000, 90000);
			System.out
					.println("60 000 éss 90 000 Ft közötti bérleti díjjal rendelkező albérletek");
			for (Flat f : results)
				System.out.println(f.toString());
			Flat f = new Flat("Káposztásmegyer", 50, 2, 62000,
					"sanyialberlet@email.hu");
			String key = service.add(f);
			results = service.search(60000, 90000);
			if (results.contains(f))
				System.out.println("Hirdetés sikeresen hozzáadva.");
			service.remove(key);
			results = service.search(60000, 90000);
			if (!results.contains(f))
				System.out.println("Hirdetés sikeresen törölve.");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}
