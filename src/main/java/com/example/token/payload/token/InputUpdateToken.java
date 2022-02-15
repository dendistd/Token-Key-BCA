package com.example.token.payload.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputUpdateToken {
	
	@JsonProperty("serial_number")
	private String serialNumber;
	
	@JsonProperty("cabang")
	private String cabang;
	
	@JsonProperty("status")
	private String status;

	public InputUpdateToken() {
		super();
	}

	public InputUpdateToken(String serialNumber, String cabang, String status) {
		super();
		this.serialNumber = serialNumber;
		this.cabang = cabang;
		this.status = status;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	

	public String getCabang() {
		return cabang;
	}

	public void setCabang(String cabang) {
		this.cabang = cabang;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "InputUpdateTokenCabang [serialNumber=" + serialNumber + ", cabang=" + cabang + ", status=" + status
				+ "]";
	}

}
