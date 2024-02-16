package com.hql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="member")
public class Member {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	@Column(name="id")
	private int id_m;
	
	@Column(name="firstname")
	private String fname_m;
	
	@Column(name="lastname")
	private String lname_m;
	
	@Column(name="telephone")
	private String phone_m;
	
	@Column(name="email")
	private String email_m;
	
	@Column(name="password")
	private String password_m;
	
	public Member() {}

	public Member(String fname_m, String lname_m, String phone_m, String email_m, String password_m) {
		this.fname_m = fname_m;
		this.lname_m = lname_m;
		this.phone_m = phone_m;
		this.email_m = email_m;
		this.password_m = password_m;
	}

	public int getId_m() {
		return id_m;
	}

	public void setId_m(int id_m) {
		this.id_m = id_m;
	}

	public String getFname_m() {
		return fname_m;
	}

	public void setFname_m(String fname_m) {
		this.fname_m = fname_m;
	}

	public String getLname_m() {
		return lname_m;
	}

	public void setLname_m(String lname_m) {
		this.lname_m = lname_m;
	}

	public String getPhone_m() {
		return phone_m;
	}

	public void setPhone_m(String phone_m) {
		this.phone_m = phone_m;
	}

	public String getEmail_m() {
		return email_m;
	}

	public void setEmail_m(String email_m) {
		this.email_m = email_m;
	}

	public String getPassword_m() {
		return password_m;
	}

	public void setPassword_m(String password_m) {
		this.password_m = password_m;
	}

	@Override
	public String toString() {
		return "Member [id_m=" + id_m + ", fname_m=" + fname_m + ", lname_m=" + lname_m + ", phone_m=" + phone_m
				+ ", email_m=" + email_m + ", password_m=" + password_m + "]";
	}
	
}
