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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Car {
	@Id
	@Column(name = "id")
	private String licensePlate;
	private String brand;
	private String model;
	@ManyToMany(cascade = { CascadeType.ALL })
	private Set<Person> owners = new HashSet<>();
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updated = null;

	private static SimpleDateFormat df = new SimpleDateFormat(
			"YYYY/MM/dd HH:mm:ss");

	@PreUpdate
	@PrePersist
	private void update() {
		updated = Calendar.getInstance();
	}

	public Car() {
	}

	public Car(String licensePlate, String brand, String model) {
		this.licensePlate = licensePlate;
		this.brand = brand;
		this.model = model;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Set<Person> getOwners() {
		return Collections.unmodifiableSet(owners);
	}

	public void addOwner(Person p) {
		owners.add(p);
	}

	public void removeOwner(Person p) {
		owners.remove(p);
	}

	@Override
	public String toString() {
		return licensePlate + " " + brand + " " + model + " "
				+ df.format(updated.getTime());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((licensePlate == null) ? 0 : licensePlate.hashCode());
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
		Car other = (Car) obj;
		if (licensePlate == null) {
			if (other.licensePlate != null)
				return false;
		} else if (!licensePlate.equals(other.licensePlate))
			return false;
		return true;
	}
}
