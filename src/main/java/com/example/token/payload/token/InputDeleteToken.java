package com.example.token.payload.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputDeleteToken {
	@JsonProperty("serial_number")
	private String serialNumber;

	public InputDeleteToken() {
		super();
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public String toString() {
		return "InputDeleteToken [serialNumber=" + serialNumber + "]";
	}
	
	

}
