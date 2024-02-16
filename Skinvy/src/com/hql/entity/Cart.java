package com.hql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cart")
public class Cart {

	@Id
	
	@Column(name="id")
	private String id_cart;
	
	@Column(name="name")
	private String name_cart;
	
	@Column(name="brand")
	private String brand_cart;
	
	@Column(name="price")
	private int price_cart;
	
	public Cart() {}

	public Cart(String id_cart, String name_cart, String brand_cart, int price_cart) {
		this.id_cart = id_cart;
		this.name_cart = name_cart;
		this.brand_cart = brand_cart;
		this.price_cart = price_cart;
	}

	public String getId_cart() {
		return id_cart;
	}

	public void setId_cart(String id_cart) {
		this.id_cart = id_cart;
	}

	public String getName_cart() {
		return name_cart;
	}

	public void setName_cart(String name_cart) {
		this.name_cart = name_cart;
	}

	public String getBrand_cart() {
		return brand_cart;
	}

	public void setBrand_cart(String brand_cart) {
		this.brand_cart = brand_cart;
	}

	public int getPrice_cart() {
		return price_cart;
	}

	public void setPrice_cart(int price_cart) {
		this.price_cart = price_cart;
	}

	@Override
	public String toString() {
		return "Cart [id_cart=" + id_cart + ", name_cart=" + name_cart + ", brand_cart=" + brand_cart + ", price_cart="
				+ price_cart + "]";
	}

}