/*
 * Copyright © 2018. Kövesdán Gábor
 * 
 * Az alábbi forráskód a "Szoftverfejlesztés Java SE platformon"
 * c. könyv második kiadásának (ISBN 978-615-00-2933-7) mellékletét
 * képezi.  A forráskódot vagy annak részeit a kiadó engedélye nélkül
 * tilos reprodukálni, adatrögzítő rendszerben tárolni, bármilyen
 * formában vagy eszközzel elektronikus úton vagy más módon közölni.
 */

package com.javaoktato.carrepository.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CarRepository implements AutoCloseable {
	EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("CarPU");
	EntityManager em = factory.createEntityManager();
	EntityTransaction tx = em.getTransaction();

	public void save(Object o) {
		tx.begin();
		em.persist(o);
		tx.commit();
	}

	public void merge(Object o) {
		tx.begin();
		em.merge(o);
		tx.commit();
	}

	public void remove(Object o) {
		tx.begin();
		em.remove(o);
		tx.commit();
	}

	public void addOwnerForCar(Car c, Person p) {
		tx.begin();
		c.addOwner(p);
		em.merge(c);
		tx.commit();
	}

	public void removeOwnerForCar(Car c, Person p) {
		tx.begin();
		c.removeOwner(p);
		em.merge(c);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	public List<Car> getCars() {
		Query q = em.createQuery("SELECT c FROM Car c");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Car> getCars(Person p) {
		// Lehet pozíció alapján is paraméterezni
		// Query q = em
		//     .createQuery("SELECT c FROM Car c WHERE ?1 MEMBER OF c.owners");
		// q.setParameter(1, p);

		// Vagy névvel is paraméterezhetünk
		Query q = em
				.createQuery("SELECT c FROM Car c WHERE :owner MEMBER OF c.owners");
		q.setParameter("owner", p);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Person> getOwners() {
		Query q = em.createQuery("SELECT p FROM Person p");
		return q.getResultList();
	}

	@Override
	public void close() {
		em.close();
		factory.close();
	}
}