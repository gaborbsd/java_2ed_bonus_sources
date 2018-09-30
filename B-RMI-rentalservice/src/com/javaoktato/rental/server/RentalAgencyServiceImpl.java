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

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import com.javaoktato.rental.domain.Flat;

public class RentalAgencyServiceImpl implements RentalAgencyService {
	private Set<Flat> flats = new HashSet<>();
	private SecureRandom random = new SecureRandom();

	@Override
	public Set<Flat> search(double minPrice, double maxPrice)
			throws RemoteException {
		Set<Flat> resultSet = new HashSet<>();
		for (Flat f : flats) {
			if ((f.getRent() < maxPrice) && (f.getRent() > minPrice))
				resultSet.add(f);
		}
		return resultSet;
	}

	@Override
	public String add(Flat f) throws RemoteException {
		f.setKey(new BigInteger(130, random).toString(32));
		synchronized (flats) {
			flats.add(f);
		}
		return f.getKey();
	}

	@Override
	public boolean remove(String key) throws RemoteException {
		for (Flat f : flats) {
			if (f.getKey().equals(key)) {
				synchronized (flats) {
					flats.remove(f);
				}
				return true;
			}
		}
		return false;
	}
}
