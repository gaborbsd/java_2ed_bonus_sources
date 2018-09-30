/*
 * Copyright © 2018. Kövesdán Gábor
 * 
 * Az alábbi forráskód a "Szoftverfejlesztés Java SE platformon"
 * c. könyv második kiadásának (ISBN 978-615-00-2933-7) mellékletét
 * képezi.  A forráskódot vagy annak részeit a kiadó engedélye nélkül
 * tilos reprodukálni, adatrögzítő rendszerben tárolni, bármilyen
 * formában vagy eszközzel elektronikus úton vagy más módon közölni.
 */

package com.javaoktato.carrepository.app;

import com.javaoktato.carrepository.domain.Car;
import com.javaoktato.carrepository.domain.CarRepository;
import com.javaoktato.carrepository.domain.Person;
import com.javaoktato.carrepository.domain.PersonSex;

public class CarRespositoryApp {

	public static void list(CarRepository repo) {
		System.out.println("Autók listája:");
		for (Car car : repo.getCars()) {
			System.out.println(car);
			for (Person person : car.getOwners())
				System.out.println("\t" + person);
		}

		System.out.println("Tulajdonosok listája:");
		for (Person person : repo.getOwners()) {
			System.out.println(person);
			for (Car car : repo.getCars(person))
				System.out.println("\t" + car);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		try (CarRepository repo = new CarRepository();) {
			Car c = new Car("AAA-111", "Ford", "Focus");
			Person p = new Person("Gábor", "Kövesdán", PersonSex.MALE);
			c.addOwner(p);
			repo.save(c);

			list(repo);

			Thread.sleep(2000);

			Person p2 = new Person("Adrienn", "Kovács", PersonSex.FEMALE);
			repo.addOwnerForCar(c, p2);

			Thread.sleep(2000);

			list(repo);
			repo.removeOwnerForCar(c, p);
			list(repo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
