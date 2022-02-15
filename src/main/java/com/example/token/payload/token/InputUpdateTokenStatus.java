package com.example.token.payload.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputUpdateTokenStatus {
	
	@JsonProperty("serial_number")
	private String serialNumber;
	
	@JsonProperty("status")
	private String status;

	public InputUpdateTokenStatus() {
		super();
	}

	public InputUpdateTokenStatus(String serialNumber, String status) {
		super();
		this.serialNumber = serialNumber;
		this.status = status;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "InputUpdateTokenStatus [serialNumber=" + serialNumber + ", status=" + status + "]";
	}
	
	

}
