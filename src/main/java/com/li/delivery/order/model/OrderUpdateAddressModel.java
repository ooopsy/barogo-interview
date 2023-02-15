package com.li.delivery.order.model;

import javax.validation.constraints.NotBlank;


public class OrderUpdateAddressModel {

	@NotBlank
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
