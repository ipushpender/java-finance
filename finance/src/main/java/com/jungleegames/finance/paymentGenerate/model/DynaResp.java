package com.jungleegames.finance.paymentGenerate.model;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class DynaResp {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DynaResp [name=" + name + "]";
	}

}
