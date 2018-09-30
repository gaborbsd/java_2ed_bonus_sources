/*
 * Copyright © 2018. Kövesdán Gábor
 * 
 * Az alábbi forráskód a "Szoftverfejlesztés Java SE platformon"
 * c. könyv második kiadásának (ISBN 978-615-00-2933-7) mellékletét
 * képezi.  A forráskódot vagy annak részeit a kiadó engedélye nélkül
 * tilos reprodukálni, adatrögzítő rendszerben tárolni, bármilyen
 * formában vagy eszközzel elektronikus úton vagy más módon közölni.
 */

package com.javaoktato.rental.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.javaoktato.rental.domain.Flat;

public class Server {
	private static final String SERVICE_NAME = "RentalService";

	public static void main(String[] args) {
		RentalAgencyService service = new RentalAgencyServiceImpl();

		Flat f1 = new Flat("Budaörs", 54, 2, 55000, "info@kovacsistvan.hu");
		Flat f2 = new Flat("Dunakeszi", 67, 4, 82000, "alberlet@ingyenmail.hu");
		Flat f3 = new Flat("Angyalföld", 35, 1, 40000,
				"angyalfoldalbi@email.hu");

		try {
			service.add(f1);
			service.add(f2);
			service.add(f3);

			RentalAgencyService stub = (RentalAgencyService) UnicastRemoteObject
					.exportObject(service, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind(SERVICE_NAME, stub);
			System.out.println("A szolgáltatás elérhető.");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
