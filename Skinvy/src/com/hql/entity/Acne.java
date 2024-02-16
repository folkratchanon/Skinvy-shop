package com.hql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="acne")
public class Acne {

	@Id
	@Column(name="id")
	private String id_a;
	
	@Column(name="name")
	private String name_a;
	
	@Column(name="brand")
	private String brand_a;
	
	@Column(name="price")
	private int price_a;
	
	public Acne() {}

	public Acne(String id_a, String name_a, String brand_a, int price_a) {
		this.id_a = id_a;
		this.name_a = name_a;
		this.brand_a = brand_a;
		this.price_a = price_a;
	}

	public String getId_a() {
		return id_a;
	}

	public void setId_a(String id_a) {
		this.id_a = id_a;
	}

	public String getName_a() {
		return name_a;
	}

	public void setName_a(String name_a) {
		this.name_a = name_a;
	}

	public String getBrand_a() {
		return brand_a;
	}

	public void setBrand_a(String brand_a) {
		this.brand_a = brand_a;
	}

	public int getPrice_a() {
		return price_a;
	}

	public void setPrice_a(int price_a) {
		this.price_a = price_a;
	}

	@Override
	public String toString() {
		return "Acne [id_a=" + id_a + ", name_a=" + name_a + ", brand_a=" + brand_a + ", price_a=" + price_a + "]";
	}
	
}
