package com.example.token.payload.koneksi;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputUpdateKoneksi {
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("serial_number")
	private String serialNumber;
	
//	@JsonProperty("user_id")
//	private String userId;
	
	@JsonProperty("status")
	private String status;
	
	public InputUpdateKoneksi() {
	super();
	}

	public InputUpdateKoneksi(String id, String serialNumber, String status) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.status = status;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return "InputUpdateKoneksi [id=" + id + ", serialNumber=" + serialNumber + ", status="
				+ status + "]";
	}
	

}
