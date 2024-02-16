package com.hql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="invoice")
public class Invoice {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	@Column(name="id")
	private int id_i;
	
	@Column(name="price")
	private int price_i;
	
	public Invoice() {}

	public Invoice(int price_i) {
		this.price_i = price_i;
	}

	public int getId_i() {
		return id_i;
	}

	public void setId_i(int id_i) {
		this.id_i = id_i;
	}

	public int getPrice_i() {
		return price_i;
	}

	public void setPrice_i(int price_i) {
		this.price_i = price_i;
	}

	@Override
	public String toString() {
		return "Invoice [id_i=" + id_i + ", price_i=" + price_i + "]";
	}
	
}
