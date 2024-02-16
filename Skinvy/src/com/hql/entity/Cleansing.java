package com.hql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cleansing")
public class Cleansing {
	
	@Id
	@Column(name="id")
	private String id_c;
	
	@Column(name="name")
	private String name_c;
	
	@Column(name="brand")
	private String brand_c;
	
	@Column(name="price")
	private int price_c;
	
	public Cleansing() {}

	public Cleansing(String id_c, String name_c, String brand_c, int price_c) {
		this.id_c = id_c;
		this.name_c = name_c;
		this.brand_c = brand_c;
		this.price_c = price_c;
	}

	public String getId_c() {
		return id_c;
	}

	public void setId_c(String id_c) {
		this.id_c = id_c;
	}

	public String getName_c() {
		return name_c;
	}

	public void setName_c(String name_c) {
		this.name_c = name_c;
	}

	public String getBrand_c() {
		return brand_c;
	}

	public void setBrand_c(String brand_c) {
		this.brand_c = brand_c;
	}

	public int getPrice_c() {
		return price_c;
	}

	public void setPrice_c(int price_c) {
		this.price_c = price_c;
	}

	@Override
	public String toString() {
		return "Cleansing [id_c=" + id_c + ", name_c=" + name_c + ", brand_c=" + brand_c + ", price_c=" + price_c + "]";
	}
	
}
