/*
 * Copyright © 2018. Kövesdán Gábor
 * 
 * Az alábbi forráskód a "Szoftverfejlesztés Java SE platformon"
 * c. könyv második kiadásának (ISBN 978-615-00-2933-7) mellékletét
 * képezi.  A forráskódot vagy annak részeit a kiadó engedélye nélkül
 * tilos reprodukálni, adatrögzítő rendszerben tárolni, bármilyen
 * formában vagy eszközzel elektronikus úton vagy más módon közölni.
 */

package com.javaoktato.rental.domain;

import java.io.Serializable;

public class Flat implements Serializable {
	private static final long serialVersionUID = 1L;

	// Itt tárolja a szerver a titkos azonosítót a törléshez,
	// ezért ezt nem adjuk át a klienseknek, tehát tranziens
	// kell legyen
	private transient String key;
	private String region;
	private int area;
	private int roomNo;
	private double rent;
	private String desc;
	private String contact;

	public Flat(String region, int area, int roomNo, double rent, String contact) {
		this.region = region;
		this.area = area;
		this.roomNo = roomNo;
		this.rent = rent;
		this.contact = contact;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + area;
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rent);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + roomNo;
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
		Flat other = (Flat) obj;
		if (area != other.area)
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (Double.doubleToLongBits(rent) != Double
				.doubleToLongBits(other.rent))
			return false;
		if (roomNo != other.roomNo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lakás " + area + "m2 alapterülettel, " + rent + "/hó\n\t" + roomNo
				+ " szoba\n\t" + "Régió: " + region + "\n\t"
				+ (desc != null ? desc : "Nincs leírás.") + "\n\t" + contact;
	}
}
