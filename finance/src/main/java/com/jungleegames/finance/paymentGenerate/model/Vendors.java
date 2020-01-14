package com.jungleegames.finance.paymentGenerate.model;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document("vendors")
public class Vendors {

	@NotNull
	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId vendorid = new ObjectId();
	@NotNull
	private String name;
	@NotNull
	private String account_name;
	@NotNull
	private String bank;
	@NotNull
	private String account_no;
	@NotNull
	private String location;
	@NotNull
	private String ifsc;

	public ObjectId getVendorid() {
		return vendorid;
	}

	public void setVendorid(ObjectId vendorid) {
		this.vendorid = vendorid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	
	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	@Override
	public String toString() {
		return "Employees [vendorid=" + vendorid + ", name=" + name + ", account_name=" + account_name + ", bank="
				+ bank + ", account_no=" + account_no + ", location=" + location + ", ifsc=" + ifsc + "]";
	}

}
