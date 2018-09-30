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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import com.javaoktato.rental.domain.Flat;

public interface RentalAgencyService extends Remote {
	Set<Flat> search(double minPrice, double maxPrice) throws RemoteException;
	String add(Flat f) throws RemoteException;
	boolean remove(String key) throws RemoteException;
}
